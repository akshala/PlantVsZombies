package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class HelpController {

    @FXML
    private Button back;

    @FXML
    public void changeScene_mainMenu(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent new_parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene new_scene = new Scene(new_parent);
        Stage old_stage = (Stage) back.getScene().getWindow();
        old_stage.setScene(new_scene);
    }
}
