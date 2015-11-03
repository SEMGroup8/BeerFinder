package com.group8.database.tables;

import com.group8.database.MysqlDriver;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Beer Table class
 */
public class Beer extends MysqlDriver{

    String from = "beers";
    private String name, description, type, origin, producer, beerPackage;
    private ImageView imV;
    int id;
    float percentage,volume;
    Boolean isTap;
    BufferedImage image = null;  //Buffered image coming from database
    InputStream tmpImg = null; //Inputstream





    // A open beer allows us to look at detailed info in
    // result screen/beerDetailsScreen
    public static Beer selectedBeer;


    /**
     * Constructor takeing a String query
     * @param query
     */
    public Beer(String query)  {
        super();

        ArrayList<Object> sqlReturn = select(query);
        this.id = Integer.parseInt(sqlReturn.get(0).toString());
        this.name = sqlReturn.get(1).toString();

        try {
            this.tmpImg = (InputStream) sqlReturn.get(2);
            this.image = javax.imageio.ImageIO.read(tmpImg);
        }catch (IOException ex){
            this.image = null;
        }
        this.description = sqlReturn.get(3).toString();
        this.type = sqlReturn.get(4).toString();
        this.origin = sqlReturn.get(5).toString();
        this.percentage = Float.parseFloat(sqlReturn.get(6).toString());
        this.producer = sqlReturn.get(7).toString();
        this.volume = Float.parseFloat(sqlReturn.get(8).toString());
        this.isTap = Boolean.parseBoolean(sqlReturn.get(9).toString());
        this.beerPackage = sqlReturn.get(10).toString();
    }

    /**
     *  Constructor takeing a Arraylist of Objects
     * @param sqlReturn
     */
    public Beer(ArrayList<Object> sqlReturn)  {
        this.id = Integer.parseInt(sqlReturn.get(0).toString());
        this.name = sqlReturn.get(1).toString();
        // Image handeling
        try {
            this.tmpImg = (InputStream) sqlReturn.get(2);
            this.image = javax.imageio.ImageIO.read(tmpImg);
        }catch (IOException ex){
            this.image = null;
        }
        this.description = sqlReturn.get(3).toString();
        this.type = sqlReturn.get(4).toString();
        this.origin = sqlReturn.get(5).toString();
        this.percentage = Float.parseFloat(sqlReturn.get(6).toString());
        this.producer = sqlReturn.get(7).toString();
        this.volume = Float.parseFloat(sqlReturn.get(8).toString());
        this.isTap = Boolean.parseBoolean(sqlReturn.get(9).toString());
        this.beerPackage = sqlReturn.get(10).toString();
    }


    /**
     * Setters and Getter methods
     * @return
     */

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


    public Image getImage() {
        Image image2;
        if(this.image == null){
            image2 = null;
        }else{
            image2 = SwingFXUtils.toFXImage(this.image, null);
        }
        return image2;
    }


    /**
     * TODO implement the actual insert method
     * @param beer
     */
    public void insertBeer(Beer beer) {

        String query = "";

        query += "INSERT INTO beers (name,description,beerType,originID,percentage,producerName,volume,package)" +
                 " VALUES (" + beer.getName() + "," + beer.getDescription() + "," + beer.getOrigin() + "," + beer.getPercentage() + ","
                 + beer.getProducer() + "," + beer.getVolume() + "," + beer.getBeerPackage() + ");";



        insert(query);
    }

    /**
     * Overide on original toString to return entire object in String format.
     * @return
     */
    @Override
    public String toString()
    {
        String result="";

        result = this.getId() + " " +  this.name + " " + this.description + " " + this.type + " " + this.origin + " " + this.percentage + " " + this.producer + " " + this.volume + " " + this.isTap + " " + this.beerPackage;

        return result;
    }




	
}
