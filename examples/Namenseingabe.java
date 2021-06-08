import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;

public class Namenseingabe extends Application {

    public static void main(String[] args){
        launch();
    }

    @Override
    public void start(Stage window) throws Exception {
        window.setTitle("Namenseingabe");
        FlowPane flowPane = new FlowPane();

        TextField nameTextField = new TextField();
        Button okButton = new Button("ok");
        flowPane.getChildren().addAll(new Label("Name"),nameTextField, okButton);

        //Ereignisbehandlung: Handler-Methode (Lambda) beim Button registriert

        okButton.setOnAction(event -> {String name = nameTextField.getText();
        System.out.println(name);}
        );

        Scene scene = new Scene(flowPane);
        window.setScene(scene);
        window.show();
    }
}
