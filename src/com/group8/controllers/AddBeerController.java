package com.group8.controllers;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.imageio.ImageIO;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.util.ArrayList;
import com.group8.database.*;

/**
 * AddBeerController to let Pub Users add new beers to the Databse.
 * -->
 * TODO error handleing, field highlightning etc
 */
public class AddBeerController extends BaseController implements Initializable{
	
	// Make Observeble Lists out of our ArrayLists
	ObservableList<String> beerTypeList =FXCollections.observableArrayList();
	ObservableList<String> beerPackageTypeList =FXCollections.observableArrayList();
	ObservableList<String> beerProducerList=FXCollections.observableArrayList();
	ObservableList<String> beerOriginList =FXCollections.observableArrayList();

	@FXML
	public TextField beerName;
	@FXML
	public TextField beerDescription;
	@FXML
	public TextField beerPercentage;
	@FXML
	public ChoiceBox<String> beerPackageType;
	@FXML
	public ChoiceBox<String> beerType;
	@FXML
	public ChoiceBox<String> beerProducer;
	@FXML
	public ChoiceBox<String> beerOrigin;
	@FXML
	public TextField beerVolume;
	@FXML
	public CheckBox beerIsTap;
	@FXML
	public ImageView beerImage;
	@FXML
	public Label addConfirmatin;
	@FXML
	public Label userName;
	@FXML
	public Button addBeerButton;
	@FXML
	public Button addBeerImageButton;
	@FXML
	FileInputStream imageStream;
	@FXML
	File file;

	// USED??
	boolean warning;

	/**
	 * Pressing the add Image button will make a filechooser popup
	 * --> requests a image of the JPG,PNG,JPEG types to load into
	 *	   the imageview and upload to Database.
	 * @param event
	 * @throws IOException
	 */
	public void addBeerImage(ActionEvent event)throws IOException {
		
		
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("open image file");
		fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

            Stage primaryStage=new Stage();
			file= fileChooser.showOpenDialog(primaryStage);


			imageStream = new FileInputStream(file);
			
			
				if (file.isFile() && (file.getName().contains(".jpg")
						||file.getName().contains(".png")
						||file.getName().contains(".jpeg")
						)){
				
				
				String thumbURL = file.toURI().toURL().toString();
			//	System.out.println(thumbURL);
				Image imgLoad = new Image(thumbURL);
				beerImage.setImage(imgLoad);
				}
	} // end of method


	/**
	 * Pressing the Addbeer button this code will run and add the beer to the Database
	 * -->
	 * @param event
	 * @throws IOException
	 */
	public void addBeer(ActionEvent event) throws IOException {
		
		String sqlQuery = "select beerTypeID from beerType where beerTypeEN = '" + beerType.getValue() + "'";
		
		ArrayList<Object> result = MysqlDriver.select(sqlQuery);
		
		int typeID = Integer.parseInt(result.get(0).toString());
		
		
       String sqlQuery2 = "select distinct packageID from package where packageTypeEN = '"+ beerPackageType.getValue() + "'";
		
		ArrayList<Object> result2 = MysqlDriver.select(sqlQuery2);
		
		int typeID2 = Integer.parseInt(result2.get(0).toString());
		
		
		
			
			
            String sqlQuery4 = "select distinct originID from origin where countryName = '"+ beerOrigin.getValue() + "'";
			
			ArrayList<Object> result4 = MysqlDriver.select(sqlQuery4);
			
			String typeID4 = (result4.get(0).toString());
		
			
	   String beerInfo ;
		beerInfo = "INSERT INTO `beers`(`name`, `description`, `originID`, `percentage`, `producerName`, `package`, `image`, `beerTypeID`, `volume`, `isTap`) VALUES ('"
	    + beerName.getText() + "','" + beerDescription.getText() + "','" + typeID4 + "','" + beerPercentage.getText() + "','"
		+ beerProducer.getValue()  + "','"+ typeID2 +"',?,'" + typeID +"','" + beerVolume.getText()+"','" + (beerIsTap.isSelected() ? 1 : 0) +"')";
		

		
		 Connection con = null;
	     PreparedStatement st = null;

	        String url = "jdbc:mysql://sql.smallwhitebird.com:3306/beerfinder";
	        String user = "Gr8";
	        String password = "group8";



	        try {
	            con = DriverManager.getConnection(url, user, password);
	            st = con.prepareStatement(beerInfo);
				st.setBinaryStream(1, imageStream, (int) file.length());
				st.executeUpdate();

             
             
            Alert alert = new Alert(AlertType.INFORMATION);
 	        alert.setTitle("Information Dialog");
 	        alert.setHeaderText(null);
 	        alert.setContentText("Beer was successfully added!");
 	        alert.showAndWait();
 			mainScene.changeCenter("/com/group8/resources/views/pubInfo.fxml");

	        } catch (SQLException ex) {
	            Logger lgr = Logger.getLogger(MysqlDriver.class.getName());
	            lgr.log(Level.SEVERE, ex.getMessage(), ex);
	            
	            Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Look, an Error Dialog");
                alert.setContentText("Ooops, there was an error!");

                alert.showAndWait();

	        } finally {
	            try {
	                if (st != null) {
	                    st.close();
	                }
	                if (con != null) {
	                    con.close();
	                }

	            } catch (SQLException ex) {
	                Logger lgr = Logger.getLogger(MysqlDriver.class.getName());
	                lgr.log(Level.WARNING, ex.getMessage(), ex);
	                
	                
	                
	            }
	            
	           
	        }
		   

	        
	
	} // end of addBeer method         


		/**
		 * Initialize the AddBeerController
		 * @param location
		 * @param resources
		 */
		@Override
		public void initialize(URL location, ResourceBundle resources) {
			// TODO Auto-generated method stub
			Navigation.current_CenterFXML =  "/com/group8/resources/views/addBeer.fxml";
	    	
	    	beerTypeList.clear();
	    	String beerTypeInfo;
	    	beerTypeInfo ="select distinct beerTypeEN from beerType";
	    	
	    	ArrayList<ArrayList<Object>> result = MysqlDriver.selectMany(beerTypeInfo);
	    	for( int i = 0 ; i < result.size(); i++){
	    		beerTypeList.add(result.get(i).get(0).toString());
	    	}
	    	beerType.setItems(beerTypeList);
	    	
	        
	    
	    	beerPackageTypeList.clear();
	    	String beerPackageTypeInfo;
	    	beerPackageTypeInfo = "select distinct packageTypeEN from package";
	    	
	    	ArrayList<ArrayList<Object>> result2 = MysqlDriver.selectMany(beerPackageTypeInfo);
	    	for(int i = 0 ; i < result2.size(); i++){
	    		beerPackageTypeList.add(result2.get(i).get(0).toString());
	    	}
	    	// TODO add packagetypes??
	    	beerPackageType.setItems(beerPackageTypeList);

	    	
	    	
	    	
	    	
	    	
	    	beerProducerList.clear();
	    	String beerProducerInfo;
	    	beerProducerInfo = "select distinct producerName from producers";
	    	
	    	ArrayList<ArrayList<Object>> result3 = MysqlDriver.selectMany(beerProducerInfo);
	    	for(int i = 0 ; i < result3.size(); i++){
	    		beerProducerList.add(result3.get(i).get(0).toString());
	    	}
	    	beerProducer.setItems(beerProducerList); 
	   

	    	
	    	beerOriginList.clear();
	    	String beerOriginInfo;
	    	beerOriginInfo = "select distinct countryName from origin";
	    	
	    	ArrayList<ArrayList<Object>> result4 = MysqlDriver.selectMany(beerOriginInfo);
	    	for(int i = 0 ; i < result4.size(); i++){
	    		beerOriginList.add(result4.get(i).get(0).toString());
	    	}
	    	beerOrigin.setItems(beerOriginList);
		}



 
 
 

 
 
} // end of class
