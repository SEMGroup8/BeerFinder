package com.group8.controllers.RandomBeerControllers;

import com.group8.controllers.BaseController;
import com.group8.singletons.BeerData;
import com.group8.singletons.UserData;
import com.group8.database.MysqlDriver;
import com.group8.database.tables.Beer;
import com.group8.database.tables.BeerRank;
import com.group8.database.tables.RandomBeerQuery;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;


public class Scene4Controller extends BaseController implements Initializable {

    @FXML
    public ProgressIndicator Load;
    Service<Void> backgroundThread;
    private int count;
    private Beer randomBeer;
    private int[] arrayID;
    private RandomBeerQuery randomBeerQuery;
    private ArrayList<ArrayList<Object>> list;
    @FXML
    private Button anotherButton;
    @FXML
    private Label typeR;
    @FXML
    private Label percentageR;
    @FXML
    private ImageView imageView;
    @FXML
    private HBox HBoxButtons;
    @FXML
    private VBox VBoxInfo;
    @FXML
    private HBox rankFavourite;
    @FXML
    private Label packageR;
    @FXML
    private Button repeatButton;
    @FXML
    private Label producerR;
    @FXML
    private Label originR;
    @FXML
    private ImageView countryFlagR;
    @FXML
    private Label nameR;
    @FXML
    private Label priceR;
    @FXML
    private Text textLine;
    @FXML
    private Label countLabel;
    @FXML
    private HBox imageViewHBox;

    /**
     * Created by Mantas Namgaudis
     *
     * Showing the result of a random beer generator according to the user
     * preferences. User may repeat all the steps again or shuffle through
     * already given results. Also if logged in user may rank or add beer
     * to favourites.
     */

    @FXML
        // Showing another random result from already chosen parameters
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
        if (randomBeer.getImage() != null) {
            this.imageView.setImage(randomBeer.getImage());
        } else {
            this.imageView.setImage(new Image(new File("src/com/group8/resources/Images/beerHasNoImage.png").toURI().toString()));
        }
        this.countryFlagR.setImage(randomBeer.getCountryFlag());

    }


    @FXML
        // Repeat the RandomBeer program from start
    void onRepeatClick(ActionEvent event) throws Exception {

        mainScene.changeCenter("/com/group8/resources/views/RandomBeerScenes/scene1.fxml");
    }


    /**
     * Return one random result from array of ints.
     *
     * @param array
     * @return int
     */
    public int generateRandom(int[] array) {
        int result = 0;
        if (array.length > 0) {
            int random = (int) Math.floor(Math.random() * array.length);
            result = array[random];

            ObservableList<String> test = FXCollections.observableArrayList();
        }
        return result;
    }

    @FXML
    public void addToFavourite(ActionEvent event) throws IOException {
        if (UserData.userInstance != null) {
            String sqlQuery = "insert into beerFavourites values(" + BeerData.selectedBeer.getId() + ", " + UserData.userInstance.getId() + ");";

            //System.out.println(sqlQuery);

            MysqlDriver.insert(sqlQuery);

            UserData.userInstance.getFavouriteBeers();
        }
    }

    public void rankStar(int number) {
        if (UserData.userInstance != null) {
            BeerRank beer = new BeerRank(UserData.userInstance.getId(), BeerData.selectedBeer.getId(), number);

            beer.insertRank();

            UserData.userInstance.getFavouriteBeers();
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        Load.setStyle("-fx-accent: IVORY");
                        Load.setVisible(true);

                        // Getting query with user choices
                        randomBeerQuery = new RandomBeerQuery();

                        // Getting the results from database with query
                        list = MysqlDriver.selectMany(randomBeerQuery.resultQuery());
                        arrayID = new int[list.size()];

                        // Making the array of beer IDs from query results
                        for (int i = 0; i < list.size(); i++) {
                            Beer beer = new Beer(list.get(i));
                            // Counting results
                            count++;
                            arrayID[i] = beer.getId();
                        }

                        return null;
                    }
                };
            }
        };

        backgroundThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {

                Load.setVisible(false);

                countLabel.setText("Beers found: " + count);

                if (arrayID.length == 0) {
                    textLine.setVisible(true);
                    HBoxButtons.setVisible(true);
                    anotherButton.setVisible(false);
                    rankFavourite.setVisible(false);
                    countLabel.setVisible(false);
                    return;
                } else {

                    // Getting one beer entry chosen randomly from the list of IDs
                    ArrayList<ArrayList<Object>> list2 = MysqlDriver.selectMany(randomBeerQuery.randomQuery(generateRandom(arrayID)));

                    Beer randomBeer = new Beer(list2.get(0));

                    // Populating beer info into scene
                    typeR.setText(randomBeer.getType());
                    originR.setText(randomBeer.getOrigin());
                    percentageR.setText("" + randomBeer.getPercentage() + " %");
                    nameR.setText(randomBeer.getName());
                    packageR.setText(randomBeer.getBeerPackage());
                    producerR.setText(randomBeer.getProducer());
                    priceR.setText("" + randomBeer.getPrice() + " kr");
                    if (randomBeer.getImage() != null) {
                        imageView.setImage(randomBeer.getImage());
                    } else {
                        imageView.setImage(new Image(new File("src/com/group8/resources/Images/beerHasNoImage.png").toURI().toString()));
                    }
                    countryFlagR.setImage(randomBeer.getCountryFlag());

                    // Changing visibility of elements on scene

                    if (UserData.userInstance == null) {
                        rankFavourite.setVisible(false);
                    } else {
                        rankFavourite.setVisible(true);
                    }

                    VBoxInfo.setVisible(true);
                    HBoxButtons.setVisible(true);
                    imageViewHBox.setVisible(true);
                    countLabel.setVisible(true);
                    imageViewHBox.setBorder(new Border(new BorderStroke(Paint.valueOf("#2A1806"), BorderStrokeStyle.SOLID, new CornerRadii(2), new BorderWidths(4))));
                }

            }
        });

        backgroundThread.start();
    }
}
