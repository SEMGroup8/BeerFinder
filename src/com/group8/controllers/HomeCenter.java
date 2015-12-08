package com.group8.controllers;

import com.group8.database.MysqlDriver;
import com.group8.database.tables.Beer;
import com.group8.database.tables.Pub;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Shiratori on 24/11/15.
 * Default Home_Center FXML
 * -->
 */
public class HomeCenter extends BaseController implements Initializable
{
    @FXML
    public Button search, showPubs;
    @FXML
    public Label error;
    @FXML
    public Button randomButton;
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
    public CheckBox advancedCountry;
    @FXML
    public TextField searchText;
    @FXML
    public ProgressIndicator Load;
    // Threading service
    Service<Void> backgroundThread;

    // Checkbox that when checked shows advanced checkboxes
    public void showAdvanced()
    {
        error.setText("");

        if(advanced.isSelected()){
            randomButton.setVisible(false);
        }else{
            randomButton.setVisible(true);
        }


        // Handle diffrent casesof visability and selection
        if(!advancedDescription.isVisible() && !advancedType.isVisible() && !advancedProducer.isVisible())
        {
            advancedType.setVisible(true);
            advancedProducer.setVisible(true);
            advancedDescription.setVisible(true);
            all.setVisible(true);
            advancedName.setVisible(true);
            advancedCountry.setVisible(true);
            advancedType.setSelected(false);
            advancedProducer.setSelected(false);
            advancedDescription.setSelected(false);
            advancedName.setSelected(true);
            advancedCountry.setSelected(false);
            all.setSelected(false);
        }else
        {
            advancedType.setVisible(false);
            advancedProducer.setVisible(false);
            advancedDescription.setVisible(false);
            all.setVisible(false);
            advancedName.setVisible(false);
            advancedCountry.setVisible(false);
            advancedName.setSelected(true);
        }
    }
    // Checkbox to check all the advanced boxes
    public void checkAll()
    {
        if ( !advancedType.isSelected() || !advancedProducer.isSelected() || !advancedDescription.isSelected()) {
            advancedType.setSelected(true);
            advancedProducer.setSelected(true);
            advancedDescription.setSelected(true);
            advancedName.setSelected(true);
            advancedCountry.setSelected(true);
        }else{
            advancedType.setSelected(false);
            advancedProducer.setSelected(false);
            advancedDescription.setSelected(false);
            advancedName.setSelected(false);
            advancedCountry.setSelected(false);
        }


    }

    // Resets guide text if no input was made
    public void exitField()
    {
        if (searchText.getText().isEmpty()){
            searchText.setText("Search...");
        }
    }

    /**
     * On clicking the Search button execute query through MySqlDriver
     * @param event
     * @throws IOException
     */
    @FXML
    public void onSearch(javafx.event.ActionEvent event) throws IOException {

        // Set background service diffrent from the UI fx thread to run stuff on( i know indentation is retarded)
        backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {


                        // Load wheel until task is finished//
                        Load.setVisible(true);


                        // Fetch the user input
                        BeerData.searchInput="";


                        /**
                         * SQL query
                         *
                         * Construct a query as a String dependent on user specifications
                         */

                        {
                            // name search is defualt
                            BeerData.searchInput = "SELECT distinct `beerID`,`name`,`image`,`description`,beerTypeEN,countryName, percentage, producerName, volume, isTap, packageTypeEN, price, avStars, countryFlag" +
                                    " from beers, beerType, origin, package where " +
                                    "beers.beerTypeID = beerType.beerTypeID " +
                                    "and beers.originID = origin.originID " +
                                    "and beers.package = package.packageID " +
                                    "and (";

                            if(advancedName.isSelected()){
                                BeerData.searchInput += "name like '%" + searchText.getText() + "%'";
                            }


                            // Advanced
                            if(advanced.isSelected())
                            {
                                // For reasons
                                int selectedIteams=0;

                                if (advancedCountry.isSelected()) {
                                    if(advancedName.isSelected() || advancedProducer.isSelected() || advancedType.isSelected() || advancedDescription.isSelected()) {
                                        BeerData.searchInput += " or countryName like '%" + searchText.getText() + "%'";
                                        selectedIteams++;
                                    }else{
                                        BeerData.searchInput += "countryName like '%" + searchText.getText() + "%'";
                                    }
                                }

                                if (advancedType.isSelected()) {
                                    if(advancedName.isSelected() || advancedProducer.isSelected() || advancedDescription.isSelected() || advancedCountry.isSelected()) {
                                        BeerData.searchInput += " or beerTypeEN like '%" + searchText.getText() + "%'";
                                        selectedIteams++;
                                    } else{
                                        BeerData.searchInput += "beerTypeEN like '%" + searchText.getText() + "%'";
                                    }
                                }
                                if (advancedProducer.isSelected()) {
                                    if(advancedName.isSelected() || advancedType.isSelected() || advancedDescription.isSelected() ||advancedCountry.isSelected()) {
                                        BeerData.searchInput += " or producerName like '%" + searchText.getText() + "%'";
                                        selectedIteams++;
                                    }else{
                                        BeerData.searchInput += "producerName like '%" + searchText.getText() + "%'";
                                    }
                                }
                                if (advancedDescription.isSelected()) {
                                    if(advancedName.isSelected() || advancedProducer.isSelected() || advancedType.isSelected() || advancedCountry.isSelected()) {
                                        BeerData.searchInput += " or description like '%" + searchText.getText() + "%'";
                                        selectedIteams++;
                                    }else{
                                        BeerData.searchInput += "description like '%" + searchText.getText() + "%'";
                                    }
                                }

                                if (!advancedName.isSelected() && selectedIteams > 1){
                                    // Test Output
                                    //System.out.println(BeerData.searchInput.substring(260, 262));

                                    BeerData.searchInput = BeerData.searchInput.substring(0,260) + BeerData.searchInput.substring(262);
                                    // Test Output
                                    //System.out.println(BeerData.searchInput);
                                }
                            }
                        }
                        // Added a 100 beer limit as a safety for now / maybe have pages also?
                        BeerData.searchInput +=") limit 100 ";

                        // Execute user query
                        ArrayList<ArrayList<Object>> sqlData;

                        System.out.println(BeerData.searchInput);
                        sqlData = MysqlDriver.selectMany(BeerData.searchInput);

                        System.out.println(sqlData.size());

                        for (int i = 0; i < sqlData.size(); i++) {
                            // Add a new Beer to the beer arraylist
                            Beer beer = new Beer(sqlData.get(i));
                            // Testoutput
                            System.out.print(beer.getName()+"\n");
                            BeerData.beer.add(beer);
                        }
                        for(int i=0;i<BeerData.beer.size();i++){
                            System.out.println(BeerData.beer.get(i).getName());
                        }
                        return null;
                    }
                };
            }
        };



        // When the thread is done try to go to next stage.
        backgroundThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {
                Load.setVisible(false);
                if ((BeerData.beer.size()>0)) {


                    // Load the result stage
                    try {
                        mainScene.changeCenter("/com/group8/resources/views/result_center.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else
                {
                    advanced.setSelected(false);
                    advancedType.setVisible(false);
                    advancedProducer.setVisible(false);
                    advancedDescription.setVisible(false);
                    all.setVisible(false);
                    advancedName.setVisible(false);
                    advancedCountry.setVisible(false);
                    advancedName.setSelected(true);

                    //load.setVisible(false);
                    error.setText("No result for: " + searchText.getText());
                }
            }
        });

        // Start thread
        backgroundThread.start();

    }

    /**
     * Loads the random beer generator
     * @param event
     * @throws Exception
     */
    @FXML
    public void onRandom (ActionEvent event) throws Exception{

        mainScene.changeCenter("/com/group8/resources/views/RandomBeerScenes/scene1.fxml");
    }

    /**
     * Show all existing Pubs (testing??)
     * @param event
     * @throws Exception
     */
    @FXML
    public void onShowPubs (ActionEvent event) throws Exception{
        // Set background service diffrent from the UI fx thread to run stuff on( i know indentation is retarded)
        backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {


                        // Load wheel until task is finished//
                        Load.setVisible(true);

                        // TODO use same as other to get all fields right...
                        String listOfPub = "select pubs.pubID,`name`,image, `phoneNumber`, `description`, `offers`, `entrenceFee` from   pubs";
                        ArrayList<ArrayList<Object>> SQLData4;

                        SQLData4 = MysqlDriver.selectMany(listOfPub);
                        ArrayList<Pub> pubListDetails = new ArrayList<Pub>();

                        for (int i = 0; i < SQLData4.size(); i++) {
                            // Add a new Beer to the beer arraylist
                            Pub pub = new Pub(SQLData4.get(i));
                            pubListDetails.add(pub);

                        }


                        PubData.pubs = pubListDetails;

                        return null;
                    }
                };
            }
        };
                        backgroundThread.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
                            @Override
                            public void handle(WorkerStateEvent event) {
                                Load.setVisible(false);


                                // Load the result stage
                                try {
                                    mainScene.changeCenter("/com/group8/resources/views/pubList.fxml");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }


                            }
                        });

                        // Start thread
                        backgroundThread.start();


    }

    /**
     * Auto clear fields when selected
     * Clear the Search field
     */
    public void clearFieldSearch()
    {
        exitField();

        if (searchText.getText().equals("Search...")) {
            searchText.setText("");
        }
    }

    @FXML
    // Execute search button on pressing "Enter"
    public void searchEnterPressed(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER) {
            search.setDefaultButton(true);
        }
    }

    /**
     *  Initialize Main controller
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Reset the BeerData Arraylist
        BeerData.beer = new ArrayList<Beer>();
        Navigation.current_CenterFXML = "/com/group8/resources/views/home_center.fxml";


    }
}
