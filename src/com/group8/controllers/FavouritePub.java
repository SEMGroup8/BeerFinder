package com.group8.controllers;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import com.group8.database.MysqlDriver;
import com.group8.database.tables.Beer;
import com.group8.database.tables.MapMarker;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;

public class FavouritePub extends BaseController implements Initializable {
	@FXML
	public Button Add;
	public Button addFavouritePub;
	
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
	    public TableColumn<Pub,Image> pubImage;
	    @FXML
	    public PieChart showPie;
	    @FXML
	    public Label userName;
	
	    public ObservableList<Pub> masterData = FXCollections.observableArrayList(UserData.userInstance.pubFavouritesDetails);
	   // public ObservableList<MapMarker> masterData = FXCollections.observableArrayList(BeerData.markers);
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
		                        // Set the selectedBeer instance of beer we have to selected item
		                       // BeerData.selectedBeer = pubTable.getSelectionModel().getSelectedItem();
		                        // Load the details scene
		                        // Has to be in a tr / catch becouse of the event missmatch, ouseevent cant throw IOexceptions
		                        try {
		                            // TODO have to fix nameing
		                            Parent homescreen = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/FavouritePub.fxml"));
		                            Scene result_scene = new Scene(homescreen, 800, 600);
		                            Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		                            main_stage.setScene(result_scene);
		                            main_stage.show();
		                        } catch (IOException e) {
		                            // Print error msg
		                            //e.printStackTrace();
		                        }




		                }
		            }

		        });


		}

		
	

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		 userName.setText(UserData.userInstance.get_name());
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
	        pubTable.setItems(masterData);
		
	}

	
	public void addPFavouritePub(ActionEvent event){
		
		String favPub="INSERT INTO `favouritePub`(`pubID`, `userId`) VALUES ("+ pubID + "," + userId +")";
		MysqlDriver.insert(favPub);
	      
        
	}


	public int get_pubId() {
		// TODO Auto-generated method stub
		return get_pubId();
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
	        if(UserData.userInstance.get_isPub())
	        {
	            Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/pubInfo.fxml"));
	            Scene result_scene = new Scene(result, 800, 600);
	            Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	            main_stage.setScene(result_scene);
	            main_stage.show();
	        }
	        else
	        {
	            Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/accountSettings.fxml"));
	            Scene result_scene = new Scene(result, 800, 600);
	            Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	            main_stage.setScene(result_scene);
	            main_stage.show();
	        }
	        }
	    @FXML
	    public void backAction(ActionEvent event) throws IOException {
	        Parent homescreen = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/loggedInHomescreen.fxml"));
	        Scene result_scene = new Scene(homescreen, 800, 600);
	        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        main_stage.setScene(result_scene);
	        main_stage.show();
	    }
	
}
