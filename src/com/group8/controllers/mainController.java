package com.group8.controllers;
import com.group8.database.MysqlDriver;
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
 */
public class MainController implements Initializable {

    //
    public Button search;
    public Button login;
    public CheckBox runSqlBox;
    public CheckBox advancedType;
    public CheckBox advancedProducer;
    public CheckBox advancedDescription;
    public CheckBox advanced;
    public CheckBox all;
    public TextField searchText;
    public TextField loginText;
    public PasswordField pswrdField;
    public Label error;
    public ProgressIndicator load;


    String data="";



    // Auto clear fields when selected
    // Clear the Search field
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

    public void showAdvanced()
    {
        if(!advancedDescription.isVisible() && !advancedType.isVisible() && !advancedProducer.isVisible())
        {
            advancedType.setVisible(true);
            advancedProducer.setVisible(true);
            advancedDescription.setVisible(true);
            all.setVisible(true);
        }else
        {
            advancedType.setVisible(false);
            advancedProducer.setVisible(false);
            advancedDescription.setVisible(false);
            all.setVisible(false);
        }
    }

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
        }else {
            advancedDescription.setVisible(true);
            advancedProducer.setVisible(true);
            advancedType.setVisible(true);
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
            // name
            searchInput = "select * from beers where name like '" + searchText.getText() + "%'";
            // Advanced
            if(advanced.isSelected())
            {
                if (advancedType.isSelected()) {
                    searchInput += " or beerType like '" + searchText.getText() + "%'";
                }

                if (advancedProducer.isSelected()) {
                    searchInput += "or producerName like '" + searchText.getText() + "%'";
                }

                if (advancedDescription.isSelected()) {
                    searchInput += "or description like '" + searchText.getText() + "%'";
                }
            }
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
            // Store the data fetched from the server in the "QueryResult" object
            QueryResult.getInstance().setResult(data);
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

    @FXML
    public void onLogin(javafx.event.ActionEvent event) throws IOException{
        // Load the pub stage
        Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/pubScreen.fxml"));
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
        // Reset the "QueryResult" object.result
        QueryResult.getInstance().setResult();
    }


}