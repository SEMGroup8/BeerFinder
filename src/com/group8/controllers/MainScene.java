package com.group8.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Shiratori on 24/11/15.
 */


public class MainScene implements Initializable {
    @FXML
    private Pane center, top;



    @Override
    public void initialize(URL location, ResourceBundle resources) {


        try {
            center.getChildren().clear();
            top.getChildren().clear();

            center.getChildren().add(FXMLLoader.load(getClass().getResource("/com/group8/resources/views/home_center.fxml")));
            top.getChildren().add(FXMLLoader.load(getClass().getResource("/com/group8/resources/views/home_top.fxml")));

            System.out.println("test");
        } catch (IOException e) {

            System.out.println("test2");
            e.printStackTrace();
        }
    }
}
