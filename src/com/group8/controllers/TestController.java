package com.group8.controllers;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.*;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by AnkanX on 15-11-18.
 */
public class TestController implements Initializable,MapComponentInitializedListener {
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

    Marker marker;

    public void getCenter(){
        centerScreen.setText(map.getCenter().toString());

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        mapView.addMapInializedListener(this);
        System.out.println("loaded test!");
    }

    @Override
    public void mapInitialized() {
        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();
        LatLong start = new LatLong(57.7065806, 11.9294398);

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



        map = mapView.createMap(mapOptions);
        MarkerOptions markerOptions2 = new MarkerOptions();
        markerOptions2.position(start);
        // markerOptions1.icon("html/mymarker.png");
        markerOptions2.visible(true);
        markerOptions2.animation(Animation.DROP);

        marker = new Marker(markerOptions2);

        centerScreen.setText(start.toString());
        // gets the position of a click without drag
        map.addUIEventHandler(UIEventType.click, (JSObject obj) -> {
            LatLong ll = new LatLong((JSObject) obj.getMember("latLng"));
            System.out.println("LatLong: lat: " + ll.getLatitude() + " lng: " + ll.getLongitude());
            click.setText(ll.toString());
        });
        // gets the center of the screen after the mouse button is released
        map.addUIEventHandler(UIEventType.mouseup, (JSObject obj) -> {
            LatLong l2 = new LatLong(map.getCenter().getLatitude(), map.getCenter().getLongitude());
            //System.out.println("LatLong: lat: " + ll.getLatitude() + " lng: " + ll.getLongitude());
            centerScreen.setText(l2.toString());
        });

        map.addUIEventHandler(UIEventType.dblclick,(JSObject obj) -> {
            map.removeMarker(marker);
            LatLong l3 = new LatLong((JSObject) obj.getMember("latLng"));
            MarkerOptions markerOptions1 = new MarkerOptions();
            markerOptions1.position(l3);
            // markerOptions1.icon("html/mymarker.png");
            markerOptions1.visible(true);
            markerOptions1.animation(Animation.DROP);

            marker = new Marker(markerOptions1);
            map.addMarker(marker);
            

        });


    }


}
