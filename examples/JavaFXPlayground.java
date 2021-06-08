import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class JavaFXPlayground extends Application {

    private Button okButton;
    private TextField nameTextField;
    private Button SayHello;
    private ImageView Grievous;
    private ImageView Kenobi;


    @Override
    public void start(Stage stage1) throws Exception {
               stage1.setX(100);
               stage1.setY(100);
               stage1.setTitle("Hello das ist ein Fenstertitel");
               Grievous = new ImageView();
               String sGrievous ="Profile_-_General_Grievous.png";
               Image gimage = new Image(sGrievous, 50, 50, false, false);
               Grievous.setImage(gimage);

               Kenobi = new ImageView();
               String sKenobi = "iwqG_GfY_400x400.png";
               Image oimage = new Image(sKenobi, 50, 50 ,false, false);
               Pane pane = new FlowPane();

               pane.getChildren().addAll(new Label("General Kenobi!", Grievous));

               Label labelKenobi =  new Label("Hello there!", Kenobi);
               labelKenobi.setLayoutY(50);

               pane.getChildren().addAll(labelKenobi);



        Scene scene = new Scene(pane, 300, 300);

        stage1.setScene(scene);
        stage1.show();

        //Fenster 2

        /*
        Stage stage2 = new Stage();
        stage2.initOwner(stage1);
        stage2.initModality(Modality.APPLICATION_MODAL);
        stage2.setX(150);//Was machen die beiden Einstellungen?
        stage2.setY(170);//
        stage2.setTitle("Hallo, hier ist Marcell D'Avis. Das ist Fenster Nummer 2"); // Titel von Fenster 2

        Pane pane2 = new FlowPane();
        Label label = new Label("Hallo das ist das zweite komische Label"); //Das steht in Fenster 2

        okButton = new Button("ok");
        nameTextField = new TextField();
        SayHello = new Button("Hello Button");


        pane2.getChildren().addAll(label, okButton, nameTextField, SayHello); //Ohne dem wäre label, okButton und TextField nicht sichtbar

        pane2.setPadding(new Insets(20,20,20,20));

        //<lambda>
        okButton.setOnAction(e -> {
            System.out.println("Button geklickt, Name = " + nameTextField.getText());
        });


        SayHello.setOnAction(e -> {
            System.out.println("HELLO !");
        });
        //</lambda>


        Scene scene2 = new Scene(pane2, 600, 100); //Höhe und Breite des Fensters  2
        pane2.setStyle("-fx-background-color: lightgrey;"); // Hintergrund von Fenster 2 ist grau
        label.setTextFill(Paint.valueOf("red")); // Die Schrift in Fenster 2 ist rot
        stage2.setScene(scene2);
        stage2.show();

        */


    }
    public static void main (String[] args){launch();}

}
