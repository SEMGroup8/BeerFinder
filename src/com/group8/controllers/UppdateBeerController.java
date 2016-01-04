package com.group8.controllers;

import java.io.File;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.group8.database.MysqlDriver;
import com.group8.database.tables.Beer;

import com.group8.singletons.BeerData;
import com.group8.singletons.Navigation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;



/**
 * Created by Felipe Benjamin
 * UpdateBeerController to let Pub Users to update beers stored in the databse.
 * @author felipebenjamin
 *
 */

public class UppdateBeerController extends BaseController implements Initializable {
	
	// Make Observeble Lists out of our ArrayLists
	ObservableList<String> beerTypeList =FXCollections.observableArrayList();
	ObservableList<String> beerPackageTypeList =FXCollections.observableArrayList();
	ObservableList<String> beerProducerList=FXCollections.observableArrayList();
	ObservableList<String> beerOriginList =FXCollections.observableArrayList();
	
	public TextField beerName;
	public TextField beerDescription;
	public TextField beerPercentage;
	public ChoiceBox<String> beerPackageType;
	public ChoiceBox<String> beerType;
	public ChoiceBox<String> beerProducer;
	public ChoiceBox<String> beerOrigin;
	public TextField beerVolume;
	public CheckBox beerIsTap;
	public ImageView beerImage;
	public Label addConfirmation;

	public Button updateBeerButton;
	public Button addBeerImageButton;
	public Button beerProducerButton;
	FileInputStream imageStream;
	File file;

	
	/**
	 * Created by Felipe Benjamin
	 * Pressing the add beer image button will make a filechooser popup
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


		if (file.isFile() && (file.getName().contains("jpg")||file.getName().contains(".png")
				||file.getName().contains(".jpeg")
		)){

		String thumbURL = file.toURI().toURL().toString();
		Image imgLoad = new Image(thumbURL);
		beerImage.setImage(imgLoad);
		}
	} // end of method

	
/**
 * Created by Felipe Benjamin
 * Pressing the updatebeer button this code will run and update the beer back into the Database and notify the user if 
 * it was done.
 * -->
 * @param event
 * @throws IOException
 */
public void updateBeer(ActionEvent event) throws IOException {

		String sqlQuery = "select beerTypeID from beerType where beerTypeEN = '" + beerType.getValue() + "'";

		ArrayList<Object> result = MysqlDriver.select(sqlQuery);

		int typeID = Integer.parseInt(result.get(0).toString());


		String sqlQuery2 = "select distinct packageID from package where packageTypeEN = '"+ beerPackageType.getValue() + "'";

		ArrayList<Object> result2 = MysqlDriver.select(sqlQuery2);

		int typeID2 = Integer.parseInt(result2.get(0).toString());

		String sqlQuery4 = "select distinct originID from origin where countryName = '"+ beerOrigin.getValue() + "'";

		ArrayList<Object> result4 = MysqlDriver.select(sqlQuery4);

		String typeID4 = (result4.get(0).toString());

		String beerInfo = "";

		if(imageStream==null){
			beerInfo = "UPDATE `beers` SET `name`='"+ beerName.getText()+"',`description`= '"+beerDescription.getText()+
					"',`beerTypeID`= "+ typeID +",`originID`= '"+ typeID4+"',`percentage`= " + Float.parseFloat(beerPercentage.getText())
					+",`producerName`= '"+beerProducer.getValue()+"',`volume`= " + Integer.parseInt(beerVolume.getText())
					+",`isTap`="+(beerIsTap.isSelected() ? 1 : 0)+",`package`= "+typeID2
					+" WHERE beerID = "+ BeerData.selectedBeer.getId();
		}
		else
		{
			beerInfo = "UPDATE `beers` SET `name`='"+ beerName.getText()+"',`image` = ?,`description`= '"+beerDescription.getText()+
					"',`beerTypeID`= "+ typeID +",`originID`= '"+ typeID4+"',`percentage`= " + Float.parseFloat(beerPercentage.getText())
					+",`producerName`= '"+beerProducer.getValue()+"',`volume`= " + Integer.parseInt(beerVolume.getText())
					+",`isTap`="+(beerIsTap.isSelected() ? 1 : 0)+",`package`= "+typeID2
					+" WHERE beerID = "+ BeerData.selectedBeer.getId();
		}
	
		Connection con = null;
		PreparedStatement st = null;

		String url = "jdbc:mysql://sql.smallwhitebird.com:3306/beerfinder";
		String user = "Gr8";
		String password = "group8";


        try {
			con = DriverManager.getConnection(url, user, password);
			st = con.prepareStatement(beerInfo);
			// st.executeUpdate(query);
			if(imageStream!=null)
			{
				st.setBinaryStream(1, imageStream, (int) file.length());
			}
         
		st.executeUpdate();

        Alert alert = new Alert(AlertType.INFORMATION);
	        alert.setTitle("Information Dialog");
	        alert.setHeaderText(null);
	        alert.setContentText("Beer was successfully updated!");

	        alert.showAndWait();
	
        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(MysqlDriver.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
            
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Look, an Error Dialog");
            alert.setContentText("Ooops, there was an error!");

            alert.showAndWait();

        } catch (NullPointerException ex){
        	 Alert alert = new Alert(AlertType.ERROR);
             alert.setTitle("Error Dialog");
             alert.setHeaderText("Look, an Error Dialog");
             alert.setContentText("Ooops, there was an error!");

             alert.showAndWait();

        	
        }finally {
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

			String query2 = "SELECT distinct `beerID`,`name`,`image`,`description`,beerTypeEN,countryName, percentage, producerName, volume, isTap, packageTypeEN, price, avStars, countryFlag" +
					" from beers, beerType, origin, package where " +
					"beers.beerTypeID = beerType.beerTypeID " +
					"and beers.originID = origin.originID " +
					"and beers.package = package.packageID " +
					"and beerID =" + BeerData.selectedBeer.getId() + "";

			BeerData.selectedBeer = new Beer(MysqlDriver.select(query2));
			mainScene.changeCenter("/com/group8/resources/views/beerDetails_center.fxml");
		}
	}// end of method


/**
 * Created by Felipe Benjamin
 * Initialize the UpdateBeerController
 * @param location
 * @param resources
 */
	@Override
	public void initialize(URL location, ResourceBundle resources) {

		Navigation.current_CenterFXML = "/com/group8/resources/views/addBeer.fxml";

		beerName.setText(BeerData.selectedBeer.getName());
    	beerDescription.setText(BeerData.selectedBeer.getDescription());
        beerPercentage.setText("" + BeerData.selectedBeer.getPercentage());
        beerVolume.setText("" + BeerData.selectedBeer.getVolume());
    	beerImage.setImage(BeerData.selectedBeer.getImage());
        if (BeerData.selectedBeer.getIsTap() == true){
        	beerIsTap.setSelected(true);
        }

    	beerTypeList.clear();
    	String beerTypeInfo;
    	beerTypeInfo ="select distinct beerTypeEN from beerType";
    	
    	ArrayList<ArrayList<Object>> result = MysqlDriver.selectMany(beerTypeInfo);
    	for( int i = 0 ; i < result.size(); i++){
    		beerTypeList.add(result.get(i).get(0).toString());
    	}
    	beerType.setItems(beerTypeList);
    	
        beerType.setValue(BeerData.selectedBeer.getType());
    
    	beerPackageTypeList.clear();
    	String beerPackageTypeInfo;
    	beerPackageTypeInfo = "select distinct packageTypeEN from package";
    	
    	ArrayList<ArrayList<Object>> result2 = MysqlDriver.selectMany(beerPackageTypeInfo);
    	for(int i = 0 ; i < result2.size(); i++){
    		beerPackageTypeList.add(result2.get(i).get(0).toString());
    	}
    
    	beerPackageType.setItems(beerPackageTypeList);
    	beerPackageType.setValue(BeerData.selectedBeer.getBeerPackage());
    	

    	beerProducerList.clear();
    	String beerProducerInfo;
    	beerProducerInfo = "select distinct producerName from producers";
    	
    	ArrayList<ArrayList<Object>> result3 = MysqlDriver.selectMany(beerProducerInfo);
    	for(int i = 0 ; i < result3.size(); i++){
    		beerProducerList.add(result3.get(i).get(0).toString());
    	}
    	beerProducer.setItems(beerProducerList); 
        beerProducer.setValue(BeerData.selectedBeer.getProducer());

    	
    	beerOriginList.clear();
    	String beerOriginInfo;
    	beerOriginInfo = "select distinct countryName from origin";
    	
    	ArrayList<ArrayList<Object>> result4 = MysqlDriver.selectMany(beerOriginInfo);
    	for(int i = 0 ; i < result4.size(); i++){
    		beerOriginList.add(result4.get(i).get(0).toString());
    	}
    	beerOrigin.setItems(beerOriginList);
    	beerOrigin.setValue(BeerData.selectedBeer.getOrigin());
    	
	}

	
	
	/**
	 * Created by Felipe Benjamin
	 * Pressing the addProducer button this code will bring a pop up window where the user
	 * can add a new producer of beers and save it to the database and finally update the information
	 * in the producer choicebox.
	 * @param event
	 * @throws IOException
	 */
	public void addBeerProducer(ActionEvent event) throws IOException {
		
		final Stage dialog = new Stage();
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initOwner(Navigation.primaryStage);
		VBox dialogVbox = new VBox(20);
		dialogVbox.setAlignment(Pos.CENTER);
		dialogVbox.getChildren().add(new Text("Add the producer to the list!"));
		TextField producer = new TextField("Type in producer name:");
		Button addProducerToDB = new Button("Add producer");

		addProducerToDB.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {

				String query = "INSERT INTO `producers`(`producerName`) VALUES ('"+ producer.getText() + "')";

				MysqlDriver.insert(query);

				beerProducerList.clear();
				String beerProducerInfo;
				beerProducerInfo = "select distinct producerName from producers";

				ArrayList<ArrayList<Object>> result3 = MysqlDriver.selectMany(beerProducerInfo);
				for (int i = 0; i < result3.size(); i++) {
					beerProducerList.add(result3.get(i).get(0).toString());
				}
				beerProducer.setItems(beerProducerList);

				dialog.close();
			}
		});

		dialogVbox.getChildren().add(producer);
		dialogVbox.getChildren().add(addProducerToDB);

		Scene dialogScene = new Scene(dialogVbox, 300, 200);
		dialog.setScene(dialogScene);
		dialog.show();
	}
} // end of class


