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

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by AnkanX on 15-10-22.
 *
 * TODO Visual Upgrade & optimizeation
 *
 *
 *
 */
public class MainController implements Initializable {

    // Declaration of FXML elements
    @FXML
    public Button search;
    @FXML
    public Button login;
    @FXML
    public CheckBox runSqlBox;
    @FXML
    public CheckBox advancedType;
    @FXML
    public CheckBox advancedProducer;
    @FXML
    public CheckBox advancedDescription;
    @FXML
    public CheckBox advanced;
    @FXML
    public CheckBox all;
    @FXML
    public TextField searchText;
    @FXML
    public TextField loginText;
    @FXML
    public PasswordField pswrdField;
    @FXML
    public Label error;

    // TODO implement threads
   // public ProgressIndicator load;

    /*
     Auto clear fields when selected
     Clear the Search field
    */
    public void clearFieldSearch()
    {
        exitField();

        if (searchText.getText().equals("Search...")) {
            searchText.setText("");
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

    // Checkbox that when checked shows advanced checkboxes
    public void showAdvanced()
    {
        if(runSqlBox.isSelected())
        {
            runSqlBox.setSelected(false);
        }

        // Handle diffrent casesof visability and selection
        if(!advancedDescription.isVisible() && !advancedType.isVisible() && !advancedProducer.isVisible())
        {
            advancedType.setVisible(true);
            advancedProducer.setVisible(true);
            advancedDescription.setVisible(true);
            all.setVisible(true);
            advancedType.setSelected(false);
            advancedProducer.setSelected(false);
            advancedDescription.setSelected(false);
            all.setSelected(false);
        }else
        {
            advancedType.setVisible(false);
            advancedProducer.setVisible(false);
            advancedDescription.setVisible(false);
            all.setVisible(false);
        }
    }
    // Checkbox to check all the advanced boxes
    public void checkAll()
    {
        if ( !advancedType.isSelected() || !advancedProducer.isSelected() || !advancedDescription.isSelected()) {
            advancedType.setSelected(true);
            advancedProducer.setSelected(true);
            advancedDescription.setSelected(true);
        }else{
            advancedType.setSelected(false);
            advancedProducer.setSelected(false);
            advancedDescription.setSelected(false);
        }


    }

    // Makes the advanced search viseble or inviseble depending on runSQL
    public void noSearch()
    {


        if(runSqlBox.isSelected()) {
            advancedDescription.setVisible(false);
            advancedProducer.setVisible(false);
            advancedType.setVisible(false);
            all.setVisible(false);
            advanced.setSelected(false);
        }else if(!runSqlBox.isSelected() && advanced.isSelected()){
            advancedDescription.setVisible(true);
            advancedProducer.setVisible(true);
            advancedType.setVisible(true);
            all.setVisible(true);
            error.setText("");
        }else
        {
            error.setText("");
        }
    }

    // Resets guide text if no input was made
    public void exitField()
    {
        if (loginText.getText().isEmpty()){
            loginText.setText("Type here:");
        }

        if (searchText.getText().isEmpty()){
            searchText.setText("Search...");
        }

        if (pswrdField.getText().isEmpty()){
            pswrdField.setText("password");
        }


    }

    /*
        On clicking the Search button execute query through MySqlDriver
     */
    @FXML
    public void onSearch(javafx.event.ActionEvent event) throws IOException {

        // Load wheel until task is finished//
        // load.setVisible(true);

        // Fetch the user input
        String searchInput;

        // SQL query
        if (runSqlBox.isSelected()) {
            searchInput = searchText.getText();
        }else {
            // name search is defualt
            searchInput = "select * from beers where name like '%" + searchText.getText() + "%'";
            // Advanced
            if(advanced.isSelected())
            {
                if (advancedType.isSelected()) {
                    searchInput += " or beerType like '%" + searchText.getText() + "%'";
                }
                if (advancedProducer.isSelected()) {
                    searchInput += "or producerName like '%" + searchText.getText() + "%'";
                }
                if (advancedDescription.isSelected()) {
                    searchInput += "or description like '%" + searchText.getText() + "%'";
                }
            }
        }


        // Execute user query
        ArrayList<ArrayList<Object>> sqlData;

        sqlData = MysqlDriver.selectMany(searchInput);

        for (int i = 0; i < sqlData.size(); i++) {
            // Add a new Beer to the beer arraylist
            Beer beer = new Beer(sqlData.get(i));
            // Testoutput
            System.out.print(beer.getName());
            BeerData.beer.add(beer);
        }


        if ((BeerData.beer.size()>0)) {

            // Load the result stage
            Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/resultScreen.fxml"));
            Scene result_scene = new Scene(result,800,600);
            Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            main_stage.setScene(result_scene);
            main_stage.show();
        }else
        {
           //load.setVisible(false);
           error.setText("Invalid Search String!");
        }
    }

    // Login Button event
    @FXML
    public void onLogin(javafx.event.ActionEvent event) throws IOException{

        String username = loginText.getText();
        String password = pswrdField.getText();

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

    @FXML
    public void onRegister(javafx.event.ActionEvent event) throws IOException
    {
        // Load the Register stage
        Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/registerUser.fxml"));
        Scene result_scene = new Scene(result,800,600);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();
    }

    /*
        Initialize Main controller
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Reset the BeerData Arraylist
        BeerData.beer = new ArrayList<Beer>();
    }


}
