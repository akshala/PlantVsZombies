package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class NewPlayerMenuController {

    public void changeScene_mainMenu(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent oldPlayer_parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
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
}
