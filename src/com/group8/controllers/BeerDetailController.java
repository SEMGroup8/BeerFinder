package com.group8.controllers;

import com.group8.database.MysqlDriver;
import com.group8.database.tables.Beer;
import com.group8.database.tables.BeerRank;

import com.group8.database.tables.MapMarker;
import com.group8.database.tables.User;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sun.plugin.javascript.JSObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by AnkanX on 15-10-27.
 * TODO fix nice detail layout and images
 */
public class BeerDetailController implements Initializable{


    @FXML
    public Button googleMaps;
    @FXML
    public Label gMapsError;
    @FXML
    public Button back, favourite, addToPub;
    @FXML
    public Button newSearch;
    @FXML
    public Label showBeerName;
    @FXML
    public Label showOrigin;
    @FXML
    public Label showBeerType;
    @FXML
    public Label showPercentage;
    @FXML
    public TextArea showDescription;
    @FXML
    public Label showVolume;
    @FXML
    public Label showTap;
    @FXML
    public Label showPackage;
    @FXML
    public Label showProducer;
    @FXML
    public Label showPrice;
    @FXML
    public ImageView showImage;
    @FXML
    public Button oneStar, twoStar, threeStar, fourStar, fiveStar;
    @FXML
    public Label rankShow;
    @FXML
    public Label added;

    /**
     * Back button pressed takes you back to "result screen"
     * @param event
     * @throws IOException
     */
    @FXML
    public void backAction(ActionEvent event) throws IOException {

        if (!Navigation.backFXML.equals("/com/group8/resources/views/favourites.fxml")) {
            // Update the beer list for changes
            BeerData.beer = new ArrayList<Beer>();
            ArrayList<ArrayList<Object>> sqlData;
            System.out.println(BeerData.searchInput);
            sqlData = MysqlDriver.selectMany(BeerData.searchInput);

            for (int i = 0; i < sqlData.size(); i++) {
                // Add a new Beer to the beer arraylist
                Beer beer = new Beer(sqlData.get(i));
                // Testoutput
                //System.out.print(beer.getName()+"\n");
                BeerData.beer.add(beer);
            }
        }
        Parent homescreen = FXMLLoader.load(getClass().getResource(Navigation.backFXML));
        Scene result_scene = new Scene(homescreen, 800, 600);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();
    }
    public void rankStar(int number){
    	if(UserData.userInstance!=null) {
            BeerRank beer = new BeerRank(UserData.userInstance.get_id(), BeerData.selectedBeer.getId(), number);

            beer.insertRank();

            UserData.userInstance.getFavourites();
        }
    }

    /**
     * Get the map scene loading the pubs that sell the beer selected
     * @param event
     * @throws IOException
     */
    @FXML
    public void getMaps(ActionEvent event) throws IOException {

        BeerData.markers = new ArrayList<MapMarker>();

        // TODO SQL query for getting Pubs that have the BeerData.selectedBeer

        // populate the tableView with those pubs

        String sqlQuery = "SELECT beerInPub.pubID, name, address, price, latitude, longitude, inStock " +
                "from pubs, pubAddress, beerInPub where " +
                "pubs.pubID = beerInPub.pubID " +
                "and pubs.addressID = pubAddress.addressID " +
                "and beerInPub.beerID = " + BeerData.selectedBeer.getId() + " " +
                "order by price asc";

        System.out.println(sqlQuery);
        // Execute user query to get markers
        ArrayList<ArrayList<Object>> sqlData;
        sqlData = MysqlDriver.selectManyOther(sqlQuery);

        for (int i = 0; i < sqlData.size(); i++) {
            // Add a new marker to the beer arraylist
            MapMarker marker = new MapMarker(sqlData.get(i));
            BeerData.markers.add(marker);
            System.out.println(marker.isInStock());

            System.out.println(marker.getPrice());
        }

        if ((BeerData.markers.size()>0)) {

            // Load the result stage
            Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/googleMaps.fxml"));
            Scene result_scene = new Scene(result,800,600);
            Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            main_stage.setScene(result_scene);
            main_stage.show();
        }else {

            System.out.println(sqlQuery);
            ArrayList<ArrayList<Object>> geoData = MysqlDriver.selectManyOther(sqlQuery);
            System.out.println(geoData.size());
            System.out.println("No Pubs selling this beer");
            gMapsError.setVisible(true);
        }
    }

    @FXML
    public void onRankOneStar(ActionEvent event) throws IOException {
        rankStar(1);
    }
    @FXML
    public void onRankTwoStar(ActionEvent event) throws IOException {
        rankStar(2);
    }
    @FXML
    public void onRankThreeStar(ActionEvent event) throws IOException {
        rankStar(3);
    }
    @FXML
    public void onRankFourStar(ActionEvent event) throws IOException {
        rankStar(4);
    }
    @FXML
    public void onRankFiveStar(ActionEvent event) throws IOException {
        rankStar(5);
    }

    @FXML
    public void addToFavourite(ActionEvent event) throws IOException
    {
        if(UserData.userInstance!=null)
        {
            String sqlQuery = "insert into favourites values(" + BeerData.selectedBeer.getId() + ", " + UserData.userInstance.get_id() + ");";

            System.out.println(sqlQuery);

            MysqlDriver.insert(sqlQuery);

            UserData.userInstance.getFavourites();

            added.setVisible(true);
        }
    }

    @FXML
    public void addToPub(ActionEvent event) throws IOException {
        if (UserData.userInstance != null)
        {
            if (UserData.userInstance.get_isPub()) {
                final Stage dialog = new Stage();
                dialog.initModality(Modality.APPLICATION_MODAL);
                dialog.initOwner(Navigation.primaryStage);
                VBox dialogVbox = new VBox(20);
                dialogVbox.setAlignment(Pos.CENTER);
                dialogVbox.getChildren().add(new Text("Add a beer to your pub!"));
                TextField price = new TextField("Type in price:");
                Button addBeerToPub = new Button("Add to pub");

                addBeerToPub.setOnAction(
                    new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {

                            String query = "Insert into beerInPub values("
                                    + UserData.userInstance.get_pubId() + ", "
                                    + BeerData.selectedBeer.getId() + ", "
                                    + Float.parseFloat(price.getText()) + ", 1)";

                            MysqlDriver.insert(query);

                            System.out.println("Inserted beer to pub");
                            dialog.close();
                        }
                    });

                dialogVbox.getChildren().add(price);
                dialogVbox.getChildren().add(addBeerToPub);

                Scene dialogScene = new Scene(dialogVbox, 300, 200);
                dialog.setScene(dialogScene);
                dialog.show();
            }
        }
    }

    /**
     * Home Button
     * @param event
     * @throws IOException
     */
    @FXML
    public void returnHome(ActionEvent event) throws IOException {
        Parent homescreen = FXMLLoader.load(getClass().getResource(Navigation.homescreenFXML));
        Scene result_scene = new Scene(homescreen, 800, 600);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();
    }

    /**
     * Initialize beerDetail controller
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Navigation.beerDetailviewFXML = "/com/group8/resources/views/beerDetailsScreen.fxml";


        if(UserData.userInstance==null)
        {
            oneStar.setVisible(false);
            twoStar.setVisible(false);
            threeStar.setVisible(false);
            fourStar.setVisible(false);
            fiveStar.setVisible(false);

            favourite.setVisible(false);
        }
        else{
            oneStar.setVisible(true);
            twoStar.setVisible(true);
            threeStar.setVisible(true);
            fourStar.setVisible(true);
            fiveStar.setVisible(true);

            favourite.setVisible(true);

            if(UserData.userInstance.get_isPub())
            {
                addToPub.setVisible(true);
            }
        }

        // test output
        System.out.println("beerDetails accsessed and initializeing!");
        // Display Name of beer
        showBeerName.setText(BeerData.selectedBeer.getName());
        // Display Origin
        showOrigin.setText(BeerData.selectedBeer.getOrigin());
        // Display beer Type
        showBeerType.setText(BeerData.selectedBeer.getType());
        // Display beer Description
        showDescription.setText(BeerData.selectedBeer.getDescription());
       // Display Rank of beer
        rankShow.setText(""+BeerData.selectedBeer.getAvRank());
        // Display if beer is tap
        if (BeerData.selectedBeer.getIsTap().toString().equals("false")) {

            showTap.setText("This beer is not on Tap");
        } else {

            showTap.setText("This beer is on Tap");
        }
        // Try loading the image, if there is none will use placeholder
        if (BeerData.selectedBeer.getImage() == null) {
            System.out.println("No image! Will use Placeholder Image!");
        } else{
            showImage.setImage(BeerData.selectedBeer.getImage());
        }
        // Display beer volume
        showVolume.setText("" + BeerData.selectedBeer.getVolume() + " ml");
        // Display beer percentage
        showPercentage.setText(""+BeerData.selectedBeer.getPercentage()+"%");
        // Display beer package
        showPackage.setText(BeerData.selectedBeer.getBeerPackage());
        // Display the beer producer
        showProducer.setText(BeerData.selectedBeer.getProducer());
        // Display beer price
        showPrice.setText(BeerData.selectedBeer.getPrice()+":-");

        // Test the data in our beer instance
        System.out.println(BeerData.selectedBeer.toString());
    }
}
