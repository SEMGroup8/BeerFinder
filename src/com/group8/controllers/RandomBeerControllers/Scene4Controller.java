package com.group8.controllers.RandomBeerControllers;

import com.group8.database.MysqlDriver;
import com.group8.database.tables.Beer;
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
import javafx.stage.Stage;

import java.util.ArrayList;

public class Scene4Controller {

   private int count;
   private Beer randomBeer;
   private int[] arrayID;
   private RandomBeerQuery randomBeerQuery;


    @FXML
    private Button anotherButton11;
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
    private HBox HBoxFound;
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
    void onHomeClick(ActionEvent event) {

    }

    @FXML
    void onAnotherClick(ActionEvent event) {

        ArrayList<ArrayList<Object>> list2 = MysqlDriver.selectMany(this.randomBeerQuery.randomQuery(generateRandom(this.arrayID)));

        Beer randomBeer = new Beer(list2.get(0));
        this.randomBeer = randomBeer;

        this.typeR.setText(randomBeer.getType());
        this.originR.setText(randomBeer.getOrigin());
        this.percentageR.setText("" + randomBeer.getPercentage());
        this.nameR.setText(randomBeer.getName());
        this.packageR.setText(randomBeer.getBeerPackage());
        this.producerR.setText(randomBeer.getProducer());
        this.priceR.setText("" + randomBeer.getPrice());

    }


    @FXML
    void onRepeatClick(ActionEvent event) throws Exception {
        Stage stage;
        Parent root;
        stage = (Stage) repeatButton.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/RandomBeerScenes/scene1.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }


    @FXML
    void onCheckButtonClick(ActionEvent event) {

        RandomBeerQuery randomBeerQuery = new RandomBeerQuery();
        this.randomBeerQuery = randomBeerQuery;

        ArrayList<ArrayList<Object>> list = MysqlDriver.selectMany(this.randomBeerQuery.resultQuery());
        this.arrayID = new int[list.size()];

        for (int i = 0; i < list.size(); i++) {
            Beer beer = new Beer(list.get(i));
            count++;
            this.arrayID[i] = beer.getId();

        }

//        System.out.println(arrayID[0]);
//        System.out.println("Count: " + count + " Array: " + arrayID.length);

        ArrayList<ArrayList<Object>> list2 = MysqlDriver.selectMany(randomBeerQuery.randomQuery(generateRandom(this.arrayID)));

        Beer randomBeer = new Beer(list2.get(0));
        this.randomBeer = randomBeer;

        this.typeR.setText(randomBeer.getType());
        this.originR.setText(randomBeer.getOrigin());
        this.percentageR.setText("" + randomBeer.getPercentage());
        this.nameR.setText(randomBeer.getName());
        this.packageR.setText(randomBeer.getBeerPackage());
        this.producerR.setText(randomBeer.getProducer());
        this.priceR.setText("" + randomBeer.getPrice());
        this.beerFound.setText("" + count);

        VBoxInfo.setVisible(true);
        HBoxButtons.setVisible(true);
        HBoxFound.setVisible(true);
        imageView.setVisible(true);
        checkButton.setVisible(false);

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
}
