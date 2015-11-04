package com.group8.controllers;

import java.util.ArrayList;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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
	


	public Button addBeerButton;
	
	
	
    public void initialize(){
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
		beerInfo = "INSERT INTO `beers`(`name`, `description`, `originID`, `percentage`, `producerName`, `package`, `image`, `beerTypeID`) VALUES ('"
	    + beerName.getText() + "','" + beerDescription.getText() + "','" + beerOrigin.getText().toLowerCase() + "','" + beerPercentage.getText() + "','" 
		+ beerProducer.getText() + "','"+ beerPackageType.getText() +"', '" + defaultImage + "','" + typeID +"')";
		
		System.out.println(beerInfo);
		
		MysqlDriver.insert(beerInfo);
		
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
