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
import studiplayer.audio.*;


import java.net.URL;
import java.util.List;

import static javafx.application.Application.launch;

public class Player extends Application {

    private Button playButton;
    private Button pauseButton;
    private Button stopButton;
    private Button nextButton;
    private Button editorButton;
    private PlayList playlist;
    private String PlayListPathname; //Name was given (playListPathname?)

    public static final String DEFAULT_PLAYLIST = "playlists/DefaultPlayList.m3u";
    private static final String INITIAL_PLAYTIME = "00:00";
    private static final String PREFIX_FOR_CURRENT_SONG = "Current song: ";
    private static final String NO_CURRENT_SONG = "no current song";
    private static final String NO_PLAYTIME = "--:--";

    public Player(){
    }
    //@Override
    public void start(Stage primaryStage) throws Exception{

        playButton = createButton("play.png");
        pauseButton = createButton("pause.png");
        stopButton = createButton("stop.png");
        nextButton = createButton("next.png");
        editorButton = createButton("pl_editor.png");
        Label playTime = new Label(INITIAL_PLAYTIME); //TODO: Change to actual String
        playlist = new PlayList();


        List<String> parameters = getParameters().getRaw();
        String SongDescription = "no current song"; //TODO: change to current Title of the song
        Label songlabel = new Label(PREFIX_FOR_CURRENT_SONG + SongDescription);

        if (parameters.size() != 0){

            String sparameters;
            sparameters = parameters.toString();

            SongDescription = parameters.toString();

            PlayListPathname = sparameters;
            playlist.loadFromM3U(sparameters);
        }

        else {
            playlist.loadFromM3U(DEFAULT_PLAYLIST);
            playTime.setText(NO_PLAYTIME);
            primaryStage.setTitle(NO_CURRENT_SONG);

        }

        BorderPane mainPane = new BorderPane();
        HBox ButtonBox = new HBox();

        Scene scene = new Scene(mainPane, 700, 90);
        primaryStage.setScene(scene);

        primaryStage.setTitle(SongDescription);

        mainPane.setTop(songlabel);
        ButtonBox.getChildren().addAll(playTime,playButton, pauseButton, stopButton, nextButton, editorButton);
        mainPane.setCenter(ButtonBox);
        primaryStage.show();

        playButton.setOnAction(e -> {
            playCurrentSong();
        });
        pauseButton.setOnAction(e -> {
            pauseCurrentSong();
        });
        stopButton.setOnAction(e -> {
            stopCurrentSong();
        });
        nextButton.setOnAction(e -> {
            nextSong();
        });
        editorButton.setOnAction(e -> {
           // editCurrentPlaylist(); // Wird in der letzten Teilaufgabe implementiert
        });
    }

    private void playCurrentSong() {
       System.out.println( "Playing " + playlist.getCurrentAudioFile().toString());
       System.out.println("Filename is " +playlist.getCurrentAudioFile().getFilename());
       System.out.println("Index is " + playlist.getCurrent());
    }

    private void pauseCurrentSong(){
        System.out.println("Pausing" + playlist.getCurrentAudioFile().toString());
        System.out.println("Filename is " +playlist.getCurrentAudioFile().getFilename());
        System.out.println("Index is " + playlist.getCurrent());
    }

    private void stopCurrentSong(){
        System.out.println("Stoping " + playlist.getCurrentAudioFile().toString());
        System.out.println("Filename is " +playlist.getCurrentAudioFile().getFilename());
        System.out.println("Index is " + playlist.getCurrent());
    }

    private void nextSong(){
        playlist.changeCurrent();
        System.out.println("Switching to next Audiofile " + playlist.getCurrentAudioFile().toString());
        System.out.println("Filename is " +playlist.getCurrentAudioFile().getFilename());
        System.out.println("Index is " + playlist.getCurrent());
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
