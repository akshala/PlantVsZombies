package sample;

import javafx.animation.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import javafx.scene.control.TextField;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;
import java.util.concurrent.TimeUnit;




public class LevelSceneController implements Initializable {

    static int sunToken = 0;
    private String LevelNo;
    ArrayList<Zombie> Zombies;
    ArrayList<Sun> Suns;
    ArrayList<Plant> Plants;
    ArrayList<LawnMower> LawnMowers;
    Random rand;
    String plant_category;
    ArrayList<PlantCard> PlantCards;

    @FXML
    private Pane PeaMainPane;

    @FXML
    private AnchorPane LevelSceneMainPane;

    @FXML
    private GridPane Row1;

    @FXML
    private GridPane Row2;

    @FXML
    private GridPane Row3;

    @FXML
    private GridPane Row4;

    @FXML
    private GridPane Row5;

    @FXML
    private MenuButton menu;

    @FXML
    private ProgressBar timer;

    @FXML
    private TextField sunPoints ;

    @FXML
    private ImageView LawnMower1;

    @FXML
    private ImageView LawnMower2;

    @FXML
    private ImageView LawnMower3;

    @FXML
    private ImageView LawnMower4;

    @FXML
    private ImageView LawnMower5;

    @FXML
    private ImageView PlantCard1;

    @FXML
    private ImageView PlantCard2;

    @FXML
    private ImageView PlantCard3;

    @FXML
    private ImageView PlantCard4;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.minutes(0), new KeyValue(timer.progressProperty(), 0)),
                new KeyFrame(Duration.minutes(0.5), new KeyValue(timer.progressProperty(), 1))
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        rand = new Random();
    }


    @FXML
    void plantDragDetected(MouseEvent event) {
        ImageView PlantCard = (ImageView) event.getSource();
        Dragboard db = PlantCard.startDragAndDrop(TransferMode.ANY);
        ClipboardContent cb = new ClipboardContent();
        switch (PlantCard.getId()){
            case "PlantCard1" : cb.putString("/Images/Pea shooter.gif"); plant_category = "Pea_shooter";break;
            case "PlantCard2" : cb.putString("/Images/Sunflower.png"); plant_category = "sunflower"; break;
            case "PlantCard3" : cb.putString("/Images/Walnut.png");plant_category = "walnut"; break;
            case "PlantCard4" : cb.putString("/Images/CherryBomb.png");plant_category = "cherryBomb"; break;
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
            Plant plant;
            switch(plant_category){
                case "Pea_shooter" : plant = new PeaShooter(cell); break;
                case "sunflower" : plant = new Sunflower(cell); break;
                case "walnut" : plant = new WalnutBomb(cell); break;
                case "cherryBomb" : plant = new CherryBomb(cell); break;
                default:
                    throw new IllegalStateException("Unexpected value: " + plant_category);
            }
            Plants.add(plant);
            if(event.getDragboard().getString().equals("/Images/Pea shooter.gif")) {
                Image peaImage = new Image((getClass().getResourceAsStream("/Images/pea.png")));

                ImageView pea = new ImageView(peaImage);
                pea.setFitHeight(10);
                pea.setFitWidth(10);
                TranslateTransition T = new TranslateTransition();
                T.setNode(pea);
                T.setToX(500);

                T.setDuration(Duration.seconds(5));
                T.setCycleCount(500);
                T.play();
                Pane p = new Pane(pea);
                p.setLayoutX(cell.getLocalToSceneTransform().getTx() + 20);
                p.setLayoutY(cell.getLocalToSceneTransform().getTy() + 20);
                PeaMainPane.getChildren().add(p);
                for(Zombie zombie: Zombies){
                    Timeline t = new Timeline( new KeyFrame( Duration.seconds(0.5),(e) -> {
                        collision_with_pea(pea, zombie);
                    }));
                    t.setCycleCount(Animation.INDEFINITE);
                    t.play();
                }
            }
        }
    }

    public void changeScene_mainMenu(ActionEvent event) throws IOException {
        Parent new_parent = FXMLLoader.load(getClass().getResource("MainMenu.fxml"));
        Scene new_scene = new Scene(new_parent);
        Stage old_stage = (Stage) menu.getScene().getWindow();
        old_stage.setScene(new_scene);
    }

    public void setSceneNumber(String id) {
        LevelNo = id;
        System.out.println(LevelNo);
        if(id.equals("level1")){
            Row1.setDisable(true);Row5.setDisable(true);
            Row2.setDisable(true);Row4.setDisable(true);
            Row1.setBackground(new Background(new BackgroundFill(Color.color(0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
            Row2.setBackground(new Background(new BackgroundFill(Color.color(0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
            Row4.setBackground(new Background(new BackgroundFill(Color.color(0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
            Row5.setBackground(new Background(new BackgroundFill(Color.color(0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));

        }
        if(LevelNo.equals("level2")){
            Row1.setDisable(true);Row5.setDisable(true);
            Row1.setBackground(new Background(new BackgroundFill(Color.color(0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
            Row5.setBackground(new Background(new BackgroundFill(Color.color(0,0,0), CornerRadii.EMPTY, Insets.EMPTY)));
        }
        createLevel();
    }

    public void set_plantCards(){
        for(PlantCard plantCard: PlantCards){
            Timeline t = new Timeline( new KeyFrame( Duration.seconds(0.5),(event) -> {
                if(sunToken < plantCard.getSunCost()){
//                    System.out.println(sunToken + " " + plantCard.getSunCost());
                    plantCard.imageView.setVisible(false);
                }
                else{
                    plantCard.imageView.setVisible(true);
                }
            }));
            t.setCycleCount(Animation.INDEFINITE);
            t.play();
        }
    }

    private void createLevel() {
        InitializeZombies();
        InitializeSuns();
        InitializeLawnMowers();
        InitializePlantCards();
        for(LawnMower lawnmower: LawnMowers){
            for(Zombie zombie: Zombies){
                Timeline t = new Timeline( new KeyFrame( Duration.seconds(0.5),(event) -> {
                    collision_with_lawnmower(lawnmower, zombie);
                }));
                t.setCycleCount(Animation.INDEFINITE);
                t.play();
            }
        }

        try{
            for(Plant plant: Plants){
                for(Zombie zombie: Zombies){
                    Timeline t = new Timeline( new KeyFrame( Duration.seconds(0.5),(event) -> {
                        collision_with_plant(plant, zombie);
                    }));
                    t.setCycleCount(Animation.INDEFINITE);
                    t.play();
                }
            }
        }
        catch(NullPointerException e){
        }
        is_ZombieDead();
        zombie_reached_house();
        set_plantCards();
    }

    void is_ZombieDead(){
        for(Zombie zombie: Zombies){
            Timeline t = new Timeline( new KeyFrame( Duration.seconds(0.5),(event) -> {
                if(zombie.getHealth() < 0) {
                    Zombies.remove(zombie);
                    removeObject(zombie.imageView);
                }
            }));
            t.setCycleCount(Animation.INDEFINITE);
            t.play();
        }
    }

    void zombie_reached_house(){
        for(Zombie zombie: Zombies){
            Timeline t = new Timeline( new KeyFrame( Duration.seconds(0.5),(event) -> {
                ImageView zombie_image = zombie.imageView;
                if(zombie_image.getLocalToSceneTransform().getTx() < 10 && zombie_image.isVisible()){
                    System.out.println("Game Over");

                    Image image = new Image("Images/loser.jpeg");
                    ImageView imageView = new ImageView();
                    imageView.setImage(image);
                    imageView.setFitWidth(300);
                    imageView.setFitHeight(200);
                    Pane pane = new Pane(imageView);
                    pane.setLayoutY(100);
                    pane.setLayoutX(200);
                    LevelSceneMainPane.getChildren().add(pane);
                }
            }));
            t.setCycleCount(Animation.INDEFINITE);
            t.play();
        }
    }

    void collision_with_plant(Plant plant_object, Zombie zombie_object){
        ImageView plant = plant_object.imageView;
        ImageView zombie = zombie_object.imageView;
        if(collisionDetection(plant, zombie)){
            zombie_object.attack();
            Plants.remove(plant_object);
            removeObject(plant);
        }
    }

    void collision_with_pea(ImageView pea, Zombie zombie_object){
        ImageView zombie = zombie_object.imageView;
        if(collisionDetection(pea, zombie)){
            zombie_object.setHealth(zombie_object.getHealth() - 2);
            removeObject(pea);
        }
    }

    void collision_with_lawnmower(LawnMower lawnmower_object, Zombie zombie_object){
        ImageView lawnmower = lawnmower_object.imageView;
        ImageView zombie = zombie_object.imageView;
        if(collisionDetection(lawnmower, zombie)){
            lawnmower_object.attack();
            Zombies.remove(zombie_object);
            removeObject(zombie);
        }
    }

    boolean collisionDetection(ImageView first, ImageView second){
        Bounds first_bound = first.localToScene(first.getBoundsInLocal());
        Bounds second_bound = second.localToScene(second.getBoundsInLocal());
//        System.out.println(first_bound.getCenterX());
//        System.out.println(second_bound.getCenterX());
        if(first_bound.intersects(second_bound)){
            System.out.println("Collision");
            return true;
        }
        return false;
    }

    void removeObject(ImageView imageview){
        imageview.setVisible(false);
        LevelSceneMainPane.getChildren().remove(imageview.getParent());
//        System.out.println(imageview.getX());
    }

    private void InitializeZombies() {
        String[] ZombieTypes = {"/Images/Zombieidle.gif", "/Images/ConeZombie.gif","/Images/flying zombie.gif", "/Images/Balloon Zombie.gif", "/Images/GiantZombie.gif", "/Images/FlagZombie.gif"};
        Integer[][] LevelZombieTable= {{7, 0, 0, 0, 0, 1},
                {5, 5, 0, 0, 0, 1},
                {3, 5, 0, 0, 0, 1},
                {3, 3, 4, 3, 0, 1},
                {2, 3, 2, 2, 2, 1}};
        System.out.println(LevelNo.charAt(5));
        System.out.println("At initialize zombies !");
        int level = (int)LevelNo.charAt(5)-49;
        System.out.println(level);
        System.out.println("Just printed level number");
        createZombies(LevelZombieTable[level], ZombieTypes);

    }

    private void createZombies(Integer[] zombieNum, String[] zombieTypes) {
        Integer[] health = {5,8,8,9,10,5};
        Integer[] attack = {2,5,5,6,7,5};
        Zombies = new ArrayList<Zombie>();
        for(int i = 0; i < 6; i ++){
            for(int j = 0; j < zombieNum[i]; j++){
                Zombie zomb = new Zombie(zombieTypes[i], health[i], attack[i], rand.nextInt(20), rand.nextInt(5), LevelSceneMainPane);
                Zombies.add(zomb);
            }
        }
    }

    private void InitializeSuns() {
        int sunNo = 7;
        Suns = new ArrayList<Sun>();
        for (int i = 0; i < sunNo; i ++){
            Sun sun = new Sun(rand.nextInt(120), 1, 100 + rand.nextInt(500), 0, LevelSceneMainPane, this);
            Suns.add(sun);
        }
    }

    private void InitializeLawnMowers() {
        LawnMowers = new ArrayList<LawnMower>();
        LawnMowers.add(new LawnMower(LawnMower1, 1));
        LawnMowers.add(new LawnMower(LawnMower2, 2));
        LawnMowers.add(new LawnMower(LawnMower3, 3));
        LawnMowers.add(new LawnMower(LawnMower4, 4));
        LawnMowers.add(new LawnMower(LawnMower5, 5));
    }

    private void InitializePlantCards(){
        PlantCards = new ArrayList<>();
        PlantCards.add(new PeaShooterCard(PlantCard1));
        PlantCards.add(new SunflowerCard(PlantCard2));
        PlantCards.add(new WalnutBombCard(PlantCard3));
        PlantCards.add(new CherryBombCard(PlantCard4));
    }

    public TextField getSunPoints(){
        return sunPoints;
    }
}