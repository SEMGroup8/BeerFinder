package com.group8.controllers;
import com.group8.database.MysqlDriver;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by AnkanX on 15-10-22.
 */
public class Main_Controller implements Initializable {

    //
    public Button Search;
    public TextField SearchText;
    String data="";


    /*
        On clicking the Search button execute query through MySqlDriver
     */
    @FXML
    public void onButtonClick(javafx.event.ActionEvent event) throws IOException {
        // Fetch the user input
        String searchInput;

        searchInput = SearchText.getText();
        // Execute user query
        ArrayList<ArrayList> sqlData = new ArrayList<>();

        sqlData = MysqlDriver.selectMany(searchInput);

        for (int i = 0; i < sqlData.size(); i++) {
            ArrayList<Object> row = sqlData.get(i);

            for (int j = 0; j < row.size(); j++) {

                data += row.get(j).toString();

            }
            data += "\n";
        }
        // Test output
        System.out.println(data);
        // Store the data fetched from the server in the "develop_SQL_query" object
        develop_SQL_query.getInstance().setResult(data);
        // Load the result stage
        Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/result.fxml"));
        Scene result_scene = new Scene(result);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();
    }

    /*
        Initialize Main controller
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Reset the "develop_SQL_query" object.result
        develop_SQL_query.getInstance().setResult();
    }


}