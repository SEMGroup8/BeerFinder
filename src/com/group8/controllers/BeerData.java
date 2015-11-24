package com.group8.controllers;

import com.group8.database.tables.Beer;
import com.group8.database.tables.MapMarker;
import com.group8.database.tables.Pub;
import com.lynden.gmapsfx.javascript.object.LatLong;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by AnkanX on 15-10-26.
 *
 * TODO make a better holding class contained inside a directory for holding classes for better structure.
 *
 */
public class BeerData {
    /*
     Create a Arraylist of Beer(s)
    */
    public static ArrayList<Beer> beer;

    // A open beer allows us to look at detailed info in
    // result screen/beerDetailsScreen
    public static Beer selectedBeer;

    // The SQL query
    public static String searchInput;

    // The pubs that have a sertain beer
    public static ArrayList<MapMarker> markers;

    public static LatLong Address = null;


}
