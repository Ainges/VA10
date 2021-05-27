package studiplayer.ui;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import static javafx.application.Application.launch;

public class Player extends Application {

    public Player(){
    }
    //@Override
    public void start(Stage primaryStage) throws Exception{
        BorderPane mainPane = new BorderPane();

        Scene scene = new Scene(mainPane, 700, 90);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args){
        launch(args);
    }
}
