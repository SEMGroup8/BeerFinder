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
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Shows the information about a Users pub and makes him able to manipulate that data.
 * --> User is able to update fields on his pub or just view the current state of it.
 */
public class PubInfo extends BaseController implements Initializable{
	public TextField pubID;
	public TextField pubName;
	public TextField pubAddress;
	public TextField pubPhoneNumber;
	public TextField pubDescription;
	public TextField pubOffer;
	
	public TextField pubEntranceFee;
	
	public Button pubSaveNew;
	public Button addBeer;
	@FXML
	public Button getMap;
	@FXML
    private ImageView pubImage;
    @FXML
    private Button loadImg;

	// Latlong
	double latitude;
	double longitude;

   
	
	



	//@Override
	public void initialize(URL location, ResourceBundle resources) {

		Navigation.current_CenterFXML= "/com/group8/resources/views/pubInfo.fxml";
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

		mainScene.changeCenter("/com/group8/resources/views/addBeer.fxml");
         
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
			dialogStage.getIcons().add(new Image("file:src/com/group8/resources/Images/Icon.png"));
			// Show the dialog and wait until the user closes it
			dialogStage.show();


			// Some real cool shit

			// When exeting the window update the address and fetch the latlong
			dialogStage.setOnHidden(new EventHandler<WindowEvent>() {
				@Override
				public void handle(WindowEvent event) {
					if(BeerData.Address != null) {
						pubAddress.setText(BeerData.Address.toString());

						// Store the address as lat and long doubles
						latitude = BeerData.Address.getLatitude();
						longitude = BeerData.Address.getLongitude();
						System.out.println(latitude + " " + longitude);
					}
				}
			});

		}


}

	


		
	
	
	





	

