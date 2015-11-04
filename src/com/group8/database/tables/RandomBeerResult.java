package com.group8.database.tables;

import com.group8.controllers.RandomBeerControllers.Scene1Controller;
import com.group8.controllers.RandomBeerControllers.Scene2Controller;
import com.group8.controllers.RandomBeerControllers.Scene3Controller;
import com.group8.database.MysqlDriver;

import java.util.ArrayList;

/**
 * Created by Mantas on 2015.11.04.
 */
public class RandomBeerResult extends MysqlDriver {

    String query;
    ArrayList<ArrayList<Object>> searchResult;

    Scene1Controller scene1 = new Scene1Controller();
    Scene2Controller scene2 = new Scene2Controller();
    Scene3Controller scene3 = new Scene3Controller();



// Search string
    public String resultQuery(){

        query = "SELECT * FROM beers WHERE (price < " + scene1.getPricePicked() + " AND percentage < "
                + scene2.getPercentagePicked() + " AND beerTypeID IN " + scene3.getTypesPicked() + ")";

        //Test print
        System.out.println(query);

       return query;
    }
}


    //    SELECT beerID FROM beers WHERE (price < 20 AND percentage < 8.0 AND beerTypeID IN (7,9))