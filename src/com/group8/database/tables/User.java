package com.group8.database.tables;

import com.group8.controllers.BeerData;
import com.group8.database.MysqlDriver;
import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.ArrayList;

public class User extends MysqlDriver
{
    private int _id;
    private String _username;
    private String _fullName;
    private String _password;
    private String _email;
    private boolean _isPub;
    private int _pubId;
    private Pub _pub;

    public ArrayList<Beer> favourites;

    public User()
    {

    }

    public User(String query)
    {
        super();

        ArrayList<Object> sqlReturn = select(query);

        if(sqlReturn==null)
        {
            return;
        }

        this._id = Integer.parseInt(sqlReturn.get(0).toString());
        this._username = sqlReturn.get(1).toString();
        this._fullName = sqlReturn.get(2).toString();
        this._password = sqlReturn.get(3).toString();
        this._email = sqlReturn.get(4).toString();

        System.out.println(sqlReturn.get(5).toString());

        switch (sqlReturn.get(5).toString())
        {

        }
        this._isPub = Boolean.parseBoolean(sqlReturn.get(5).toString());

        System.out.println(_isPub);
        
        this._pubId = Integer.parseInt(sqlReturn.get(6).toString());

        if(this._isPub)
        {
            this._pub = new Pub("Select * from pubs where pubID = '" + this._pubId + "';");
        }

        getFavourites();
    }

    public User(ArrayList<Object> sqlData)
    {
        _id = Integer.parseInt(sqlData.get(0).toString());

        if(Boolean.parseBoolean(sqlData.get(5).toString()))
        {
            setUser(sqlData.get(1).toString(), sqlData.get(2).toString(), sqlData.get(3).toString(), sqlData.get(4).toString(), Boolean.parseBoolean(sqlData.get(5).toString()), Integer.parseInt(sqlData.get(6).toString()));
        }
        else
        {
            setUser(sqlData.get(1).toString(), sqlData.get(2).toString(), sqlData.get(3).toString(), sqlData.get(4).toString(), Boolean.parseBoolean(sqlData.get(5).toString()));

        }

        if(_isPub)
        {
            getBeers();
        }
        else
        {
            getFavourites();
        }
    }

    public void setUser(String name, String fullName, String password, String email, boolean isPub)
    {
        this._username = name;
        this._fullName = fullName;
        this._password = password;
        this._email = email;
        this._isPub = isPub;
    }

    public void setUser(String name, String fullName, String password, String email, boolean isPub, int pubId)
    {
        this._username = name;
        this._fullName = fullName;
        this._password = password;
        this._email = email;

        this._isPub = isPub;
        if(this._isPub)
        {
            this._pubId = pubId;
        }
    }

    public void getFavourites()
    {
        String sqlQuery = "select beers.beerID, name, image, description, beerTypeEN, countryName, percentage, producerName, volume, isTap, packageTypeEN, price, avStars " +
                "from beers, favourites, beerType, origin, package where beers.package = package.packageID and beers.beerTypeID = beerType.beerTypeID and beers.originID = origin.OriginID and beers.beerID = favourites.beerID and favourites.userId = " + _id + ";";

        // Execute user query
        ArrayList<ArrayList<Object>> sqlData;

        sqlData = MysqlDriver.selectMany(sqlQuery);

        favourites = new ArrayList<Beer>();

        for (int i = 0; i < sqlData.size(); i++) {
            // Add a new Beer to the beer arraylist
            Beer beer = new Beer(sqlData.get(i));
            // Testoutput
            System.out.print(beer.getName()+"\n");
            this.favourites.add(beer);
        }
    }

    public void getBeers()
    {
        String sqlQuery = "select beers.beerID, name, image, description, beerTypeEN, countryName, percentage, producerName, volume, isTap, packageTypeEN, price, avStars " +
                "from beers, beerInPub, beerType, origin, package where beers.package = package.packageID and beers.beerTypeID = beerType.beerTypeID and beers.originID = origin.OriginID and beers.beerID = beerInPub.beerID and beerInPub.pubID = " + _pubId + ";";

        // Execute user query
        ArrayList<ArrayList<Object>> sqlData;

        sqlData = MysqlDriver.selectMany(sqlQuery);

        favourites = new ArrayList<Beer>();

        for (int i = 0; i < sqlData.size(); i++) {
            // Add a new Beer to the beer arraylist
            Beer beer = new Beer(sqlData.get(i));
            // Testoutput
            System.out.print(beer.getName()+"\n");
            this.favourites.add(beer);
        }
    }

    public void insert()
    {
        String selectQuery =
                "INSERT INTO `beerfinder`.`users` (`userId`, `username`, `fullName`, `password`, `email`, `isPub`, `pubID`)" +
                "VALUES (NULL, '" + _username + "', '" + _fullName + "', '" + _password + "', '" + _email + "', '" +
                        (this._isPub ? 1 : 0) + "', " + (this._isPub ? this._pubId : "NULL") + ");";

        insert(selectQuery);

        System.out.println("Inserted");
    }

    public void updateUser()
    {
        String query = "UPDATE `users` SET `fullName` = '" + _fullName + "', `password` = '" + _password +
                "', `email` = '" + _email + "' WHERE `users`.`userId` = " + _id + ";";

        System.out.println(query);

        update(query);
    }

    public int get_id() {
        return _id;
    }

    public String get_name() {
        return _username;
    }

    public boolean get_isPub() {
        return _isPub;
    }

    public String get_fullName() {
        return _fullName;
    }

    public String get_password() {
        return _password;
    }

    public String get_email() {
        return _email;
    }

    public boolean is_isPub() {
        return _isPub;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public int get_pubId() {
        return _pubId;
    }


}
