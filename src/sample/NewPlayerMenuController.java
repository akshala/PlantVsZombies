package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class NewPlayerMenuController {

    @FXML
    private TextField PlayerName;

    @FXML
    private Button start;

    @FXML
    private Button back_mainMenu;

    @FXML
    public void changeScene_mainMenu(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent new_parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene new_scene = new Scene(new_parent);
        Stage old_stage = (Stage) start.getScene().getWindow();
        old_stage.setScene(new_scene);
    }

    @FXML
    public void changeScene_levelSelect(javafx.event.ActionEvent actionEvent) throws IOException {
        Player player = new Player(PlayerName.getText());
//        Player player = new Player(PlayerName.getAccessibleText());
        FXMLLoader LevelSelectLoader= new FXMLLoader(getClass().getResource("LevelSelect.fxml"));
        AnchorPane LSParent = LevelSelectLoader.load();
        LevelSelect controller = LevelSelectLoader.getController();
        Scene new_scene = new Scene(LSParent);
        controller.setPlayer(player);
        Stage old_stage = (Stage) start.getScene().getWindow();
        old_stage.setScene(new_scene);

    }
}
