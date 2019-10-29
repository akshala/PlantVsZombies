package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class LevelSelect {
    public void changeScene_levelScene(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent oldPlayer_parent = FXMLLoader.load(getClass().getResource("LevelScene.fxml"));
        Scene oldPlayer_scene = new Scene(oldPlayer_parent);
        Stage oldPlayer_stage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        oldPlayer_stage.setScene(oldPlayer_scene);
    }
}
