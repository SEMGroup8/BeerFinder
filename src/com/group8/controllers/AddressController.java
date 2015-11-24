package com.group8.controllers;

import com.group8.database.tables.Beer;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import com.sun.javafx.stage.WindowHelper;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.stage.*;
import javafx.stage.Window;
import netscape.javascript.JSObject;

import javax.swing.plaf.ComponentInputMapUIResource;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.stream.IntStream;

/**
 * Created by AnkanX on 15-11-18.
 *
 * The address popup window that originates from the PubInfo window
 * When a Pub owner wants to add an address.
 *
 */
public class AddressController implements Initializable,MapComponentInitializedListener {

    // Connect the FXML elements
    @FXML
    public Node root;
    @FXML
    public Label centerScreen;
    @FXML
    public Label click;
    @FXML
    public GoogleMapView mapView;
    @FXML
    public GoogleMap map;
    @FXML
    public Button getCenter;
    @FXML
    public Button add;

    // Tmp address for sending to main window
    LatLong address;

    // We will only have one active marker so we keep track of it here
    Marker marker;

    ImageView img= new ImageView((this.getClass().getResource("/com/group8/resources/Images/Icon_2.png").toString()));


    /**
     * Sets a label to the center coordinates of the map when button pressed.
     */
    public void getCenter(){
        centerScreen.setText(map.getCenter().toString());
        map.setCenter(new LatLong(57.7065806, 11.9294398));
        map.setZoom(15);

    }

    public void addAddress(){
        img.setFitWidth(60);
        img.setFitHeight(60);
        // Set global to address
        BeerData.Address = address;
        if (BeerData.Address == null){

            // Show confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Address Information");
            alert.setHeaderText("Alert!");
            alert.setContentText("No Address selected!");
            alert.setGraphic(img);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/com/group8/resources/Images/Icon.png"));
            alert.showAndWait();
        }else {

            // Show confirmation
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Address Information");
            alert.setHeaderText("Alert!");
            alert.setContentText("Your Address has been added to the main window!");
            alert.setGraphic(img);
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("file:src/com/group8/resources/Images/Icon.png"));
            alert.showAndWait();
            root.getScene().getWindow().hide();


        }

        // get a handle to the stage
        //Stage stage = (Stage) root.getScene().getWindow();
        // do what you have to do
        //stage.close();


    }

    /**
     * Initialize the Address window
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapView.addMapInializedListener(this);
        System.out.println("loaded test!");
    }

    /**
     * Initialize the map
     */
    @Override
    public void mapInitialized() {

        // Hack for fixing corner cases
       // address = new LatLong(0.0,0.0);
        //BeerData.Address = address;

        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();
        LatLong start = new LatLong(57.7065806, 11.9294398);

        // Set the formating of our map
        mapOptions.center(start)
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(false)
                .panControl(true)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(false)
                .zoom(15)
                .mapTypeControl(false);


        // Create the map with the formating
        map = mapView.createMap(mapOptions);

        // Add a starter marker
        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(start);
        markerOptions2.visible(true);

        // Startmarker
        marker = new Marker(markerOptions2);

        // Show longlat of the center map
        centerScreen.setText(start.toString());

        // Gets the position of a click without drag
        map.addUIEventHandler(UIEventType.click, (JSObject obj) -> {
            LatLong ll = new LatLong((JSObject) obj.getMember("latLng"));
            System.out.println("LatLong: lat: " + ll.getLatitude() + " lng: " + ll.getLongitude());
            click.setText(ll.toString());
        });


        // Gets the center of the screen after the mouse button is released after a drag action.
        map.addUIEventHandler(UIEventType.mouseup, (JSObject obj) -> {
            LatLong l2 = new LatLong(map.getCenter().getLatitude(), map.getCenter().getLongitude());
            //System.out.println("LatLong: lat: " + ll.getLatitude() + " lng: " + ll.getLongitude());
            centerScreen.setText(l2.toString());
        });

        // Double click on map place a marker
        map.addUIEventHandler(UIEventType.dblclick,(JSObject obj) -> {
            map.removeMarker(marker);
            LatLong l3 = new LatLong((JSObject) obj.getMember("latLng"));
            MarkerOptions markerOptions1 = new MarkerOptions();
            markerOptions1.position(l3);
            markerOptions1.visible(true);
            markerOptions1.animation(Animation.DROP)
                    .visible(true)
                    .title("Point 1");

            // Apply the formating of the marker to a new marker
            marker = new Marker(markerOptions1);

            // Save the current markers latlong
            address = l3;

            // Add the marker to the map
            map.addMarker(marker);

            // Adding a info window with the long lat of the marker
            InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
            infoWindowOptions.content("<h2>" + " Your Pub "+ "</h2>"
                    + "LatLong: lat: " + l3.getLatitude() + " lng: " + l3.getLongitude() + "<br>");
            InfoWindow markerWindow = new InfoWindow(infoWindowOptions);
            markerWindow.open(map,marker);



            // Map repaint hack
            map.setZoom(map.getZoom()-1);
            map.setZoom(map.getZoom()+1);



        });


    }


}
