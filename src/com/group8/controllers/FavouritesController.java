package com.group8.controllers;

import com.group8.database.tables.Beer;
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
 * Created by Linus Eiderström Swahn.
 *
 * Controller for the favourite beer scene where the user can see his favourite beers.
 *
 * Inherits BaseController for some UI-funcitonality.
 *
 * Implements Initializable so that we can tale advantage of the initialize() function.
 */
public class FavouritesController extends BaseController implements Initializable
{
    @FXML
    public Button logout, account;
    @FXML
    public Button Back;
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
    public Label userName;

    public ObservableList<Beer> beerList = FXCollections.observableArrayList(UserData.userInstance.favourites);

    /**
     * Created by Linus Eiderström Swahn.
     *
     * Select a beer row and proceed to the beerDetail scene
     */
    public void getRow(){
        beerTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

            // If the user double clicks.
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {

                    // Set the selectedBeer instance of beer we have to the selected item
                    BeerData.selectedBeer = beerTable.getSelectionModel().getSelectedItem();
                    // Load the details scene

                    try {

                        mainScene.changeCenter("/com/group8/resources/views/beerDetails_center.fxml");

                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });
    }

    /**
     * Created by Linus Eiderström Swahn.
     *
     * initialize is defined in the Initializable interface.
     * It runs once when the controller loads when the fxml is called.
     *
     * Sets the data in the tableview.
     *
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Navigation.current_CenterFXML =  "/com/group8/resources/views/favourites.fxml";

        // You have to have a get function that is named get +" type" for it to work sets values.
        beerName.setCellValueFactory(new PropertyValueFactory<Beer, String>("Name"));
        beerType.setCellValueFactory(new PropertyValueFactory<Beer, String>("Type"));
        beerOrigin.setCellValueFactory(new PropertyValueFactory<Beer, String>("Origin"));
        beerProducer.setCellValueFactory(new PropertyValueFactory<Beer, String>("Producer"));
        beerPackage.setCellValueFactory(new PropertyValueFactory<Beer, String>("BeerPackage"));
        avrageRank.setCellValueFactory(new PropertyValueFactory<Beer, String>("AvRank"));
        beerPercentage.setCellValueFactory(new PropertyValueFactory<Beer, String>("Percentage"));

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
        beerTable.setItems(beerList);
    }
}
