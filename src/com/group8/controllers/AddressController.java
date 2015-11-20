package com.group8.controllers;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

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
    public Label centerScreen;
    @FXML
    public Label click;
    @FXML
    public GoogleMapView mapView;
    @FXML
    public GoogleMap map;
    @FXML
    public Button getCenter;

    // We will only have one active marker so we keep track of it here
    Marker marker;

    /**
     * Sets a label to the center coordinates of the map when button pressed.
     */
    public void getCenter(){
        centerScreen.setText(map.getCenter().toString());

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

            // Add the marker to the map
            map.addMarker(marker);

            // Adding a info window with the long lat of the marker
            InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
            infoWindowOptions.content("<h2> Your Pub </h2>"
                    + "LatLong: lat: " + l3.getLatitude() + " lng: " + l3.getLongitude() + "<br>");
            InfoWindow markerWindow = new InfoWindow(infoWindowOptions);
            markerWindow.open(map,marker);



            // Map repaint hack
            map.setZoom(map.getZoom()-1);
            map.setZoom(map.getZoom()+1);



        });


    }


}
