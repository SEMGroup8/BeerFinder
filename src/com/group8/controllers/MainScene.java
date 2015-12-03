package com.group8.controllers;

import com.group8.database.MysqlDriver;
import com.group8.database.tables.Beer;
import com.group8.database.tables.MapMarker;
import javafx.concurrent.Service;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Shiratori on 24/11/15.
 *
 * Main Graphical Controller aka root
 * --> Used as a canvas to paint content on ex changeing the top and center FXML's
 *     to navigate the user around the program functions.
 */


public class MainScene implements Initializable {
    @FXML
    private HBox center, top;
    @FXML
    private Node root;
    @FXML
    public Button backButton;
    @FXML
    public Button homeButton;
    @FXML
    public Button mapsButton;
    @FXML
    public ProgressIndicator Load;

    Service<Void> backgroundThread;

    /**
     * Initialize function
     * --> Sets the default Home screen FXML's ( Home_top and Home_center )
     *     to current FXML's and loads their controllers.
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Navigation.homescreenFXML = "/com/group8/resources/views/home_center.fxml";
        Navigation.current_CenterFXML =  "/com/group8/resources/views/home_center.fxml";


        try {
            center.getChildren().clear();
            top.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group8/resources/views/home_center.fxml"));

            center.getChildren().add(loader.load());

            HomeCenter centerController = (HomeCenter)loader.getController();

            centerController.init(this);

            //
            loader = new FXMLLoader(getClass().getResource("/com/group8/resources/views/home_top.fxml"));
            top.getChildren().add(loader.load());

            HomeTop topController = (HomeTop)loader.getController();

            topController.init(this);


            System.out.println("test");




        } catch (IOException e) {

            System.out.println("test2");
            e.printStackTrace();
        }
    }

    /**
     * Change the center FXML
     * --> Used to change the center FXML to the supplied URL FXML and load its controller
     * @param url
     * @throws IOException
     */
    public void changeCenter(String url) throws IOException
    {
        // Remember my past
        Navigation.breadcrubs.add(Navigation.current_CenterFXML);


        for(int i = 0; i < Navigation.breadcrubs.size();i++){
            System.out.println(" Nav " + i +" ->" + Navigation.breadcrubs.get(i));
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
     * Change the top FXML
     * --> Used by the Login & Logout function to change the top FXML to supplied URL FXML.
     * @param url
     * @throws IOException
     */
    public void changeTop(String url) throws IOException
    {
        top.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));

        top.getChildren().add(loader.load());

        BaseController topController = (BaseController)loader.getController();

        topController.init(this);



    }

    /**
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
     * Change the current center FXML to the last visited. (not duplicate)
     * --> Used by the "Back" button to change current FXML to last visited
     *     last entry in Navigation.breadcrubs Array, and if the last visited
     *     was the result view it should update the list of beers in case of changes.
     * @throws IOException
     */
    public void goBack() throws IOException {


        if (Navigation.breadcrubs.get(Navigation.breadcrubs.size()-1).equals("/com/group8/resources/views/result_center.fxml")) {

            // Set background service diffrent from the UI fx thread to run stuff on( i know indentation is retarded)
            backgroundThread = new Service<Void>() {
                @Override
                protected Task<Void> createTask() {
                    return new Task<Void>() {
                        @Override
                        protected Void call() throws Exception {


                            // Load wheel until task is finished//
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


                        // Load the result stage
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
            // Load the result stage
            try {
                changeCenterBack(Navigation.breadcrubs.get(Navigation.breadcrubs.size() - 1));

            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    /**
     * Changes the center FXML to the googleMaps.FXML
     * --> Used by the "google maps" button for when in the beerdetail view
     *     and want to see where the beer is in stock.
     * @param event
     * @throws IOException
     */
    public void getMaps(ActionEvent event) throws IOException {

        BeerData.markers = new ArrayList<MapMarker>();

        // TODO SQL query for getting Pubs that have the BeerData.selectedBeer

        // populate the tableView with those pubs

        String sqlQuery = "SELECT beerInPub.pubID, name, address, price, latitude, longitude, inStock " +
                "from pubs, pubAddress, beerInPub where " +
                "pubs.pubID = beerInPub.pubID " +
                "and pubs.addressID = pubAddress.addressID " +
                "and beerInPub.beerID = " + BeerData.selectedBeer.getId() + " " +
                "order by price asc";

        System.out.println(sqlQuery);
        // Execute user query to get markers
        ArrayList<ArrayList<Object>> sqlData;
        sqlData = MysqlDriver.selectManyOther(sqlQuery);

        for (int i = 0; i < sqlData.size(); i++) {
            // Add a new marker to the beer arraylist
            MapMarker marker = new MapMarker(sqlData.get(i));
            BeerData.markers.add(marker);
            System.out.println(marker.isInStock());

            System.out.println(marker.getPrice());
        }

        if ((BeerData.markers.size() > 0)) {

            // Load the result stage
            changeCenter("/com/group8/resources/views/googleMaps.fxml");
        } else {

            System.out.println(sqlQuery);
            ArrayList<ArrayList<Object>> geoData = MysqlDriver.selectManyOther(sqlQuery);
            System.out.println(geoData.size());
            System.out.println("No Pubs selling this beer");
           // gMapsError.setVisible(true);
        }
    }
}
