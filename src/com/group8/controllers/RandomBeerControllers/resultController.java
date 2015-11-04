package com.group8.controllers.RandomBeerControllers;

import com.group8.database.MysqlDriver;
import com.group8.database.tables.Beer;
import com.group8.database.tables.RandomBeerResult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class resultController {

    @FXML
    private Label testResult;

    @FXML
    private Button checkButton;

    @FXML
    private ImageView imageView;

    @FXML
    private Button repeatButton;

    @FXML
    void onRepeatClick(ActionEvent event) {

    }

    // Testing

     RandomBeerResult searchRandomBeerResult = new RandomBeerResult();


    @FXML
    void onCheckButtonClick(ActionEvent event) {

        String query = "SELECT * FROM beers WHERE originID = 'LT'";
        ArrayList<ArrayList<Object>> list1;

        RandomBeerResult randomBeerResult = new RandomBeerResult();

        list1 = MysqlDriver.selectMany(randomBeerResult.resultQuery());

        for (int i = 0; i < list1.size(); i++) {
            Beer beer = new Beer(list1.get(i));

            System.out.println(beer.getId() + "\n");

            // testResult.setText(beer.getId()+"\n");
        }

    }
}
