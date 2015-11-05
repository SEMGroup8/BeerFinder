package com.group8.controllers.RandomBeerControllers;

import com.group8.controllers.BeerData;
import com.group8.controllers.Navigation;
import com.group8.controllers.UserData;
import com.group8.database.MysqlDriver;
import com.group8.database.tables.Beer;
import com.group8.database.tables.BeerRank;
import com.group8.database.tables.RandomBeerQuery;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class Scene4Controller {

   private int count;
   private Beer randomBeer;
   private int[] arrayID;
   private RandomBeerQuery randomBeerQuery;


    @FXML
    private Button anotherButton;
    @FXML
    private Label typeR;
    @FXML
    private Button checkButton;
    @FXML
    private Label beerFound;
    @FXML
    private Label percentageR;
    @FXML
    private ImageView imageView;
    @FXML
    private HBox HBoxButtons;
    @FXML
    private VBox VBoxInfo;
    @FXML
    private HBox HBoxFound, rankFavourite;
    @FXML
    private Button homeButton;
    @FXML
    private Label packageR;
    @FXML
    private Button repeatButton;
    @FXML
    private Label producerR;
    @FXML
    private Label originR;
    @FXML
    private Label nameR;
    @FXML
    private Label priceR;
    @FXML
    private Text textLine;

    @FXML // Going back to home screen
    void onHomeClick(ActionEvent event) throws Exception {

        Stage stage = (Stage) homeButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(Navigation.homescreenFXML));
        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("BeerFinder Alpha Test");
        stage.setScene(scene);
        stage.show();

    }

    @FXML // Showing another random result from already chosen parameters
    void onAnotherClick(ActionEvent event) {

        // Getting one beer entry chosen randomly from the list of IDs
        ArrayList<ArrayList<Object>> list2 = MysqlDriver.selectMany(this.randomBeerQuery.randomQuery(generateRandom(this.arrayID)));

        Beer randomBeer = new Beer(list2.get(0));
        this.randomBeer = randomBeer;

        // Populating beer info into scene
        this.typeR.setText(randomBeer.getType());
        this.originR.setText(randomBeer.getOrigin());
        this.percentageR.setText("" + randomBeer.getPercentage());
        this.nameR.setText(randomBeer.getName());
        this.packageR.setText(randomBeer.getBeerPackage());
        this.producerR.setText(randomBeer.getProducer());
        this.priceR.setText("" + randomBeer.getPrice() + " kr");

    }


    @FXML // Repeat the RandomBeer program from start
    void onRepeatClick(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;
        stage = (Stage) repeatButton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/RandomBeerScenes/scene1.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("BeerFinder Alpha Test");
        stage.setScene(scene);
        stage.show();

    }


    @FXML
    void onCheckButtonClick(ActionEvent event) {

        // Getting query with user choices
        RandomBeerQuery randomBeerQuery = new RandomBeerQuery();
        this.randomBeerQuery = randomBeerQuery;

        // Getting the results from database with query
        ArrayList<ArrayList<Object>> list = MysqlDriver.selectMany(this.randomBeerQuery.resultQuery());
        this.arrayID = new int[list.size()];

        // Making the array of beer IDs from query results
        for (int i = 0; i < list.size(); i++) {
            Beer beer = new Beer(list.get(i));
            // Counting results
            count++;
            this.arrayID[i] = beer.getId();
        }

        if (this.arrayID.length == 0){
            checkButton.setVisible(false);
            textLine.setText("No beers found :|");
            HBoxButtons.setVisible(true);
            anotherButton.setVisible(false);
            return;
        }
        else {

            // Getting one beer entry chosen randomly from the list of IDs
            ArrayList<ArrayList<Object>> list2 = MysqlDriver.selectMany(randomBeerQuery.randomQuery(generateRandom(this.arrayID)));


            Beer randomBeer = new Beer(list2.get(0));
            BeerData.selectedBeer = randomBeer;

            this.randomBeer = randomBeer;

            // Populating beer info into scene
            this.typeR.setText(randomBeer.getType());
            this.originR.setText(randomBeer.getOrigin());
            this.percentageR.setText("" + randomBeer.getPercentage());
            this.nameR.setText(randomBeer.getName());
            this.packageR.setText(randomBeer.getBeerPackage());
            this.producerR.setText(randomBeer.getProducer());
            this.priceR.setText("" + randomBeer.getPrice() + " kr");
            this.beerFound.setText("" + count);

            // Changing visibility of elements on scene
            VBoxInfo.setVisible(true);
            HBoxButtons.setVisible(true);
            rankFavourite.setVisible(true);
            HBoxFound.setVisible(true);
            imageView.setVisible(true);
            checkButton.setVisible(false);
        }
    }
    // Process the array of integers, returns one random integer
    public int generateRandom(int[] array) {
        int result = 0;
        if (array.length > 0) {
            int random = (int) Math.floor(Math.random() * array.length);
            result = array[random];

            System.out.println("Array length: " + array.length);
            System.out.println("Random number: " + random);
            System.out.println("Random result: " + array[random]);

        } else {
            System.out.println("Array is empty");
        }
        return result;
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
        }
    }

    public void rankStar(int number){
        if(UserData.userInstance!=null) {
            BeerRank beer = new BeerRank(UserData.userInstance.get_id(), BeerData.selectedBeer.getId(), number);

            beer.insertRank();

            UserData.userInstance.getFavourites();
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
}
