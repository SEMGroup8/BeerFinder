package com.group8.singletons;


import java.util.ArrayList;

import com.group8.database.tables.User;

/**
 * Created by Linus Eiderström Swahn.
 *
 * Stores singletons related to the User.
 */
public class UserData
{
    // Currently logged in user.
    public static User userInstance;
    
    public static ArrayList<User> users;
    
    public static User selected;
    
    public static ArrayList<User> following;
    
    
}
