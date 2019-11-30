
package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.Pair;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

abstract class Type implements Serializable{
    boolean active;
    protected int health;         //Check if the initial health of all plants is same. To initialize in constructor.
    private int defense;        //Check if this need to be made final
    private Pair<Integer, Integer> position;

    public Type() {
        active = true;
    }

    Pair<Integer, Integer> getPosition(){
        return position;
    }
    void setPosition(Pair<Integer, Integer> position){
        this.position = position;
    }
    public void setActiveFalse(){
        active = false;
    }
    public boolean getActiveStatus(){
        return active;
    }
    abstract void attack();
}


abstract class PlantCard{
    ImageView imageView;
    private final int sunCost;
    private final int recharge;

    PlantCard(int sunCost, int recharge, ImageView imageView){
        this.sunCost = sunCost;
        this.recharge = recharge;
        this.imageView = imageView;
    }

    int getSunCost(){
        return sunCost;
    }
    int getRecharge(){
        return recharge;
    }
}

class PeaShooterCard extends PlantCard{
    PeaShooterCard(ImageView imageview){
        super(100, 7, imageview);
    }

}

class SunflowerCard extends PlantCard{
    SunflowerCard(ImageView imageview){
        super(50, 7, imageview);
    }
}

class CherryBombCard extends PlantCard{
    CherryBombCard(ImageView imageview){
        super(150, 20, imageview);
    }
}

class WalnutBombCard extends PlantCard{
    WalnutBombCard(ImageView imageview){
        super(50, 15, imageview);
    }
}

abstract class Plant extends Type{

    String imagePath;
    ImageView imageView;
    private final int sunCost;
    private final int recharge;
    double x;
    double y;

    Plant(String path, int sunCost, int recharge, ImageView imageView){
        super();
        this.imagePath = path;
        this.sunCost = sunCost;
        this.recharge = recharge;
        this.imageView = imageView;
        x = imageView.getLocalToSceneTransform().getTx() + 20;
        y = imageView.getLocalToSceneTransform().getTy() + 20;
        active = true;
    }
    int getSunCost(){
        return sunCost;
    }
    int getRecharge(){
        return recharge;
    }

}

final class Pea{
    public boolean active;
    private int attackValue;
    Image image;
    ImageView imageView;
    TranslateTransition T;

    Pea(ImageView cell, int attackValue, LevelSceneController levelSceneController){
        active = true;
        image = new Image((getClass().getResourceAsStream("/Images/pea.png")));
        imageView = new ImageView(image);
        imageView.setFitHeight(10);
        imageView.setFitWidth(10);
        //            Scene s = cell.getScene();
        T = new TranslateTransition();
        T.setNode(imageView);
        T.setToX(500);

        T.setDuration(Duration.seconds(3));
        T.setCycleCount(1);
        T.play();
        Pane p = new Pane(imageView);
//        System.out.println(cell.getLocalToSceneTransform().getTx());
//        System.out.println(cell.getLocalToSceneTransform().getTy());
        p.setLayoutX(cell.getLocalToSceneTransform().getTx() + 20);
        p.setLayoutY(cell.getLocalToSceneTransform().getTy() + 20);
        (levelSceneController.getPeaPlane()).getChildren().add(p);
    }

    int getAttackValue(){
        return attackValue;
    }

    void setAttackValue(int attackValue){
        this.attackValue = attackValue;
    }
}


class PeaShooter extends Plant{

    private Pea pea;

    public PeaShooter(String imagePath, ImageView cell, LevelSceneController levelSceneController) {
        super(imagePath, 100, 7, cell);
        System.out.println("Created Pea Shooter");
        Timeline t = new Timeline(new KeyFrame(Duration.seconds(3),(event) -> {
            if(active == true)
                pea = new Pea(cell, 10, levelSceneController);
        }));
        t.setCycleCount(Animation.INDEFINITE);
        t.play();

    }
    Pea getPea(){
        return pea;
    }
    void attack(){
    }
}


class Sunflower extends Plant{

    private Sun sun;


    public Sunflower(String path, ImageView imageView, LevelSceneController levelSceneController, double sunX, double sunY, boolean saved) {
        super(path,50, 7, imageView);
        x = imageView.getLocalToSceneTransform().getTx() + 20;
        y = imageView.getLocalToSceneTransform().getTy() + 20;
        if(saved == false) {
            sunX = (int) x + 20;
            sunY = (int) y;
        }
        System.out.println(x);
        System.out.println(y);
        double finalSunX = sunX;
        double finalSunY = sunY;
        Timeline t = new Timeline(new KeyFrame(Duration.seconds(10),(event) -> {
            if(active)
                sun = new Sun(10, 2, finalSunX, finalSunY, levelSceneController.getMainPane(), levelSceneController);;
        }));
        t.setCycleCount(Animation.INDEFINITE);
        t.play();
    }

    void attack(){

    }
}


class CherryBomb extends Plant{

    public CherryBomb(String path, ImageView imageView, LevelSceneController levelSceneController) {
        super(path, 150, 20, imageView);
    }

    void attack(){

    }
}

class WalnutBomb extends Plant{

    public WalnutBomb(String path, ImageView imageView, LevelSceneController levelSceneController) {
        super(path,50, 15, imageView);
    }

    void attack(){

    }
}


class Sun{
    boolean active;
    Image image;
    ImageView imageView;
    Pane pane;
    TranslateTransition T;
    int source;     // 1 for scene, 2 for sunflower
    private int rechargeGiven;
    private static final String path = "/Images/sun.jpg";
    private int arrivalTime;
    Sun(int arrivalTime, int source, double x, double y, AnchorPane MainPane, LevelSceneController Lsc){
        active = true;
        this.arrivalTime = arrivalTime;
        this.source = source;
        image = new Image(getClass().getResourceAsStream((path)));
        imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {                         //Decide where to put sunToken and sunPoints in game or levelscene controller.
                if(active == true) {
                    imageView.setVisible(false);
                    active = false;
                    LevelSceneController.sunToken += 25;
                    String points = Integer.toString(LevelSceneController.sunToken);
                    TextField sunPoints = (TextField) Lsc.getSunPoints();
                    System.out.println(sunPoints);
                    sunPoints.setText(points);
                }
            }
        });
        pane = new Pane(imageView);
        pane.setLayoutX(x);
        pane.setLayoutY(y);
        MainPane.getChildren().add(pane);
        if(source == 1) {
            T = new TranslateTransition();
            T.setNode(imageView);
            imageView.setVisible(false);
            T.setDelay(Duration.seconds(arrivalTime));
            T.setToY(500);
            T.setDuration(Duration.seconds(20));
            imageView.setVisible(true);
            T.play();
        }

    }


    int getRechargeGiven(){
        return rechargeGiven;
    }

    void setRechargeGiven(int rechargeGiven){
        this.rechargeGiven = rechargeGiven;
    }


}

class Zombie extends Type{
    String imagePath;
    ImageView imageView;
    Image image;
    Pane pane;
    TranslateTransition T;
    int row;
    double x;
    private final int attack;
    private int arrivalTime;
    LevelSceneController levelSceneController;


    Zombie(String path, int health, int attack, int arrivalTime, int row, double x, AnchorPane MainPane){
        super();
        active = true;
        double[] ycoord = {54.5, 119.5, 184.5, 249.5, 314.5};
        this.row = row;
        this.imagePath = path;
        image = new Image((getClass().getResourceAsStream(path)));
        imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(60);

        T = new TranslateTransition();

        pane = new Pane(imageView);
        pane.setLayoutY(ycoord[row]);
        pane.setLayoutX(x);

        T.setNode(pane);
        T.setToX(-700);
        T.setDuration(Duration.seconds(40));
        T.setDelay(Duration.seconds(arrivalTime));
        T.play();
//        System.out.println("y = "+ycoord[row]);
//        System.out.println("x = "+ x);
        MainPane.getChildren().add(pane);

        this.arrivalTime = arrivalTime;
        this.health = health;
        this.attack = attack;
    }

    @Override
    void attack() {

    }

    void attack(Plant plant, LevelSceneController controller){
        int count = 0;
        T.stop();
        Timeline t = new Timeline( new KeyFrame( Duration.seconds(5),(event) -> {
            controller.removeObject(plant.imageView);
//            plant.imageView.setVisible(false);
        }));
        t.setCycleCount(1);
        t.play();
        T.play();
    }

    int getHealth(){
        return health;
    }

    int getAttack(){
        return attack;
    }

    int getArrivalTime(){
        return arrivalTime;
    }

    void setHealth(int health){
        this.health = health;
    }
}

class LawnMower extends Type{

    ImageView imageView;
    TranslateTransition T;
    int id;
    String path;

    LawnMower(int id, AnchorPane MainPane, String path){
        this.id = id;
        this.path = path;
        double[] ycoord = {54.5, 119.5, 184.5, 249.5, 314.5};
        Image image = new Image((getClass().getResourceAsStream(path)));
        imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(40);
        Pane p_lawn = new Pane(imageView);
        p_lawn.setLayoutX(75);
        p_lawn.setLayoutY(ycoord[id - 1]);
        MainPane.getChildren().add(p_lawn);
        System.out.println(id);
    }

    void attack(){
        TranslateTransition LawnTrans = new TranslateTransition();
        LawnTrans.setNode(imageView);
        LawnTrans.setToX(3000);
        LawnTrans.setDuration(Duration.seconds(10));
        LawnTrans.play();
    }
}

class Level{
    private int levelNumber;
    private ArrayList<Zombie> zombies;      //Array of Arraylist might be more helpful to segregate zombies row wise. Easier in attack functions of peas
    private ArrayList<Plant> plants;
    private final int rows;
    private final int columns = 9;
    private Object[][] grid;

    Level(int levelNumber, int rows){
        this.levelNumber = levelNumber;
        this.rows = rows;
        grid = new Object[rows][columns];
    }

    int getLevelNumber(){
        return levelNumber;
    }

    void addZombie(Zombie zombie){
        zombies.add(zombie);
    }

    void addPlant(Plant plant){
        plants.add(plant);
    }

    Object getGrid_object(int row, int column){
        return grid[row][column];
    }

}

class Game implements Serializable {
    int time;


    ArrayList<ZombieRegenerator> zombieRegenerators;
    ArrayList<PlantRegenerator> plantRegenerators;
    ArrayList<LawnMowerRegenerator> lawnMowerRegenerators;
    Player player;
    String levelNo;
    double progress_time;
    int sunToken;

    public Game(int sunToken, double progress_time, ArrayList<ZombieRegenerator> zombieRegenerators, ArrayList<PlantRegenerator> plantRegenerators, ArrayList<LawnMowerRegenerator> lawnMowerRegenerators, Player player, String levelNo) {
        this.zombieRegenerators = zombieRegenerators;
        this.plantRegenerators = plantRegenerators;
        this.lawnMowerRegenerators = lawnMowerRegenerators;
        this.levelNo = levelNo;
        this.player = player;
        this.progress_time = progress_time;
        this.sunToken = sunToken;

    }

    int getTime(){
        return time;
    }


    void setTime(int time){
        this.time = time;
    }

    void play(){

    }

}

class Player implements Serializable{
    private String name;
    Game game;
    Player(String name){
        this.name = name;
    }

//    public void serialize(LevelSceneController levelSceneController) throws IOException{
//        ObjectOutputStream out = null;
//        try {
//            out = new ObjectOutputStream(new FileOutputStream("out.txt"));
//            out.writeObject(levelSceneController);
//        }
//        finally {
//            out.close();
//        }
//
//    }

    String getName(){
        return name;
    }
}

class Regenerators implements Serializable{
    String imagePath;
    double x;
    double y;
    Regenerators(double x, double y, String imagePath){
        this.imagePath = imagePath;
        this.x = x;
        this.y = y;
    }
}
class ZombieRegenerator extends Regenerators{
    float health;
    int attack;
    int row;
    ZombieRegenerator(double x, double y, String imagePath, int attack, float health, int row){
        super(x, y, imagePath);
        this.health = health;
        this.attack = attack;
        this.row = row;
    }
}
class PlantRegenerator extends Regenerators{
    double x1;
    double y1;
    String RowId;
    PlantRegenerator(double x, double y, String imagePath, String Rowid, double x1, double y1){
        super(x, y, imagePath);
        this.x1 = y1;
        this.y1 = y1;
        this.RowId = Rowid;
    }
}
class LawnMowerRegenerator extends Regenerators{
    int id;
    LawnMowerRegenerator(double x, double y, String imagePath, int id){
        super(x, y, imagePath);
        this.id = id;
    }
}

