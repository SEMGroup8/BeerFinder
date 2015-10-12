package com.group8.database.tables;

import com.group8.database.MysqlDriver;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Shiratori on 12/10/15.
 */
public class Pub extends MysqlDriver{

    String from = "pubs";
    private String name, description, adress, phoneNumber, offer;
    float entranceFee;
    Image pubImage;

    public void Pub(String query)
    {
        ArrayList<Object> sqlReturn = super.select(query);

        this.name = sqlReturn.get(2).toString();
        this.description = sqlReturn.get(6).toString();
        this.adress = sqlReturn.get(3).toString();
        this.phoneNumber = sqlReturn.get(4).toString();
        this.offer = sqlReturn.get(7).toString();
        this.entranceFee = Float.parseFloat(sqlReturn.get(8).toString());
    }
    
    public void insertPub() {

        String query = "";



        super.insert(query);
    }
}
