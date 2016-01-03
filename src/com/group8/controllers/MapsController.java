package com.group8.controllers;

import com.group8.database.tables.MapMarker;
import com.group8.database.tables.Pub;
import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.MapComponentInitializedListener;
import com.lynden.gmapsfx.javascript.object.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Linus Eiderström Swahn and Andreas Fransson.
 **
 * The Google maps scene originateing from the BeerDetail scene
 * When a beer is sold by a pub this will display a google maps window with markers of those searchForPubsCheckbox
 * locations, and a tableview sorted by the price of the beer you previously selected.
 */
public class MapsController extends BaseController implements Initializable,MapComponentInitializedListener {

    @FXML
    public GoogleMapView mapView;
    @FXML
    public GoogleMap map;
    @FXML
    public TableColumn<MapMarker,String> pubsColumn;
    @FXML
    public TableColumn<MapMarker,String> priceColumn;
    @FXML
    public TableView showPubs;

    private ArrayList<Marker> markers = new ArrayList<>();
    public ObservableList<MapMarker> mapMarkerList = FXCollections.observableArrayList(BeerData.markers);

    /**
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        // Set current FXML
        Navigation.current_CenterFXML = "/com/group8/resources/views/googleMaps.fxml";

        // Add seconday initialize
        mapView.addMapInializedListener(this);

        // Populate the tableview
        pubsColumn.setCellValueFactory(new PropertyValueFactory<MapMarker, String>("PubName"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<MapMarker, String>("Price"));
        showPubs.setItems(mapMarkerList);

        showPubs.getSelectionModel().selectFirst();
    }

    /**
     * Select a Pub row and proceed to the beerDetail scene
     *
     * --> OnMouseClick get selected row
     */
    public void getRow() {
        showPubs.setOnMouseClicked(new EventHandler<MouseEvent>() {
            // Select item will only be displayed when dubbleclicked

            /**
             * Dubleclick event
             *
             * @param event
             */
            @Override
            public void handle(MouseEvent event) {

                // Single click switches the view over to that pub.
                if (event.getClickCount() == 1) {

                    // Set the selectedMarker instance of beer we have to selected item
                    int z = showPubs.getSelectionModel().getSelectedIndex();

                    for (int i = 0; i < markers.size(); i++) {
                        // Remove existing markers
                        map.removeMarker(markers.get(i));

                        // Repaint Hack
                        map.setZoom(14);
                        map.setZoom(15);
                        if(i == z) {
                            // Add the selected marker back to the map

                            map.addMarker(markers.get(z));
                            // make the infowindow for the selected marker
                            InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                            infoWindowOptions.content("<h2>" + BeerData.markers.get(i).getPubName() + "</h2>"
                                    + "Address: " + BeerData.markers.get(i).getAddress() + "<br>"
                                    + "Price. " + BeerData.markers.get(i).getPrice() + ":- <br>"
                                    + "in stock: " + BeerData.markers.get(i).InStock());
                            InfoWindow markerWindow = new InfoWindow(infoWindowOptions);
                            // Open the infowindow
                            markerWindow.open(map, markers.get(z));
                            // Set the center of the screen to the active markers position
                            map.setCenter(new LatLong(BeerData.markers.get(z).getLatitude(),BeerData.markers.get(z).getLongitude()));
                            // Set zoom to a good overview zoom
                            map.setZoom(15);
                        }

                    }
                    // If we double click, we enter the pub details.
                }else if(event.getClickCount() == 2){
                    int z = showPubs.getSelectionModel().getSelectedIndex();
                    Pub selectedPub = new Pub("select * from searchForPubsCheckbox where pubID =" + BeerData.markers.get(z).getPubID());
                    PubData.selectedPub = selectedPub;

                    try {
                        mainScene.changeCenter("/com/group8/resources/views/pubDetailView.fxml");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        });
    }

    /**
     * Secondary Initalizer
     *
     * --> Setup MapOptions
     * --> Create map via mapView added the MapOptions
     * --> Place the markers generated by searchForPubsCheckbox selling selectedBeer
     * --> Add InfoWindows to all markers with relative information
     */
        @Override
    public void mapInitialized() {

        //Set the initial properties of the map.
        MapOptions mapOptions = new MapOptions();

        mapOptions.center(new LatLong(BeerData.markers.get(0).getLatitude(), BeerData.markers.get(0).getLongitude()))
                .mapType(MapTypeIdEnum.ROADMAP)
                .overviewMapControl(true)
                .panControl(false)
                .rotateControl(false)
                .scaleControl(false)
                .streetViewControl(false)
                .zoomControl(true)
                .zoom(15);

        map = mapView.createMap(mapOptions);

        for(int i = 0; i < BeerData.markers.size(); i++) {

            LatLong markerLocation = new LatLong(BeerData.markers.get(i).getLatitude(), BeerData.markers.get(i).getLongitude());

            //Add markers to the map
            MarkerOptions markerOptions = new MarkerOptions();
            markerOptions.position(markerLocation);

            markerOptions.visible(true);

            Marker marker = new Marker(markerOptions);
            markers.add(marker);

            if(i == 0) {
                map.addMarker(markers.get(i));
                InfoWindowOptions infoWindowOptions = new InfoWindowOptions();
                infoWindowOptions.content("<h2>" + BeerData.markers.get(i).getPubName() + "</h2>"
                        + "Address: " + BeerData.markers.get(i).getAddress() + "<br>"
                        + "Price. " + BeerData.markers.get(i).getPrice() + ":- <br>"
                        + "in stock: " + BeerData.markers.get(i).InStock());
                InfoWindow markerWindow = new InfoWindow(infoWindowOptions);
                markerWindow.open(map, markers.get(i));
                map.setCenter(new LatLong(BeerData.markers.get(i).getLatitude(),BeerData.markers.get(i).getLongitude()));
                map.setZoom(15);
            }else {
                map.addMarker(markers.get(i));
            }
        }
    }
}
