import com.group8.controllers.Navigation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.File;


/**
 * Created by Shiratori on 12/10/15.
 */
public class Main extends Application{

    public static String test;

    public static void main(String[] args)
    {
        test = "Bajs";

        for(int i = 0; i < test.length(); i++)
        {
            System.out.println(test.substring(i, i+1));
        }

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Navigation.primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/homeScreen.fxml"));
        Scene scene = new Scene(root, 800, 600);

        stage.setTitle("BeerFinder Alpha Test");
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:src/com/group8/resources/Images/Icon.png"));
        stage.show();
    }
}
