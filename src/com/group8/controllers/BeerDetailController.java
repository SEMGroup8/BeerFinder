package com.group8.controllers;

import com.group8.database.tables.Beer;
import com.group8.database.tables.BeerRank;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by AnkanX on 15-10-27.
 * TODO fix nice detail layout and images
 */
public class BeerDetailController implements Initializable{

    @FXML
    public Button back;
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
    public ImageView showImage;
    @FXML
    public Button oneStar, twoStar, threeStar, fourStar, fiveStar;
    @FXML
    public Label rankShow;


    /**
     * Back button pressed takes you back to "result screen"
     * @param event
     * @throws IOException
     */
    @FXML
    public void backAction(ActionEvent event) throws IOException {
        Parent homescreen = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/resultScreen.fxml"));
        Scene result_scene = new Scene(homescreen, 800, 600);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();
    }
    public void rankStar(int number){
    	BeerRank beer = new BeerRank(UserData.userInstance.get_id(), Beer.selectedBeer.getId(), number);
    	
    	beer.insertRank();
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
    
    /**
     * Home Button
     * @param event
     * @throws IOException
     */
    @FXML
    public void returnHome(ActionEvent event) throws IOException {
        Parent homescreen = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/homeScreen.fxml"));
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


        // test output
        System.out.println("beerDetails accsessed and initializeing!");
        // Display Name of beer
        showBeerName.setText(Beer.selectedBeer.getName());
        // Display Origin
        showOrigin.setText(Beer.selectedBeer.getOrigin());
        // Display beer Type
        showBeerType.setText(Beer.selectedBeer.getType());
        // Display beer Description
        showDescription.setText(Beer.selectedBeer.getDescription());
       // Display Rank of beer
        rankShow.setText(""+Beer.selectedBeer.getAvRank());
        // Display if beer is tap
        if (Beer.selectedBeer.getIsTap().toString().equals("false")) {

            showTap.setText("This beer is not on Tap");
        } else {

            showTap.setText("This beer is on Tap");
        }
        // Try loading the image, if there is none will use placeholder
        if (Beer.selectedBeer.getImage() == null) {
            System.out.println("No image! Will use Placeholder Image!");
        } else{
            showImage.setImage(Beer.selectedBeer.getImage());
        }
        // Display beer volume
        showVolume.setText("" + Beer.selectedBeer.getVolume() + " ml");
        // Display beer percentage
        showPercentage.setText(""+Beer.selectedBeer.getPercentage()+"%");
        // Display beer package
        showPackage.setText(Beer.selectedBeer.getBeerPackage());
        // Display the beer producer
        showProducer.setText(Beer.selectedBeer.getProducer());


        // Test the data in our beer instance
        System.out.println(Beer.selectedBeer.toString());



    }



}
