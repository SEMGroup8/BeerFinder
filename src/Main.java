import java.util.ArrayList;
import java.util.Scanner;
import com.group8.database.*;

import java.sql.*;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;


/**
 * Created by Shiratori on 12/10/15.
 */
public class Main extends Application{

	String searchString;
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);

        ArrayList<ArrayList> sqlData = new ArrayList<>();

        sqlData = MysqlDriver.selectMany("Select * from beers");

       for(int i = 0; i < sqlData.size(); i++)
        {
            ArrayList<Object> row = sqlData.get(i);

            for(int j = 0; j<row.size(); j++)
            {
                System.out.println(row.get(j).toString());
            }
        }

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/homescreen.fxml"));

        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("FXML Welcome");
        stage.setScene(scene);
        stage.show();
    }
}
