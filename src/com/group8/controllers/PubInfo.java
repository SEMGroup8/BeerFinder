package com.group8.controllers;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.group8.database.MysqlDriver;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


	
	
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
	
	public void onAddBeer(ActionEvent event){
		 Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/addBeer.fxml"));
         Scene result_scene = new Scene(result,800,600);
         Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
         main_stage.setScene(result_scene);
         main_stage.show();
	}
}

	

	
		// TODO Auto-generated method stub
		
	
	
	





	

