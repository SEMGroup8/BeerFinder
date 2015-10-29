package com.group8.controllers;

import com.group8.database.MysqlDriver;
import com.group8.database.tables.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.scene.control.CheckBox;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Shiratori on 26/10/15.
 */
public class RegisterUserController {

    @FXML
    public Button back;
    @FXML
    public Button register;
    @FXML
    public TextField username;
    @FXML
    public TextField email, pubName;
    @FXML
    public CheckBox isPub;
    @FXML
    public PasswordField password;

    @FXML
    public Label usernameError, emailError, pubNameError, passwordError, isPubError;

    @FXML
    public void onRegister(ActionEvent event) throws IOException
    {
        if(!checkInput())
        {
            System.out.println("in check input");
            return;
        }

        String selectQuery = "Select * from users where username = '" + username.getText() + "';";
        if (!checkAvailability(selectQuery))
        {
            System.out.println("In username check");
            usernameError.setText("Username is in use.");

            return;
        }

        selectQuery = "Select * from users where email = '" + email.getText() + "';";

        if (!checkAvailability(selectQuery))
        {
            System.out.println("In email check");

            emailError.setText("Email is in use.");

            return;
        }

        User newUser = new User(username.getText(), password.getText(), email.getText(), isPub.isSelected());

        newUser.insert();

    }

    //Checks if the input is correct.
    private boolean checkInput()
    {
        boolean canRegister = true;

        if(username.getText().length()==0)
        {
            usernameError.setText("Username has to be filled in.");
            canRegister = false;
        }

        if(!email.getText().contains("@")||!email.getText().contains("."))
        {
            emailError.setText("Email is invalid.");
            canRegister = false;
        }

        if(email.getText().length()==0)
        {
            emailError.setText("Email has to be filled in.");
            canRegister = false;
        }

        if(password.getText().length()<8)
        {
            passwordError.setText("Password has to be at least 8 characters.");
            canRegister = false;
        }

        if(password.getText().length()==0)
        {
            passwordError.setText("Password can't be empty.");
            canRegister = false;
        }

        if(isPub.isSelected())
        {
            if(pubName.getText().length() == 0)
            {
                pubNameError.setText("Pub name can't be empty if you are a pub owner");
                canRegister = false;
            }
        }

        return canRegister;
    }

    public boolean checkAvailability(String query)
    {
        ArrayList<Object> returnedUser = MysqlDriver.select(query);

        boolean canRegister = true;

        if(returnedUser.size()==0)
        {
            canRegister =  false;
        }

        return canRegister;
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
