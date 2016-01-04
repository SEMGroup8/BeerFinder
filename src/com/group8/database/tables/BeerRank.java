package com.group8.database.tables;
import java.util.Scanner;
import com.group8.database.MysqlDriver;

/**
 * Beer Rank class to keep track of the ranking of each beer.
 */
public class BeerRank extends MysqlDriver{
	// Beer rank variables
	int Stars;
	int userId;
	int beerID;

	/**
	 * Insert the rank into the database
	 * --> Useing the MySqlDriver "insert" method.
	 */
	public void insertRank(){
		String sqlQuery = "insert into beerRank values('" + Stars + "', '" + userId + "' , '" + beerID + "');";
		//System.out.println(sqlQuery);
		insert(sqlQuery);

		sqlQuery = "update beers set avStars = (Select AVG(Star) from beerRank where beerID = "+beerID+") where beerID ="+beerID+";";
		update(sqlQuery);


	}

	/**
	 * Beer rank constructor
	 * @param userId
	 * @param beerID
	 * @param stars
	 */
public BeerRank (int userId, int beerID, int stars){
		this.Stars = stars;
		this.userId = userId;
		this.beerID = beerID;
	}



}