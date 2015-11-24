package com.group8.controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.group8.database.MysqlDriver;
import com.lynden.gmapsfx.MainApp;

import javafx.event.ActionEvent;
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
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


public class PubInfo implements Initializable{
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
	
	Image imgLoad;
	FileInputStream imageStream;
	File file;
   
	
	



	public void initialize(URL location, ResourceBundle resources) {
				
		pubName.setText(BeerData.pubDetails.get_name());
		pubPhoneNumber.setText(BeerData.pubDetails.get_phoneNumber());
		pubAddress.setText(BeerData.pubDetails.get_adress());
		pubDescription.setText(BeerData.pubDetails.get_description());
		pubOffer.setText(BeerData.pubDetails.get_offer());

		
	}
		 
		
	public void updatePub(ActionEvent event) throws SQLException, ClassNotFoundException{
		
		float entrance = Float.parseFloat(pubEntranceFee.getText());
		
		Byte[] emptyImage = new Byte[0];
		String pubInfo = "";
		int pubID = UserData.userInstance.get_pubId();
		
		String url = "jdbc:mysql://sql.smallwhitebird.com:3306/beerfinder";
        String user = "Gr8";
        String password = "group8";
        
		Class.forName("com.mysql.jdbc.Driver"); 
		Connection con = DriverManager.getConnection(url, user, password);
		

		PreparedStatement statement = con.prepareStatement("UPDATE `pubs` SET `name`='"+pubName.getText()+"',`addressID`='"+pubAddress.getText()+"',`phoneNumber`='"+pubPhoneNumber.getText()+"',`image`=?,`description`='"+pubDescription.getText()+"',`offers`='"+pubOffer.getText()+"',`entrenceFee`="+entrance+" WHERE pubID="+pubID);
		statement.setBinaryStream(1, imageStream, (int) file.length());
		System.out.println(pubInfo);
		  statement.executeUpdate();
	}
	
	
	public void loadPubImage(ActionEvent event)throws IOException {
		
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("open image file");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));


		Stage primaryStage=new Stage();
		file= fileChooser.showOpenDialog(primaryStage);
		imageStream = new FileInputStream(file);
		
			
				if (file.isFile() && file.getName().contains("jpg")){
				
				
				String thumbURL = file.toURI().toURL().toString();
				System.out.println(thumbURL);
				Image imgLoad = new Image(thumbURL);
				pubImage.setImage(imgLoad);
				
				
			System.out.println(imgLoad);
			}
		
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

	


		
	
	
	





	

