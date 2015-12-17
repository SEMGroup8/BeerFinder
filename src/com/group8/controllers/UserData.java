package com.group8.controllers;


import java.util.ArrayList;

import com.group8.database.tables.User;

/**
 * Class for storing current User instance.
 * --> Used to be able to show user his favourite list of beer
 *     make user able to rank beers, or if user is a Pub owner
 *     make him able to manipulate his Pub data and add beers
 *     to his pub.
 */
public class UserData
{
    public static User userInstance;
    
    public static ArrayList<User> users;
    
    public static User selected;
    
    public static ArrayList<User>following;
    
    
}
