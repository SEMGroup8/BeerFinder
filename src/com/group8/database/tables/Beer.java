package com.group8.database.tables;

import com.group8.database.MysqlDriver;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by Linus Eiderström Swahn
 * Beer Table class
 */
public class Beer extends MysqlDriver{

    private String from = "beers";
    private String name, description, type, origin, producer, beerPackage;
    private int id;

    private float avRank;
    private float percentage,price;
    private int volume;
    private Boolean isTap;

    //Buffered image coming from database
    private BufferedImage image = null;
    private BufferedImage countryFlag = null;




    /**
     * Created by
     *
     * Constructor taking a String query that returns one beer from the database.
     * @param query
     * Query that should select the beer we want from the database.
     */
    public Beer(String query)  {
        super();

        ArrayList<Object> sqlReturn = select(query);
        this.id = Integer.parseInt(sqlReturn.get(0).toString());
        this.name = sqlReturn.get(1).toString();

        try {
            InputStream tmpImg = (InputStream) sqlReturn.get(2);
            this.image = javax.imageio.ImageIO.read(tmpImg);
        }catch (IOException ex){
            this.image = null;
        }

        this.description = sqlReturn.get(3).toString();
        this.type = sqlReturn.get(4).toString();
        this.origin = sqlReturn.get(5).toString();
        this.percentage = Float.parseFloat(sqlReturn.get(6).toString());
        this.producer = sqlReturn.get(7).toString();
        this.volume = Integer.parseInt(sqlReturn.get(8).toString());
        this.isTap = Boolean.parseBoolean(sqlReturn.get(9).toString());
        this.beerPackage = sqlReturn.get(10).toString();
        this.price = Float.parseFloat(sqlReturn.get(11).toString());
        this.avRank = Float.parseFloat(sqlReturn.get(12).toString());
        try {
            InputStream tmpImg = (InputStream) sqlReturn.get(13);
            this.countryFlag = javax.imageio.ImageIO.read(tmpImg);
        }catch (IOException ex){
            this.countryFlag = null;
        }
    }

    /**
     * Created by
     *
     *  Constructor taking an Arraylist of Objects representing the columns of a row on the database.
     * @param sqlReturn
     */
    public Beer(ArrayList<Object> sqlReturn)  {
        this.id = Integer.parseInt(sqlReturn.get(0).toString());
        this.name = sqlReturn.get(1).toString();
        // Image handeling
        try {
            InputStream tmpImg = (InputStream) sqlReturn.get(2);
            this.image = javax.imageio.ImageIO.read(tmpImg);
        }catch (IOException ex) {
            this.image = null;
            ex.printStackTrace();
        }

        this.description = sqlReturn.get(3).toString();
        this.type = sqlReturn.get(4).toString();
        this.origin = sqlReturn.get(5).toString();
        this.percentage = Float.parseFloat(sqlReturn.get(6).toString());
        this.producer = sqlReturn.get(7).toString();
        this.volume = Integer.parseInt(sqlReturn.get(8).toString());
        this.isTap = Boolean.parseBoolean(sqlReturn.get(9).toString());
        this.beerPackage = sqlReturn.get(10).toString();
        this.price = Float.parseFloat(sqlReturn.get(11).toString());
        this.avRank = Float.parseFloat(sqlReturn.get(12).toString());

        try {
            InputStream tmpImg = (InputStream) sqlReturn.get(13);
            this.countryFlag = javax.imageio.ImageIO.read(tmpImg);
        }catch (IOException ex){
            this.countryFlag = null;
            ex.printStackTrace();
        }
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

    public int getVolume() {
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

    public float getAvRank() {
        return avRank;
    }

    public float getPrice(){
        return this.price;
    }

    public Image getCountryFlag(){
        Image flagImage;
        if(this.countryFlag == null){
            flagImage = null;
        }else{
            flagImage = SwingFXUtils.toFXImage(this.countryFlag, null);
        }
        return flagImage;
    }


    /**
     * Overide on original toString to return entire object in String format.
     * @return
     */
    @Override
    public String toString()
    {
        String result;

        result = this.getId() + " " +  this.name + " " + this.description + " " + this.type + " " + this.origin + " " + this.percentage + " " + this.producer + " " + this.volume + " " + this.isTap + " " + this.beerPackage;

        return result;
    }
}
