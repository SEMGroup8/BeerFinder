package com.group8.singletons;

import com.group8.database.tables.Beer;
import com.group8.database.tables.MapMarker;
import com.group8.database.tables.Pub;
import com.lynden.gmapsfx.javascript.object.LatLong;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Used to store data on searchForPubsCheckbox.
 * --> Used by the Pubdetail view to store data before showing selected data to the user.
 */
public class PubData {
	
	public static Pub loggedInPub;
	  
    public static ArrayList<Pub> pubs;

    // A open beer allows us to look at detailed info in
    // result screen/beerDetailsScreen
    public static Pub selectedPub;

    // The SQL query
    public static String searchInput;

    // The searchForPubsCheckbox that have a certain beer
    public static ArrayList<MapMarker> markers;

    public static LatLong Address = null;


}
