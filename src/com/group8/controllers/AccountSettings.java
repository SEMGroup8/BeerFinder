package com.group8.controllers;

import com.group8.database.MysqlDriver;
import com.group8.database.tables.User;
import com.group8.singletons.UserData;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Shiratori on 03/11/15.
 */
public class AccountSettings extends BaseController implements Initializable
{
    @FXML
    public Button back;
    @FXML
    public Button update;
    @FXML
    public TextField fullName;
    @FXML
    public TextField email;
    @FXML
    public PasswordField password;
    @FXML
    public Label emailError, passwordError, isPubError, fullNameError;
    @FXML
    public void onUpdate(ActionEvent event) throws IOException
    {
        if(!checkInput())
        {
            System.out.println("in check input");
            return;
        }

        String selectQuery = "Select * from users where email = '" + email.getText() + "' and "
                + UserData.userInstance.getId() + " not in (select userId from users);";

        System.out.println(selectQuery);

        if (!checkAvailability(selectQuery))
        {
            System.out.println("In email check");

            emailError.setText("Email is in use.");

            return;
        }

        User newUser = new User();

        newUser.setUser(UserData.userInstance.get_name(), fullName.getText(), password.getText(), email.getText(), UserData.userInstance.getIsPub(), UserData.userInstance.getPubId());
        newUser.setId(UserData.userInstance.getId());

        UserData.userInstance = newUser;

        newUser.updateUser();
    }

    //Checks if the input is correct.
    private boolean checkInput()
    {
        boolean canUpdate = true;

        if(fullName.getText().length()==0)
        {
            fullNameError.setText("Name has to be filled in.");
            canUpdate = false;
        }

        if(!email.getText().contains("@")||!email.getText().contains("."))
        {
            emailError.setText("Email is invalid.");
            canUpdate = false;
        }

        if(email.getText().length()==0)
        {
            emailError.setText("Email has to be filled in.");
            canUpdate = false;
        }

        if(password.getText().length()<8)
        {
            passwordError.setText("Password has to be at least 8 characters.");
            canUpdate = false;
        }

        if(password.getText().length()==0)
        {
            passwordError.setText("Password can't be empty.");
            canUpdate = false;
        }

        return canUpdate;
    }

    public boolean checkAvailability(String query)
    {
        ArrayList<Object> returnedUser = MysqlDriver.select(query);

        boolean canUpdate = true;

        if(returnedUser!=null)
        {
            canUpdate =  false;
        }

        return canUpdate;
    }

    /*
    Back button pressed takes you back to "home screen"
    */
    @FXML
    public void onBack(ActionEvent event) throws IOException {
        mainScene.changeCenter("/com/group8/resources/views/home_Center.fxml");
    }

    public void initialize(URL location, ResourceBundle resources) {

        fullName.setText(UserData.userInstance.getFullName());
        password.setText(UserData.userInstance.getPassword());
        email.setText(UserData.userInstance.getEmail());
    }
}
