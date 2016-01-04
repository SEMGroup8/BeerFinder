package com.group8.controllers;

import com.group8.database.MysqlDriver;
import com.group8.database.tables.Pub;
import com.group8.database.tables.User;
import com.group8.singletons.PubData;
import com.group8.singletons.UserData;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Linus Eiderström Swahn
 *
 * Controller for the top top ui element when the user is logged out.
 *
 * Inherits BaseController for some UI-functionality.
 */
public class HomeTop extends BaseController{

    @FXML
    public TextField loginText;
    @FXML
    public PasswordField pswrdField;
    @FXML
    public Button login;
    @FXML
    public ProgressIndicator Load;

    private Service<Void> backgroundThread;

    ImageView img= new ImageView((this.getClass().getResource("/com/group8/resources/Images/Icon_2.png").toString()));

    /**
     * Created by Linus Eiderström Swahn.
     *
     * Gets called when the user presses the login button.
     *
     * Checks the credentials the user provided with the database.
     * If the user info is correct, the user gets stored and the user is logged in.
     * The top element changes to the logged in version instead.
     *
     * Login Button event
     * @param event
     * @throws IOException
     */
    @FXML
    public void onLogin(javafx.event.ActionEvent event) throws IOException{

        Load.setStyle("-fx-accent: IVORY");

        // Thread the login so that the whole program doesn't hang while we wait for the server.
        backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        // Loading wheel until task is finished
                        Load.setVisible(true);

                        String username = loginText.getText();
                        String password = pswrdField.getText();

                        String sqlQuery = "Select * from users where lower(username) = '" + username.toLowerCase() + "' and password = '" + password + "';";

                        ArrayList<Object> userData = MysqlDriver.select(sqlQuery);

                        // No user found.
                        if (userData == null) {
                            return null;
                        }

                        // Create new user instance with the fetched data.
                        User fetchedUser = new User(userData);

                        // Safety checks so the correct user has been found.
                        if (!fetchedUser.get_name().toLowerCase().equals(username.toLowerCase())) {

                            return null;
                        }

                        // load the static user instance with the logged in user data.
                        UserData.userInstance = fetchedUser;

                        // If the user is a pub owner.
                        if(fetchedUser.getIsPub())
                        {
                            // Get the pub of the user.
                        	String sqlQuery2 = "select * from pubs where pubID=" + UserData.userInstance.getPubId();
                        	Pub fetchedPub = new Pub(sqlQuery2);
                            PubData.loggedInPub = fetchedPub;
                        }

                        return null;
                    }
                };
            }
        };

        /**
         * Created by Andreas Fransson.
         *
         * When the thread is done try to go to next stage.
         */
        backgroundThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                Load.setVisible(false);

                //Error checking if userinstance is null, then dont try to load the loggedintop

                if (UserData.userInstance == null) {
                    // Show user password / username error
                    img.setFitWidth(60);
                    img.setFitHeight(60);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login Error");
                    alert.setHeaderText("Login failed");
                    alert.setContentText("Wrong Username or Password!");
                    alert.setGraphic(img);
                    Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
                    stage.getIcons().add(new Image("file:src/com/group8/resources/Images/Icon.png"));
                    alert.showAndWait();
                }else{
                    // load the loggedinTOP
                    try {
                        mainScene.changeTop("/com/group8/resources/views/loggedInTop.fxml");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        backgroundThread.start();
    }


    /**
     * Created by Andreas Fransson.
     *
     * Resets guide text if no input was made
     */
    public void exitField()
    {
        if (loginText.getText().isEmpty()){
            loginText.setText("Type here:");
        }

        if (pswrdField.getText().isEmpty()){
            pswrdField.setText("password");
        }
    }

    /**
     * Created by Andreas Fransson.
     *
     * Clear the Login field
     */
    public void clearFieldLogin()
    {
        exitField();
        if (loginText.getText().equals("Type here:")) {
            loginText.setText("");
        }
    }

    /**
     * Created by Andreas Fransson
     *
     * Clear the password field
     */
    public void clearFieldPassword()
    {
        exitField();
        if(pswrdField.getText().equals("password"))
        {
            pswrdField.setText("");
        }
    }

    /**
     * Created by Linus Eiderström Swahn.
     *
     * When the user presses the register button, load the Register user scene.
     * @param event
     * @throws IOException
     */
    @FXML
    public void onRegister(javafx.event.ActionEvent event) throws IOException
    {
        // load the Register stage
        mainScene.changeCenter("/com/group8/resources/views/registerUser.fxml");
    }

    /**
     * Created by Mantas Namgaudis
     *
     * When the user presses enter, press the login button.
     */
    @FXML
    public void passwordEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            login.setDefaultButton(true);
        }
    }
}
