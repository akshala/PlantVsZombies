package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class LevelSceneController {

    @FXML
    private MenuButton menu;

    @FXML
    private ProgressBar timer;

    @FXML
    void plantDragDetected(MouseEvent event) {
        ImageView PlantCard = (ImageView) event.getSource();
        Dragboard db = PlantCard.startDragAndDrop(TransferMode.ANY);
        ClipboardContent cb = new ClipboardContent();
        switch (PlantCard.getId()){
            case "PlantCard1" : cb.putString("/Images/Pea shooter.gif");break;
            case "PlantCard2" : cb.putString("/Images/Sunflower.png");break;
            case "PlantCard3" : cb.putString("/Images/Walnut.png");break;
            case "PlantCard4" : cb.putString("/Images/CherryBomb.png");break;
        }
        db.setContent(cb);
        event.consume();
    }
    @FXML
    void plantDraggingOver(DragEvent event) {
        if(event.getDragboard().hasString()){
            event.acceptTransferModes(TransferMode.ANY);
        }
    }
    @FXML
    void plantDropped(DragEvent event) {
        ImageView cell = (ImageView) event.getSource();
        if(cell.getImage() != null){
            System.out.println("Plant already present!");
        }
        else {
            cell.setImage(new Image(getClass().getResourceAsStream(event.getDragboard().getString())));
        }
    }

    public void changeScene_mainMenu(ActionEvent event) throws IOException {
        Parent oldPlayer_parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene oldPlayer_scene = new Scene(oldPlayer_parent);
        Stage oldPlayer_stage = (Stage) menu.getScene().getWindow();
        oldPlayer_stage.setScene(oldPlayer_scene);
    }

    public void progressBarShift(ActionEvent event) throws InterruptedException {
        for(int i=0; i<1000; i++){
            timer.setProgress(i/1000);
            TimeUnit.SECONDS.sleep(10);
        }
    }
}
