package com.group8.database.tables;

import java.awt.Image;
import java.util.ArrayList;

import com.group8.database.MysqlDriver;

public class Beer extends MysqlDriver{
	
	// 
    String from = "beers";
    // 
    private String name, description, type, origin, producer, beerPackage;
    int id;
    float percentege,volume;
    boolean isTap;
    Image beerImage;
    
    public void Beer(String query)
    {
    	 ArrayList<Object> sqlReturn = super.select(query);
    	 this.id = Integer.parseInt(sqlReturn.get(1).toString());
         this.name = sqlReturn.get(2).toString();
         this.description = sqlReturn.get(4).toString();
         this.type = sqlReturn.get(5).toString();
         this.origin = sqlReturn.get(6).toString();
         this.percentege = Float.parseFloat(sqlReturn.get(7).toString());
         this.producer = sqlReturn.get(8).toString();
         this.volume = Float.parseFloat(sqlReturn.get(9).toString());
         this.isTap = Boolean.parseBoolean(sqlReturn.get(10).toString());
         this.beerPackage = sqlReturn.get(11).toString();
    }
	
    public void insertBeer() {

        String query = "";



        super.insert(query);
    }
	
	
}
