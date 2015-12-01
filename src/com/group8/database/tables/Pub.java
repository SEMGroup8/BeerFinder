package com.group8.database.tables;

import com.group8.database.MysqlDriver;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

/**
 * Created by Shiratori on 12/10/15.
 *
 * Class to represent Pubs.
 *
 * Derives from MysqlDriver and thus inherits the relevant functions for inserting and selecting form the database.
 *
 */
public class Pub extends MysqlDriver{

    private int _pubId, _adressId;
    private String _name;
    private String _description;


    private String _adress;
    private String _phoneNumber;
    private String _offer;

    private double _geoLong;

    private double _geoLat;
    float _entranceFee;
    BufferedImage pubImage;


    public Pub()
    {

    }

    public Pub(String query)
    {
        super();

        ArrayList<Object> sqlReturn = select(query);

        this._pubId = Integer.parseInt(sqlReturn.get(0).toString());
        this._name = sqlReturn.get(1).toString();
        this._description = sqlReturn.get(5).toString();

        String adressQuery = "Select address from pubAddress where addressID = " + Integer.parseInt(sqlReturn.get(2).toString());

        ArrayList<Object> addressReturn = select(adressQuery);

        this._adressId = Integer.parseInt(sqlReturn.get(2).toString());
        this._adress = addressReturn.get(0).toString();

        this._phoneNumber = sqlReturn.get(3).toString();
        this._offer = sqlReturn.get(6).toString();
        this._entranceFee = Float.parseFloat(sqlReturn.get(7).toString());
    }

    /*
    TODO implement the actual insert method

    */
    public boolean insertPub() {

        String query = "Select * from pubs where name = '" + this._name + "';";

        ArrayList<Object> mysqlData = select(query);

        if(mysqlData!=null)
        {
            return false;
        }

        query = "Insert into pubs(pubID, name) values(NULL, '" + this._name + "');";

        insert(query);

        query = "Select * from pubs where name = '" + this._name + "';";

        mysqlData = select(query);

        this._pubId = Integer.parseInt(mysqlData.get(0).toString());

        System.out.println(_pubId);

        return true;
    }

    public int get_pubId() {
        return _pubId;
    }

    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    public double get_geoLat() {
        return _geoLat;
    }

    public void set_geoLat(double _geoLat) {
        this._geoLat = _geoLat;
    }

    public double get_geoLong() {
        return _geoLong;
    }

    public void set_geoLong(double _geoLong) {
        this._geoLong = _geoLong;
    }

    public String get_address() {
        return _adress;
    }

    public void set_phoneNumber(String _phoneNumber){
	this._phoneNumber = _phoneNumber;
	
}
    public String get_phoneNumber(){
    	
    	return _phoneNumber;
    }
    
    public void set_offer(String _offer){
    	this._offer = _offer;
    	
    }
    
    public String get_offer(){
    	
    	return _offer;
    }
    
    public void set_entranceFee(float _entranceFee){
    	
    	this._entranceFee = _entranceFee;
    }
    
    public float get_entranceFee(){
    	
    	return _entranceFee;
    }
    
    public void set_description(String _description){
    	
    	this._description = _description;
    }
    
    public String get_description(){
    	return _description;
    }
 public Image getImage(){
	 
	Image image2;
     if(this.pubImage == null){
         image2 = null;
     }else{
         image2 = SwingFXUtils.toFXImage(this.pubImage, null);
     }
     return image2;
 }

   /* public String toString()
    {
        String result;

       result = this.get_pubId() + " " +  this._name + " " + this._description + " " + this._offer + " " + this._entranceFee + " " + this._adress + " " + this._phoneNumber + " " + this._geoLong + " " + this._geoLat;

        return result;
    }*/


}

