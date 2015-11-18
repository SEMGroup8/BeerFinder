package com.group8.controllers;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.event.UIEventType;
import com.lynden.gmapsfx.javascript.object.GoogleMap;
import com.lynden.gmapsfx.javascript.object.LatLong;
import com.lynden.gmapsfx.javascript.object.MapOptions;
import com.lynden.gmapsfx.javascript.object.MapTypeIdEnum;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import netscape.javascript.JSObject;

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
        
        centerScreen.setText(start.toString());
        // gets the position of a click without drag
        map.addUIEventHandler(UIEventType.click, (JSObject obj) -> {
            LatLong ll = new LatLong((JSObject) obj.getMember("latLng"));
            //System.out.println("LatLong: lat: " + ll.getLatitude() + " lng: " + ll.getLongitude());
            click.setText(ll.toString());
        });
        // gets the center of the screen after the mouse button is released
        map.addUIEventHandler(UIEventType.mouseup, (JSObject obj) ->{
            LatLong l2 = new LatLong(map.getCenter().getLatitude(),map.getCenter().getLongitude());
        //System.out.println("LatLong: lat: " + ll.getLatitude() + " lng: " + ll.getLongitude());
        centerScreen.setText(l2.toString());
        });

    }


}
