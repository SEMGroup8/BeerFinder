package com.group8.controllers;

import com.group8.database.MysqlDriver;
import com.group8.database.tables.Beer;
import com.group8.database.tables.Pub;

import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.collections.*;

import javax.swing.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Controller for the center home scene.
 *
 * Inherits BaseController for some UI-functionality.
 *
 * Implements Initializable so that we can tale advantage of the initialize() function.
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
    public Button beerScanButton;
    @FXML
    private Button beerScanSearchButton;

    public TextField searchText;
    @FXML
    public ProgressIndicator Load;
    @FXML
    public ToggleButton notButton;
    @FXML
    public ListView<String> notList;
    @FXML
    public SplitPane notWindow;
    @FXML
    public Label notifications;

    private SwingNode webCam;
    private BorderPane pane;
    private Stage webcamStage;
    private Scene webcamScene;
    private static String barcode = null;
    private static Button workaroundButton;
    boolean cameraOpen= false;
    
    int notNum=0;

    private Service<Void> backgroundThread;

    public void getRow(){
        notList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            // Select item will only be displayed when dubbleclicked

            /**
             * Dubleclick event
             * @param event
             */
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    // Show that we can select items and print it
                    //System.out.println("clicked on " + beerTable.getSelectionModel().getSelectedItem());
                    // Set the selectedBeer instance of beer we have to selected item
                    String notification = notList.getSelectionModel().getSelectedItem();
                    
                    String remove = "Delete from notifications where notification = '"+notification+"' and userId = "+UserData.userInstance.getId()+";";
                    MysqlDriver.update(remove);
                    
                    notification = notification.substring(0, notification.indexOf(' ')); 
                    
                    // load the details scene
                    // Has to be in a tr / catch becouse of the event missmatch, ouseevent cant throw IOexceptions
                    BeerData.searchInput = "SELECT distinct `beerID`,`name`,`image`,`description`,beerTypeEN,countryName, percentage, producerName, volume, isTap, packageTypeEN, price, avStars, countryFlag " +
                            "from beers, beerType, origin, package " +
                            "where beers.beerTypeID = beerType.beerTypeID " +
                            "and beers.originID = origin.originID " +
                            "and beers.package = package.packageID " +
                            "and (";
                    
                    BeerData.searchInput += "name like '" + notification + "');";
                    
                    ArrayList<ArrayList<Object>> sqlData;

                    sqlData = MysqlDriver.selectMany(BeerData.searchInput);

                    for (int i = 0; i < sqlData.size(); i++) {
                        // Add a new Beer to the beer arraylist
                        Beer beer = new Beer(sqlData.get(i));
                        
                        BeerData.beer.add(beer);
                    }
                    
                    if ((BeerData.beer.size()==1)) {

                        BeerData.selectedBeer = BeerData.beer.get(0);
                        try {
                            mainScene.changeCenter("/com/group8/resources/views/beerDetails_center.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    if ((BeerData.beer.size()>1)) {

                        // load the result stage
                        try {
                            mainScene.changeCenter("/com/group8/resources/views/result_center.fxml");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

        });
    }
    
    public void onNot(){
    	if(notButton.isSelected()){
    		notWindow.setVisible(true);
    	}
    	else{
    		notWindow.setVisible(false);
    	}
    }
    
    /**
     * Created by Andreas Fransson.
     */
    public void showAdvanced()
    {
        error.setText("");

        if(advanced.isSelected()){
            randomButton.setVisible(false);
            showPubs.setVisible(false);
        }else{
            randomButton.setVisible(true);
            showPubs.setVisible(true);
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

    /**
     * Created by Andreas Fransson.
     */
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

    /**
     * Created by Andreas Fransson.
     *
     */
    public void exitField()
    {
        if (searchText.getText().isEmpty()){
            searchText.setText("Search...");
        }
    }

    /**
     * Created by Andreas Fransson.
     *
     * On clicking the Search button execute query through MySqlDriver
     * @param event
     * @throws IOException
     */
    @FXML
    public void onSearch(javafx.event.ActionEvent event) throws IOException {

        // Set background service different from the UI fx thread to run stuff on
        backgroundThread = new Service<Void>() {
            @Override
            protected Task<Void> createTask() {
                return new Task<Void>() {
                    @Override
                    protected Void call() throws Exception {

                        //Get source of pressed button
                        Object source = event.getSource();

                        // load wheel until task is finished//
                        Load.setVisible(true);

                        // Fetch the user input
                        BeerData.searchInput="";


                        /**
                         * SQL query
                         *
                         * Construct a query as a String dependent on user specifications
                         */

                        //If request comes from the barcode scanner
                        if(((Node)event.getSource()).getId() == beerScanSearchButton.getId()){

                            BeerData.searchInput = "SELECT distinct `beerID`,`name`,`image`,`description`,beerTypeEN,countryName, percentage, producerName, volume, isTap, packageTypeEN, price, avStars, countryFlag, barcode" +
                                    " from beers, beerType, origin, package where " +
                                    "beers.beerTypeID = beerType.beerTypeID " +
                                    "and beers.originID = origin.originID " +
                                    "and beers.package = package.packageID " +
                                    "and (beers.barcode = " + barcode;
                        }
                        else
                        {
                            // name search is defualt
                            BeerData.searchInput = "SELECT distinct `beerID`,`name`,`image`,`description`,beerTypeEN,countryName, percentage, producerName, volume, isTap, packageTypeEN, price, avStars, countryFlag " +
                                    "from beers, beerType, origin, package " +
                                    "where beers.beerTypeID = beerType.beerTypeID " +
                                    "and beers.originID = origin.originID " +
                                    "and beers.package = package.packageID " +
                                    "and (";

                            if (advancedName.isSelected()) {
                                BeerData.searchInput += "name like '%" + searchText.getText() + "%'";
                            }

                            // Advanced
                            if (advanced.isSelected()) {
                                // For reasons
                                int selectedIteams = 0;

                                if (advancedCountry.isSelected()) {
                                    if (advancedName.isSelected() || advancedProducer.isSelected() || advancedType.isSelected() || advancedDescription.isSelected()) {
                                        BeerData.searchInput += " or countryName like '%" + searchText.getText() + "%'";
                                        selectedIteams++;
                                    } else {
                                        BeerData.searchInput += "countryName like '%" + searchText.getText() + "%'";
                                    }
                                }

                                if (advancedType.isSelected()) {
                                    if (advancedName.isSelected() || advancedProducer.isSelected() || advancedDescription.isSelected() || advancedCountry.isSelected()) {
                                        BeerData.searchInput += " or beerTypeEN like '%" + searchText.getText() + "%'";
                                        selectedIteams++;
                                    } else {
                                        BeerData.searchInput += "beerTypeEN like '%" + searchText.getText() + "%'";
                                    }
                                }
                                if (advancedProducer.isSelected()) {
                                    if (advancedName.isSelected() || advancedType.isSelected() || advancedDescription.isSelected() || advancedCountry.isSelected()) {
                                        BeerData.searchInput += " or producerName like '%" + searchText.getText() + "%'";
                                        selectedIteams++;
                                    } else {
                                        BeerData.searchInput += "producerName like '%" + searchText.getText() + "%'";
                                    }
                                }
                                if (advancedDescription.isSelected()) {
                                    if (advancedName.isSelected() || advancedProducer.isSelected() || advancedType.isSelected() || advancedCountry.isSelected()) {
                                        BeerData.searchInput += " or description like '%" + searchText.getText() + "%'";
                                        selectedIteams++;
                                    } else {
                                        BeerData.searchInput += "description like '%" + searchText.getText() + "%'";
                                    }
                                }

                                if (!advancedName.isSelected() && selectedIteams > 1) {

                                    BeerData.searchInput = BeerData.searchInput.substring(0, 260) + BeerData.searchInput.substring(262);
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
                if(cameraOpen) {
                    try {

                        closeWebcam();

                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                }

                // If only one result for a search, go straight to beer profile
                if ((BeerData.beer.size()==1)) {

                    BeerData.selectedBeer = BeerData.beer.get(0);
                    try {
                        mainScene.changeCenter("/com/group8/resources/views/beerDetails_center.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                if ((BeerData.beer.size()>1)) {

                    // load the result stage
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


                        // load wheel until task is finished//
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


                // load the result stage
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

    @FXML
    public void onBeerScan (ActionEvent event) throws Exception {

        cameraOpen = true;
        webCam = new SwingNode();
        SwingNode barcode = new SwingNode();
        pane = new BorderPane();
        workaroundButton = new Button();
        workaroundButton.setOnAction(e -> beerScanSearchButton.fire());
        Button webcamClose = new Button("X");
        webcamClose.setOnAction(e -> closeWebcam());


        //Dim background
        Navigation.primaryStage.setOpacity(0);


        // Webcam window settings
        webcamStage = new Stage();
        webcamScene = new Scene(pane, 400, 250);
        webcamStage.setScene(webcamScene);
        webcamStage.initStyle(StageStyle.TRANSPARENT);
        webcamStage.setAlwaysOnTop(true);
        webcamScene.setFill(Color.TRANSPARENT);
        pane.setCenter(webCam);
        pane.setTop(webcamClose);
        pane.setBorder(new Border(new BorderStroke(Paint.valueOf("orange"), BorderStrokeStyle.SOLID, new CornerRadii(5), new BorderWidths(3))));
        pane.setBackground(new Background(new BackgroundFill(Paint.valueOf("black"), new CornerRadii(8), null)));

        webcamStage.show();

        getWebcam(webCam);
    }

    private void getWebcam(final SwingNode webcam) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {

                BeerScanner scanner = new BeerScanner();
                webcam.setContent(scanner.panel);
            }
        });
    }

    public static void setBarcode(String string){
        barcode = string;
    }

    public static void checkIfbarcodeIsSet(){

        boolean running = true;
        do {
            if(barcode == null){
                continue;
            }
            else{
                try {
                    workaroundButton.fire();
                    running = false;
                }
                catch (NullPointerException e){
                }
            }
        }
        while (running);
    }

    public void closeWebcam() {
        webcamStage.close();
        Navigation.primaryStage.setOpacity(1);
        BeerScanner.disconnectWebcam();
        //System.exit(0);

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
        beerScanSearchButton.setVisible(false);
       if(UserData.userInstance != null){
    	   notButton.setDisable(false);
           String sqlSt = "Select notification from notifications where userId = "+ UserData.userInstance.getId() +";";
           ArrayList<ArrayList<Object>> notSql = MysqlDriver.selectMany(sqlSt);
           notNum = notSql.size();
           notButton.setText(notNum+"");
           notifications.setText("You have "+notNum+" Notifications");
           ObservableList<String> notMessage = FXCollections.observableArrayList();
           for(int i=0; i<notNum;i++){
        	   notMessage.add(notSql.get(i).get(0).toString());
           }
           notList.setItems(notMessage);
       }else{
    	   notButton.setDisable(true);
           notWindow.setVisible(false);
       }
        Load.setStyle("-fx-accent: IVORY");
        
    }

}
