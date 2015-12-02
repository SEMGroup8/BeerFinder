package com.group8.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by AnkanX on 15-10-24.
 *
 * TODO Insert method
 *
 */
public class PubController  extends BaseController implements Initializable {

    private final static PubController instance = new PubController();


    /*
    Initialize Pub controller
    */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.print("Initialized the pub Controller!");
    }

}
