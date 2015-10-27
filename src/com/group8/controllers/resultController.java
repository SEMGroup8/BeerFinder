package com.group8.controllers;

import com.group8.database.tables.Beer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javax.security.auth.callback.Callback;
import javax.swing.text.TabableView;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by AnkanX on 15-10-22.
 *
 * TODO Visual upgrade + presentation of objects
 *
 */
public class ResultController implements Initializable {

    @FXML
    public Button Back;
    @FXML
    public Button Maps;
    @FXML
    public TableView<Beer> beerTable;
    @FXML
    public TableColumn<Beer, String> beerName;
    @FXML
    public TableColumn<Beer, String> beerType;
    @FXML
    public TableColumn<Beer, String> beerDescription;
    @FXML
    public TableColumn<Beer, String> beerOrigin;
    @FXML
    public TableColumn<Beer, String> beerProducer;
    @FXML
    public TableColumn<Beer, String> beerPackage;
    @FXML
    public TableColumn<Beer, String> beerPercentage;
    @FXML
    public TableColumn<Beer, String> beerIsTap;
    @FXML
    public TableColumn<Beer, String> image;




    public ObservableList<Beer> masterData = FXCollections.observableArrayList(BeerData.beer);


    /*
        Back button pressed takes you back to "home screen"
        @param
    */
    @FXML
    public void backAction(ActionEvent event) throws IOException {
        Parent homescreen = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/homeScreen.fxml"));
        Scene result_scene = new Scene(homescreen, 800, 600);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();

    }

    @FXML
    public void getMaps(ActionEvent event) throws IOException {
        Parent homescreen = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/googleMaps.fxml"));
        Scene result_scene = new Scene(homescreen, 800, 600);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();


    }

    public void getRow() {
        beerTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    System.out.println("clicked on " + beerTable.getSelectionModel().getSelectedItem());
                }
            }
        });
    }


    /*
        initialize result controller
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        // You have to have a get function that is named get +" type" for it to work sets values.
        beerName.setCellValueFactory(new PropertyValueFactory<Beer, String>("Name"));
        beerType.setCellValueFactory(new PropertyValueFactory<Beer, String>("Type"));
        beerDescription.setCellValueFactory(new PropertyValueFactory<Beer, String>("Description"));
        beerOrigin.setCellValueFactory(new PropertyValueFactory<Beer, String>("Origin"));
        beerProducer.setCellValueFactory(new PropertyValueFactory<Beer, String>("Producer"));
        beerPackage.setCellValueFactory(new PropertyValueFactory<Beer, String>("BeerPackage"));
        beerIsTap.setCellValueFactory(new PropertyValueFactory<Beer, String>("IsTap"));
        beerPercentage.setCellValueFactory(new PropertyValueFactory<Beer, String>("Percentage"));
        //Populate the Tableview
        beerTable.setItems(masterData);

    }
}
