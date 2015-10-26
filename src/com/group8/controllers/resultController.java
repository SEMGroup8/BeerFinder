package com.group8.controllers;

import com.group8.database.tables.Beer;
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
public class ResultController implements Initializable {

    public Button Back;
    public Button Maps;
    public TextArea resultText;

    /*
        Back button pressed takes you back to "home screen"
      */
    @FXML
    public void backAction(ActionEvent event) throws IOException {
        Parent homescreen = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/homeScreen.fxml"));
        Scene result_scene = new Scene(homescreen, 800, 600);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();

    }

    @FXML
    public void getMaps(ActionEvent event) throws IOException {
        Parent homescreen = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/googleMaps.fxml"));
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
        //System.out.println("Vid Odins skägg det funkar! " + QueryResult.getInstance().getResult().toString());
        // Set the Text square on the top element of the result scene
        // to current "QueryResult" Object.result
        //resultText.setText(QueryResult.getInstance().getResult().toString());

        // Display derp results
        String result="";
        for(int i = 0; i < BeerData.beer.size();i++)
        {
        result += BeerData.beer.get(i).toString() + "\n";
        resultText.setText(result);
        }
    }

}
