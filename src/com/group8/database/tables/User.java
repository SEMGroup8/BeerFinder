package com.group8.database.tables;

import com.group8.database.MysqlDriver;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;

public class User extends MysqlDriver
{
    private int _id;
    private String _name;

    private boolean _isPub;
    private int _pubId;
    private Pub _pub;

    public User(String query)
    {
        super();

        ArrayList<Object> sqlReturn = super.select(query);

        this._id = Integer.parseInt(sqlReturn.get(0).toString());
        this._name = sqlReturn.get(1).toString();

        this._isPub = Boolean.parseBoolean(sqlReturn.get(3).toString());

        this._pubId = Integer.parseInt(sqlReturn.get(4).toString());

        if(this._isPub)
        {
            this._pub = new Pub("Select * from pubs where pubID = '" + this._pubId + "';");
        }
    }

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _name;
    }

    public boolean get_isPub() {
        return _isPub;
    }
}
