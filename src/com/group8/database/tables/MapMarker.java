package com.group8.database.tables;

import com.group8.database.MysqlDriver;
import java.util.ArrayList;

/**
 * Created by Linus Eiderström Swahn
 *
 * Class for constructing Google Maps Markers.
 */
public class MapMarker extends MysqlDriver
{
    // Marker variables
    private int pubID;

    private double longitude, latitude;


    private boolean inStock;


    private double price;

    private String pubName;

    private String address;

    /**
     * Created by Linus Eiderström Swahn.
     *
     * Constructor creating an instance out of an ArrayList of objects representing a row in the database.
     *
     * @param markerData
     */
    public MapMarker(ArrayList<Object> markerData)
    {
        this.pubID = Integer.parseInt(markerData.get(0).toString());
        this.pubName = markerData.get(1).toString();
        this.address = markerData.get(2).toString();
        this.price = Double.parseDouble(markerData.get(3).toString());
        this.latitude = Double.parseDouble(markerData.get(4).toString());
        this.longitude = Double.parseDouble(markerData.get(5).toString());
        this.inStock = Boolean.parseBoolean(markerData.get(6).toString());
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getPubName() {
        return pubName;
    }

    public String getAddress() {
        return address;
    }

    public double getPrice() {
        return price;
    }

    public boolean isInStock() {
        return inStock;
    }

    public String InStock() {

        if (isInStock()) {
            return "yes";
        } else {
            return "no";
        }
    }
    
    public int getPubID(){
    	return pubID;
    }
}
