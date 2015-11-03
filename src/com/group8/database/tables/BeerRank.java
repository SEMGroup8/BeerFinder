package com.group8.database.tables;
import java.util.Scanner;
import com.group8.database.MysqlDriver;

public class BeerRank extends MysqlDriver{
	int Stars;
	int userId;
	int beerID;
	public void insertRank(){
		String sqlQuery = "insert into beerRank values(" + Stars + ", " + userId + " , " + beerID + ");";
		
		insert(sqlQuery);
	}
	public BeerRank (int userId, int beerID, int stars){
		this.Stars = stars;
		this.userId = userId;
		this.beerID = beerID;
	}
}