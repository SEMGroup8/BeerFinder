package com.group8.controllers;

import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by Shiratori on 04/11/15.
 */
public class Navigation
{
    public static Stage primaryStage;
    public static String homescreenFXML;
    public static String resultviewFXML;
    public static String backFXML;
    public static String beerDetailviewFXML;
    public static String mapviewFXML;
    public static String current_CenterFXML;

    // TODO store previous pages so you can correctly go back
   public static ArrayList<String> breadcrubs = new ArrayList<String>();
}
