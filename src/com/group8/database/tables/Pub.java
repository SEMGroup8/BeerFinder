package com.group8.database.tables;

import com.group8.database.MysqlDriver;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
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
    
    public Pub(ArrayList<Object> sqlReturn)
    {
        this._pubId = Integer.parseInt(sqlReturn.get(0).toString());
        this._name = sqlReturn.get(1).toString();
        this._description = sqlReturn.get(4).toString();
//        this._adressId = Integer.parseInt(sqlReturn.get(2).toString());System.out.println(_adressId + "adddressss");
//        String adressQuery = "Select address from pubAddress where addressID = " + Integer.parseInt(sqlReturn.get(2).toString());
//        ArrayList<Object> addressReturn = select(adressQuery);
 //       this._adress = addressReturn.get(0).toString();
        try {
            InputStream tmpImg = (InputStream) sqlReturn.get(2);
            this.pubImage = javax.imageio.ImageIO.read(tmpImg);
        }catch (IOException ex){
            this.pubImage = null;
        }
        this._phoneNumber = sqlReturn.get(3).toString();
       this._offer = sqlReturn.get(5).toString();
        this._entranceFee = Float.parseFloat(sqlReturn.get(6).toString());
    }


    /*
    TODO implement the actual insert method

    */
    public boolean insertPub() throws FileNotFoundException {


        FileInputStream imageStream;
        File file;

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("open image file");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));

        Stage primaryStage=new Stage();
        file= fileChooser.showOpenDialog(primaryStage);


        imageStream = new FileInputStream(file);

        String query = "Select * from pubs where name = '" + this._name + "';";


        ArrayList<Object> mysqlData = select(query);

        if(mysqlData!=null)
        {
            return false;
        }
        Connection con = null;
        PreparedStatement st = null;
        String url = "jdbc:mysql://sql.smallwhitebird.com:3306/beerfinder";
        String user = "Gr8";
        String password = "group8";
        try {
            con = DriverManager.getConnection(url, user, password);
        query = "Insert into pubAddress(addressID, address, latitude, longitude) values(NULL, 'no address', 0,0);";

            st = con.prepareStatement(query);
            // st.executeUpdate(query);
            st.executeUpdate();

        query = "Insert into pubs(addressID, name, pubID, image) values(LAST_INSERT_ID(), '" + this._name + "',NULL, ?);";


            st = con.prepareStatement(query);
            // st.executeUpdate(query);
            st.setBinaryStream(1, imageStream, (int) file.length());
            st.executeUpdate();
        } catch (SQLException ex){
            ex.printStackTrace();
        }

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

    public int get_adressId() {
        return _adressId;
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

    public String toString2()
    {
        String result;

       result = this.get_pubId() + " " +  this._name + " " + this._description + " " + this._offer + " " + this._entranceFee + " " + this._adress + " " + this._phoneNumber + " " + this._geoLong + " " + this._geoLat +"" +this.getImage();

        return result;
    }


}

