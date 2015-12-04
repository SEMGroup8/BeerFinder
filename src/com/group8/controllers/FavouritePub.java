package com.group8.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.group8.database.MysqlDriver;
import com.group8.database.tables.Pub;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
	
//	    public ObservableList<Pub> masterData = FXCollections.observableArrayList(UserData.userInstance.pubFavouritesDetails);
	    int userId=UserData.userInstance.get_id();
		int pubID = UserData.userInstance.get_pubId();
		
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
		                        // Load the details scene
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
		// TODO Auto-generated method stub

		 Navigation.backFXML = "/com/group8/resources/views/favourites.fxml";
	        Navigation.resultviewFXML = "/com/group8/resources/views/FavouritePub.fxml";
			System.out.println(pubName+"  sooooooo");
	        // You have to have a get function that is named get +" type" for it to work sets values.
	        pubName.setCellValueFactory(new PropertyValueFactory<Pub, String>("_name"));
	       // pubAddress.setCellValueFactory(new PropertyValueFactory<Pub, String>("Address"));
	        pubPhoneNumber.setCellValueFactory(new PropertyValueFactory<Pub, String>("_phoneNumber"));
	        pubOffer.setCellValueFactory(new PropertyValueFactory<Pub, String>("_offers"));
	        pubDescription.setCellValueFactory(new PropertyValueFactory<Pub, String>("_description"));
	        pubEntranceFee.setCellValueFactory(new PropertyValueFactory<Pub, String>("_entranceFee"));
	       // pubEntranceFee.setCellValueFactory(new PropertyValueFactory<Pub, String>("Percentage"));
	       

	        // Try loading the image, if there is none will use placeholder
//	        pubImage.setCellValueFactory(new PropertyValueFactory<Pub, Image>("Image"));
//	        /**
//	         * Set the Cellfactory
//	         */
//	        pubImage.setCellFactory(new Callback<TableColumn<Pub, Image>, TableCell<Pub, Image>>() {
//	            @Override
//	            public TableCell<Pub, Image> call(TableColumn<Pub, Image> param) {
//	                TableCell<Pub, Image> cell = new TableCell<Pub, Image>() {
//
//	                    /**
//	                     * Override the updateItem method to set a imageView
//	                     * @param item
//	                     * @param empty
//	                     */
//	                    @Override
//	                    public void updateItem(Image item, boolean empty) {
//
//	                        if(!empty) {
//	                            if (item != null) {
//	                                VBox vb = new VBox();
//	                                vb.setAlignment(Pos.CENTER);
//	                                ImageView imgVw = new ImageView();
//	                                imgVw.setImage(item);
//	                                imgVw.setFitWidth(20);
//	                                imgVw.setFitHeight(40);
//	                                vb.getChildren().addAll(imgVw);
//	                                setGraphic(vb);
//
//	                            } else {
//	                                VBox vb = new VBox();
//	                                vb.setAlignment(Pos.CENTER);
//	                                ImageView imgVw = new ImageView();
//	                                imgVw.setImage(new Image(new File("src/com/group8/resources/Images/beerHasNoImage.png").toURI().toString()));
//	                                imgVw.setFitWidth(20);
//	                                imgVw.setFitHeight(40);
//	                                // Test Output
//	                                //System.out.println(imgVw.getImage().toString());
//	                                vb.getChildren().addAll(imgVw);
//	                                setGraphic(vb);
//
//	                            }
//	                        }
//	                    }
//	                };
//	                return cell;
//	            }
//
//	        });



	        //Populate the Tableview
	        //pubTable.setItems(masterData);
		
	}

	public void addPFavouritePub(ActionEvent event){
		
		String favPub="INSERT INTO `favouritePub`(`pubID`, `userId`) VALUES ("+ pubID + "," + userId +")";
		MysqlDriver.insert(favPub);
	      
        
	}


	public int get_pubId() {
		// TODO Auto-generated method stub
		return get_pubId();
	}
	
}
