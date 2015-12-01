package com.group8.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.group8.database.tables.Pub;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PubDetailViewController implements Initializable{
	
    public ImageView pubImage;
	public TextArea descriptionArea;
	public Label pubNameLabel;
	public Label pubAddressLabel;
	public Label pubOffersLabel;
	public Label pubEntranceFeeLabel;
	public Label phoneNumberLabel;
	public Button homeScreenButton;
	
	
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	  pubImage.setImage(PubData.selectedPub.getImage());
    
	  pubNameLabel.setText(PubData.selectedPub.get_name());
    pubAddressLabel.setText(PubData.selectedPub.get_address());
	  pubOffersLabel.setText(PubData.selectedPub.get_offer());
	  pubEntranceFeeLabel.setText("" +PubData.selectedPub.get_entranceFee());
	  phoneNumberLabel.setText(PubData.selectedPub.get_phoneNumber());
	  descriptionArea.setText(PubData.selectedPub.get_description());
	  
	}
	public void goToHomeScreen(ActionEvent event)throws IOException {
		Parent parent = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/homescreen.fxml"));
	    Scene result_scene = new Scene(parent, 800, 600);
	    Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	    main_stage.setScene(result_scene);
	    main_stage.show();
	    
	}
	

}
