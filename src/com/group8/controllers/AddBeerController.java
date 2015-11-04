package com.group8.controllers;

import java.io.IOException;
import java.util.ArrayList;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import com.group8.database.*;


public class AddBeerController  {
	
	
	ObservableList<String> beerTypeList =FXCollections.observableArrayList();
	public TextField beerName;
	public TextField beerDescription;
	public TextField beerPercentage;
	public TextField beerPackageType;
	public ChoiceBox<String> beerType;
	public TextField beerProducer;
	public TextField beerOrigin;
	public TextField beerVolume;
	public CheckBox beerIsTap;
	 @FXML
	    public Button logout, account, favourites;
	 public Label userName;

	public Button addBeerButton;
	
	
	
    public void initialize(){
    	
    	userName.setText(UserData.userInstance.get_name());
    	
    	beerTypeList.clear();
    	String beerTypeInfo;
    	beerTypeInfo ="select distinct beerTypeEN from beerType";
    	
    	ArrayList<ArrayList<Object>> result = MysqlDriver.selectMany(beerTypeInfo);
    	for( int i = 0 ; i < result.size(); i++){
    		beerTypeList.add(result.get(i).get(0).toString());
    	}
    	beerType.setItems(beerTypeList);
    }


	public void addBeer(ActionEvent event) {
		
		String sqlQuery = "select beerTypeID from beerType where beerTypeEN = '" + beerType.getValue() + "'";
		
		ArrayList<Object> result = MysqlDriver.select(sqlQuery);
		
		int typeID = Integer.parseInt(result.get(0).toString());
		
		
		
		Byte[] defaultImage = new Byte[0];
		
		
	    String beerInfo ;
		beerInfo = "INSERT INTO `beers`(`name`, `description`, `originID`, `percentage`, `producerName`, `package`, `image`, `beerTypeID`, `volume`, `isTap`) VALUES ('"
	    + beerName.getText() + "','" + beerDescription.getText() + "','" + beerOrigin.getText().toUpperCase() + "','" + beerPercentage.getText() + "','" 
		+ beerProducer.getText() + "','"+ beerPackageType.getText() +"','" + defaultImage + "','" + typeID +"','" + beerVolume.getText()+"','" + (beerIsTap.isSelected() ? 1 : 0) +"')";
		
	//	System.out.println(beerInfo);
		
		MysqlDriver.insert(beerInfo);
		
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

	        Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/pubInfo.fxml"));
	        Scene result_scene = new Scene(result, 800, 600);
	        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        main_stage.setScene(result_scene);
	        main_stage.show();
	    }

	    @FXML
	    public void onFavourites(javafx.event.ActionEvent event) throws IOException
	    {

	        Parent result = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/favourites.fxml"));
	        Scene result_scene = new Scene(result, 800, 600);
	        Stage main_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
	        main_stage.setScene(result_scene);
	        main_stage.show();
	    }

	


 
 
  /*public void existingBeers(ActionEvent event){
 BeerData.beer = new ArrayList<Beer>();
 ArrayList<ArrayList<Object>> sqlData;
String searchInput = "select * from beers";
sqlData = MysqlDriver.selectMany(searchInput);

 for (int i = 0; i < sqlData.size(); i++) {
     // Add a new Beer to the beer arraylist
  Beer beer = new Beer(sqlData.get(i));
     // Testoutput
     System.out.print(beer.getName()+"\n");
     BeerData.beer.add(beer);
 }
 }*/
 

 
 
} // end of class
