package com.group8.controllers;


import com.group8.database.MysqlDriver;
import com.group8.database.tables.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoggedInHomeScreenController extends mainController
{
    //
    public Button Search;
    public Button logout;
    public CheckBox runSqlBox;
    public TextField SearchText;
    public Label userName;
    public Label Error;
    public ProgressIndicator Load;

    @Override
    // Resets guide text if no input was made
    public void exitField()
    {
        if (SearchText.getText().isEmpty()){
            SearchText.setText("Search...");
        }
    }
    @FXML
    public void onLogout(javafx.event.ActionEvent event) throws IOException {
        UserData.userInstance = null;

        Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/homescreen.fxml"));
        Scene result_scene = new Scene(result, 800, 600);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();
    }

    //Initialize Main controller
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        userName.setText(UserData.userInstance.get_name());
    }
}
