package com.group8.database.tables;

import com.group8.database.MysqlDriver;
import com.sun.org.apache.xml.internal.security.utils.Base64;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by Shiratori on 12/10/15.
 *
 * Class to represent Pubs.
 *
 * Derives from MysqlDriver and thus inherits the relevant functions for inserting and selecting form the database.
 *
 */
public class Pub extends MysqlDriver{
	
	File file;

    private int _pubId, _adressId;
    private String _name;
    private String _description;


    private String _adress;
    private String _phoneNumber;
    private String _offer;

    private double _geoLong;

    private double _geoLat;
    float _entranceFee;

	private BufferedImage image;


    public Pub()
    {

    }

    public Pub(String query) throws MalformedURLException
    {
        super();

        ArrayList<Object> sqlReturn = selectPub(query);

        this._pubId = Integer.parseInt(sqlReturn.get(0).toString());
        this._name = sqlReturn.get(1).toString();
        this._description = sqlReturn.get(5).toString();

        String adressQuery = "Select address from pubAddress where addressID = " + Integer.parseInt(sqlReturn.get(2).toString());

        ArrayList<Object> addressReturn = select(adressQuery);

        this._adressId = Integer.parseInt(sqlReturn.get(2).toString());
        this._adress = addressReturn.get(0).toString();
        try {
            InputStream tmpImg = (InputStream) sqlReturn.get(4);
            this.image = javax.imageio.ImageIO.read(tmpImg);
        }catch (IOException ex){
        	ex.printStackTrace();
            this.image = null;
        }
        this._phoneNumber = sqlReturn.get(3).toString();
        this._offer = sqlReturn.get(6).toString();
        this._entranceFee = Float.parseFloat(sqlReturn.get(7).toString());
    }
    
    private String ImageParseImg(String string) {
		// TODO Auto-generated method stub
		return null;
	}

	public Pub(ArrayList<Object> sqlReturn)
    {
        this._pubId = Integer.parseInt(sqlReturn.get(0).toString());
        this._name = sqlReturn.get(1).toString();
        this._description = sqlReturn.get(4).toString();
//        this._adressId = Integer.parseInt(sqlReturn.get(2).toString());System.out.println(_adressId + "adddressss");
//        String adressQuery = "Select address from pubAddress where addressID = " + Integer.parseInt(sqlReturn.get(2).toString());
//        ArrayList<Object> addressReturn = select(adressQuery);
//        this._adress = addressReturn.get(0).toString();
        try {
            InputStream tmpImg = (InputStream) sqlReturn.get(2);
            this.image = javax.imageio.ImageIO.read(tmpImg);
        }catch (IOException ex){
            this.image = null;
        }
        this._phoneNumber = sqlReturn.get(3).toString();
       this._offer = sqlReturn.get(5).toString();
        this._entranceFee = Float.parseFloat(sqlReturn.get(6).toString());
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
    
    private ArrayList<Object> selectPub(String query) {

        ArrayList<Object> result = new ArrayList<>();

        Connection con = null;
        Statement st = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://sql.smallwhitebird.com:3306/beerfinder";
        String user = "Gr8";
        String password = "group8";

        try {
            con = DriverManager.getConnection(url, user, password);
            st = con.createStatement();
            rs = st.executeQuery(query);
            ResultSetMetaData metaData = rs.getMetaData();

            if(!rs.next())
            {
                try {
                    if (rs != null) {
                        rs.close();
                    }
                    if (st != null) {
                        st.close();
                    }
                    if (con != null) {
                        con.close();
                    }

                } catch (SQLException ex) {
                    Logger lgr = Logger.getLogger(MysqlDriver.class.getName());
                    lgr.log(Level.WARNING, ex.getMessage(), ex);
                }

                return null;
            }

            result = new ArrayList<>();

            for(int i = 1; i<=metaData.getColumnCount(); i++)
            {
                if(i == 5){

                    InputStream image =rs.getBinaryStream(5);
                    result.add(image);
                }else {
                    result.add(rs.getObject(i));
                }
            }


        } catch (SQLException ex) {
            Logger lgr = Logger.getLogger(MysqlDriver.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (st != null) {
                    st.close();
                }
                if (con != null) {
                    con.close();
                }

            } catch (SQLException ex) {
                Logger lgr = Logger.getLogger(MysqlDriver.class.getName());
                lgr.log(Level.WARNING, ex.getMessage(), ex);
            }
        }

        return result;
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

    public String get_adress() {
        return _adress;
    }
    public String get_description() {
        return _description;
    }

    public String get_offer() {
        return _offer;
    }
    public float get_entranceFee() {
        return _entranceFee;
    }

    public String get_phoneNumber() {
        return _phoneNumber;
    }

    public Image getImage()
    {
    	Image image2;
        if(this.image == null){
        	System.out.println("Image is empty");
            image2 = null;
        }else{
            image2 = SwingFXUtils.toFXImage(this.image, null);
        }
        return image2;
    }
    
	
}
