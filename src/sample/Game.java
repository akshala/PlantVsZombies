package sample;

import javafx.animation.TranslateTransition;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import javafx.util.Pair;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.awt.*;
import java.util.ArrayList;

abstract class Type{
    protected int health;         //Check if the initial health of all plants is same. To initialize in constructor.
    private int defense;        //Check if this need to be made final
    private Pair<Integer, Integer> position;

    Pair<Integer, Integer> getPosition(){
        return position;
    }
    void setPosition(Pair<Integer, Integer> position){
        this.position = position;
    }
    abstract void attack();
}

abstract class Plant extends Type{
    private final int sunCost;
    private final int recharge;

    Plant(int sunCost, int recharge){
        this.sunCost = sunCost;
        this.recharge = recharge;
    }
    int getSunCost(){
        return sunCost;
    }
    int getRecharge(){
        return recharge;
    }

}

final class Pea{
    private int attackValue;

    int getAttackValue(){
        return attackValue;
    }

    void setAttackValue(int attackValue){
        this.attackValue = attackValue;
    }
}


class PeaShooter extends Plant{
    PeaShooter(int sunCost, int recharge){
        super(sunCost, recharge);
    }
    private Pea pea;

    void attack(){
        pea = new Pea();
    }
}

class Sun{
    Image image;
    ImageView imageView;
    Pane pane;
    TranslateTransition T;
    int source;     // 1 for scene, 2 for sunflower
    private int rechargeGiven;
    private static final String path = "/Images/sun.jpg";
    private int arrivalTime;
    Sun(int arrivalTime, int source, int x, int y, AnchorPane MainPane){
        this.arrivalTime = arrivalTime;
        this.source = source;
        image = new Image(getClass().getResourceAsStream((path)));
        imageView = new ImageView(image);
        imageView.setFitHeight(30);
        imageView.setFitWidth(30);
        imageView.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {                         //Decide where to put sunToken and sunPoints in game or levelscene controller.
                imageView.setVisible(false);
                LevelSceneController.sunToken += 25;
                String points = Integer.toString(LevelSceneController.sunToken);
                LevelSceneController.sunPoints.setText(points);
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

class Sunflower extends Plant{
    Sunflower(int sunCost, int recharge){
        super(sunCost, recharge);
    }
    Sun sun;

    void attack(){
//        sun = new Sun();
    }
}

class CherryBomb extends Plant{
    CherryBomb(int sunCost, int recharge){
        super(sunCost, recharge);
    }

    void attack(){

    }
}

class WalnutBomb extends Plant{
    WalnutBomb(int sunCost, int recharge){
        super(sunCost, recharge);
    }

    void attack(){

    }
}

class Zombie extends Type{
    ImageView imageView;
    Image image;
    Pane pane;
    TranslateTransition T;
    private final int attack;
    private int arrivalTime;


    Zombie(String path, int health, int attack, int arrivalTime, int row, AnchorPane MainPane){
        double[] ycoord = {54.5, 119.5, 184.5, 249.5, 314.5};
        image = new Image((getClass().getResourceAsStream(path)));
        imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitWidth(50);
        imageView.setFitHeight(60);

        T = new TranslateTransition();
        T.setNode(imageView);
        T.setToX(-700);
        T.setDuration(Duration.seconds(40));
        T.setDelay(Duration.seconds(arrivalTime));
        T.play();
        pane = new Pane(imageView);
        pane.setLayoutY(ycoord[row]);
        pane.setLayoutX(700);
        MainPane.getChildren().add(pane);

        this.arrivalTime = arrivalTime;
        this.health = health;
        this.attack = attack;
    }

    void attack(){

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
    Pane pane;
    TranslateTransition T;
    int id;

    LawnMower(ImageView imageView, int id){
        this.imageView = imageView;
        this.id = id;
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

class Game{
    Level level;
    int time;           //Ask it's use

    Game(Level level, int time){
        this.level = level;
        this.time = time;
    }

    int getTime(){
        return time;
    }

    Level getLevel(){
        return level;
    }

    void setTime(int time){
        this.time = time;
    }

    void play(){

    }

}

class Player{
    private String name;
    Game game;

    String getName(){
        return name;
    }
}

