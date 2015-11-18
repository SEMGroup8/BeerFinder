package com.group8.controllers.RandomBeerControllers;

import com.group8.controllers.Navigation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.control.RangeSlider;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Scene2Controller implements Initializable {

    private RangeSlider percentageSlider = new RangeSlider(0, 20, 0, 20);

    static double percentagePickedLow = 0;
    static double percentagePickedHigh = 20;

    @FXML
    private Button continueButton2;
    @FXML
    private CheckBox yesCheckbox;
    @FXML
    private CheckBox noCheckbox;
    @FXML
    private Button homeButton;
    @FXML
    private Pane pane;


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
            percentagePickedLow = 0;
            percentagePickedHigh = 20;
        }
        else {
            percentagePickedLow = percentageSlider.getLowValue();
            percentagePickedHigh = percentageSlider.getHighValue();
        }
        System.out.println("Percentage picked:" + percentagePickedLow + " and " + percentagePickedHigh);

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

    @FXML // Going back to home screen
    void onHomeClick(ActionEvent event) throws Exception {

        Stage stage = (Stage) homeButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(Navigation.homescreenFXML));
        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("BeerFinder Alpha Test");
        stage.setScene(scene);
        stage.show();

    }
// Getters
    public double getPercentagePickedLow(){
        return percentagePickedLow;
    }

    public double getPercentagePickedHigh(){
        return percentagePickedHigh;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pane.getChildren().add(percentageSlider);
        percentageSlider.setShowTickMarks(true);
        percentageSlider.setShowTickLabels(true);
        percentageSlider.setBlockIncrement(1);
        percentageSlider.majorTickUnitProperty().set(1);
        percentageSlider.setLayoutX(85);
        percentageSlider.setLayoutY(320);
        percentageSlider.setPrefHeight(32);
        percentageSlider.setPrefWidth(500);
        percentageSlider.setVisible(false);
    }
}

