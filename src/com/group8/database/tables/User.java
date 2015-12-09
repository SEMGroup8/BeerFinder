package com.group8.database.tables;

import com.group8.controllers.BeerData;
import com.group8.database.MysqlDriver;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class for constructing Users of diffrent kinds.
 * --> Used by the RegisterUserController.
 */
public class User extends MysqlDriver
{
    // User Data
    private int _id;
    private String _username;
    private String _fullName;
    private String _password;
    private String _email;
    private boolean _isPub;
    private int _pubId;
    private Pub _pub;
    // User Specific Extra Data
    public ArrayList<Beer> favourites;
    public ArrayList<Pub> pubFavouritesDetails;
    public ArrayList<Pub> pubListDetails;

    /**
     * Default User constructor.
     */
    public User()
    {

    }

    /**
     * User constructor.
     * --> Calls diffrent functions inside the User constructor class to construct a User
     *     from specifications.
     * --> Takes a SQL query and constructs a User or Pubowner User depending
     *     on if _isPub is 'true' or not.
     * @param query
     */
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

    /**
     * User constructor.
     * --> Calls diffrent functions inside the User constructor class to construct a User
     *     from specifications.
     * --> Takes a Arraylist of the type Object and constructs a User or Pubowner User depending
     *     on if _isPub is 'true' or not.
     * @param sqlData
     */
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
           // getFavourites();
        }
        else
        {
            getFavourites();
            getPubFavourites();
        }
    }

    /**
     * User setter.
     * --> Takes a number odf variables to construct a User object. ( Have no pubID) ??
     * @param name
     * @param fullName
     * @param password
     * @param email
     * @param isPub
     */
    public void setUser(String name, String fullName, String password, String email, boolean isPub)
    {
        this._username = name;
        this._fullName = fullName;
        this._password = password;
        this._email = email;
        this._isPub = isPub;
    }

    /**
     * User setter.
     * --> Takes a number odf variables to construct a User object.
     * @param name
     * @param fullName
     * @param password
     * @param email
     * @param isPub
     * @param pubId
     */
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

    /**
     * TODO ??
     */
    public void getPubs() throws IOException {
		String listOfPub = "select pubs.pubID,`name`,image, `phoneNumber`, `description`, `offers`, `entrenceFee` from   pubs";
		ArrayList<ArrayList<Object>> SQLData4;

		SQLData4 = MysqlDriver.selectMany(listOfPub);
		System.out.println(SQLData4 + " meeeee");
		pubListDetails = new ArrayList<Pub>();

		for (int i = 0; i < SQLData4.size(); i++) {
			// Add a new Beer to the beer arraylist
			Pub pub = new Pub(SQLData4.get(i));
			// Testoutput
			System.out.println(pub.get_name() + "  which pub???");
			this.pubListDetails.add(pub);

		}
	}

    /**
     * Gets normal users Favourites list of pubs.
     * --> Used when pushing the "Favourite pubs" button.
     * --> Gets an array of pubs from the server and stores them in the UserInstance.
     */
	public void getPubFavourites() {
		System.out.println("get userId" + _id);
		String selFavPub = "select pubs.pubID,`name`, image,`phoneNumber`, `description`, `offers`, `entrenceFee` FROM `pubs`,`favouritePub` WHERE pubs.pubID=favouritePub.pubId AND favouritePub.userId = "	+ _id;
		ArrayList<ArrayList<Object>> sqlData3;

		sqlData3 = MysqlDriver.selectMany(selFavPub);
		System.out.println(sqlData3 +" \n trrrryyyyyfavpub");
		pubFavouritesDetails = new ArrayList<Pub>();
		System.out.println(pubFavouritesDetails+"....ok");

		for (int i = 0; i < sqlData3.size(); i++) {
			// Add a new Beer to the beer arraylist
			Pub pubFavouritesDetails1 = new Pub(sqlData3.get(i));
			// Testoutput
			System.out.print(pubFavouritesDetails1.get_name() + "  again\n");
			this.pubFavouritesDetails.add(pubFavouritesDetails1);
		}
	}
    /**
     * Gets normal users Favourites list of beers.
     * --> Used when pushing the "Favourite beers" button.
     * --> Gets an array of beers from the server and stores them in the UserInstance.
     */
    public void getFavourites()
    {
        String sqlQuery = "select beers.beerID, name, image, description, beerTypeEN, countryName, percentage, producerName, volume, isTap, packageTypeEN, price, avStars, countryFlag " +
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

    /**
     *
     */
    public void getBeers()
    {
        String sqlQuery = "select beers.beerID, name, image, description, beerTypeEN, countryName, percentage, producerName, volume, isTap, packageTypeEN, beerInPub.price, avStars, countryFlag " +
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

    /**
     * User insert fucntion.
     */
    public void insert()
    {
        String selectQuery =
                "INSERT INTO `beerfinder`.`users` (`userId`, `username`, `fullName`, `password`, `email`, `isPub`, `pubID`)" +
                "VALUES (NULL, '" + _username + "', '" + _fullName + "', '" + _password + "', '" + _email + "', '" +
                        (this._isPub ? 1 : 0) + "', " + (this._isPub ? this._pubId : "NULL") + ");";

        insert(selectQuery);

        System.out.println("Inserted");
    }

    /**
     * User update function.
     */
    public void updateUser()
    {
        String query = "UPDATE `users` SET `fullName` = '" + _fullName + "', `password` = '" + _password +
                "', `email` = '" + _email + "' WHERE `users`.`userId` = " + _id + ";";

        System.out.println(query);

        update(query);

        getFavourites();
    }

    // Getters and setters.
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
