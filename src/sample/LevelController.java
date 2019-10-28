package sample;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;

import java.util.Collections;

public class LevelController {


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
}
