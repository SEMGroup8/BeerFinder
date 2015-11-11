package com.group8.controllers;

import com.group8.database.tables.Beer;

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

    public static String searchInput;

    public static int[] beerID;

}
