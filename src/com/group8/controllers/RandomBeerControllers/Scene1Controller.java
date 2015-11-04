package com.group8.controllers.RandomBeerControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.stage.Stage;

import java.io.IOException;

public class Scene1Controller {

    static double pricePicked = 500;


    @FXML
    private Button continueButton1;
    @FXML
    private Slider priceSlider;
    @FXML
    private CheckBox yesCheckbox;
    @FXML
    private CheckBox noCheckbox;


    @FXML
        // Clicking button "Continue"
    void onContinueClick1(ActionEvent event) throws IOException {

        Stage stage = (Stage) continueButton1.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/RandomBeerScenes/scene2.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("BeerFinder Alpha Test");
        stage.setScene(scene);
        stage.show();

        // Slider value setter
        if (noCheckbox.isSelected() || (!noCheckbox.isSelected() && !yesCheckbox.isSelected())){
            pricePicked = 500;
        }
        else {
            pricePicked = (int) priceSlider.getValue();
        }
        System.out.println("Price picked:" + pricePicked);

    }

    @FXML //Checking box "Yes"
    void yesSelected1(ActionEvent event) {
        if (yesCheckbox.isSelected()) {
            noCheckbox.setVisible(false);
            noCheckbox.setSelected(false);
            priceSlider.setVisible(true);
        }
        if (!yesCheckbox.isSelected()) {
            noCheckbox.setVisible(true);
            priceSlider.setVisible(false);
        }
    }


    public double getPricePicked(){
        return pricePicked;
    }



}