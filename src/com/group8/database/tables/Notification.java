package com.group8.database.tables;
import com.group8.database.MysqlDriver;

/**
 * Created by Iosif Roberto Delatolas
 * 
 * Notification class to keep track of the notifications of each users favorite beers, pubs and users.
 */
public class Notification extends MysqlDriver{
	// Notification variables
	int pubID;
	int subed; // subed comes from subscribed and it is a boolean using 0 and 1
	int userId;
	int added;
	int beerID;
	int id;

	/**
	 * Insert the subscribed value into the database
	 * --> Using the MySqlDriver "insert" method.
	 */
	public void insertSubed(){
		String sqlQuery = "update favoritePub set subed=" + subed + " where userId=" + userId + " and pubID=" + pubID + ";";
		//System.out.println(sqlQuery);
		update(sqlQuery);
		}
	/**
	 * Insert the added value into the database
	 * --> Using the MySqlDriver "insert" method.
	 * 
	 */
	public void insertAdded(){
		String sqlQuery = "update favoritePub set subed=" + added + " where beerID=" + beerID + " and userId=" + userId + ";";
		//System.out.println(sqlQuery);
		update(sqlQuery);
		}
	/**
	 * Insert the id value into the database
	 * --> Using the MySqlDriver "insert" method.
	 * 
	 */
//	public void insertid(){
//		String sqlQuery = "update followUser set kati=" + kati + " where id=" + id + " and userId=" + userId + ";";
//		//System.out.println(sqlQuery);
//		update(sqlQuery);
//		}

	/**
	 * Notification constructor
	 * @param pubID
	 * @param subed
	 * @param userId
	 * @param added;
	 * @param beerID;
	 * @param id;
	 */
	public Notification (int pubID, int subed, int userId,int extra){
		this.pubID = pubID;
		this.subed = subed;
		this.userId = userId;
	}

	public Notification (int added, int beerID, int userId){
		this.userId = userId;
		this.added = added;
		this.beerID = beerID;
	}
	
//	public Notification (int id, int userId){
//		this.userId = userId;
//		this.id = id;
//	}


}