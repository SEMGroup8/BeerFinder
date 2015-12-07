import com.group8.controllers.Navigation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;


/**
 * Created by Shiratori on 12/10/15.
 */
public class Main extends Application {

    public static String test;
    File file = new File("src/8bit projekt.wav");

    public static void main(String[] args) {
        test = "Bajs";

        for (int i = 0; i < test.length(); i++) {
            System.out.println(test.substring(i, i + 1));
        }

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {

        Navigation.primaryStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/mainScene.fxml"));
        Scene scene = new Scene(root, 800, 600);
        // scene.getStylesheets().add("com/group8/resources/css/resultTableCss.css");
        stage.setTitle("BeerFinder Beta Test");
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:src/com/group8/resources/Images/Icon.png"));
        play();
        System.out.println("running --> start");
        stage.show();

    }

    /**
     * Starts playing 8bit background music when the program launches.
     * --> will start before the FX thread is initialized.
     * -->
     */
    public void play() {
        Thread irritateingBackgorundMusic = new Thread() {
            @Override
            public void run() {

                // test
                System.out.println("running --> irritateing backgorund music");

                try {
                    Clip clip = AudioSystem.getClip();
                    // Set the audiostream to selected file --> row 23
                    clip.open(AudioSystem.getAudioInputStream(file));
                    // set to low background volume
                    FloatControl volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
                    volume.setValue(-12.0f);
                    // Loop the clip
                    clip.loop(Clip.LOOP_CONTINUOUSLY);

                        Thread.sleep(clip.getMicrosecondLength());

                } catch (LineUnavailableException e) {
                    e.printStackTrace();
                } catch (UnsupportedAudioFileException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        };
        // Set thread to daemon, makes it terminate when the program does.
        irritateingBackgorundMusic.setDaemon(true);
        // Start the thread
        irritateingBackgorundMusic.start();

    }
}

