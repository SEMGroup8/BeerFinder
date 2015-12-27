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
 * Created by Created by Linus Eiderström Swahn.
 *
 * Controller for the logged in top part of the ui.
 *
 * Controller for the top top ui element when the user is logged out.
 *
 * Inherits BaseController for some UI-functionality.
 *
 * Implements Initializable so that we can tale advantage of the initialize() function.
 */
public class LoggedInTop extends BaseController implements Initializable
{
    @FXML
    public Button logout, account, beerFavourite, pubFavourite, profileButton, usersListButton;
    @FXML
    public Label userName;
    ImageView img= new ImageView((this.getClass().getResource("/com/group8/resources/Images/Icon_2.png").toString()));

    /**
     * Created by Linus Eiderström Swahn.
     *
     * Initialize Main controller
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // Hides certain elements if we are a pub.
        if(UserData.userInstance.getIsPub())
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

    /**
     * Created by Linus Eiderström Swahn.
     *
     * Gets called when the user presses the logout button.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void onLogout(javafx.event.ActionEvent event) throws IOException
    {
        UserData.userInstance = null;

        mainScene.changeTop("/com/group8/resources/views/home_top.fxml");
        mainScene.changeCenter("/com/group8/resources/views/home_center.fxml");
    }

    /**
     * Created by Linus Eiderström Swahn.
     *
     * Gets called when the user presses the account button.
     *
     * Depending on if the user is a pub owner or not different scenes get called.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void onAccount(javafx.event.ActionEvent event) throws IOException
    {
        if(UserData.userInstance.getIsPub())
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
