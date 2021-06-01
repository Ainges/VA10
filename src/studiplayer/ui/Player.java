package studiplayer.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;

import static javafx.application.Application.launch;

public class Player extends Application {

    private Button playButton;
    private Button pauseButton;
    private Button stopButton;
    private Button nextButton;
    private Button editorButton;

    public Player(){
    }
    //@Override
    public void start(Stage primaryStage) throws Exception{

        playButton = createButton("play.png");
        pauseButton = createButton("pause.png");
        stopButton = createButton("stop.png");
        nextButton = createButton("next.png");
        editorButton = createButton("pl_editor.png");
        Label playTime = new Label("--.--"); //TODO: Change to actual String

        BorderPane mainPane = new BorderPane();
        HBox ButtonBox = new HBox();

        Scene scene = new Scene(mainPane, 700, 90);
        primaryStage.setScene(scene);

        String SongDescription = "no current song"; //TODO: change to current Title of the song
        primaryStage.setTitle(SongDescription);

        Label songlabel = new Label(SongDescription);
        mainPane.setTop(songlabel);
        ButtonBox.getChildren().addAll(playTime,playButton, pauseButton, stopButton, nextButton, editorButton);
        mainPane.setCenter(ButtonBox);
        primaryStage.show();
    }

    private Button createButton(String iconfile){
        Button button = null;
        try{
            URL url = getClass().getResource("/icons/" + iconfile);
            Image icon = new Image(url.toString());
            ImageView imageView = new ImageView(icon);
            imageView.setFitHeight(48);
            imageView.setFitWidth(48);
            button = new Button("", imageView);
            button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        }catch (Exception e){
            System.out.println("Image "+"icons/" + iconfile + " not found!");
            System.exit(-1);
        }
        return button;
    }

    public static void main(String[] args){
        launch(args);
    }
}
