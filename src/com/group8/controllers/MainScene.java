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

    public void changeCenter(String url) throws IOException
    {

        Navigation.backFXML = Navigation.current_CenterFXML;

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

    public void changeTop(String url) throws IOException
    {
        top.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));

        top.getChildren().add(loader.load());

        BaseController topController = (BaseController)loader.getController();

        topController.init(this);



    }

    public void goHome() throws IOException
    {

        changeCenter(Navigation.homescreenFXML);

    }

    public void goBack() throws IOException {


        if (Navigation.backFXML.equals("/com/group8/resources/views/result_center.fxml")) {

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
                            changeCenter(Navigation.backFXML);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
            });

            // Start thread
            backgroundThread.start();


        }




    }








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
