package com.group8.controllers;

import com.group8.database.tables.Beer;
import com.group8.database.tables.Pub;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
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
    public Button logout, account;
    @FXML
    public Button Back;
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
   public TableColumn<Pub, Image> image;
  
    @FXML
    public Label userName;

    public ObservableList<Pub> masterData = FXCollections.observableArrayList(PubData.pubs);
    

    /**
     * Back button pressed takes you back to "home screen"
     * @param event
     * @throws IOException
     */
    @FXML
    public void backAction(ActionEvent event) throws IOException {
        String backActionString = "";

        Parent homescreen = FXMLLoader.load(getClass().getResource(Navigation.homescreenFXML));
        Scene result_scene = new Scene(homescreen, 800, 600);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();
    }

    @FXML
    public void onLogout(javafx.event.ActionEvent event) throws IOException
    {
        UserData.userInstance = null;

        Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/homescreen.fxml"));
        Scene result_scene = new Scene(result, 800, 600);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();
    }

    @FXML
    public void onAccount(javafx.event.ActionEvent event) throws IOException
    {

        Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/accountSettings.fxml"));
        Scene result_scene = new Scene(result, 800, 600);
        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main_stage.setScene(result_scene);
        main_stage.show();
    }

    /**
     * Select a beer row and proceed to the beerDetail scene
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
                    System.out.println("clicked on " + pubTable.getSelectionModel().getSelectedItem());
                    // Set the selectedBeer instance of beer we have to selected item
                   // BeerData.selectedBeer = pubTable.getSelectionModel().getSelectedItem();
                    // Load the details scene
                    // Has to be in a tr / catch becouse of the event missmatch, ouseevent cant throw IOexceptions
                    try {
                        // TODO have to fix nameing
                        Parent homescreen = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/beerDetailsScreen.fxml"));
                        Scene result_scene = new Scene(homescreen,800,600);
                        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                        main_stage.setScene(result_scene);
                        main_stage.show();
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

//        Navigation.backFXML = "/com/group8/resources/views/favourites.fxml";
//        Navigation.resultviewFXML = "/com/group8/resources/views/favourites.fxml";

        // You have to have a get function that is named get +" type" for it to work sets values.
        pubName.setCellValueFactory(new PropertyValueFactory<Pub, String>("_name"));
	       // pubAddress.setCellValueFactory(new PropertyValueFactory<Pub, String>("Address"));
	        pubPhoneNumber.setCellValueFactory(new PropertyValueFactory<Pub, String>("_phoneNumber"));
	        pubOffer.setCellValueFactory(new PropertyValueFactory<Pub, String>("_offers"));
	        pubDescription.setCellValueFactory(new PropertyValueFactory<Pub, String>("_description"));
	        pubEntranceFee.setCellValueFactory(new PropertyValueFactory<Pub, String>("_entranceFee"));
//        beerPercentage.setCellValueFactory(new PropertyValueFactory<Beer, String>("Percentage"));
        System.out.println(pubName +"doki doki");


//        // Try loading the image, if there is none will use placeholder
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
                                // Test Output
                                //System.out.println(imgVw.getImage().toString());
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