import com.group8.singletons.Navigation;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 * Created by Linus Eiderström Swahn.
 *
 * Main class of the application.
 */
public class Main extends Application {

    public static void main(String[] args) {

        launch(args);
    }

    /**
     * Created by Linus Eiderström Swahn.
     *
     * Start of the javafx application.
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {

        Navigation.primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/mainScene.fxml"));
        Scene scene = new Scene(root, 800, 600);
        stage.setTitle("BeerFinder");
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:src/com/group8/resources/Images/Icon.png"));
        stage.show();

        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                System.exit(0);
            }
        });
    }
}

