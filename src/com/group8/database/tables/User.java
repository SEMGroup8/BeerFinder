package com.group8.database.tables;

import com.group8.controllers.UserData;
import com.group8.database.MysqlDriver;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.image.Image;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;

/**
 * Created by Linus Eiderström Swahn.
 *
 * User class representing a user in the application.
 *
 * Subclass of MysqlDriver, inherits database functionality.
 */
public class User extends MysqlDriver
{
    // User Data
    private int id;
    private String username;
    private String fullName;
    private String password;
    private String email;
    private boolean isPub;
    private int pubId;
    private Pub pub;
    private int age;
    private int numFollowers;

    public ArrayList<Beer> favourites;
    public ArrayList<Beer> beersInPub;
    public ArrayList<Pub> pubFavouritesDetails;
    public ArrayList<Pub> pubListDetails;
    public ArrayList<User>allUsers;
    public ArrayList<User>followedUsers;
    private BufferedImage userImage;
    private InputStream tmpImg;

    /**
     * Default constructor.
     */
    public User(){}

    /**
     * Created by Linus Eiderström Swahn.
     * Constructor with parameters for all fields of the user.
     * @param name
     * @param fullName
     * @param password
     * @param email
     * @param isPub
     * @param pubId
     * @param age
     */
    public User(String name, String fullName, String password, String email, boolean isPub, int pubId, int age)
    {
        this.username = name;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.age = age;
        this.isPub = isPub;
        if(this.isPub)
        {
            this.pubId = pubId;
        }
    }

    /**
     * Created by Linus Eiderström SWAHN.
     * Constructor.
     * Constructs a user from a sql-query, selecting one user.
     *
     * @param query
     * String containing the sql-query for selecting a user.
     */
    public User(String query)
    {
        super();

        ArrayList<Object> sqlReturn = select(query);

        if(sqlReturn==null)
        {
            return;
        }

        this.id = Integer.parseInt(sqlReturn.get(0).toString());
        this.username = sqlReturn.get(1).toString();
        this.fullName = sqlReturn.get(2).toString();
        this.password = sqlReturn.get(3).toString();
        this.email = sqlReturn.get(4).toString();
        this.age =Integer.parseInt(sqlReturn.get(7).toString());
        try {
            tmpImg = (InputStream) sqlReturn.get(8);
            this.userImage = javax.imageio.ImageIO.read(tmpImg);

       }catch (IOException ex){
           this.userImage = null;
       }

        System.out.println(sqlReturn.get(5).toString());

        switch (sqlReturn.get(5).toString())
        {

        }
        this.isPub = Boolean.parseBoolean(sqlReturn.get(5).toString());

        System.out.println(isPub);
        
        if(isPub)
        {
        	this.pubId = Integer.parseInt(sqlReturn.get(6).toString());
        }

        if(this.isPub)
        {
            this.pub = new Pub("Select * from pubs where pubID = '" + this.pubId + "';");
        }
    }

    /**
     * Created by Linus Eiderström Swahn.
     *
     * Constructor.
     * Constructs a User from an ArrayList of objects.
     *
     * @param sqlData
     * ArrayList of objects.
     */
    public User(ArrayList<Object> sqlData)
    {
        this.id = Integer.parseInt(sqlData.get(0).toString());
        this.age=Integer.parseInt(sqlData.get(7).toString());

        // load the Image.
        try {
            tmpImg = (InputStream) sqlData.get(8);
            this.userImage = javax.imageio.ImageIO.read(tmpImg);

        }catch (IOException ex){
            this.userImage = null;
        }

        // Is a pub owner or not
        if(Boolean.parseBoolean(sqlData.get(5).toString()))
        {
            setUser(sqlData.get(1).toString(), sqlData.get(2).toString(), sqlData.get(3).toString(), sqlData.get(4).toString(), Boolean.parseBoolean(sqlData.get(5).toString()), Integer.parseInt(sqlData.get(6).toString()));
        }
        else
        {
            setUser(sqlData.get(1).toString(), sqlData.get(2).toString(), sqlData.get(3).toString(), sqlData.get(4).toString(), Boolean.parseBoolean(sqlData.get(5).toString()));

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
        this.username = name;
        this.fullName = fullName;
        this.password = password;
        this.email = email;
        this.isPub = isPub;
    }

    /**
     * User setter.
     * --> Takes a number of variables to construct a User object.
     * @param name
     * @param fullName
     * @param password
     * @param email
     * @param isPub
     * @param pubId
     */
    public void setUser(String name, String fullName, String password, String email, boolean isPub, int pubId)
    {
        this.username = name;
        this.fullName = fullName;
        this.password = password;
        this.email = email;

        this.isPub = isPub;
        if(this.isPub)
        {
            this.pubId = pubId;
            this.pub = new Pub("Select * from pubs where pubID = '" + this.pubId + "';");
        }
    }

    /**
     * Created by Collins
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
			System.out.println(pub.getName() + "  which pub???");
			this.pubListDetails.add(pub);

		}
	}

    /**
     * Created by Collins
     */
    public void getUsers(){
    	//String userList="Select users.userId, username, fullname, password, email, isPub from users where isPub="+0;
    	String userList="select * from users where isPub="+0;
    	ArrayList<ArrayList<Object>> users;
        users=	MysqlDriver.selectMany(userList);
    	allUsers = new ArrayList<User>();
    	
    	for (int i = 0; i < users.size(); i++) {

            User user1 = new User(users.get(i));

            this.allUsers.add(user1);

        }
    }

    /**
     * Created by
     */
    public void getFollowers(){
    	//String sqlQuery="select followUser.id, username,image, password, fullname, email, age  from users, followUser where users.userId=followUser.id AND followUser.userId="+ UserData.userInstance.getId() ;
    	String sqlQuery="select * from users, followUser where users.userId=followUser.id AND followUser.userId="+ UserData.userInstance.getId();
    	ArrayList<ArrayList<Object>>follower;
    	follower=MysqlDriver.selectMany(sqlQuery);
    	
    	followedUsers= new ArrayList<User>();
    	numFollowers=follower.size();
    	for (int i = 0; i < follower.size(); i++) {
    		
    		User fol = new User(follower.get(i));
    		
    		this.followedUsers.add(fol);
        }
    }

    /**
     * Created by Collins
     * Gets normal users Favourites list of pubs.
     * --> Used when pushing the "Favourite pubs" button.
     * --> Gets an array of pubs from the server and stores them in the UserInstance.
     */
	public void getPubFavourites() {
		String selFavPub = "select pubs.pubID,`name`, image,`phoneNumber`, `description`, `offers`, `entrenceFee` FROM `pubs`,`favouritePub` WHERE pubs.pubID=favouritePub.pubId AND favouritePub.userId = "	+ id;
		ArrayList<ArrayList<Object>> sqlData3;

		sqlData3 = MysqlDriver.selectMany(selFavPub);
		pubFavouritesDetails = new ArrayList<Pub>();

		for (int i = 0; i < sqlData3.size(); i++) {
			
			Pub pubFavouritesDetails1 = new Pub(sqlData3.get(i));
			// Testoutput
			this.pubFavouritesDetails.add(pubFavouritesDetails1);
		}
	}
    /**
     * Created by Linus Eiderström Swahn.
     *
     * Selects all beers the user has as a favourite and stores it as an array.
     */
    public void getFavouriteBeers()
    {
        String sqlQuery =
                "select beers.beerID, name, image, description, beerTypeEN, countryName, percentage, producerName, volume, isTap, packageTypeEN, price, avStars, countryFlag " +
                "from beers, favourites, beerType, origin, package " +
                "where beers.package = package.packageID " +
                "and beers.beerTypeID = beerType.beerTypeID " +
                "and beers.originID = origin.OriginID " +
                "and beers.beerID = favourites.beerID " +
                "and favourites.userId = " + id + ";";

        ArrayList<ArrayList<Object>> sqlData;

        sqlData = selectMany(sqlQuery);

        this.favourites = new ArrayList<Beer>();

        for (int i = 0; i < sqlData.size(); i++) {
            // Add a new Beer to the beer arraylist
            Beer beer = new Beer(sqlData.get(i));

            this.favourites.add(beer);
        }
    }

    /**
     * Created by Linus Eiderström Swahn.
     *
     * Get all the beers the pub is selling.
     */
    public void getBeersInPub()
    {
        String sqlQuery =
                "select beers.beerID, name, image, description, beerTypeEN, countryName, percentage, producerName, volume, isTap, packageTypeEN, beerInPub.price, avStars, countryFlag " +
                "from beers, beerInPub, beerType, origin, package " +
                "where beers.package = package.packageID " +
                        "and beers.beerTypeID = beerType.beerTypeID " +
                        "and beers.originID = origin.OriginID " +
                        "and beers.beerID = beerInPub.beerID " +
                        "and beerInPub.pubID = " + pubId + ";";

        ArrayList<ArrayList<Object>> sqlData;

        sqlData = MysqlDriver.selectMany(sqlQuery);

        beersInPub = new ArrayList<Beer>();

        for (int i = 0; i < sqlData.size(); i++) {
            // Add a new Beer to the beer arraylist
            Beer beer = new Beer(sqlData.get(i));

            this.beersInPub.add(beer);
        }
    }

    /**
     * Created by Linus Eiderström Swahn.
     *
     * Specialized insert function for the user.
     * Inserts the user into the database.
     */
    public void insert()
    {
    	// Loads a default image for users pub when registering
        File file = new File("src/com/group8/resources/Images/home.jpg");
        FileInputStream imageStream = null;
        try {
			imageStream = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
     // Setting up database connection
        Connection con = null;
        PreparedStatement st = null;
        String url = "jdbc:mysql://sql.smallwhitebird.com:3306/beerfinder";
        String user = "Gr8";
        String password = "group8";

        try {
            con = DriverManager.getConnection(url, user, password);

            String selectQuery =
                    "INSERT INTO `beerfinder`.`users` (`userId`, `username`, `fullName`, `password`, `email`, `isPub`, `pubID`, `image`, `age`)" +
                    "VALUES (NULL, '" + this.username + "', '" + this.fullName + "', '" + this.password + "', '" + this.email + "', '" +
                            (this.isPub ? 1 : 0) + "', " + (this.isPub ? this.pubId : "NULL") + ", ?, " + this.age + ");";
            st = con.prepareStatement(selectQuery);
            // Add the image bytestream
            st.setBinaryStream(1, imageStream, (int) file.length());
            st.executeUpdate();
        } catch (SQLException ex){
            // in case of SQL error print error msg
            ex.printStackTrace();
        }
    }

    /**
     * Created by Linus Eiderström Swahn.
     *
     * Updates the user on the database.
     */
    public void updateUser()
    {
        String query = "UPDATE `users` SET `fullName` = '" + this.fullName + "', `password` = '" + this.password +
                "', `email` = '" + this.email + "' WHERE `users`.`userId` = " + this.id + ";";

        update(query);
    }

    // Getters and setters.
    public int getId() {
        return id;
    }

    public String get_name() {
        return username;
    }

    public boolean getIsPub() {
        return isPub;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPassword() {
        return password;
    }
    public Pub getPub(){
    	return pub;
    }

    public String getEmail() {
        return email;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPubId() {
        return pubId;
    }
    public int getAge() {
        return age;
    }
    public int getNumFollowers() {
        return numFollowers;
    }
	public Image getImage() {
		  Image image2;
	        if(this.userImage == null){
	              image2 = null;
	        }else{
	              image2 = SwingFXUtils.toFXImage(this.userImage, null);
	        }
	        return image2;
	    }
}
