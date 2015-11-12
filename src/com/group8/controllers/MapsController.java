package com.group8.controllers;

import com.group8.database.tables.Beer;
import com.group8.database.tables.MapMarker;
import com.group8.database.tables.Pub;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;



/**
 * Created by AnkanX on 15-10-24.
 *
 * TODO learn the goglefx API and implement it
 *
 */
public class MapsController implements Initializable,MapComponentInitializedListener {

    @FXML
    public Button Back;
    @FXML
    public Button Home;
    @FXML
    public GoogleMapView mapView;
    @FXML
    public GoogleMap map;
    @FXML
    public TableColumn<MapMarker,String> pubsColumn;
    @FXML
    public TableView showPubs;

    private ArrayList<Marker> markers = new ArrayList<>();

    public ObservableList<MapMarker> masterData = FXCollections.observableArrayList(BeerData.markers);

    /**
     * Back button pressed takes you back to "result screen"
     * @param event
     * @throws IOException
     */
    @FXML
    public void backAction(ActionEvent event) throws IOException {
        Parent homescreen = FXMLLoader.load(getClass().getResource(Navigation.resultviewFXML));
        Scene result_scene = new Scene(homescreen, 800, 600);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();
    }

    /**
     * Home Button
     * @param event
     * @throws IOException
     */
    @FXML
    public void returnHome(ActionEvent event) throws IOException {
        Parent homescreen = FXMLLoader.load(getClass().getResource(Navigation.homescreenFXML));
        Scene result_scene = new Scene(homescreen, 800, 600);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        mapView.addMapInializedListener(this);
        pubsColumn.setCellValueFactory(new PropertyValueFactory<MapMarker, String>("PubName"));
        showPubs.setItems(masterData);

    }

    @Override
    public void mapInitialized() {




        // TODO add the geoPosition column to the pub table ( string double,double ( maybe have secure input method in addpub?))
        // TODO make string -> double double converter
        // TODO set coordinates to BeerData.selectedBeerPubs.geoPosition(1 result)
        // TODO set propertyValueFactory of the showPubs column to populate with all the pubs that have the beer
        // TODO set markers of all the pubs on map, and add dubbleClick listener so when u click a beer in the column the view is transported to that marker
        // TODO able to "follow" pubs marked by markers

        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(BeerData.markers.get(1).getLatitude(), BeerData.markers.get(1).getLongitude()))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(true)
                .zoom(15);

        map = mapView.createMap(mapOptions);

        for(int i = 0; i < BeerData.markers.size(); i++){

            LatLong markerLocation = new LatLong(BeerData.markers.get(i).getLatitude(), BeerData.markers.get(i).getLongitude());
            System.out.println("Loaded: " + BeerData.markers.get(i).getLatitude() + " " + BeerData.markers.get(i).getLongitude());


            //Add markers to the map
            MarkerOptions markerOptions1 = new MarkerOptions();
            markerOptions1.position(markerLocation);


            Marker marker = new Marker(markerOptions1);
            markers.add(marker);

            map.addMarker(markers.get(i));

            InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
            infoWindowOptions.content("<h2>" + BeerData.markers.get(i).getPubName() + "</h2>"
                    + "Address: " + BeerData.markers.get(i).getAddress() + "<br>"
                    + "Price. " + BeerData.markers.get(i).getPrice() + ":-");

            InfoWindow markerWindow = new InfoWindow(infoWindowOptions);
            markerWindow.open(map, markers.get(i));

            System.out.println(BeerData.markers.get(i).getAddress());
        }
    }
}
