package studiplayer.ui;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import studiplayer.audio.AudioFile;
import studiplayer.audio.PlayList;

import java.net.URL;
import java.util.List;

public class Player extends Application {

    private Button playButton;
    private Button pauseButton;
    private Button stopButton;
    private Button nextButton;
    private Button editorButton;
    private PlayList playlist;
    private String PlayListPathname; //Name was given (playListPathname?)
    private String SongDescription = NO_CURRENT_SONG;
    private Label playTime = new Label(INITIAL_PLAYTIME);
    private Label songlabel;
    private volatile Boolean stopped;

    public static final String DEFAULT_PLAYLIST = "playlists/DefaultPlayList.m3u";
    private static final String INITIAL_PLAYTIME = "00:00";
    private static final String PREFIX_FOR_CURRENT_SONG = "Current song: ";
    private static final String NO_CURRENT_SONG = "no current song";
    private static final String NO_PLAYTIME = "--:--";

    public Player() {
    }

    //@Override
    public void start(Stage primaryStage) throws Exception {

        playButton = createButton("play.png");
        pauseButton = createButton("pause.png");
        stopButton = createButton("stop.png");
        nextButton = createButton("next.png");
        editorButton = createButton("pl_editor.png");

        playlist = new PlayList();


        List<String> parameters = getParameters().getRaw();
        songlabel = new Label(PREFIX_FOR_CURRENT_SONG + SongDescription);

        if (parameters.size() != 0) {

            String sparameters;
            sparameters = parameters.toString();

            SongDescription = parameters.toString();

            PlayListPathname = sparameters;
            playlist.loadFromM3U(sparameters);
        } else {
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
        ButtonBox.getChildren().addAll(playTime, playButton, pauseButton, stopButton, nextButton, editorButton);
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

        pauseButton.setDisable(true);
        stopButton.setDisable(true);
    }

    private void playCurrentSong() {
        //System.out.println("Playing " + playlist.getCurrentAudioFile().toString());
        //System.out.println("Filename is " + playlist.getCurrentAudioFile().getFilename());
        //System.out.println("Index is " + playlist.getCurrent());
        stopped = false;
        setButtonStates(true,false,false,false,false);
        updateSongInfo(playlist.getCurrentAudioFile());

        if (playlist.getCurrentAudioFile() != null) {
            // Start threads
            (new TimerThread()).start();
            (new PlayerThread()).start();
        }
           }

    private void pauseCurrentSong() {
        System.out.println("Toggle 'pause'" + playlist.getCurrentAudioFile().toString());
        switch (stopped+""){
            case "false": System.out.println("---PAUSING---"); ; break;
            case "true": ;System.out.println("---PLAYING---"); ;break;
        }
        System.out.println("Filename is " + playlist.getCurrentAudioFile().getFilename());
        System.out.println("Index is " + playlist.getCurrent());

        studiplayer.basic.BasicPlayer.togglePause();
        stopped = !stopped;
        setButtonStates(true, false,false,false,false);
        if (playlist.getCurrentAudioFile() != null) {
            // Start threads
            (new TimerThread()).start();
            (new PlayerThread()).start();
        }
    }

    private void stopCurrentSong() {
        System.out.println("Stoping " + playlist.getCurrentAudioFile().toString());
        System.out.println("Filename is " + playlist.getCurrentAudioFile().getFilename());
        System.out.println("Index is " + playlist.getCurrent());

        studiplayer.basic.BasicPlayer.stop();

        updateSongInfo(playlist.getCurrentAudioFile());

        stopped = true;
        //playTime.setText(INITIAL_PLAYTIME); //outdated
        stopButton.setDisable(true);
        pauseButton.setDisable(true);
        playButton.setDisable(false);


    }

    private void nextSong() {

        if (!stopped) {
            studiplayer.basic.BasicPlayer.stop();
            stopped = !stopped;
        }
        System.out.println("Switching to next Audiofile...");
        System.out.println("Stopping " + playlist.getCurrentAudioFile().toString());
        System.out.println("Filename is " + playlist.getCurrentAudioFile().getFilename());
        System.out.println("Current index is " + playlist.getCurrent());

        playlist.changeCurrent();

        playCurrentSong();



        pauseButton.setDisable(false);
        stopButton.setDisable(false);

        //SongDescription = playlist.getCurrentAudioFile().toString(); //outdated
        //playTime.setText(INITIAL_PLAYTIME); //outdated
        updateSongInfo(playlist.getCurrentAudioFile());


    }

    public void updateSongInfo(AudioFile af) { //TODO: Test ob auch Fenstertitel geändert wird.
        if (af == null) {
            SongDescription = NO_CURRENT_SONG;
            songlabel.setText(PREFIX_FOR_CURRENT_SONG + SongDescription);
            playTime.setText(NO_PLAYTIME);
        }
        SongDescription = af.toString();

        songlabel.setText(PREFIX_FOR_CURRENT_SONG + SongDescription);
        playTime.setText(INITIAL_PLAYTIME);
    }
    private void refreshUI(){
        Platform.runLater(()-> {
            if (playlist != null && playlist.size()>0){
                updateSongInfo(playlist.getCurrentAudioFile());
                setButtonStates(false, true, false ,true, false);
            } else {
                updateSongInfo(null);
                setButtonStates(true, true, true, true, false);
            }
        });
    }

    private void setButtonStates(boolean playButtonStat,
                                 boolean stopButtonState, boolean nextButtonState,
                                 boolean pauseButtonState, boolean editorButtonState) {
        playButton.setDisable(playButtonStat);
        stopButton.setDisable(stopButtonState);
        nextButton.setDisable(nextButtonState);
        pauseButton.setDisable(pauseButtonState);
        editorButton.setDisable(editorButtonState);


    }


    private Button createButton(String iconfile) {
        Button button = null;
        try {
            URL url = getClass().getResource("/icons/" + iconfile);
            Image icon = new Image(url.toString());
            ImageView imageView = new ImageView(icon);
            imageView.setFitHeight(48);
            imageView.setFitWidth(48);
            button = new Button("", imageView);
            button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        } catch (Exception e) {
            System.out.println("Image " + "icons/" + iconfile + " not found!");
            System.exit(-1);
        }
        return button;
    }

    public static void main(String[] args) {
        launch(args);
    }

    private class TimerThread extends Thread{

        public void run(){
            while (stopped == false && !playlist.isEmpty()){

                String stest = playlist.getCurrentAudioFile().getFormattedPosition();

                playTime.setText(stest);

                try {
                    sleep(100);
                }
                catch(Exception e ) {
                    throw new RuntimeException("RuntimeException in TimerThread!");
                }
            }
        }

    }
    private class PlayerThread extends Thread{
        public void run(){
            while (stopped == false && !playlist.isEmpty()){
                try {
                    studiplayer.basic.BasicPlayer.play(playlist.getCurrentAudioFile().getPathname());
                    playlist.changeCurrent();//right spot?
                    updateSongInfo(playlist.getCurrentAudioFile()); //right spot?
                }catch (Exception e){
                e.printStackTrace();
                }

            }
        }

    }
}
