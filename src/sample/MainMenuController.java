package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class MainMenuController {

    @FXML
    private Button exit;


    @FXML
    public void changeScene_oldPlayer(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent new_parent = FXMLLoader.load(getClass().getResource("OldPlayerMenu.fxml"));
        Scene new_scene = new Scene(new_parent);
        Stage old_stage = (Stage) exit.getScene().getWindow();
        old_stage.setScene(new_scene);
    }

    public void changeScene_newPlayer(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent new_parent = FXMLLoader.load(getClass().getResource("NewPlayerMenu.fxml"));
        Scene new_scene = new Scene(new_parent);
        Stage old_stage = (Stage) exit.getScene().getWindow();
        old_stage.setScene(new_scene);
    }

    public void changeScene_levelSelect(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent new_parent = FXMLLoader.load(getClass().getResource("levelSelect.fxml"));
        Scene new_scene = new Scene(new_parent);
        Stage old_stage = (Stage) exit.getScene().getWindow();
        old_stage.setScene(new_scene);
    }

    public void changeScene_levelScene(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent new_parent = FXMLLoader.load(getClass().getResource("LevelScene.fxml"));
        Scene new_scene = new Scene(new_parent);
        Stage old_stage = (Stage) exit.getScene().getWindow();
        old_stage.setScene(new_scene);
    }
}
