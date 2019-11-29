package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;



public class LevelSelect {

    @FXML
    private AnchorPane LevelSelectMain;

    @FXML
    public void changeScene_levelScene(javafx.event.ActionEvent actionEvent) throws IOException {
        String Level = ((Node)actionEvent.getSource()).getId();
        FXMLLoader LevelSceneLoader= new FXMLLoader(getClass().getResource("LevelScene.fxml"));
        AnchorPane LSParent = LevelSceneLoader.load();
        LevelSceneController controller = LevelSceneLoader.getController();
        controller.setSceneNumber(Level);
        Scene LScene = new Scene(LSParent);
        Stage oldPlayer_stage = (Stage) LevelSelectMain.getScene().getWindow();
        oldPlayer_stage.setScene(LScene);
    }
}
