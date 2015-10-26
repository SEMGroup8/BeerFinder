package com.group8.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

/**
 * Created by Shiratori on 26/10/15.
 */
public class RegisterUserController {

    public Button back, register;
    public TextField username, email, pubName;
    public Checkbox isPub;
    public PasswordField password;

    @FXML
    public void onRegister(ActionEvent event) throws IOException
    {

    }

    /*
    Back button pressed takes you back to "home screen"
    */
    @FXML
    public void onBack(ActionEvent event) throws IOException {
        Parent homescreen = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/homeScreen.fxml"));
        Scene result_scene = new Scene(homescreen, 800, 600);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();

    }

}
