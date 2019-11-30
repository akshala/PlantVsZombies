package sample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

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
        System.out.println("Changed to scene new player");
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

    public void changeScene_help(javafx.event.ActionEvent actionEvent) throws IOException {
        Parent new_parent = FXMLLoader.load(getClass().getResource("help.fxml"));
        Scene new_scene = new Scene(new_parent);
        Stage old_stage = (Stage) exit.getScene().getWindow();
        old_stage.setScene(new_scene);
    }

    public void changeScene_SerializedlevelScene(javafx.event.ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        Game savedGame = deserialize();
        FXMLLoader LevelSceneLoader= new FXMLLoader(getClass().getResource("LevelScene.fxml"));
        AnchorPane LSParent = LevelSceneLoader.load();
        LevelSceneController controller = LevelSceneLoader.getController();
        controller.loadSavedGame(savedGame);
        Scene LScene = new Scene(LSParent);
        Stage oldPlayer_stage = (Stage) exit.getScene().getWindow();
        oldPlayer_stage.setScene(LScene);
    }

    private Game deserialize() throws IOException, ClassNotFoundException {
        Game savedGame = null;
        ObjectInputStream in = null;
        try{
            in = new ObjectInputStream(new FileInputStream("out.txt"));
            savedGame = (Game) in.readObject();
        }
        finally {
            in.close();
        }
        return savedGame;
    }
    public void exit(javafx.event.ActionEvent actionEvent) throws IOException{
        System.exit(1);
    }
}
