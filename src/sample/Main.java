package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.Serializable;
import java.util.ArrayList;


class App implements Serializable{
    ArrayList<Player> Players = new ArrayList<Player>();
}

public class Main extends Application {

    @FXML
    AnchorPane LevelSceneMainPane;

    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("PlantVsZombies");
        Parent root = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        System.out.println(root);
        primaryStage.setScene((new Scene(root, 650, 400)));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
