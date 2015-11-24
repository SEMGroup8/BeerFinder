package com.group8.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Shiratori on 24/11/15.
 */


public class MainScene implements Initializable {
    @FXML
    private Pane center, top;
    @FXML
    private Node root;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        try {
            center.getChildren().clear();
            top.getChildren().clear();

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/group8/resources/views/home_center.fxml"));

            center.getChildren().add(loader.load());

            HomeCenter centerController = (HomeCenter)loader.getController();

            centerController.init(this);

            loader = new FXMLLoader(getClass().getResource("/com/group8/resources/views/home_top.fxml"));
            top.getChildren().add(loader.load());

            HomeTop topController = (HomeTop)loader.getController();

            topController.init(this);

            System.out.println("test");
        } catch (IOException e) {

            System.out.println("test2");
            e.printStackTrace();
        }
    }

    public void changeCenter(String url) throws IOException
    {
        center.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));

        center.getChildren().add(loader.load());

        BaseController topController = (BaseController)loader.getController();

        topController.init(this);
    }

    public void changeTop(String url) throws IOException
    {
        top.getChildren().clear();

        FXMLLoader loader = new FXMLLoader(getClass().getResource(url));

        top.getChildren().add(loader.load());

        BaseController topController = (BaseController)loader.getController();

        topController.init(this);
    }
}
