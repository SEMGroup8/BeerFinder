package com.group8.database.tables;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.*;


import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.group8.database.MysqlDriver;

import javax.imageio.ImageIO;

public class Beer extends MysqlDriver{

    String from = "beers";
    private String name, description, type, origin, producer, beerPackage;
    int id;
    float percentage,volume;
    Boolean isTap;
    //Blob beerImage;
    InputStream input;

    // A open beer allows us to look at detailed info in
    // result screen/beerDetailsScreen
    public static Beer selectedBeer;

    // Constructor
    public Beer(String query)
    {
    	 ArrayList<Object> sqlReturn = super.select(query);
        this.id = Integer.parseInt(sqlReturn.get(0).toString());
        this.name = sqlReturn.get(1).toString();
      //  this.beerImage = ;
        //this.input = sqlReturn.get(2).;
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
   // public Image getBeerImage() {
   //     return beerImage;
    //}

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

   // public Image getImage(){
  //      return this.beerImage;
    //}

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
