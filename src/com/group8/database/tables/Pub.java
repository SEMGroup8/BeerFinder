package com.group8.database.tables;

import com.group8.database.MysqlDriver;


import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by
 *
 * Class to represent Pubs.
 *
 * Derives from MysqlDriver and thus inherits the relevant functions for inserting and selecting form the database.
 *
 */
public class Pub extends MysqlDriver{

    // Pub fields
    private int pubId, adressId;
    private String name;
    private String description;
    private String adress;
    private String phoneNumber;
    private String offer;
    private double geoLong;
    private double geoLat;
    private float entranceFeee;
    private BufferedImage pubImage;
    private InputStream tmpImg;

    /**
     * Default Constructor.
     */
    public Pub() {}

    /**
     * Pub constructor.
     * @param query
     */
    public Pub(String query)
    {
        super();

        ArrayList<Object> sqlReturn = select(query);

        this.pubId = Integer.parseInt(sqlReturn.get(0).toString());
        this.name = sqlReturn.get(1).toString();
        this.adressId = Integer.parseInt(sqlReturn.get(2).toString());
        this.phoneNumber = sqlReturn.get(3).toString();
        this.description = sqlReturn.get(5).toString();
        this.offer = sqlReturn.get(6).toString();
        this.entranceFeee = Float.parseFloat(sqlReturn.get(7).toString());

        try {
             tmpImg = (InputStream) sqlReturn.get(4);
            this.pubImage = javax.imageio.ImageIO.read(tmpImg);
            System.out.println("->"+tmpImg.toString());

        }catch (IOException ex){
            this.pubImage = null;
        }

        String adressQuery = "Select address from pubAddress where addressID = " + Integer.parseInt(sqlReturn.get(2).toString());

        ArrayList<Object> addressReturn = select(adressQuery);

        this.adress = addressReturn.get(0).toString();
    }

    /**
     * Pub constructor.
     * @param sqlReturn
     */
    public Pub(ArrayList<Object> sqlReturn)
    {
        this.pubId = Integer.parseInt(sqlReturn.get(0).toString());
        this.name = sqlReturn.get(1).toString();
        this.description = sqlReturn.get(4).toString();
//        this.adressId = Integer.parseInt(sqlReturn.get(2).toString());System.out.println(adressId + "adddressss");
//        String adressQuery = "Select address from pubAddress where addressID = " + Integer.parseInt(sqlReturn.get(2).toString());
//        ArrayList<Object> addressReturn = select(adressQuery);
 //       this.adress = addressReturn.get(0).toString();
        try {
            tmpImg = (InputStream) sqlReturn.get(2);
            this.pubImage = javax.imageio.ImageIO.read(tmpImg);
            System.out.println("->"+tmpImg.toString());
            System.out.println("->"+tmpImg.available());
        }catch (IOException ex){
            this.pubImage = null;
        }
        this.phoneNumber = sqlReturn.get(3).toString();
       this.offer = sqlReturn.get(5).toString();
        this.entranceFeee = Float.parseFloat(sqlReturn.get(6).toString());
    }


    /**
     * Insert a pub into the pub table + add a address row in the address table that is
     * connected to that repective pub.
     * -->
     * @return
     * @throws FileNotFoundException
     */
    public boolean insertPub() throws FileNotFoundException {

        // Loads a default image for users pub when regestering
        File file = new File("src/com/group8/resources/Images/home.jpg");
        FileInputStream imageStream;
        imageStream = new FileInputStream(file);
        //
        String query = "Select * from pubs where name = '" + this.name + "';";


        ArrayList<Object> mysqlData = select(query);

        if(mysqlData!=null)
        {
            return false;
        }
        // Setting up database connection
        Connection con = null;
        PreparedStatement st = null;
        String url = "jdbc:mysql://sql.smallwhitebird.com:3306/beerfinder";
        String user = "Gr8";
        String password = "group8";
        // Try to insert a pub and a address
        try {
            con = DriverManager.getConnection(url, user, password);
        query = "Insert into pubAddress(addressID, address, latitude, longitude) values(NULL, 'no address', 0,0);";

            st = con.prepareStatement(query);
            st.executeUpdate();

        query = "Insert into pubs(addressID, name, pubID, image) values(LAST_INSERT_ID(), '" + this.name + "',NULL, ?);";


            st = con.prepareStatement(query);
            // Add the image bytestream
            st.setBinaryStream(1, imageStream, (int) file.length());
            st.executeUpdate();
        } catch (SQLException ex){
            // in case of SQL error print error msg
            ex.printStackTrace();
        }

        // Debug outputs to test if the pub was inserted
        query = "Select * from pubs where name = '" + this.name + "';";
        mysqlData = select(query);
        this.pubId = Integer.parseInt(mysqlData.get(0).toString());
        //System.out.println(pubId);

        return true;
    }


    public int getPubId() {
        return pubId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getGeoLat() {
        return geoLat;
    }

    public void setGeoLat(double geoLat) {
        this.geoLat = geoLat;
    }

    public double getGeoLong() {
        return geoLong;
    }

    public void setGeoLong(double geoLong) {
        this.geoLong = geoLong;
    }

    public String get_address() {
        return adress;
    }

    public void setPhoneNumber(String phoneNumber){
	this.phoneNumber = phoneNumber;
	
}
    public String getPhoneNumber(){
    	
    	return phoneNumber;
    }
    
    public void setOffer(String offer){
    	this.offer = offer;
    	
    }
    
    public String getOffer(){
    	
    	return offer;
    }
    
    public void set_entranceFee(float _entranceFee){
    	
    	this.entranceFeee = _entranceFee;
    }
    
    public float get_entranceFee(){
    	
    	return entranceFeee;
    }
    
    public void setDescription(String description){
    	
    	this.description = description;
    }

    public int getAdressId() {
        return adressId;
    }

    public String getDescription(){
    	return description;
    }

    /**
     * Return a JavaFX Image from the .pubImage by useing SwingFXUtils
     * @return
     */
    public Image getImage(){
	    Image image2;
        if(this.pubImage == null){
              image2 = null;
        }else{
              image2 = SwingFXUtils.toFXImage(this.pubImage, null);
        }
        return image2;
    }

    public InputStream getTmpImg() {
        return tmpImg;
    }

    /**
     * Testoutput returner for sys out calls.
     * @return
     */
    public String toString2()
    {
        String result;

       result = this.getPubId() + " " +  this.name + " " + this.description + " " + this.offer + " " + this.entranceFeee + " " + this.adress + " " + this.phoneNumber + " " + this.geoLong + " " + this.geoLat +"" +this.getImage();

        return result;
    }


}

