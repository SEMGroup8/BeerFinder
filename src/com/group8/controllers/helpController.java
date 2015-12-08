package com.group8.controllers;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.spreadsheet.Grid;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by AnkanX on 15-12-07.
 */
public class helpController extends BaseController implements Initializable{

    @FXML
    public TreeView<String> helpTree;
    @FXML
    public TextArea helpText;
    @FXML
    public ImageView helpImage;


    public void getRow(){
        helpTree.setOnMouseClicked(new EventHandler<MouseEvent>() {
            // Select item will only be displayed when dubbleclicked

            /**
             * Dubleclick event
             * @param event
             */
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    // Show that we can select items and print it
                    //System.out.println("clicked on " + beerTable.getSelectionModel().getSelectedItem());
                    // Set the selectedBeer instance of beer we have to selected item
                    String active = helpTree.getSelectionModel().getSelectedItem().toString();
                    System.out.println(active);
                     active = helpTree.getSelectionModel().getSelectedItem().getValue();
                    System.out.println(active);


                    // Load the details scene
                    // Has to be in a tr / catch becouse of the event missmatch, ouseevent cant throw IOexceptions
                    // try {
                    // TODO have to fix nameing
                    //mainScene.changeCenter("/com/group8/resources/views/beerDetails_center.fxml");
                    // } catch (IOException e) {
                    // Print error msg
                    //   e.printStackTrace();
                    // }
                   // helpText.setText(active);

                    showHelp(active);



                }
            }

        });


    }

    public void showHelp(String subject){

        switch (subject){

            case "Help":
                helpText.setText("This is the help section please choose a sub topic depending on your user preference. \n\n " +
                        "--> Recreational User \n\n " +
                        "--> Programmer");
                helpImage.setImage(new Image(new File("src/com/group8/resources/Images/helpStart.png").toURI().toString()));
                break;
            case "User":
                helpText.setText("This is the User's help section. \n\n " +
                        "--> Navigate to the appropriate sub topic to get specific help.");
                helpImage.setImage(new Image(new File("src/com/group8/resources/Images/userHelp.png").toURI().toString()));
                break;
            case "Programmer":
                helpText.setText("This is the Programmer's help section. \n\n " +
                                 "--> Navigate to the appropriate sub topic to get specific help. \n" +
                                 "\n " +
                                 "--> ");
                helpImage.setImage(new Image(new File("src/com/group8/resources/Images/ProgrammingHelp.png").toURI().toString()));
                break;
            case "Login":
                helpText.setText("This is the Login help section. \n\n " +
                        "To login you have to supply the following\n" +
                        "   --> Username\n" +
                        "   --> Password\n\n" +
                        "If the information inserted to either field is inacurate a error dialog window will be shown to inform the user.");
                helpImage.setImage(new Image(new File("src/com/group8/resources/Images/loginHelp.png").toURI().toString()));
                break;
            case "Register":
                helpText.setText("This is the Register User help section. \n\n" +
                        " To register a User you have to supply the following. \n\n" +
                        "   --> A Username\n" +
                        "   --> Your Full name\n" +
                        "   --> A secure password\n" +
                        "   --> A Valid Email address\n\n " +
                        " If you want to register a Pub User you also need to supply the following: \n\n" +
                        "   --> Your Pub's Name\n" +
                        "   --> Check the Pub Owner checkbox");
                helpImage.setImage(new Image(new File("src/com/group8/resources/Images/helpregister.png").toURI().toString()));
                break;
            case "Navigation":
                helpText.setText("This is the Navigation explanation section. \n\n" +
                        "--> Navigation is a part of the mainScene controller and the mainScene FXML\n\n" +
                        "--> It is used to keep track of user activity and perform a correct backtracking system.");
                helpImage.setImage(new Image(new File("src/com/group8/resources/Images/navigationHelp.png").toURI().toString()));
                break;
            case "changeCenter(String URL)":
                helpText.setText("This is the changeCenter explanation section.\n\n" +
                        "-->The function changeTop(String) gets an URL in String format and performs a FXML & Controller switch for the center element in the mainScene.");
                helpImage.setImage(new Image(new File("src/com/group8/resources/Images/changeCenter.png").toURI().toString()));
                break;
            case "changeTop(String URL)":
                helpText.setText("This is the changeTop explanation section.\n\n" +
                        "--> The function changeTop(String) gets an URL in String format and performs a FXML & Controller switch for the top element in the mainScene.");
                helpImage.setImage(new Image(new File("src/com/group8/resources/Images/changeTop.png").toURI().toString()));
                break;
        }


    }





    @Override
    public void initialize(URL location, ResourceBundle resources) {

        showHelp("Help");

        // Create Tree items
        TreeItem<String> treeRoot = new TreeItem<>("Help");
        treeRoot.setExpanded(true);

        TreeItem<String> child_User = new TreeItem<>("User");
        TreeItem<String> child_Programmer = new TreeItem<>("Programmer");

        TreeItem<String> subChild_Login = new TreeItem<>("Login");
        TreeItem<String> subChild_Register = new TreeItem<>("Register");
        TreeItem<String> subChild_Navigation = new TreeItem<>("Navigation");
        TreeItem<String> subChild_changeCenter = new TreeItem<>("changeCenter(String URL)");
        TreeItem<String> subChild_changeTop = new TreeItem<>("changeTop(String URL)");
        // Set root item
        helpTree.setRoot(treeRoot);

        // Add hirarchy
        treeRoot.getChildren().add(child_User);
        child_User.getChildren().add(subChild_Register);
        child_User.getChildren().add(subChild_Login);


        treeRoot.getChildren().add(child_Programmer);
        child_Programmer.getChildren().add(subChild_Navigation);
        child_Programmer.getChildren().add(subChild_changeTop);
        child_Programmer.getChildren().add(subChild_changeCenter);

    }
}
