package com.group8.controllers;

import com.group8.database.tables.Beer;
import com.group8.database.tables.Pub;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
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
    @FXML
    public ProgressIndicator load;
    
    public ImageView img= new ImageView((this.getClass().getResource("/com/group8/resources/Images/Icon_2.png").toString()));

    private Service<Void> backgroundThread;

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
     * Threaded.
     *
     * @param event
     * @throws IOException
     */
    @FXML
    public void onAccount(javafx.event.ActionEvent event) throws IOException
    {
        //Make a new Service
        backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        // load wheel until task is finished
                        load.setVisible(true);

                        if(UserData.userInstance.getIsPub())
                        {

                            //Makes the selected pub owner load the beers it has in it's inventory.
                            UserData.userInstance.getBeersInPub();
                        }
                        else {

                            //Load the followers, beers and pubs the user has.
                            UserData.userInstance.getFollowers();
                            UserData.userInstance.getFavouriteBeers();
                            UserData.userInstance.getPubFavourites();
                        }
                        return null;
                    }
                };
            }
        };

        //When the service has completed succesfully.
        backgroundThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                load.setVisible(false);


                if(UserData.userInstance.getIsPub())
                {

                    try {
                        mainScene.changeCenter("/com/group8/resources/views/pubInfo.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        mainScene.changeCenter("/com/group8/resources/views/MyProfile.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Start the service.
        backgroundThread.start();

    }

    /**
     *
     * @param event
     * @throws IOException
     */
    public void usersList(javafx.event.ActionEvent event) throws IOException
    {
    	UserData.userInstance.getUsers();
    	UserData.userInstance.getFollowers();
    	
        mainScene.changeCenter("/com/group8/resources/views/userList.fxml");

    }
}
