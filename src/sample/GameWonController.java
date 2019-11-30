package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;


public class GameWonController {

    @FXML
    private AnchorPane GameWinScreen;

    String curr_level;

    public void changeScene_mainMenu(ActionEvent event) throws IOException {
        Parent new_parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene new_scene = new Scene(new_parent);
        Stage old_stage = (Stage) GameWinScreen.getScene().getWindow();
        old_stage.setScene(new_scene);
    }

    @FXML
    public void changeScene_levelScene(javafx.event.ActionEvent actionEvent) throws IOException {
        String Level = curr_level;
//        Level = Level.substring(0, 4) + "";
        FXMLLoader LevelSceneLoader= new FXMLLoader(getClass().getResource("LevelScene.fxml"));
        AnchorPane GameWinScreen = LevelSceneLoader.load();
        LevelSceneController controller = LevelSceneLoader.getController();
        controller.setSceneNumber(Level);
        Scene LScene = new Scene(GameWinScreen);
        Stage oldPlayer_stage = (Stage) GameWinScreen.getScene().getWindow();
        oldPlayer_stage.setScene(LScene);
    }

    void setCurr_level(String level){
        curr_level = level;
    }

}
