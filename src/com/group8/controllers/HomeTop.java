package com.group8.controllers;

import com.group8.database.MysqlDriver;
import com.group8.database.tables.Beer;
import com.group8.database.tables.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Shiratori on 24/11/15.
 */
public class HomeTop extends BaseController{

    @FXML
    public TextField loginText;
    @FXML
    public PasswordField pswrdField;
    @FXML
    public Button login;

    /**
     * Login Button event
     * @param event
     * @throws IOException
     */
    @FXML
    public void onLogin(javafx.event.ActionEvent event) throws IOException{

        String username = loginText.getText();
        String password = pswrdField.getText();

        String sqlQuery = "Select * from users where username = '" + username + "' and password = '" + password + "';";

        System.out.println(sqlQuery);
        ArrayList<Object> userData = MysqlDriver.select(sqlQuery);

        if(userData == null)
        {
            System.out.println("Is empty");
            return;
        }

        User fetchedUser = new User(userData);

        if(!fetchedUser.get_name().equals(username))
        {
            System.out.println(username);
            System.out.println(fetchedUser.get_name());
            return;
        }

        UserData.userInstance = fetchedUser;

        //System.out.println(fetchedUser.get_isPub());

        mainScene.changeTop("/com/group8/resources/views/loggedInTop.fxml");
    }

    // Resets guide text if no input was made
    public void exitField()
    {
        if (loginText.getText().isEmpty()){
            loginText.setText("Type here:");
        }

        if (pswrdField.getText().isEmpty()){
            pswrdField.setText("password");
        }
    }

    // Clear the Login field
    public void clearFieldLogin()
    {
        exitField();
        if (loginText.getText().equals("Type here:")) {
            loginText.setText("");
        }
    }
    // Clear the password field
    public void clearFieldPassword()
    {
        exitField();
        if(pswrdField.getText().equals("password"))
        {
            pswrdField.setText("");
        }
    }

    /**
     * On Register
     * @param event
     * @throws IOException
     */
    @FXML
    public void onRegister(javafx.event.ActionEvent event) throws IOException
    {
        // Load the Register stage
        mainScene.changeCenter("/com/group8/resources/views/registerUser.fxml");
    }

    @FXML
    // Execute login button on pressing "Enter"
    public void passwordEnterPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            login.setDefaultButton(true);
        }
    }
}
