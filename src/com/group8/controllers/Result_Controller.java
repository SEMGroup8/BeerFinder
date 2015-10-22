package com.group8.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by AnkanX on 15-10-22.
 */
public class Result_Controller implements Initializable {

    public Button Back;
    public TextArea resultText;

    /*
        Back button pressed takes you back to "home screen"
      */
    @FXML
    public void backAction(ActionEvent event) throws IOException {
        Parent homescreen = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/homescreen.fxml"));
        Scene result_scene = new Scene(homescreen, 800, 600);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();

    }

    /*
        initialize result controller
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // test output
        System.out.println("Vid Odins skägg det funkar! " + develop_SQL_query.getInstance().getResult().toString());
        // Set the Text square on the top element of the result scene
        // to current "develop_SQL_query" Object.result
        resultText.setText(develop_SQL_query.getInstance().getResult().toString());
    }

}
