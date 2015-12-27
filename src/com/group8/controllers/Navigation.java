package com.group8.controllers;

import javafx.stage.Stage;

import java.util.ArrayList;

/**
 * Created by Linus Eiderström Swahn.
 *
 * Class for the Advanced Navigation system
 * --> Used to Navigate both Center FXML content and top FXML content (User input / login)
 */
public class Navigation
{
    public static Stage primaryStage;
    /**
     * Home FXML alt "scene"
     * --> Used by the "Home" button to allways return to this FXML
     */
    public static String homescreenFXML;

    /**
     * Temporary storage of Current FXML, is set in the initialization method at every center FXML alt "scene"
     * --> Used by the changeCenter method in Mainscene to add it to the breadcrubs Array when changeing FXML alt "scene"
     */
    public static String current_CenterFXML;

    /**
     * Array stores users previous center FXML alt "scene"
     * --> Used by the changeCenterBack method in Mainscene to correctly go back to users prevoius FXML "scene"
     */
    public static ArrayList<String> breadcrubs = new ArrayList<String>();
}
