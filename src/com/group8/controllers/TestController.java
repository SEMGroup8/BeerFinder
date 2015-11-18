package com.group8.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by AnkanX on 15-11-18.
 */
public class TestController implements Initializable{
    @FXML
    public Label test;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("loaded test!");
    }
}
