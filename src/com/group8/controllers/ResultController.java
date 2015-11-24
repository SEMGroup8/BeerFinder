package com.group8.controllers;

import com.group8.database.tables.Beer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
 * Created by AnkanX on 15-10-22.
 *
 * TODO Visual upgrade + presentation of objects + additional options
 *
 */
public class ResultController extends BaseController implements Initializable {


    @FXML
    public TableView<Beer> beerTable;
    @FXML
    public TableColumn<Beer, String> beerName;
    @FXML
    public TableColumn<Beer, String> beerType;
    @FXML
    public TableColumn<Beer, String> beerOrigin;
    @FXML
    public TableColumn<Beer, String> beerProducer;
    @FXML
    public TableColumn<Beer, String> beerPackage;
    @FXML
    public TableColumn<Beer, String> beerPercentage;
    @FXML
    public TableColumn<Beer, String> avrageRank;
    @FXML
    public TableColumn<Beer,Image> beerImage;
    @FXML
    public TableColumn<Beer,String> beerPrice;







    public ObservableList<Beer> masterData = FXCollections.observableArrayList(BeerData.beer);


    /**
     * Back button pressed takes you back to "home screen"
     * @param event
     * @throws IOException
     */




           /**
         * Select a beer row and proceed to the beerDetail scene
         */
    public void getRow(){
        beerTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            // Select item will only be displayed when dubbleclicked

            /**
             * Dubleclick event
             * @param event
             */
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                        // Show that we can select items and print it
                        System.out.println("clicked on " + beerTable.getSelectionModel().getSelectedItem());
                        // Set the selectedBeer instance of beer we have to selected item
                        BeerData.selectedBeer = beerTable.getSelectionModel().getSelectedItem();
                        // Load the details scene
                        // Has to be in a tr / catch becouse of the event missmatch, ouseevent cant throw IOexceptions
                        try {
                            // TODO have to fix nameing
                            mainScene.changeCenter("/com/group8/resources/views/beerDetails_center.fxml");
                        } catch (IOException e) {
                            // Print error msg
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

       // Navigation.backFXML = Navigation.current_CenterFXML;
        Navigation.current_CenterFXML = "/com/group8/resources/views/result_center.fxml";

        // You have to have a get function that is named get +" type" for it to work sets values.
        beerName.setCellValueFactory(new PropertyValueFactory<Beer, String>("Name"));
        beerType.setCellValueFactory(new PropertyValueFactory<Beer, String>("Type"));
        beerOrigin.setCellValueFactory(new PropertyValueFactory<Beer, String>("Origin"));
        beerProducer.setCellValueFactory(new PropertyValueFactory<Beer, String>("Producer"));
        beerPackage.setCellValueFactory(new PropertyValueFactory<Beer, String>("BeerPackage"));
        avrageRank.setCellValueFactory(new PropertyValueFactory<Beer, String>("AvRank"));
        beerPercentage.setCellValueFactory(new PropertyValueFactory<Beer, String>("Percentage"));
        beerPrice.setCellValueFactory(new PropertyValueFactory<Beer, String>("Price"));



        // Try loading the image, if there is none will use placeholder
        beerImage.setCellValueFactory(new PropertyValueFactory<Beer, Image>("Image"));
        /**
         * Set the Cellfactory
         */
        beerImage.setCellFactory(new Callback<TableColumn<Beer, Image>, TableCell<Beer, Image>>() {
            @Override
            public TableCell<Beer, Image> call(TableColumn<Beer, Image> param) {
                TableCell<Beer, Image> cell = new TableCell<Beer, Image>() {

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
        beerTable.setItems(masterData);

    }
    }
