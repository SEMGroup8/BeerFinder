import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.stage.Stage;


/**
 * Created by Shiratori on 12/10/15.
 */
public class Main extends Application{


    public static void main(String[] args)
    {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/homeScreen.fxml"));
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("BeerFinder Alpha Test");
        stage.setScene(scene);
        stage.show();
    }
}
