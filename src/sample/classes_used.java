package sample;

import javafx.util.Pair;

import java.util.ArrayList;

public class classes_used{

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
        private int rechargeGiven;

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
            sun = new Sun();
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
        private final int attack;
        private int arrivalTime;

        Zombie(int health, int attack, int arrivalTime){
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

        void attack(){

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

}
