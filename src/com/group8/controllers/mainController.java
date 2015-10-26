package com.group8.controllers;
import com.group8.database.MysqlDriver;
import com.group8.database.tables.Beer;
import com.group8.database.tables.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by AnkanX on 15-10-22.
 */
public class mainController implements Initializable {

    //
    public Button Search;
    public Button Login;
    public CheckBox runSqlBox;
    public TextField SearchText;
    public TextField LoginText;
    public PasswordField PswrdField;
    public Label Error;
    public ProgressIndicator Load;


    String data="";

    // Auto clear fields when selected
    // Clear the Search field
    public void clearFieldSearch()
    {
        exitField();

        if (SearchText.getText().equals("Search...")) {
            SearchText.setText("");
        }
    }
    // Clear the Login field
    public void clearFieldLogin()
    {
        exitField();
        if (LoginText.getText().equals("Type here:")) {
            LoginText.setText("");
        }
    }
    // Clear the password field
    public void clearFieldPassword()
    {
        exitField();
        if(PswrdField.getText().equals("password"))
        {
            PswrdField.setText("");
        }
    }

    // Resets guide text if no input was made
    public void exitField()
    {
        if (LoginText.getText().isEmpty()){
            LoginText.setText("Type here:");
        }

        if (SearchText.getText().isEmpty()){
            SearchText.setText("Search...");
        }

        if (PswrdField.getText().isEmpty()){
            PswrdField.setText("password");
        }


    }

    /*
        On clicking the Search button execute query through MySqlDriver
     */
    @FXML
    public void onSearch(javafx.event.ActionEvent event) throws IOException {

        // Load wheel until task is finished
        Load.setVisible(true);

        // Fetch the user input
        String searchInput;

        if (runSqlBox.isSelected()) {
            searchInput = SearchText.getText();
        }else {
            // Advanced ( LOL SO EASYEEEEY )
            searchInput = "select * from beers where name like '" + SearchText.getText() + "%'";
        }

        // Execute user query
        ArrayList<ArrayList> sqlData = new ArrayList<>();

        sqlData = MysqlDriver.selectMany(searchInput);

        for (int i = 0; i < sqlData.size(); i++) {
            ArrayList<Object> row = sqlData.get(i);

            for (int j = 0; j < row.size(); j++) {

                    data += row.get(j).toString() + " ";


            }
            data += "\n";
        }
        // Test output
        System.out.println(data);

        if (!data.isEmpty()) {
            // Store the data fetched from the server in the "queryResult" object
            queryResult.getInstance().setResult(data);
            // Load the result stage
            Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/resultScreen.fxml"));
            Scene result_scene = new Scene(result,800,600);
            Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            main_stage.setScene(result_scene);
            main_stage.show();
        }else
        {
           Load.setVisible(false);
           Error.setText("Invalid Search String!");
        }
    }

    @FXML
    public void onLogin(javafx.event.ActionEvent event) throws IOException{

        String username = LoginText.getText();
        String password = PswrdField.getText();

        String sqlQuery = "Select * from users where username = '" + username + "' and password = '" + password + "';";

        User fetchedUser = new User(sqlQuery);

        if(!fetchedUser.get_name().equals(username))
        {
            System.out.println(username);
            System.out.println(fetchedUser.get_name());
            return;
        }

        UserData.userInstance = fetchedUser;

        if(fetchedUser.get_isPub())
        {
            // Load the pub stage
            Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/pubScreen.fxml"));
            Scene result_scene = new Scene(result,800,600);
            Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            main_stage.setScene(result_scene);
            main_stage.show();
        }
        else
        {
            // Load the pub stage
            Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/loggedInHomescreen.fxml"));
            Scene result_scene = new Scene(result,800,600);
            Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            main_stage.setScene(result_scene);
            main_stage.show();
        }
    }

    /*
        Initialize Main controller
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Reset the "queryResult" object.result
        queryResult.getInstance().setResult();
    }


}