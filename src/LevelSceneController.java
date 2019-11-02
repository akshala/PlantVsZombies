package sample;

import javafx.animation.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.concurrent.TimeUnit;

public class LevelSceneController implements Initializable {


    @FXML
    private AnchorPane MainPain;

    @FXML
    private MenuButton menu;

    @FXML
    private ProgressBar timer;

    @FXML
    private ImageView sun;

    @FXML
    private ImageView Zombie1;
    private ImageView[] ZV = new ImageView[5];

    @FXML
    private Image[] ZI = new Image[5];


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.minutes(0), new KeyValue(timer.progressProperty(), 0)),
                new KeyFrame(Duration.minutes(0.5), new KeyValue(timer.progressProperty(), 1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        TranslateTransition ZombTrans = new TranslateTransition();
        ZombTrans.setNode(Zombie1);
        ZombTrans.setToX(-500);
        ZombTrans.setDuration(Duration.seconds(10));
        ZombTrans.setCycleCount(500);
        ZombTrans.play();

        for(int i = 0; i < 5; i ++){
            ZI[i] = new Image(getClass().getResource("/Images/FlagZombie.gif").toExternalForm());
            ZV[i] = new ImageView(ZI[i]);
//            this.MainPain.getChildren().add(ZV[i]);
        }
    }

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
}
