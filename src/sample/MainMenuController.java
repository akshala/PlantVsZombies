package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;

public class MainMenuController {
    @FXML
    public void changeScene_oldPlayer(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent oldPlayer_parent = FXMLLoader.load(getClass().getResource("OldPlayerMenu.fxml"));
        Scene oldPlayer_scene = new Scene(oldPlayer_parent);
        Stage oldPlayer_stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        oldPlayer_stage.setScene(oldPlayer_scene);
    }

    public void changeScene_newPlayer(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent oldPlayer_parent = FXMLLoader.load(getClass().getResource("NewPlayerMenu.fxml"));
        Scene oldPlayer_scene = new Scene(oldPlayer_parent);
        Stage oldPlayer_stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        oldPlayer_stage.setScene(oldPlayer_scene);
    }

    public void changeScene_levelSelect(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent oldPlayer_parent = FXMLLoader.load(getClass().getResource("levelSelect.fxml"));
        Scene oldPlayer_scene = new Scene(oldPlayer_parent);
        Stage oldPlayer_stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        oldPlayer_stage.setScene(oldPlayer_scene);
    }

    public void changeScene_levelScene(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent oldPlayer_parent = FXMLLoader.load(getClass().getResource("LevelScene.fxml"));
        Scene oldPlayer_scene = new Scene(oldPlayer_parent);
        Stage oldPlayer_stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        oldPlayer_stage.setScene(oldPlayer_scene);
    }
}
