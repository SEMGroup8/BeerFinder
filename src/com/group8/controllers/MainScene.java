package com.group8.controllers;

import com.group8.database.MysqlDriver;
import com.group8.database.tables.Beer;
import com.group8.database.tables.MapMarker;
import com.lynden.gmapsfx.MainApp;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Linus Eiderström Swahn
 *
 * The controller for the main root scene of the application.
 *
 * Contains a top, center and bottom element which the content is switched out of depending where the user is.
 *
 * Implements Initializable so that we can tale advantage of the initialize() function.
 */

public class MainScene implements Initializable {
    @FXML
    private HBox center, top;
    @FXML
    public Button backButton;
    @FXML
    public Button homeButton;
    @FXML
    public Button mapsButton;
    @FXML
    public ProgressIndicator Load;
    @FXML
    Button helpButton;
    @FXML
    public Label gmapsError;
    Service<Void> backgroundThread;

    /**
     * Created by Linus Eiderström Swahn.
     *
     * initialize is defined in the Initializable interface.
     * It runs once when the controller loads when the fxml is called.
     *
     * Sets the default location in the application, the home screen and the logged out top screen.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Navigation.homescreenFXML = "/com/group8/resources/views/home_center.fxml";
        Navigation.current_CenterFXML =  "/com/group8/resources/views/home_center.fxml";

        Load.setStyle("-fx-accent: IVORY");

        // Sets the center and top fxml-documents.
        try {
            center.getChildren().clear();
            top.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group8/resources/views/home_center.fxml"));

            center.getChildren().add(loader.load());

            HomeCenter centerController = (HomeCenter)loader.getController();

            centerController.init(this);

            loader = new FXMLLoader(getClass().getResource("/com/group8/resources/views/home_top.fxml"));
            top.getChildren().add(loader.load());

            HomeTop topController = (HomeTop)loader.getController();

            topController.init(this);

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * Created by Linus Eiderström Swahn.
     *
     * Change the center FXML-document.
     *
     * @param url
     * The path to the new fxml-document.
     * @throws IOException
     */
    public void changeCenter(String url) throws IOException
    {
        // Remember my past
        Navigation.breadcrubs.add(Navigation.current_CenterFXML);

        center.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));

        center.getChildren().add(loader.load());

        // All controllers inherit from BaseController, so this is generic.
        BaseController centerController = (BaseController)loader.getController();

        centerController.init(this);
        if (url.equals("/com/group8/resources/views/beerDetails_center.fxml"))
        {
            mapsButton.setVisible(true);
        }else{
            mapsButton.setVisible(false);
        }

        if(!url.equals("/com/group8/resources/views/home_center.fxml"))
        {
            backButton.setVisible(true);
            homeButton.setVisible(true);
        }else
        {
            backButton.setVisible(false);
            homeButton.setVisible(false);
        }

    }

    /**
     * Created by Andreas Fransson.
     *
     * Change center FXML back to last visited center FXML
     * --> Used by the "Back" button to load last entry in the Navigation.breadcrubs Array.
     * @param url
     * @throws IOException
     */
    public void changeCenterBack(String url) throws IOException
    {

        Navigation.breadcrubs.remove(Navigation.breadcrubs.size()-1);

        for(int i =0; i <= Navigation.breadcrubs.size()-1;i++) {
            System.out.println(" Nav  now ->" + i + " ->" + Navigation.breadcrubs.get(i));
        }


        center.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));

        center.getChildren().add(loader.load());

        BaseController topController = (BaseController)loader.getController();


        topController.init(this);
        if (url.equals("/com/group8/resources/views/beerDetails_center.fxml"))
        {
            mapsButton.setVisible(true);
        }else{
            mapsButton.setVisible(false);
        }

        if(!url.equals("/com/group8/resources/views/home_center.fxml"))
        {
            backButton.setVisible(true);
            homeButton.setVisible(true);
        }else
        {
            backButton.setVisible(false);
            homeButton.setVisible(false);
        }

    }

    /**
     * Created by Linus Eiderström Swahn.
     *
     * Change the top FXML-document
     *
     * @param url
     * Path to the new fxml-document.
     * @throws IOException
     */
    public void changeTop(String url) throws IOException
    {
        top.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));

        top.getChildren().add(loader.load());

        // All controllers inherit from BaseController, so this i generic.
        BaseController topController = (BaseController)loader.getController();

        topController.init(this);

    }

    /**
     * Created by Andreas Fransson.
     *
     * Change the center FXML to Home_center
     * --> Used by the "Home" button to allways return to default Home_center.
     * @throws IOException
     */
    public void goHome() throws IOException
    {
        changeCenter(Navigation.homescreenFXML);
        Navigation.breadcrubs = new ArrayList<String>();
    }

    /**
     * Created by Andreas Fransson.
     *
     * Change the current center FXML to the last visited. (not duplicate)
     * --> Used by the "Back" button to change current FXML to last visited
     *     last entry in Navigation.breadcrubs Array, and if the last visited
     *     was the result view it should update the list of beers in case of changes.
     * @throws IOException
     */
    public void goBack() throws IOException {

        // We need to make a new search since we went back to the result screen.
        if (Navigation.breadcrubs.get(Navigation.breadcrubs.size()-1).equals("/com/group8/resources/views/result_center.fxml")) {

            // Set background service different from the UI fx thread to run stuff on
            backgroundThread = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {

                            // load wheel until task is finished//
                            Load.setVisible(true);

                            // Update the beer list for changes
                            BeerData.beer = new ArrayList<Beer>();
                            ArrayList<ArrayList<Object>> sqlData;
                            System.out.println(BeerData.searchInput);
                            sqlData = MysqlDriver.selectMany(BeerData.searchInput);

                            for (int i = 0; i < sqlData.size(); i++) {
                                // Add a new Beer to the beer arraylist
                                Beer beer = new Beer(sqlData.get(i));
                                // Testoutput
                                //System.out.print(beer.getName()+"\n");
                                BeerData.beer.add(beer);
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

                        // load the result stage
                        try {
                            changeCenterBack(Navigation.breadcrubs.get(Navigation.breadcrubs.size()-1));

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

            // Start thread
            backgroundThread.start();

        }else{
            // load the result stage
            try {
                changeCenterBack(Navigation.breadcrubs.get(Navigation.breadcrubs.size() - 1));

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Created by Andreas Fransson and Linus Eiderström Swahn.
     *
     * Gets all searchForPubsCheckbox who has this beer.
     * Creates MapMarkers for each and then loads the map view.
     *
     * @param event
     * @throws IOException
     */
    public void getMaps(ActionEvent event) throws IOException {

        BeerData.markers = new ArrayList<MapMarker>();

        String sqlQuery = "SELECT beerInPub.pubID, name, address, price, latitude, longitude, inStock " +
                "from searchForPubsCheckbox, pubAddress, beerInPub " +
                "where searchForPubsCheckbox.pubID = beerInPub.pubID " +
                "and searchForPubsCheckbox.addressID = pubAddress.addressID " +
                "and beerInPub.beerID = " + BeerData.selectedBeer.getId() + " " +
                "order by price asc";

        // Execute user query to get markers
        ArrayList<ArrayList<Object>> sqlData;
        sqlData = MysqlDriver.selectMany(sqlQuery);

        for (int i = 0; i < sqlData.size(); i++) {

            // Add a new marker to the beer arraylist
            MapMarker marker = new MapMarker(sqlData.get(i));
            BeerData.markers.add(marker);
        }

        // Does any pub have this beer in it's inventory?
        if ((BeerData.markers.size() > 0)) {

            // load the result stage
            changeCenter("/com/group8/resources/views/googleMaps.fxml");
        } else {
            // Testoutput
            ArrayList<ArrayList<Object>> geoData = MysqlDriver.selectMany(sqlQuery);
            gmapsError.setVisible(true);
        }
    }

    /**
     * Created by Andreas Fransson.
     *
     * @throws IOException
     */
    public void help() throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(MainApp.class.getResource("/com/group8/resources/views/help.fxml"));
        BorderPane page = loader.load();
        // Create the dialog Stage.
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Help");
        dialogStage.initModality(Modality.WINDOW_MODAL);
        dialogStage.initOwner(Navigation.primaryStage);
        Scene scene = new Scene(page);
        dialogStage.setScene(scene);
        dialogStage.getIcons().add(new Image("file:src/com/group8/resources/Images/Icon.png"));
        // Show the dialog and wait until the user closes it
        dialogStage.show();
    }
}
