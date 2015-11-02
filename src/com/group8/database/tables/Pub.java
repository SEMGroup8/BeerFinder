package com.group8.database.tables;

import com.group8.database.MysqlDriver;

import java.awt.*;
import java.util.ArrayList;

/**
 * Created by Shiratori on 12/10/15.
 *
 * Class to represent Pubs.
 *
 * Derives from MysqlDriver and thus inherits the relevant funcitons for inserting and selecting form the database.
 *
 */
public class Pub extends MysqlDriver{

    private int _pubId;
    private String _name, _description, _adress, _phoneNumber, _offer;
    float entranceFee;
    Image pubImage;

    public Pub()
    {

    }

    public Pub(String query)
    {
        super();

        ArrayList<Object> sqlReturn = select(query);

        this._name = sqlReturn.get(2).toString();
        this._description = sqlReturn.get(6).toString();
        this._adress = sqlReturn.get(3).toString();
        this._phoneNumber = sqlReturn.get(4).toString();
        this._offer = sqlReturn.get(7).toString();
        this.entranceFee = Float.parseFloat(sqlReturn.get(8).toString());
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
}
