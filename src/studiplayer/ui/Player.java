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

    public static final String DEFAULT_PLAYLIST = "playlists/DefaultPlayList.m3u";
    private static final String INITIAL_PLAYTIME = "00:00";
    private static final String PREFIX_FOR_CURRENT_SONG = "Current song: ";
    private static final String NO_CURRENT_SONG = "no current song";
    private static final String NO_PLAYTIME = "--:--";
    private Button playButton;
    private Button pauseButton;
    private Button stopButton;
    private Button nextButton;
    private Button editorButton;
    private PlayList playList;
    private String playListPathname; //Name was given (playListPathname?)
    private Label songDescription;
    private final Label playTime = new Label(INITIAL_PLAYTIME);
    private Label songlabel;
    private Stage primaryStage;
    private PlayListEditor playListEditor;
    private volatile boolean stopped;
    private boolean editorVisible;

    public Player() {


    }

    public static void main(String[] args) {
        launch(args);
    }

    //@Override
    public void start(Stage primaryStage) {

        playButton = createButton("play.png");
        pauseButton = createButton("pause.png");
        stopButton = createButton("stop.png");
        nextButton = createButton("next.png");
        editorButton = createButton("pl_editor.png");

        pauseButton.setDisable(true);
        stopButton.setDisable(true);

        playList = new PlayList();

        playListEditor = new PlayListEditor(this, this.playList);
        editorVisible = false;
        songDescription = new Label();
        songDescription.setText(NO_CURRENT_SONG);


        List<String> parameters = getParameters().getRaw();
        songlabel = new Label(PREFIX_FOR_CURRENT_SONG + songDescription.getText());

        if (parameters.size() != 0) {

            String sparameters;
            sparameters = parameters.toString();

            songDescription.setText(parameters.toString());

            playListPathname = sparameters;
            playList.loadFromM3U(sparameters);
        }
        if (parameters == null) {
            playList.loadFromM3U(DEFAULT_PLAYLIST);
            playTime.setText(NO_PLAYTIME);
            primaryStage.setTitle(NO_CURRENT_SONG);
        } else {
            playList.loadFromM3U(DEFAULT_PLAYLIST);
            playTime.setText(NO_PLAYTIME);
            primaryStage.setTitle(NO_CURRENT_SONG);
        }

        BorderPane mainPane = new BorderPane();
        HBox ButtonBox = new HBox();

        Scene scene = new Scene(mainPane, 700, 90);
        this.primaryStage = primaryStage;
        primaryStage.setScene(scene);

        this.primaryStage.setTitle(songDescription.getText());

        mainPane.setTop(songlabel);
        ButtonBox.getChildren().addAll(playTime, playButton, pauseButton, stopButton, nextButton, editorButton);
        mainPane.setCenter(ButtonBox);
        primaryStage.show();

        playButton.setOnAction(e -> playCurrentSong());
        pauseButton.setOnAction(e -> pauseCurrentSong());
        stopButton.setOnAction(e -> stopCurrentSong());
        nextButton.setOnAction(e -> nextSong());
        editorButton.setOnAction(e -> {
            if (editorVisible) {
                editorVisible = false;
                playListEditor.hide();
            } else {
                editorVisible = true;
                playListEditor.show();
            }
        });


    }

    void playCurrentSong() {
        playButton.setDisable(true);
        setButtonStates(true, false, false, false, false);
        stopped = false;
        updateSongInfo(playList.getCurrentAudioFile());


        if (playList.getCurrentAudioFile() != null) {
            // Start threads
            (new TimerThread()).start();
            (new PlayerThread()).start();
        }
    }

    private void pauseCurrentSong() {
        studiplayer.basic.BasicPlayer.togglePause();
        stopped = !stopped;

        /*if (playList.getCurrentAudioFile() != null) {
            // Start threads
            (new TimerThread()).start();
            (new PlayerThread()).start();
        }*/
        setButtonStates(true, false, false, false, false);
    }

    private void stopCurrentSong() {

        /*if (playList.getCurrentAudioFile() != null) {
            // Start threads
            (new TimerThread()).start();
            (new PlayerThread()).start();
        }*/
        stopped = true;

        studiplayer.basic.BasicPlayer.stop();
        setButtonStates(false, true, false, true, false);
        updateSongInfo(playList.getCurrentAudioFile());
        this.playTime.setText(INITIAL_PLAYTIME);
    }

    private void nextSong() {

        if (!stopped) {
            studiplayer.basic.BasicPlayer.stop();
            stopped = !stopped;
        }
        playList.changeCurrent();
        playCurrentSong();
        updateSongInfo(playList.getCurrentAudioFile());
        setButtonStates(true, false, false, false, false);
    }

    public void updateSongInfo(AudioFile af) {
        if (af == null) {
            songDescription.setText(NO_CURRENT_SONG);
            songlabel.setText(PREFIX_FOR_CURRENT_SONG + songDescription.getText());
            playTime.setText(NO_PLAYTIME);
        } else {
            this.songDescription.setText(af.toString());
            this.songlabel.setText(PREFIX_FOR_CURRENT_SONG + songDescription.getText());
            this.primaryStage.setTitle(songDescription.getText());
            this.playTime.setText(playList.getCurrentAudioFile().getFormattedPosition());
        }
    }

    private void refreshUI() {
        Platform.runLater(() -> {
            if (playList != null && playList.size() > 0) {
                updateSongInfo(playList.getCurrentAudioFile());
                setButtonStates(false, true, false, true, false);
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

    public void setEditorVisible(boolean editorVisible) {
        this.editorVisible = editorVisible;
    }

    public String getPlayListPathname() {
        return playListPathname;
    }

    public void setPlayList(String s) {
        this.playList = new PlayList(s);
        refreshUI();
    }

    private class TimerThread extends Thread {
        @Override
        public void run() {
            while (stopped && !playList.isEmpty()) {
                String stest = playList.getCurrentAudioFile().getFormattedPosition(); //for debugging
                playTime.setText(stest); //for debugging
                try {
                    sleep(100);
                } catch (Exception e) {
                    throw new RuntimeException("RuntimeException in TimerThread!");
                }
            }
        }

    }

    private class PlayerThread extends Thread {
        @Override
        public void run() {
            while (stopped == false && !playList.isEmpty()) {
                try {
                    studiplayer.basic.BasicPlayer.play(playList.getCurrentAudioFile().getPathname());
                    //playList.changeCurrent();//right spot? TODO: really //?
                    updateSongInfo(playList.getCurrentAudioFile()); //right spot?
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }

    }

}
