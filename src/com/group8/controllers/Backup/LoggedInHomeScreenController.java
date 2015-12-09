//package com.group8.controllers.Backup;
//
//
//import com.group8.controllers.BeerData;
//import com.group8.controllers.MainController;
//import com.group8.controllers.Navigation;
//import com.group8.controllers.UserData;
//import com.group8.database.MysqlDriver;
//import com.group8.database.tables.Beer;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Node;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.scene.control.*;
//import javafx.scene.input.KeyCode;
//import javafx.scene.input.KeyEvent;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.ArrayList;
//import java.util.ResourceBundle;
//
//public class LoggedInHomeScreenController extends MainController {
//    // Declaration of elements
//    @FXML
//    public Button logout, account, favourites, randomButton;
//    @FXML
//    public Label userName;
//
//    // Declaration of FXML elements
//    @FXML
//    public Button search;
//    @FXML
//    public CheckBox advancedType;
//    @FXML
//    public CheckBox advancedProducer, advancedOrigin;
//    @FXML
//    public CheckBox advancedDescription;
//    @FXML
//    public CheckBox advanced;
//    @FXML
//    public CheckBox advancedCountry;
//    @FXML
//    public CheckBox all;
//    @FXML
//    public CheckBox advancedName;
//    @FXML
//    public TextField searchText;
//    @FXML
//    public Label error;
//
//    // TODO implement threads
//    // public ProgressIndicator load;
//
//    /**
//     * Auto clear fields when selected
//     * Clear the Search field
//     */
//    public void clearFieldSearch() {
//        exitField();
//
//        if (searchText.getText().equals("Search...")) {
//            searchText.setText("");
//        }
//    }
//
//    // Checkbox that when checked shows advanced checkboxes
//    public void showAdvanced() {
//
//        error.setText("");
//
//        if(advanced.isSelected()){
//            randomButton.setVisible(false);
//        }else{
//            randomButton.setVisible(true);
//        }
//
//
//        // Handle diffrent casesof visability and selection
//        if (!advancedDescription.isVisible() && !advancedType.isVisible() && !advancedProducer.isVisible()) {
//            advancedType.setVisible(true);
//            advancedProducer.setVisible(true);
//            advancedDescription.setVisible(true);
//            advancedCountry.setVisible(true);
//            all.setVisible(true);
//            advancedName.setVisible(true);
//            advancedType.setSelected(false);
//            advancedProducer.setSelected(false);
//            advancedDescription.setSelected(false);
//            advancedCountry.setSelected(false);
//            advancedName.setSelected(true);
//            all.setSelected(false);
//        } else {
//            advancedType.setVisible(false);
//            advancedProducer.setVisible(false);
//            advancedCountry.setVisible(false);
//            advancedDescription.setVisible(false);
//            all.setVisible(false);
//            advancedName.setVisible(false);
//            advancedName.setSelected(true);
//
//        }
//    }
//
//    // Checkbox to check all the advanced boxes
//    public void checkAll() {
//        if (!advancedType.isSelected() || !advancedProducer.isSelected() || !advancedDescription.isSelected()) {
//            advancedType.setSelected(true);
//            advancedProducer.setSelected(true);
//            advancedDescription.setSelected(true);
//            advancedName.setSelected(true);
//            advancedCountry.setSelected(true);
//        } else {
//            advancedType.setSelected(false);
//            advancedCountry.setSelected(false);
//            advancedProducer.setSelected(false);
//            advancedDescription.setSelected(false);
//            advancedName.setSelected(false);
//        }
//    }
//
//
//
//    // Resets guide text if no input was made
//    public void exitField() {
//        if (searchText.getText().isEmpty()) {
//            searchText.setText("Search...");
//        }
//    }
//
//    /**
//     * On clicking the Search button execute query through MySqlDriver
//     *
//     * @param event
//     * @throws IOException
//     */
//    @FXML
//    public void onSearch(javafx.event.ActionEvent event) throws IOException {
//
//        // Load wheel until task is finished//
//        // load.setVisible(true);
//// Fetch the user input
//        BeerData.searchInput="";
//
//
//
//        /**
//         * SQL query
//         *
//         * Construct a query as a String dependent on user specifications
//         */
//
//       {
//            // name search is defualt
//            BeerData.searchInput = "SELECT distinct `beerID`,`name`,`image`,`description`,beerTypeEN,countryName, percentage, producerName, volume, isTap, packageTypeEN, price, avStars" +
//                    " from beers, beerType, origin, package where " +
//                    "beers.beerTypeID = beerType.beerTypeID " +
//                    "and beers.originID = origin.originID " +
//                    "and beers.package = package.packageID " +
//                    "and (";
//
//            if(advancedName.isSelected()){
//                BeerData.searchInput += "name like '%" + searchText.getText() + "%'";
//            }
//
//
//            // Advanced
//            if(advanced.isSelected())
//            {
//                // For reasons
//                int selectedItems=0;
//
//                if (advancedCountry.isSelected()) {
//                    if(advancedName.isSelected() || advancedProducer.isSelected() || advancedType.isSelected() || advancedDescription.isSelected()) {
//                        BeerData.searchInput += " or countryName like '%" + searchText.getText() + "%'";
//                        selectedItems++;
//                    }else{
//                        BeerData.searchInput += "countryName like '%" + searchText.getText() + "%'";
//                    }
//                }
//
//                if (advancedType.isSelected()) {
//                    if(advancedName.isSelected() || advancedProducer.isSelected() || advancedDescription.isSelected() || advancedCountry.isSelected()) {
//                        BeerData.searchInput += " or beerTypeEN like '%" + searchText.getText() + "%'";
//                        selectedItems++;
//                    } else{
//                        BeerData.searchInput += "beerTypeEN like '%" + searchText.getText() + "%'";
//                    }
//                }
//                if (advancedProducer.isSelected()) {
//                    if(advancedName.isSelected() || advancedType.isSelected() || advancedDescription.isSelected() ||advancedCountry.isSelected()) {
//                        BeerData.searchInput += " or producerName like '%" + searchText.getText() + "%'";
//                        selectedItems++;
//                    }else{
//                        BeerData.searchInput += "producerName like '%" + searchText.getText() + "%'";
//                    }
//                }
//                if (advancedDescription.isSelected()) {
//                    if(advancedName.isSelected() || advancedProducer.isSelected() || advancedType.isSelected() || advancedCountry.isSelected()) {
//                        BeerData.searchInput += " or description like '%" + searchText.getText() + "%'";
//                        selectedItems++;
//                    }else{
//                        BeerData.searchInput += "description like '%" + searchText.getText() + "%'";
//                    }
//                }
//
//                if (!advancedName.isSelected() && selectedItems > 1){
//                    // Test Output
//                    System.out.println(BeerData.searchInput.substring(260, 262));
//
//                    BeerData.searchInput = BeerData.searchInput.substring(0,260) + BeerData.searchInput.substring(262);
//                    // Test Output
//                    System.out.println(BeerData.searchInput);
//                }
//            }
//        }
//        BeerData.searchInput +=") limit 100";
//
//
//        // Execute user query
//        ArrayList<ArrayList<Object>> sqlData;
//
//        sqlData = MysqlDriver.selectMany(BeerData.searchInput);
//
//        for (int i = 0; i < sqlData.size(); i++) {
//            // Add a new Beer to the beer arraylist
//            Beer beer = new Beer(sqlData.get(i));
//            // Testoutput
//            //System.out.print(beer.getName()+"\n");
//            BeerData.beer.add(beer);
//        }
//
//
//        if ((BeerData.beer.size() > 0)) {
//
//            // Load the result stage
//            Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/result_center.fxml"));
//            Scene result_scene = new Scene(result, 800, 600);
//            Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            main_stage.setScene(result_scene);
//            main_stage.show();
//        } else
//        {
//            advanced.setSelected(false);
//            advancedType.setVisible(false);
//            advancedProducer.setVisible(false);
//            advancedDescription.setVisible(false);
//            all.setVisible(false);
//            advancedName.setVisible(false);
//            advancedCountry.setVisible(false);
//            advancedName.setSelected(true);
//
//            //load.setVisible(false);
//            error.setText("No result for: " + searchText.getText());
//        }
//    }
//
//    @FXML
//    // Execute search button on pressing "Enter"
//    public void searchEnterPressed(KeyEvent event){
//        if (event.getCode() == KeyCode.ENTER) {
//            search.setDefaultButton(true);
//        }
//    }
//
//
//    /**
//     * Initialize Main controller
//     *
//     * @param location
//     * @param resources
//     */
//    @Override
//    public void initialize(URL location, ResourceBundle resources)
//    {
//        if(UserData.userInstance.get_isPub())
//        {
//            account.setText("Pub");
//        }
//
//        // Reset the BeerData Arraylist
//        BeerData.beer = new ArrayList<Beer>();
//        userName.setText(UserData.userInstance.get_name());
//
//        Navigation.homescreenFXML = "/com/group8/resources/views/Backup/loggedInHomescreen.fxml";
//        Navigation.resultviewFXML = "/com/group8/resources/views/result_center.fxml";
//        Navigation.backFXML = "/com/group8/resources/views/Backup/loggedInHomescreen.fxml";
//    }
//
//    @FXML
//    public void onLogout(javafx.event.ActionEvent event) throws IOException
//    {
//        UserData.userInstance = null;
//
//        Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/Backup/homescreen.fxml"));
//        Scene result_scene = new Scene(result, 800, 600);
//        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        main_stage.setScene(result_scene);
//        main_stage.show();
//    }
//
//    @FXML
//    public void onAccount(javafx.event.ActionEvent event) throws IOException
//    {
//        if(UserData.userInstance.get_isPub())
//        {
//            Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/pubInfo.fxml"));
//            Scene result_scene = new Scene(result, 800, 600);
//            Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            main_stage.setScene(result_scene);
//            main_stage.show();
//        }
//        else
//        {
//            Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/accountSettings.fxml"));
//            Scene result_scene = new Scene(result, 800, 600);
//            Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//            main_stage.setScene(result_scene);
//            main_stage.show();
//        }
//    }
//
//    @FXML
//    public void onRandom (ActionEvent event) throws Exception{
//
//        Stage stage = (Stage) randomButton.getScene().getWindow();
//        Parent root = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/RandomBeerScenes/scene1.fxml"));
//        Scene scene = new Scene(root);
//
//        stage.setTitle("BeerFinder Alpha Test");
//        stage.setScene(scene);
//        stage.show();
//    }
//
//    @FXML
//    public void onFavourites(javafx.event.ActionEvent event) throws IOException
//    {
//        Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/favourites.fxml"));
//        Scene result_scene = new Scene(result, 800, 600);
//        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        main_stage.setScene(result_scene);
//        main_stage.show();
//    }
//}
