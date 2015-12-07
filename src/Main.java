import com.group8.controllers.Navigation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;


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
        Parent root = FXMLLoader.load(getClass().getResource("/com/group8/resources/views/mainScene.fxml"));
        Scene scene = new Scene(root, 800, 600);
       // scene.getStylesheets().add("com/group8/resources/css/resultTableCss.css");
        stage.setTitle("BeerFinder Beta Test");
        stage.setScene(scene);
        stage.getIcons().add(new Image("file:src/com/group8/resources/Images/Icon.png"));
        //play();
        stage.show();

    }

    // DERP
    public void play() {

            new Thread() {

                @Override
                public void run() {
                    try {
                        File file = new File("src/11k16bitpcm.wav");
                        Clip clip = AudioSystem.getClip();
                        clip.open(AudioSystem.getAudioInputStream(file));
                        clip.start();
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
            }
                    .start();
        }

}
