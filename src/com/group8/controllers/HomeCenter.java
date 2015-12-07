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
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

/**
 * Created by Shiratori on 24/11/15.
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
    public ProgressBar progressBar;
    public ProgressIndicator Load;

    private SwingNode webCam;
    private BorderPane pane;
    private Stage webcamStage;
    private Scene webcamScene;
    private static String barcode = null;
    private static Button workaroundButton;

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

                        //Get source of pressed button
                        Object source = event.getSource();

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

                            //Mantas doesnt know what hes doing

                            System.out.println("Source 1: "+ ((Node)event.getSource()).getId());
                            System.out.println("Source 2: "+ beerScanSearchButton.getId());

                            //If request comes from the barcode scanner

                            if(((Node)event.getSource()).getId() == beerScanSearchButton.getId()){

                                BeerData.searchInput = "SELECT distinct `beerID`,`name`,`image`,`description`,beerTypeEN,countryName, percentage, producerName, volume, isTap, packageTypeEN, price, avStars, countryFlag, barcode" +
                                        " from beers, beerType, origin, package where " +
                                        "beers.beerTypeID = beerType.beerTypeID " +
                                        "and beers.originID = origin.originID " +
                                        "and beers.package = package.packageID " +
                                        "and (beers.barcode = " + barcode;

                                System.out.println(BeerData.searchInput);
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
                                    System.out.println(BeerData.searchInput.substring(260, 262));

                                    BeerData.searchInput = BeerData.searchInput.substring(0,260) + BeerData.searchInput.substring(262);
                                    // Test Output
                                    System.out.println(BeerData.searchInput);
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
                webcamStage.close();
                Navigation.primaryStage.setOpacity(1); //Undim stage

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

    @FXML
    public void onRandom (ActionEvent event) throws Exception{

        mainScene.changeCenter("/com/group8/resources/views/RandomBeerScenes/scene1.fxml");
    }
    
    @FXML
    public void onShowPubs (ActionEvent event) throws Exception{

    	String listOfPub = "select pubs.pubID,`name`,image, `phoneNumber`, `description`, `offers`, `entrenceFee` from   pubs";
		ArrayList<ArrayList<Object>> SQLData4;

		SQLData4 = MysqlDriver.selectMany(listOfPub);
		System.out.println(SQLData4 + " meeeee");
		ArrayList<Pub> pubListDetails = new ArrayList<Pub>();

		for (int i = 0; i < SQLData4.size(); i++) {
			// Add a new Beer to the beer arraylist
			Pub pub = new Pub(SQLData4.get(i));
			// Testoutput
			System.out.println(pub.get_name() + "  which pub???");
			pubListDetails.add(pub);

		}
		
		PubData.pubs = pubListDetails;
		
    	mainScene.changeCenter("/com/group8/resources/views/pubList.fxml");
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

        System.out.println("Inside onBeerScan " + barcode);

        webcamStage.show();

        getWebcam(webCam);

        //System.out.println(Arrays.toString(com.group8.resources.Tools.ThreadUtilities.getAllThreadInfos()));
        //System.out.println(com.group8.resources.Tools.ThreadUtilities.getAllThreadInfos());
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

    public void closeWebcam(){
        webcamStage.close();
        Navigation.primaryStage.setOpacity(1);
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
    }

}
