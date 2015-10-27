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
    float percentage,volume;
    Boolean isTap;
    Image beerImage;


    public Beer(String query)
    {
    	 ArrayList<Object> sqlReturn = super.select(query);
        this.id = Integer.parseInt(sqlReturn.get(0).toString());
        this.name = sqlReturn.get(1).toString();
        this.description = sqlReturn.get(3).toString();
        this.type = sqlReturn.get(4).toString();
        this.origin = sqlReturn.get(5).toString();
        this.percentage = Float.parseFloat(sqlReturn.get(6).toString());
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
        this.percentage = Float.parseFloat(sqlReturn.get(6).toString());
        this.producer = sqlReturn.get(7).toString();
        this.volume = Float.parseFloat(sqlReturn.get(8).toString());
        this.isTap = Boolean.parseBoolean(sqlReturn.get(9).toString());
        this.beerPackage = sqlReturn.get(10).toString();
    }

    /*
    Setters and Gettersß
     */
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

    public float getPercentage() {
        return percentage;
    }

    public float getVolume() {
        return volume;
    }

    public Boolean getIsTap() {
        return isTap;
    }


    /*
        TODO implement the actual insert method

     */
    public void insertBeer(Beer beer) {

        String query = "";

        query += "INSERT INTO beers (name,description,beerType,originID,percentage,producerName,volume,package)" +
                 " VALUES (" + beer.getName() + "," + beer.getDescription() + "," + beer.getOrigin() + "," + beer.getPercentage() + ","
                 + beer.getProducer() + "," + beer.getVolume() + "," + beer.getBeerPackage() + ");";



        super.insert(query);
    }
    /*
    Overide on original toString to return entire object in String format.
     */
    @Override
    public String toString()
    {
        String result="";

        result = this.getId() + " " +  this.name + " " + this.description + " " + this.type + " " + this.origin + " " + this.percentage + " " + this.producer + " " + this.volume + " " + this.isTap + " " + this.beerPackage;

        return result;
    }




	
}
