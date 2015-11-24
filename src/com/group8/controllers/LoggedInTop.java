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
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Shiratori on 24/11/15.
 */
public class LoggedInTop implements Initializable
{
    // Declaration of elements
    @FXML
    public Button logout, account, favourites;
    @FXML
    public Label userName;

    private MainScene mainScene;

    public void init(MainScene mainScene)
    {
        this.mainScene = mainScene;
    }

    /**
     * Initialize Main controller
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        if(UserData.userInstance.get_isPub())
        {
            account.setText("Pub");
        }

        // Reset the BeerData Arraylist
        BeerData.beer = new ArrayList<Beer>();
        userName.setText(UserData.userInstance.get_name());

        Navigation.homescreenFXML = "/com/group8/resources/views/loggedInHomescreen.fxml";
        Navigation.resultviewFXML = "/com/group8/resources/views/resultScreen.fxml";
        Navigation.backFXML = "/com/group8/resources/views/loggedInHomescreen.fxml";
    }

    // Resets guide text if no input was made
    public void exitField() {

    }

    @FXML
    public void onLogout(javafx.event.ActionEvent event) throws IOException
    {
        UserData.userInstance = null;

        Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/homescreen.fxml"));
        Scene result_scene = new Scene(result, 800, 600);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();
    }

    @FXML
    public void onAccount(javafx.event.ActionEvent event) throws IOException
    {
        if(UserData.userInstance.get_isPub())
        {
            Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/pubInfo.fxml"));
            Scene result_scene = new Scene(result, 800, 600);
            Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            main_stage.setScene(result_scene);
            main_stage.show();
        }
        else
        {
            Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/accountSettings.fxml"));
            Scene result_scene = new Scene(result, 800, 600);
            Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            main_stage.setScene(result_scene);
            main_stage.show();
        }
    }

    @FXML
    public void onFavourites(javafx.event.ActionEvent event) throws IOException
    {
        Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/favourites.fxml"));
        Scene result_scene = new Scene(result, 800, 600);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();
    }

}
