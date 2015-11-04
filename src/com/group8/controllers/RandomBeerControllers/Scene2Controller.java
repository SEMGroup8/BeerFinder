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

public class Scene2Controller {

    static double percentagePicked = 18;

    @FXML
    private Button continueButton2;
    @FXML
    private Slider percentageSlider;
    @FXML
    private CheckBox yesCheckbox;
    @FXML
    private CheckBox noCheckbox;


    @FXML
        // Clicking button "Continue"
    void onContinueClick2(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) continueButton2.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/RandomBeerScenes/scene3.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("BeerFinder Alpha Test");
        stage.setScene(scene);
        stage.show();

        // Slider value setter
        if (noCheckbox.isSelected() || (!noCheckbox.isSelected() && !yesCheckbox.isSelected())){
            percentagePicked = 18;
        }
        else {
            percentagePicked = percentageSlider.getValue();
        }
        System.out.println("Percentage picked:" + percentagePicked);

    }

    @FXML
        //Checking box "Yes"
    void yesSelected2(ActionEvent event) {
        if (yesCheckbox.isSelected()) {
            noCheckbox.setVisible(false);
            noCheckbox.setSelected(false);
            percentageSlider.setVisible(true);
        }
        if (!yesCheckbox.isSelected()) {
            noCheckbox.setVisible(true);
            percentageSlider.setVisible(false);
        }
    }

    public double getPercentagePicked(){
        return percentagePicked;
    }


}

