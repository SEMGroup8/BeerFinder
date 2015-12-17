package com.group8.controllers.RandomBeerControllers;

import com.group8.controllers.BaseController;
import com.group8.controllers.Navigation;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.control.RangeSlider;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Scene2Controller extends BaseController implements Initializable {

    private RangeSlider percentageSlider = new RangeSlider(0, 15, 0, 15);

    static double percentagePickedLow = 0;
    static double percentagePickedHigh = 20;

    @FXML
    private Button continueButton2;
    @FXML
    private CheckBox yesCheckbox;
    @FXML
    private CheckBox noCheckbox;
    @FXML
    private Pane pane;
    @FXML
    private Label showLowLable;
    @FXML
    private Label showHighLable;


    @FXML
        // Clicking button "Continue"
    void onContinueClick2(ActionEvent event) throws IOException {

        mainScene.changeCenter("/com/group8/resources/views/RandomBeerScenes/scene3.fxml");


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
            showLowLable.setVisible(true);
            showHighLable.setVisible(true);
        }
        if (!yesCheckbox.isSelected()) {
            noCheckbox.setVisible(true);
            percentageSlider.setVisible(false);
            showHighLable.setVisible(false);
            showLowLable.setVisible(false);
        }
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
        percentageSlider.setBlockIncrement(1);
        percentageSlider.setLayoutX(95);
        percentageSlider.setLayoutY(220);
        percentageSlider.setPrefHeight(32);
        percentageSlider.setPrefWidth(500);
        percentageSlider.getStylesheets().add("/com/group8/resources/css/RangeSlider.css");
        percentageSlider.setVisible(false);

        percentageSlider.lowValueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue == null){
                    showLowLable.setText("0");
                    return;
                }
                showLowLable.setText(String.valueOf(Math.round(newValue.intValue())));
            }
        });

        percentageSlider.highValueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue == null){
                    showHighLable.setText("20");
                    return;
                }
                showHighLable.setText(String.valueOf(Math.round(newValue.intValue())));
            }
        });
    }
}

