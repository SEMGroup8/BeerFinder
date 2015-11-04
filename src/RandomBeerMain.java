import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class RandomBeerMain extends Application {


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{

        Parent root = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/RandomBeerScenes/scene1.fxml"));
        Scene scene1 = new Scene(root);
        primaryStage.setScene(scene1);
        primaryStage.show();

    }
}


