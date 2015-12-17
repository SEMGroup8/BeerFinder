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
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.controlsfx.control.RangeSlider;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Scene1Controller extends BaseController implements Initializable {

    static double pricePickedLow = 0;
    static double pricePickedHigh = 500;


    private RangeSlider priceSlider = new RangeSlider(0, 500, 0, 500);


    @FXML
    private Button continueButton1;
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
    void onContinueClick1(ActionEvent event) throws IOException {

        mainScene.changeCenter("/com/group8/resources/views/RandomBeerScenes/scene2.fxml");

        // Slider value setter
        if (noCheckbox.isSelected() || (!noCheckbox.isSelected() && !yesCheckbox.isSelected())){
            pricePickedLow = 0;
            pricePickedHigh = 500;
        }
        else {
            pricePickedLow = (int) priceSlider.getLowValue();
            pricePickedHigh = (int) priceSlider.getHighValue();
        }
        System.out.println("Price picked:" + pricePickedLow + " and " + pricePickedHigh);

    }

    @FXML //Checking box "Yes"
    void yesSelected1(ActionEvent event) {
        if (yesCheckbox.isSelected()) {
            noCheckbox.setVisible(false);
            noCheckbox.setSelected(false);
            priceSlider.setVisible(true);
            showLowLable.setVisible(true);
            showHighLable.setVisible(true);
        }
        if (!yesCheckbox.isSelected()) {
            noCheckbox.setVisible(true);
            priceSlider.setVisible(false);
            showHighLable.setVisible(false);
            showLowLable.setVisible(false);
        }
    }


// Getters
    public double getPricePickedLow(){
        return pricePickedLow;
    }
    public double getPricePickedHigh(){
        return pricePickedHigh;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        pane.getChildren().add(priceSlider);
        priceSlider.setBlockIncrement(100);
        priceSlider.setLayoutX(95);
        priceSlider.setLayoutY(220);
        priceSlider.setPrefHeight(32);
        priceSlider.setPrefWidth(500);
        priceSlider.getStylesheets().add("/com/group8/resources/css/RangeSlider.css");
        priceSlider.setVisible(false);

        priceSlider.lowValueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue == null){
                    showLowLable.setText("0");
                    return;
                }
                showLowLable.setText(String.valueOf(Math.round(newValue.intValue())));
            }
        });

        priceSlider.highValueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue == null){
                    showHighLable.setText("500");
                    return;
                }
                showHighLable.setText(String.valueOf(Math.round(newValue.intValue())));
            }
        });

    }
}