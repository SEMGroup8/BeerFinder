package com.group8.controllers;

import com.group8.database.tables.Beer;
import com.group8.database.tables.Pub;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by AnkanX on 15-10-26.
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
    public static ArrayList<Pub> selectedBeerPubs;

    // TEST
    public static String geoPos;
    public static double geoPos1;
    public static double geoPos2;

    public static int[] beerID;

}
