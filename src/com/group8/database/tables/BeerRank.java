package com.group8.database.tables;
import java.util.Scanner;
import com.group8.database.MysqlDriver;

public class BeerRank extends MysqlDriver{
	int Stars;
	int userId;
	int beerID;
	public void insertRank(){
		String sqlQuery = "insert into beerRank values('" + Stars + "', '" + userId + "' , '" + beerID + "');";
		System.out.println(sqlQuery);
		insert(sqlQuery);

		sqlQuery = "update beers set avStars = (Select AVG(Star) from beerRank where beerID = "+beerID+") where beerID ="+beerID+";";
		update(sqlQuery);


	}
	public BeerRank (int userId, int beerID, int stars){
		this.Stars = stars;
		this.userId = userId;
		this.beerID = beerID;
	}



}