package com.group8.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.group8.database.MysqlDriver;

import com.lynden.gmapsfx.MainApp;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class PubInfo {
	public TextField pubID;
	public TextField pubName;
	public TextField pubAddress;
	public TextField pubPhoneNumber;
	public TextField pubDescription;
	public TextField pubOffer;
	
	public TextField pubEntranceFee;
	
	public Button pubSaveNew;
	public Button addBeer;

	public Button getMap;
	@FXML
    private ImageView pubImage;

    @FXML
    private Button loadImg;

   
	
	



	//@Override
	public void initialize(URL location, ResourceBundle resources) {
				
		// TODO Auto-generated method stub
		
		
	}
		 
		
	public void addBeer(ActionEvent event){
		
		float entrance = Float.parseFloat(pubEntranceFee.getText());
		
		Byte[] emptyImage = new Byte[0];
		String pubInfo = "";
		
		pubInfo="INSERT INTO `pubs` (`name`, `address`, `phoneNumber`, `image`, `description`, `offers`, `entrenceFee`) VALUES ('";
		
		pubInfo	+= pubName.getText()+"','"+pubAddress.getText()+"','"+pubPhoneNumber.getText()+"','";
						
		pubInfo+= emptyImage + "', '"+pubOffer.getText()+"','"+pubDescription.getText()+"', '"+entrance+"');";

		System.out.println(pubInfo);
		MysqlDriver.insert(pubInfo);
	}
	
	public void onAddBeer(ActionEvent event) throws IOException{
		
		Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/addBeer.fxml"));
         Scene result_scene = new Scene(result,800,600);
         Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         main_stage.setScene(result_scene);
         main_stage.show();
         
	}

		public void getMap(javafx.event.ActionEvent event) throws IOException{

			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(MainApp.class.getResource("/com/group8/resources/views/AddressMap.fxml"));
			BorderPane page = loader.load();
			// Create the dialog Stage.
			Stage dialogStage = new Stage();
			dialogStage.setTitle("Add Your Location");
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.initOwner(Navigation.primaryStage);
			Scene scene = new Scene(page);
			dialogStage.setScene(scene);
			// Show the dialog and wait until the user closes it
			dialogStage.show();
			// Some real cool shit
			dialogStage.setOnCloseRequest(new EventHandler() {
				@Override
				public void handle(Event event) {
					pubAddress.setText(BeerData.Address.toString());

				}
			});
			dialogStage.setOnHidden(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					pubAddress.setText(BeerData.Address.toString());
				}
			});

		}


}

	


		
	
	
	





	

