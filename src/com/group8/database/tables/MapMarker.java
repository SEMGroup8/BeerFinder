package com.group8.database.tables;

import com.group8.database.MysqlDriver;
import com.mysql.fabric.jdbc.FabricMySQLDriver;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;

/**
 * Created by Shiratori on 12/11/15.
 */
public class MapMarker extends MysqlDriver
{
    private int pubID;

    private double longitude, latitude;

    private boolean inStock;

    private double price;

    private String pubName;

    private String address;

    public MapMarker(ArrayList<Object> arrayList)
    {
        this.pubID = Integer.parseInt(arrayList.get(0).toString());
        this.pubName = arrayList.get(1).toString();
        this.address = arrayList.get(2).toString();
        this.price = Double.parseDouble(arrayList.get(3).toString());
        this.latitude = Double.parseDouble(arrayList.get(4).toString());
        this.longitude = Double.parseDouble(arrayList.get(5).toString());
        this.inStock = Boolean.parseBoolean(arrayList.get(6).toString());
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
