package com.group8.controllers;

import com.group8.database.tables.Pub;

import com.group8.singletons.Navigation;
import com.group8.singletons.PubData;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.util.Callback;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Shiratori on 04/11/15.
 */
public class PubList extends BaseController implements Initializable
{

    @FXML
    public TableView<Pub> pubTable;
    @FXML
    public TableColumn<Pub, String> pubName;
    @FXML
    public TableColumn<Pub, String> pubEntranceFee;
    @FXML
    public TableColumn<Pub, String> pubPhoneNumber;
    @FXML
    public TableColumn<Pub, String> pubDescription;
    @FXML
    public TableColumn<Pub, String> pubOffer;
    @FXML
    public TableColumn<Pub,String> pubAddress;
    @FXML
    public TableColumn<Pub, Image> image;


    public ObservableList<Pub> masterData = FXCollections.observableArrayList(PubData.pubs);

    /**
     * Select a pub row and proceed to the beerDetail scene
     */
    public void getRow(){
        pubTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            // Select item will only be displayed when dubbleclicked

            /**
             * Dubleclick event
             * @param event
             */
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    // Show that we can select items and print it
                    int id = pubTable.getSelectionModel().getSelectedItem().getPubId();
                    // Has to be in a tr / catch becouse of the event missmatch, ouseevent cant throw IOexceptions

                    Pub selectedPub = new Pub("select * from pubs where pubID =" + id);
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
     * initialize result controller
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Navigation.current_CenterFXML = "/com/group8/resources/views/pubList.fxml";

        // You have to have a get function that is named get +" type" for it to work sets values.
        pubName.setCellValueFactory(new PropertyValueFactory<Pub, String>("name"));
        pubAddress.setCellValueFactory(new PropertyValueFactory<Pub, String>("address"));
        pubPhoneNumber.setCellValueFactory(new PropertyValueFactory<Pub, String>("phoneNumber"));
        pubOffer.setCellValueFactory(new PropertyValueFactory<Pub, String>("offers"));
        pubDescription.setCellValueFactory(new PropertyValueFactory<Pub, String>("description"));
        pubEntranceFee.setCellValueFactory(new PropertyValueFactory<Pub, String>("entranceFee"));


        // Try loading the image, if there is none will use placeholder
        image.setCellValueFactory(new PropertyValueFactory<Pub, Image>("image"));
        /**
         * Set the Cellfactory
         */
        image.setCellFactory(new Callback<TableColumn<Pub, Image>, TableCell<Pub, Image>>() {
            @Override
            public TableCell<Pub, Image> call(TableColumn<Pub, Image> param) {
                TableCell<Pub, Image> cell = new TableCell<Pub, Image>() {

                    /**
                     * Override the updateItem method to set a imageView
                     * @param item
                     * @param empty
                     */
                    @Override
                    public void updateItem(Image item, boolean empty) {

                        if(!empty) {
                            if (item != null) {
                                VBox vb = new VBox();
                                vb.setAlignment(Pos.CENTER);
                                ImageView imgVw = new ImageView();
                                imgVw.setImage(item);
                                imgVw.setFitWidth(20);
                                imgVw.setFitHeight(40);
                                vb.getChildren().addAll(imgVw);
                                setGraphic(vb);

                            } else {
                                VBox vb = new VBox();
                                vb.setAlignment(Pos.CENTER);
                                ImageView imgVw = new ImageView();
                                imgVw.setImage(new Image(new File("src/com/group8/resources/Images/beerHasNoImage.png").toURI().toString()));
                                imgVw.setFitWidth(20);
                                imgVw.setFitHeight(40);
                                vb.getChildren().addAll(imgVw);
                                setGraphic(vb);

                            }
                        }
                    }
                };
                return cell;
            }

        });


        //Populate the Tableview
        pubTable.setItems(masterData);

    }
}