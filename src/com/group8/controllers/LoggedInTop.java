package com.group8.controllers;

import com.group8.database.tables.Beer;
import com.group8.database.tables.Pub;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
    public Button logout, account, beerFavourite, pubFavourite, profileButton, usersListButton;
    @FXML
    public Label userName;
    ImageView img= new ImageView((this.getClass().getResource("/com/group8/resources/Images/Icon_2.png").toString()));

    /**
     * Initialize Main controller
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        if(UserData.userInstance.getPub())
        {
            account.setText("Pub");
            beerFavourite.setVisible(false);
            pubFavourite.setVisible(false);
        }

        // Reset the BeerData Arraylist
        BeerData.beer = new ArrayList<Beer>();
        userName.setText(UserData.userInstance.get_name());

        PubData.pubs = new ArrayList<Pub>();

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
        if(UserData.userInstance.getPub())
        {
        	
            mainScene.changeCenter("/com/group8/resources/views/pubInfo.fxml");
        }
        else
        {
        	UserData.userInstance.getFollowers();

        	 mainScene.changeCenter("/com/group8/resources/views/MyProfile.fxml");
        }
    }

    @FXML
    public void onBeerFavourites(javafx.event.ActionEvent event) throws IOException
    {
        mainScene.changeCenter("/com/group8/resources/views/favourites.fxml");

    }
    public void onProfile(javafx.event.ActionEvent event) throws IOException
    {
        mainScene.changeCenter("/com/group8/resources/views/otherUsersProfile.fxml");

    }
    
    public void usersList(javafx.event.ActionEvent event) throws IOException
    {
    	UserData.userInstance.getUsers();
    	UserData.userInstance.getFollowers();
    	
        mainScene.changeCenter("/com/group8/resources/views/userList.fxml");

    }
    
    @FXML
    public void onPubFavourites(javafx.event.ActionEvent event) throws IOException
    {
        if(!UserData.userInstance.pubFavouritesDetails.isEmpty()) {
            mainScene.changeCenter("/com/group8/resources/views/FavouritePub.fxml");
        }else{
            img.setFitWidth(60);
            img.setFitHeight(60);
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Favourites Error");
            alert.setHeaderText("Cant't Show Favourite Pubs :/");
            alert.setContentText("You don't have any Favourite Pubs.");
            alert.setGraphic(img);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/com/group8/resources/Images/Icon.png"));
            alert.showAndWait();

        }
    }
}
