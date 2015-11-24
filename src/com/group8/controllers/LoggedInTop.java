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
public class LoggedInTop extends BaseController implements Initializable
{
    // Declaration of elements
    @FXML
    public Button logout, account, favourites;
    @FXML
    public Label userName;

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

        mainScene.changeTop("/com/group8/resources/views/home_top.fxml");
        mainScene.changeCenter("/com/group8/resources/views/home_center.fxml");
    }

    @FXML
    public void onAccount(javafx.event.ActionEvent event) throws IOException
    {
        if(UserData.userInstance.get_isPub())
        {
            mainScene.changeCenter("/com/group8/resources/views/pubInfo.fxml");
        }
        else
        {
            mainScene.changeCenter("/com/group8/resources/views/accountSettings.fxml");
        }
    }

    @FXML
    public void onFavourites(javafx.event.ActionEvent event) throws IOException
    {
        mainScene.changeCenter("/com/group8/resources/views/favourites.fxml");
    }

}
