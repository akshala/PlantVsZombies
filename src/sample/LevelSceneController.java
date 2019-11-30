package sample;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
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

import javafx.scene.control.TextField;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.URL;
import java.util.*;


public class LevelSceneController implements Initializable, Serializable {

    static int sunToken = 0;
    private String LevelNo;
    ArrayList<Zombie> Zombies;
    ArrayList<Sun> Suns;
    ArrayList<Plant> Plants;
    ArrayList<LawnMower> LawnMowers;
    Random rand;
    String plant_category;
    ArrayList<PlantCard> PlantCards;
    int[] levelTime = {2, 3, 4, 5, 5};

    @FXML
    private Pane PeaMainPane;

    @FXML
    private AnchorPane LevelSceneMainPane;

    @FXML
    private GridPane MainGrid;

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
    private Player player;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        rand = new Random();
    }

    void set_progressBar(){
        switch(LevelNo){
            case "level1" : timer(levelTime[0]);
                            break;
            case "level2" : timer(levelTime[1]);
                            break;
            case "level3" : timer(levelTime[2]);
                            break;
            case "level4" :
            case "level5" :
                            timer(levelTime[3]);
                            break;
        }
    }

    void timer(int t){
        Timeline t1 = new Timeline(
                new KeyFrame(Duration.minutes(0), new KeyValue(timer.progressProperty(), 0)),
                new KeyFrame(Duration.minutes(t), new KeyValue(timer.progressProperty(), 1)));
        t1.setCycleCount(Animation.INDEFINITE);
        t1.play();
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
        else{
            String imagePath = event.getDragboard().getString();
            Image image = new Image(getClass().getResourceAsStream(event.getDragboard().getString()));
            cell.setImage(image);
            Plant plant;
//            System.out.println("Before creating a plant");
            switch(plant_category){
                case "Pea_shooter" : plant = new PeaShooter(imagePath, cell,this); break;
                case "sunflower" : plant = new Sunflower(imagePath, cell, this); break;
                case "walnut" : plant = new WalnutBomb(imagePath, cell, this); break;
                case "cherryBomb" : plant = new CherryBomb(imagePath, cell, this); break;
                default:
                    throw new IllegalStateException("Unexpected value: " + plant_category);
            }
//            System.out.println("Finished creating plant");
            Plants.add(plant);
//            System.out.println("After creating a plant");
            if(plant_category.equals("Pea_shooter")){
                for(Zombie zombie: Zombies){
                    Timeline t = new Timeline( new KeyFrame( Duration.seconds(0.5),(e) -> {
                        Pea pea = ((PeaShooter)plant).getPea();
                        if(pea != null && pea.active)
                            collision_with_pea(plant, pea, zombie);
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
        setLevel(id);

        createLevel(false);
    }

    public void setLevel(String id){
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

    private void createLevel(boolean saved) {

        if(!saved) {
            InitializeZombies();
            InitializeSuns();
            InitializeLawnMowers();
            Plants = new ArrayList<Plant>();
        }
        set_progressBar();
        InitializePlantCards() ;

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
            Timeline t = new Timeline( new KeyFrame( Duration.seconds(0.5),(event) -> {

                for(Plant plant: Plants){
                    if(plant.getActiveStatus()) {
                        for (Zombie zombie : Zombies) {
                            collision_with_plant(plant, zombie);
                        }
                    }
                }
            }));
            t.setCycleCount(Animation.INDEFINITE);
            t.play();
        }
        catch(NullPointerException ignored){
        }
        set_plantCards();
        is_ZombieDead();
        zombie_reached_house();
        gameWin();
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
            var lambdaContext = new Object() {
                Timeline t = null;
            };
            lambdaContext.t = new Timeline( new KeyFrame( Duration.seconds(0.5),(event) -> {
                ImageView zombie_image = zombie.imageView;
                if(zombie_image.getLocalToSceneTransform().getTx() < 10 && zombie_image.isVisible()){
                    System.out.println("Game Over");

                    lambdaContext.t.stop();
                    Parent new_parent = null;
                    try {
//                        new_parent = FXMLLoader.load(getClass().getResource("GameLost.fxml"));
//                        Scene new_scene = new Scene(new_parent);
//                        Stage old_stage = (Stage) menu.getScene().getWindow();
//                        old_stage.setScene(new_scene);

                        FXMLLoader GameLostLoader = new FXMLLoader(getClass().getResource("GameLost.fxml"));
                        AnchorPane LSParent = GameLostLoader.load();
                        GameWonController controller = GameLostLoader.getController();
                        controller.setCurr_level(LevelNo);
                        Scene LScene = new Scene(LSParent);
                        Stage oldPlayer_stage = (Stage) LevelSceneMainPane.getScene().getWindow();
                        oldPlayer_stage.setScene(LScene);

                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }));
            lambdaContext.t.setCycleCount(Animation.INDEFINITE);
            lambdaContext.t.play();
        }
    }

    void gameWin() {
        final boolean[] flag = {false};
        var lambdaContext = new Object() {
            Timeline t = null;
        };
        lambdaContext.t =  new Timeline(new KeyFrame(Duration.seconds(0.5), (event) -> {
            try {
                Zombies.get(0);
            } catch (IndexOutOfBoundsException e) {
                System.out.println("You win");
                flag[0] = true;
                lambdaContext.t.stop();
                try {
//                    Parent new_parent = FXMLLoader.load(getClass().getResource("GameWon.fxml"));
//                    Scene new_scene = new Scene(new_parent);
//                    Stage old_stage = (Stage) menu.getScene().getWindow();
//                    old_stage.setScene(new_scene);

                    FXMLLoader GameWonLoader = new FXMLLoader(getClass().getResource("GameWon.fxml"));
                    AnchorPane LSParent = GameWonLoader.load();
                    GameWonController controller = GameWonLoader.getController();
                    controller.setCurr_level(LevelNo);
                    Scene LScene = new Scene(LSParent);
                    Stage oldPlayer_stage = (Stage) LevelSceneMainPane.getScene().getWindow();
                    oldPlayer_stage.setScene(LScene);

                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }));
        lambdaContext.t.setCycleCount(Animation.INDEFINITE);
        lambdaContext.t.play();
    }


    void collision_with_plant(Plant plant_object, Zombie zombie_object){
        ImageView plant = plant_object.imageView;
        ImageView zombie = zombie_object.imageView;
        if(collisionDetection(plant, zombie)){
//            System.out.println("Collided with Plant");
            if(plant_object.getClass().getName().equals("PeaShooter") || plant_object.getClass().getName().equals("Sunflower")){
                zombie_object.attack(plant_object, this);
                plant_object.setActiveFalse();
                removeObject(plant);
            }
            else if(plant_object.getClass().getName().equals("CherryBomb")){
                Zombies.remove(zombie_object);
                removeObject(zombie);
            }
            else if(plant_object.getClass().getName().equals("WalnutBomb")){
                TranslateTransition t = zombie_object.T;
                SequentialTransition seqTransition = new SequentialTransition (
                        new PauseTransition(Duration.seconds(10)), t);
                seqTransition.play();
                plant_object.setActiveFalse();
                removeObject(plant);
            }
        }
    }

    void collision_with_pea(Plant plant, Pea pea, Zombie zombie_object){
        ImageView zombie = zombie_object.imageView;
        if(collisionDetection(pea.imageView, zombie)){
            zombie_object.setHealth(zombie_object.getHealth() - 2);
//            System.out.println("Pea collided with zombie ");
            pea.active = false;
//            System.out.println(pea);
            removeObject(pea.imageView);
//            removePea(plant, pea);
        }
    }

//    private void removePea(Plant plant, Pea pea) {
//        pea.imageView.setX(plant.imageView.getX());
//        pea.imageView.setY(plant.imageView.getY());
//    }

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
            return true;
        }
        return false;
    }


    void removeObject(ImageView imageview) {
        imageview.setVisible(false);
        imageview.setDisable(true);
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
//        System.out.println("At initialize zombies !");
        int level = (int)LevelNo.charAt(5)-49;
//        System.out.println(level);
//        System.out.println("Just printed level number");
        createZombies(level + 1, LevelZombieTable[level], ZombieTypes);
    }

    private void createZombies(int level, Integer[] zombieNum, String[] zombieTypes) {
        Integer[] health = {5,8,8,9,10,5};
        Integer[] attack = {2,5,5,6,7,5};
        Zombies = new ArrayList<Zombie>();
        for(int i = 0; i < 6; i ++){
            for(int j = 0; j < zombieNum[i]; j++){
                Zombie zomb;
//                System.out.println("Level " + level);
                if(level == 1){
                    zomb = new Zombie(zombieTypes[i], health[i], attack[i], rand.nextInt(20), 2, 700, LevelSceneMainPane);
                    Zombies.add(zomb);
                }
                else if(level == 2){
                    zomb = new Zombie(zombieTypes[i], health[i], attack[i], rand.nextInt(20), 1 + rand.nextInt(3), 700, LevelSceneMainPane);
                    Zombies.add(zomb);
                }
                else{
                    zomb = new Zombie(zombieTypes[i], health[i], attack[i], rand.nextInt(20), rand.nextInt(5), 700, LevelSceneMainPane);
                    Zombies.add(zomb);
                }
            }
        }
    }

    private void InitializeSuns() {
        int sunNo = 7;
        Suns = new ArrayList<Sun>();
        for (int i = 0; i < sunNo; i ++){
            Sun sun = new Sun(rand.nextInt(120), 1, 100 + rand.nextInt(500), -20, LevelSceneMainPane, this);
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

    public Pane getPeaPlane() {
        return PeaMainPane;
    }

    public AnchorPane getMainPane() {
        return LevelSceneMainPane;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    @FXML
    void Save_changeScene_mainMenu(ActionEvent event) throws IOException {
        saveGame();
        changeScene_mainMenu(event);
    }

    @FXML
    void SaveAndContinue(ActionEvent event) throws IOException {
        saveGame();
    }

    private void saveGame() throws IOException {
//        System.out.println("Starting to save game...");
        ArrayList<ZombieRegenerator> zombieRegenerators = new ArrayList<ZombieRegenerator>();
        ArrayList<PlantRegenerator> plantRegenerators = new ArrayList<PlantRegenerator>();
        ArrayList<LawnMowerRegenerator> lawnMowerRegenerators = new ArrayList<LawnMowerRegenerator>();
        for(Zombie zombie: Zombies){
//            System.out.println(zombie+"Without active check");
            if(zombie.active){
//                System.out.println(zombie);
                double x = 700 + zombie.imageView.getParent().getTranslateX();
                double y = zombie.imageView.getParent().getLayoutY();
                String imagePath = zombie.imagePath;
                int row = zombie.row;
                ZombieRegenerator zombGen = new ZombieRegenerator(x, y, imagePath, zombie.getAttack(), zombie.health, row);
                zombieRegenerators.add(zombGen);
            }
        }
        for(Plant plant: Plants){
            if(plant.active) {
                double x = plant.imageView.getParent().getLayoutX();
                double y = plant.imageView.getParent().getLayoutY();
                String imagePath = plant.imagePath;
//                System.out.println("Plant saving time coordinates "+x+" "+y);
                PlantRegenerator plantGen = new PlantRegenerator(x, y, imagePath);
                plantRegenerators.add(plantGen);
            }
        }
        for(LawnMower lawnMower: LawnMowers){
            if(lawnMower.active) {
                double x = lawnMower.imageView.getParent().getLayoutX();
                double y = lawnMower.imageView.getParent().getLayoutY();
                String imagePath = lawnMower.imageView.getImage().getUrl();
                LawnMowerRegenerator lawnMowerGen = new LawnMowerRegenerator(x, y, imagePath);
                lawnMowerRegenerators.add(lawnMowerGen);
            }
        }
        Game game = new Game(zombieRegenerators, plantRegenerators, lawnMowerRegenerators, player, LevelNo);
        ObjectOutputStream out = null;
        try {
            out = new ObjectOutputStream(new FileOutputStream("out.txt"));
            out.writeObject(game);
        }
        finally {
            out.close();
        }
        System.out.println("Game saved");

    }

    public void loadSavedGame(Game savedGame) {
        System.out.println("Loading saved game....");
        ArrayList<ZombieRegenerator> zombieRegenerators;
        ArrayList<PlantRegenerator> plantRegenerators;
        ArrayList<LawnMowerRegenerator> lawnMowerRegenerators;
        Player player;
        String levelNo;

        zombieRegenerators = savedGame.zombieRegenerators;
        plantRegenerators = savedGame.plantRegenerators;
        lawnMowerRegenerators = savedGame.lawnMowerRegenerators;
        levelNo = savedGame.levelNo;
        player = savedGame.player;

        setLevel(levelNo);

        //Loading Zombies
        Zombies = new ArrayList<Zombie>();
//        System.out.println(zombieRegenerators);
        for(ZombieRegenerator zombGen : zombieRegenerators){
            String path = zombGen.imagePath;
            double x = zombGen.x;
            double y = zombGen.y;
//            System.out.println(path);
//            System.out.println(x);
//            System.out.println(y);
            Zombie zomb = new Zombie(path, (int) zombGen.health, zombGen.attack, 0, zombGen.row, x, LevelSceneMainPane);
            Zombies.add(zomb);
        }
        //Loading Zombies
        Plants = new ArrayList<Plant>();
//        System.out.println(plantRegenerators);
        for(PlantRegenerator plantGen : plantRegenerators){
            String imagePath = plantGen.imagePath;
            double x = plantGen.x;
            double y = plantGen.y;
//            System.out.println(imagePath);
//            System.out.println(x);
//            System.out.println(y);

            ImageView cell = new ImageView();
            cell.setFitWidth(53);
            cell.setFitHeight(65);
            Pane p = new Pane(cell);
            p.setLayoutX(x);
            p.setLayoutY(y);
            LevelSceneMainPane.getChildren().add(p);

            Image image = new Image(getClass().getResourceAsStream(imagePath));
            cell.setImage(image);
            Plant plant;
//            System.out.println("Before creating a plant");
            switch(imagePath){
                case "/Images/Pea Shooter.gif" : plant = new PeaShooter(imagePath, cell,this); break;
                case "/Images/Sunflower.png" : plant = new Sunflower(imagePath, cell, this); break;
                case "/Images/Walnut.png" : plant = new WalnutBomb(imagePath, cell, this); break;
                case "/Images/CherryBomb.png" : plant = new CherryBomb(imagePath, cell, this); break;
                default:
                    throw new IllegalStateException("Unexpected value: " + plant_category);
            }
//            System.out.println("Finished creating plant");
            Plants.add(plant);
//            System.out.println("After creating a plant");
            if(imagePath.equals("Pea_shooter")) {
                for (Zombie zombie : Zombies) {
                    Timeline t = new Timeline(new KeyFrame(Duration.seconds(0.5), (e) -> {
                        Pea pea = ((PeaShooter) plant).getPea();
                        if (pea != null && pea.active == true)
                            collision_with_pea(plant, pea, zombie);
                    }));
                    t.setCycleCount(Animation.INDEFINITE);
                    t.play();
                }
            }

        }

    }
}
