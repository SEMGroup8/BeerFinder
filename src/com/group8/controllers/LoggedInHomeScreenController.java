package com.group8.controllers;


import com.group8.database.MysqlDriver;
import com.group8.database.tables.Beer;
import com.group8.database.tables.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LoggedInHomeScreenController extends MainController {
    // Declaration of elements
    @FXML
    public Button logout, account, favourites;
    @FXML
    public Label userName;

    // Declaration of FXML elements
    @FXML
    public Button search;
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
    public CheckBox advancedName;
    @FXML
    public TextField searchText;
    @FXML
    public Label error;

    // TODO implement threads
    // public ProgressIndicator load;

    /**
     * Auto clear fields when selected
     * Clear the Search field
     */
    public void clearFieldSearch() {
        exitField();

        if (searchText.getText().equals("Search...")) {
            searchText.setText("");
        }
    }

    // Checkbox that when checked shows advanced checkboxes
    public void showAdvanced() {
        if (runSqlBox.isSelected()) {
            runSqlBox.setSelected(false);
        }

        // Handle diffrent casesof visability and selection
        if (!advancedDescription.isVisible() && !advancedType.isVisible() && !advancedProducer.isVisible()) {
            advancedType.setVisible(true);
            advancedProducer.setVisible(true);
            advancedDescription.setVisible(true);
            all.setVisible(true);
            advancedName.setVisible(true);
            advancedType.setSelected(false);
            advancedProducer.setSelected(false);
            advancedDescription.setSelected(false);
            advancedName.setSelected(true);
            all.setSelected(false);
        } else {
            advancedType.setVisible(false);
            advancedProducer.setVisible(false);
            advancedDescription.setVisible(false);
            all.setVisible(false);
            advancedName.setVisible(false);
            advancedName.setSelected(true);
        }
    }

    // Checkbox to check all the advanced boxes
    public void checkAll() {
        if (!advancedType.isSelected() || !advancedProducer.isSelected() || !advancedDescription.isSelected()) {
            advancedType.setSelected(true);
            advancedProducer.setSelected(true);
            advancedDescription.setSelected(true);
            advancedName.setSelected(true);
        } else {
            advancedType.setSelected(false);
            advancedProducer.setSelected(false);
            advancedDescription.setSelected(false);
            advancedName.setSelected(false);
        }


    }

    /**
     * Makes the advanced search viseble or inviseble depending on runSQL
     */
    public void noSearch() {


        if (runSqlBox.isSelected()) {
            advancedDescription.setVisible(false);
            advancedProducer.setVisible(false);
            advancedType.setVisible(false);
            all.setVisible(false);
            advanced.setSelected(false);
            advancedName.setSelected(false);
            advancedName.setVisible(false);

        } else if (!runSqlBox.isSelected() && advanced.isSelected()) {
            advancedDescription.setVisible(true);
            advancedProducer.setVisible(true);
            advancedType.setVisible(true);
            all.setVisible(true);
            error.setText("");
            advancedName.setVisible(true);
            advancedName.setSelected(true);
        } else {
            error.setText("");
        }
    }

    // Resets guide text if no input was made
    public void exitField() {
        if (searchText.getText().isEmpty()) {
            searchText.setText("Search...");
        }
    }

    /**
     * On clicking the Search button execute query through MySqlDriver
     *
     * @param event
     * @throws IOException
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
        } else {
            // name search is defualt
            searchInput = "select * from beers where ";

            if (advancedName.isSelected()) {
                searchInput += "name like '%" + searchText.getText() + "%'";
            }


            // Advanced
            if (advanced.isSelected()) {
                // For reasons
                int selectedIteams = 0;

                if (advancedType.isSelected()) {
                    if (advancedName.isSelected() || advancedProducer.isSelected() || advancedDescription.isSelected()) {
                        searchInput += " or beerType like '%" + searchText.getText() + "%'";
                        selectedIteams++;
                    } else {
                        searchInput += "beerType like '%" + searchText.getText() + "%'";
                    }
                }
                if (advancedProducer.isSelected()) {
                    if (advancedName.isSelected() || advancedType.isSelected() || advancedDescription.isSelected()) {
                        searchInput += " or producerName like '%" + searchText.getText() + "%'";
                        selectedIteams++;
                    } else {
                        searchInput += "producerName like '%" + searchText.getText() + "%'";
                    }
                }
                if (advancedDescription.isSelected()) {
                    if (advancedName.isSelected() || advancedProducer.isSelected() || advancedType.isSelected()) {
                        searchInput += " or description like '%" + searchText.getText() + "%'";
                        selectedIteams++;
                    } else {
                        searchInput += "description like '%" + searchText.getText() + "%'";
                    }
                }

                if (!advancedName.isSelected() && selectedIteams > 1) {
                    // Test Output
                    System.out.println(searchInput.substring(26, 28));

                    searchInput = searchInput.substring(0, 26) + searchInput.substring(29);
                    // Test Output
                    System.out.println(searchInput);
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
            System.out.print(beer.getName() + "\n");
            BeerData.beer.add(beer);
        }


        if ((BeerData.beer.size() > 0)) {

            // Load the result stage
            Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/resultScreen.fxml"));
            Scene result_scene = new Scene(result, 800, 600);
            Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            main_stage.setScene(result_scene);
            main_stage.show();
        } else {
            //load.setVisible(false);
            error.setText("Invalid Search String!");
        }
    }

    /**
     * Initialize Main controller
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources)
    {
        // Reset the BeerData Arraylist
        BeerData.beer = new ArrayList<Beer>();
        userName.setText(UserData.userInstance.get_name());

        Navigation.homescreenFXML = "/com/group8/resources/views/loggedInHomescreen.fxml";
        Navigation.resultviewFXML = "/com/group8/resources/views/resultScreen.fxml";
        Navigation.backFXML = "/com/group8/resources/views/loggedInHomescreen.fxml";
    }

    @FXML
    public void onLogout(javafx.event.ActionEvent event) throws IOException
    {
        UserData.userInstance = null;

        Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/homescreen.fxml"));
        Scene result_scene = new Scene(result, 800, 600);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();
    }

    @FXML
    public void onAccount(javafx.event.ActionEvent event) throws IOException
    {

        Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/accountSettings.fxml"));
        Scene result_scene = new Scene(result, 800, 600);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();
    }

    @FXML
    public void onFavourites(javafx.event.ActionEvent event) throws IOException
    {

        Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/favourites.fxml"));
        Scene result_scene = new Scene(result, 800, 600);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();
    }
}
