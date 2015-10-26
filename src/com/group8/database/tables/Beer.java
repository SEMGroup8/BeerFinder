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
    
    public Beer(String query)
    {
    	 ArrayList<Object> sqlReturn = super.select(query);
        this.id = Integer.parseInt(sqlReturn.get(0).toString());
        this.name = sqlReturn.get(1).toString();
        this.description = sqlReturn.get(3).toString();
        this.type = sqlReturn.get(4).toString();
        this.origin = sqlReturn.get(5).toString();
        this.percentege = Float.parseFloat(sqlReturn.get(6).toString());
        this.producer = sqlReturn.get(7).toString();
        this.volume = Float.parseFloat(sqlReturn.get(8).toString());
        this.isTap = Boolean.parseBoolean(sqlReturn.get(9).toString());
        this.beerPackage = sqlReturn.get(10).toString();
    }

    public Beer(ArrayList<Object> sqlReturn)
    {
        this.id = Integer.parseInt(sqlReturn.get(0).toString());
        this.name = sqlReturn.get(1).toString();
        this.description = sqlReturn.get(3).toString();
        this.type = sqlReturn.get(4).toString();
        this.origin = sqlReturn.get(5).toString();
        this.percentege = Float.parseFloat(sqlReturn.get(6).toString());
        this.producer = sqlReturn.get(7).toString();
        this.volume = Float.parseFloat(sqlReturn.get(8).toString());
        this.isTap = Boolean.parseBoolean(sqlReturn.get(9).toString());
        this.beerPackage = sqlReturn.get(10).toString();
    }

    public Image getBeerImage() {
        return beerImage;
    }

    public String getFrom() {
        return from;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }

    public String getOrigin() {
        return origin;
    }

    public String getProducer() {
        return producer;
    }

    public String getBeerPackage() {
        return beerPackage;
    }

    public int getId() {
        return id;
    }

    public float getPercentege() {
        return percentege;
    }

    public float getVolume() {
        return volume;
    }

    public boolean isTap() {
        return isTap;
    }

    public void insertBeer() {

        String query = "";



        super.insert(query);
    }




	
}
