package com.group8.database.tables;

import com.group8.database.MysqlDriver;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;

public class User extends MysqlDriver
{
    private int _id;
    private String _name, _password, _email;

    private boolean _isPub;
    private int _pubId;
    private Pub _pub;

    public User(String query)
    {
        super();

        ArrayList<Object> sqlReturn = select(query);

        if(sqlReturn==null)
        {
            return;
        }

        this._id = Integer.parseInt(sqlReturn.get(0).toString());
        this._name = sqlReturn.get(1).toString();
        this._password = sqlReturn.get(2).toString();

        System.out.println(sqlReturn.get(4).toString());

        switch (sqlReturn.get(4).toString())
        {

        }
        this._isPub = Boolean.parseBoolean(sqlReturn.get(4).toString());

        this._pubId = Integer.parseInt(sqlReturn.get(5).toString());

        if(this._isPub)
        {
            this._pub = new Pub("Select * from pubs where pubID = '" + this._pubId + "';");
        }
    }

    public User(ArrayList<Object> sqlData)
    {
        this(sqlData.get(1).toString(), sqlData.get(2).toString(), sqlData.get(3).toString(), Boolean.parseBoolean(sqlData.get(4).toString()), Integer.parseInt(sqlData.get(5).toString()));
    }

    public User(String name, String password, String email, boolean isPub, int pubId)
    {
        this._name = name;
        this._password = password;
        this._email = email;

        this._isPub = isPub;
        if(this._isPub)
        {
            this._pubId = pubId;
        }
    }

    public void insert()
    {
        String selectQuery =
                "INSERT INTO `beerfinder`.`users` (`userId`, `username`, `password`, `email`, `isPub`, `pubID`)" +
                "VALUES (NULL, '" + _name + "', '" + _password + "', '" + _email + "', '" +
                        (this._isPub ? 1 : 0) + "', " + (this._isPub ? this._pubId : "NULL") + ");";

        insert(selectQuery);

        System.out.println("Inserted");
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
