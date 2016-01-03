package com.group8.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.group8.database.tables.Pub;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
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

/**
 * Show users favourite pubs.
 * --> Follow function
 */
public class FavouritePub extends BaseController implements Initializable {

		@FXML
		public Button Add;
		@FXML
		public TableView<Pub> pubTable;
	    @FXML
	    public TableColumn<Pub, String> pubName;
	    @FXML
	    public TableColumn<Pub, String> pubAddress;
	    @FXML
	    public TableColumn<Pub, String> pubPhoneNumber;
	    @FXML
	    public TableColumn<Pub, String> pubOffer;
	    @FXML
	    public TableColumn<Pub, String> pubDescription;
	    @FXML
	    public TableColumn<Pub, String> pubEntranceFee;
	    @FXML
	    public TableColumn<Pub, Image> image;
	
	    public ObservableList<Pub> masterData1 = FXCollections.observableArrayList(UserData.userInstance.pubFavouritesDetails);
	    int userId=UserData.userInstance.getId();
		int pubID = UserData.userInstance.getPubId();
		
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
		                       PubData.selectedPub = pubTable.getSelectionModel().getSelectedItem();
		                        // load the details scene
		                        // Has to be in a tr / catch becouse of the event missmatch, ouseevent cant throw IOexceptions
		                        try {
									mainScene.changeCenter("/com/group8/resources/views/pubDetailView.fxml");
		                        } catch (IOException e) {
		                            // Print error msg
		                            e.printStackTrace();
		                        }




		                }
		            }

		        });


		}
	
		  @Override
		    public void initialize(URL location, ResourceBundle resources) {

		  Navigation.current_CenterFXML = "/com/group8/resources/views/pubList.fxml";

	        // You have to have a get function that is named get +" type" for it to work sets values.
	            pubName.setCellValueFactory(new PropertyValueFactory<Pub, String>("_name"));
		        pubAddress.setCellValueFactory(new PropertyValueFactory<Pub, String>("_adressId"));
		        pubPhoneNumber.setCellValueFactory(new PropertyValueFactory<Pub, String>("_phoneNumber"));
		        pubOffer.setCellValueFactory(new PropertyValueFactory<Pub, String>("_offer"));
		        pubDescription.setCellValueFactory(new PropertyValueFactory<Pub, String>("_description"));
		        pubEntranceFee.setCellValueFactory(new PropertyValueFactory<Pub, String>("_entranceFee"));
	            System.out.println(pubName +"doki doki");


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
	                                imgVw.setFitWidth(60);
	                                imgVw.setFitHeight(40);
	                                vb.getChildren().addAll(imgVw);
	                                setGraphic(vb);

	                            } else {
	                                VBox vb = new VBox();
	                                vb.setAlignment(Pos.CENTER);
	                                ImageView imgVw = new ImageView();
	                                imgVw.setImage(new Image(new File("src/com/group8/resources/Images/beerHasNoImage.png").toURI().toString()));
	                                imgVw.setFitWidth(60);
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
	        pubTable.setItems(masterData1);

	    }
	}