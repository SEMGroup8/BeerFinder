package com.group8.controllers.RandomBeerControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;

public class Scene3Controller {

    static String typesPicked = "(";

    @FXML
    private CheckBox OtherChecked;
    @FXML
    private CheckBox noCheckbox;
    @FXML
    private CheckBox SpecialCheck;
    @FXML
    private VBox VBoxTypes;
    @FXML
    private CheckBox AleCheck;
    @FXML
    private CheckBox WheatCheck;
    @FXML
    private CheckBox LagerCheck;
    @FXML
    private CheckBox PorterCheck;
    @FXML
    private CheckBox DarkLagerCheck;
    @FXML
    private Button continueButton3;
    @FXML
    private CheckBox yesCheckbox;
    @FXML
    private CheckBox FermentedCheck;

    @FXML // Clicking button "Continue"
    void onContinueClick3(ActionEvent event) throws IOException {
        Stage stage;
        Parent root;
        stage = (Stage) continueButton3.getScene().getWindow();
        root = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/RandomBeerScenes/scene4.fxml"));
        Scene scene = new Scene(root);

        stage.setTitle("BeerFinder Alpha Test");
        stage.setScene(scene);
        stage.show();

        typesPicked = "(";

        // Checking checkboxes and initializing typesPicked

        if (AleCheck.isSelected()){
            typesPicked = typesPicked + "1,";
        }
        if (WheatCheck.isSelected()){
            typesPicked = typesPicked + "9,";
        }
        if (LagerCheck.isSelected()){
            typesPicked = typesPicked + "4,";
        }
        if (PorterCheck.isSelected()){
            typesPicked = typesPicked + "6,";
        }
        if (DarkLagerCheck.isSelected()){
            typesPicked = typesPicked + "5,";
        }
        if (OtherChecked.isSelected()){
            typesPicked = typesPicked + "2,";
        }
        if (SpecialCheck.isSelected()){
            typesPicked = typesPicked + "7,";
        }
        if (FermentedCheck.isSelected()){
            typesPicked = typesPicked + "8,";
        }

        typesPicked = typesPicked.substring(0, typesPicked.length()-1) + ")";
        System.out.println("Types picked:" + typesPicked);

    }

    @FXML //Checking box "Yes"
    void yesSelected3(ActionEvent event) {
        if (yesCheckbox.isSelected()) {
            noCheckbox.setVisible(false);
            noCheckbox.setSelected(false);
            VBoxTypes.setVisible(true);
        }
        if (!yesCheckbox.isSelected()) {
            noCheckbox.setVisible(true);
            VBoxTypes.setVisible(false);
            AleCheck.setSelected(false);
            WheatCheck.setSelected(false);
            LagerCheck.setSelected(false);
            PorterCheck.setSelected(false);
            DarkLagerCheck.setSelected(false);
            OtherChecked.setSelected(false);
            SpecialCheck.setSelected(false);
            FermentedCheck.setSelected(false);
        }
    }

    public String getTypesPicked(){
        return typesPicked;
    }
}
