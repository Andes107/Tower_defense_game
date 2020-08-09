package sample;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.layout.*;

import Monsters.*;
import Towers.*;
import MapObject.*;

import java.util.*;


public class gameController {

    @FXML
    private AnchorPane leftAnchorPane;

    @FXML
    private AnchorPane rightAnchorPane;

    @FXML
    private Label basicTower;

    @FXML
    private Label iceTower;

    @FXML
    private Label deathStar;

    @FXML
    private Label catapult;

    private int ARENA_SIZE;
    /*In the following, commented are variables in controller*/
    /*Basic.java: x,y*/private int basicr1; private int basicDamage; /*Basic.java map*/
    /*Ice.java: x,y*/private int icer1; private int iceBump; /*Ice.java map*/
    /*DeathStar.java: x,y, r1: ARENA_SIZE*/private int deathStarDamage; /*DeathStar.java map*/
    /*Catapult.java: x,y*/private int catapultr1; private int catapultDamage; private int catapultr2; /*Catapult.java map*/

    /*=======towerNew Starts=======*/
    private int towerNewBackXFrontY;
    private int towerNewBackYFrontX;
    private int towerNewDelBackFrontSize;
    private Label towerNewFrontLabel;
    /*=======towerDel Starts=======*/
    private ImageView towerDelFrontImgView;
    /*=====monsterNew Starts=======*/
    private int monsterNewHealth;
    private int monsterNewCounter;
    private int monsterNewHealthScalar;
    private int monsterNewCounterScalar;

    /*=======tower utility=======*/
    private HashMap<Tower, ImageView> towerToImageMap;
        /*Objective: for towerDel*/
    private HashMap<ImageView, Tower> imageToTowerMap;
    /*=======monster utility=======*/
    private HashMap<Monster, ImageView> monsterToImageMap;
    private HashMap<ImageView, Monster> imageToMonsterMap;
    /*=======mapObject utility=======*/
    private mapObject[][] map;
    private List<mapObject> mapWithoutMonster;

    private ImageView upgradeTower;

    @FXML
    public void initialize() {
        ARENA_SIZE = 25;
        Tower.ARENA_SIZE = this.ARENA_SIZE;
        Monster.ARENA_SIZE = this.ARENA_SIZE;
        mapObject.ARENA_SIZE = this.ARENA_SIZE;
        basicr1 = ARENA_SIZE/2;
        basicDamage = 5;
        icer1 = ARENA_SIZE/ 2;
        iceBump = 5;
        deathStarDamage = 10;
        catapultr1 = 5;
        catapultr2 = 15;
        catapultDamage = 10;
        towerNewDelBackFrontSize = 5;
        towerNewFrontLabel = null;
        towerDelFrontImgView = null;
        monsterNewHealth = 10;
        monsterNewCounter = 4;
        monsterNewHealthScalar = 2;
        monsterNewCounterScalar = 2;
        towerToImageMap = new HashMap<Tower, ImageView>();
        imageToTowerMap = new HashMap<ImageView, Tower>();
        monsterToImageMap = new HashMap<Monster, ImageView>();
        imageToMonsterMap = new HashMap<ImageView, Monster>();
        towerNewBackXFrontY = -1;
        towerNewBackYFrontX = -1;
        map = mapObject.initializeMap();
        mapWithoutMonster = mapObject.initializeMapWithoutMonster(map);
        initializeDragRelatedEvent();
    }
    public void initializeDragRelatedEvent() {
        basicTower.setOnDragDetected(mouseEvent -> {
            Dragboard db = basicTower.startDragAndDrop(TransferMode.ANY);

            ClipboardContent content = new ClipboardContent();
            content.putString(basicTower.getText());
            content.putImage(new Image(getClass().getResourceAsStream("/basicTower.png")));
            db.setContent(content);

            mouseEvent.consume();
        });
        iceTower.setOnDragDetected(mouseEvent -> {
            Dragboard db = iceTower.startDragAndDrop(TransferMode.ANY);

            ClipboardContent content = new ClipboardContent();
            content.putString(iceTower.getText());
            content.putImage(new Image(getClass().getResourceAsStream("/iceTower.png")));
            db.setContent(content);

            mouseEvent.consume();
        });
        deathStar.setOnDragDetected(mouseEvent -> {
            Dragboard db = deathStar.startDragAndDrop(TransferMode.ANY);

            ClipboardContent content = new ClipboardContent();
            content.putString(deathStar.getText());
            content.putImage(new Image(getClass().getResourceAsStream("/deathStar.png")));
            db.setContent(content);

            mouseEvent.consume();
        });
        catapult.setOnDragDetected(mouseEvent -> {
            Dragboard db = catapult.startDragAndDrop(TransferMode.ANY);

            ClipboardContent content = new ClipboardContent();
            content.putString(catapult.getText());
            content.putImage(new Image(getClass().getResourceAsStream("/catapult.png")));
            db.setContent(content);

            mouseEvent.consume();
        });
        leftAnchorPane.setOnDragOver(event -> {
            /*                 accept it only if it is  not dragged from the same node
             * and if it has a string data */
            if (event.getGestureSource() != leftAnchorPane &&
                    event.getDragboard().hasString()) {
                /*                     allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }

            event.consume();
        });
        leftAnchorPane.setOnDragDropped(dragEvent -> {/*
            for (Node node : leftAnchorPane.getChildren())
                if (node instanceof ImageView && ((ImageView) node).getX() == 0)
                    newPane.getChildren().remove(node);*/
            Dragboard db = dragEvent.getDragboard();
/*            boolean success = false;
            if (db.hasString()) {
                ((Label)dragEvent.getGestureTarget()).setText(db.getString());
                success = true;
            }*/
            towerNewFrontLabel = (Label)dragEvent.getGestureSource();
            towerNewBackXFrontY = (int)(ARENA_SIZE * dragEvent.getY() / leftAnchorPane.getHeight());
            System.out.println("towerNewBackXFrontY: " + towerNewBackXFrontY);
            towerNewBackYFrontX = (int)(ARENA_SIZE * dragEvent.getX() / leftAnchorPane.getWidth()); //this will be in the map
            System.out.println("towerNewBackYFrontX: " + towerNewBackYFrontX);
            System.out.println("towerNewBackFrontSize: " + towerNewDelBackFrontSize);
            dragEvent.setDropCompleted(true);
            dragEvent.consume();
        });
    }

    @FXML
    public void nextFrame(ActionEvent actionEvent) {
        gameLoop();
    }

    public void gameLoop() {
        Tower.towerDamageBack(towerToImageMap.keySet(), map);
        towerNew();
        towerDel();
        monsterNewRanGen();
//        printmap();
        /*
        * towerUp(); Remark: Stuck in UI design, will come back later after some concrete work is done here.
        * checkCuasalty();
        * monsterNextMove();
        * */
    }

    public void printmap() {
/*        System.out.println("Is there tower?");
        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j) {
                int x = (map[i][j].tower != null? 1 : 0);
                if (map[i][j].tower != null && map[i][j].tower.x == i && map[i][j].tower.y == j)
                    System.out.print((x + 1) +  " ");
                else
                    System.out.print(x + " ");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("Kill Zone");
        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j)
                System.out.print(map[i][j].towers.size() + " ");
            System.out.println();
        }*/
        System.out.println();
        System.out.println("Monster exists");
        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j)
                System.out.print((map[i][j].monster == null? 0 : 1) + " ");
            System.out.println();
        }

    }

    public void towerNew() {
        if (Tower.towerNewBackMapAva(towerNewBackXFrontY, towerNewBackYFrontX, towerNewDelBackFrontSize, map) && towerNewFrontIsNew()) {
            Tower towerNewTempTower = towerNewGenTower();
            Tower.towerNewBackFillMapTower(towerNewTempTower, towerNewBackXFrontY, towerNewBackYFrontX, towerNewDelBackFrontSize, map, mapWithoutMonster);
            towerNewFrontHashUpdate(towerNewTempTower, towerNewFrontGenImgView());
            towerNewFrontRestore();
        }/*
        System.out.println("monstertoimagemap.keyset().size() : " + monsterToImageMap.keySet().size());
        for (Monster monster : monsterToImageMap.keySet()) {
            System.out.println("newNext: " + (monster.newNext == null));
            System.out.println("next: " + (monster.next == null));
        }*/
    }

    public boolean towerNewFrontIsNew() {
        return (towerNewFrontLabel != null && towerNewBackXFrontY != -1 && towerNewBackYFrontX != -1);
    }

    public Tower towerNewGenTower() {
        if (towerNewFrontLabel == basicTower)
            return new Basic(towerNewBackXFrontY, towerNewBackYFrontX, basicr1, basicDamage,map);
         else if (towerNewFrontLabel == iceTower)
            return new Ice(towerNewBackXFrontY, towerNewBackYFrontX, icer1, iceBump,map);
        else if (towerNewFrontLabel == deathStar)
            return new DeathStar(towerNewBackXFrontY, towerNewBackYFrontX, ARENA_SIZE, deathStarDamage,map);
        else
            return new Catapult(towerNewBackXFrontY, towerNewBackYFrontX, catapultr1, catapultDamage, catapultr2,map);
    }

    public ImageView towerNewFrontGenImgView() {
        ImageView frontEndTower;
        if (towerNewFrontLabel == basicTower)
            frontEndTower = new ImageView(new Image(getClass().getResourceAsStream("/basicTower.png")));
        else if (towerNewFrontLabel == iceTower)
            frontEndTower = new ImageView(new Image(getClass().getResourceAsStream("/iceTower.png")));
        else if (towerNewFrontLabel == deathStar)
            frontEndTower = new ImageView(new Image(getClass().getResourceAsStream("/deathStar.png")));
        else
            frontEndTower = new ImageView(new Image(getClass().getResourceAsStream("/catapult.png")));
        frontEndTower.fitWidthProperty().bind(leftAnchorPane.widthProperty().divide(ARENA_SIZE).multiply(towerNewDelBackFrontSize));
        frontEndTower.fitHeightProperty().bind(leftAnchorPane.heightProperty().divide(ARENA_SIZE).multiply(towerNewDelBackFrontSize));
        frontEndTower.xProperty().bind(leftAnchorPane.widthProperty().divide(ARENA_SIZE).multiply(towerNewBackYFrontX - towerNewDelBackFrontSize /2));
        frontEndTower.yProperty().bind(leftAnchorPane.heightProperty().divide(ARENA_SIZE).multiply(towerNewBackXFrontY - towerNewDelBackFrontSize /2));
        frontEndTower.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                upgradeTower = (ImageView) (event.getSource());
            } else if (event.isSecondaryButtonDown()) {
                towerDelFrontImgView = (ImageView) (event.getSource());
            }
        });
        leftAnchorPane.getChildren().add(frontEndTower);
        return frontEndTower;
    }

    public void towerNewFrontHashUpdate(Tower backendTower, ImageView frontendTowerImageView) {
        towerToImageMap.put(backendTower,frontendTowerImageView);
        imageToTowerMap.put(frontendTowerImageView,backendTower);
    }

    public void towerNewFrontRestore() {
        towerNewFrontLabel = null;
        towerNewBackXFrontY = -1;
        towerNewBackYFrontX = -1;
    }

    public void towerDel() {
        if (towerDelFrontImgView != null) {
        towerDelFrontDelImgView();
        Tower.towerDelBackRemoveMapTower(imageToTowerMap.get(towerDelFrontImgView), towerNewDelBackFrontSize, map, mapWithoutMonster);
        imageToTowerMap.get(towerDelFrontImgView).towerDelBackRemoveKillZone(map);
        towerDelFrontHashDel();
        towerDelFrontRestore();
        }
    }

    public void towerDelFrontDelImgView() {
        leftAnchorPane.getChildren().remove(towerDelFrontImgView);
    }

    public void towerDelFrontHashDel() {
        Tower tempDelTower = imageToTowerMap.get(towerDelFrontImgView);
        imageToTowerMap.remove(towerDelFrontImgView);
        towerToImageMap.remove(tempDelTower);
    }

    public void towerDelFrontRestore() {
        towerDelFrontImgView = null;
    }

    public void monsterNewRanGen() {
        if (mapWithoutMonster.size() != 0) {
        Monster monsterNewTempMon = Monster.monsterNewRanGen(mapWithoutMonster, map,  monsterNewHealth, monsterNewCounter, monsterNewHealthScalar, monsterNewCounterScalar);
        ImageView monsterNewTempImg = monsterNewFrontGenImgView(monsterNewTempMon);
        monsterNewFrontHashUpdate(monsterNewTempMon, monsterNewTempImg);
        }

    }

    public ImageView monsterNewFrontGenImgView(Monster monsterNewTemp) {
        ImageView frontendMonster;
        if (monsterNewTemp instanceof Penguin)
            frontendMonster = new ImageView(new Image(getClass().getResourceAsStream("/penguin.png")));
        else if (monsterNewTemp instanceof Unicorn)
            frontendMonster = new ImageView(new Image(getClass().getResourceAsStream("/unicorn.png")));
        else
            frontendMonster = new ImageView(new Image(getClass().getResourceAsStream("/fox.png")));
        frontendMonster.fitWidthProperty().bind(leftAnchorPane.widthProperty().divide(ARENA_SIZE));
        frontendMonster.fitHeightProperty().bind(leftAnchorPane.heightProperty().divide(ARENA_SIZE));
        frontendMonster.xProperty().bind(leftAnchorPane.widthProperty().multiply(monsterNewTemp.simpleYProperty()).divide(ARENA_SIZE));
        frontendMonster.yProperty().bind(leftAnchorPane.heightProperty().multiply(monsterNewTemp.simpleXProperty()).divide(ARENA_SIZE));
        leftAnchorPane.getChildren().add(frontendMonster);
        return frontendMonster;
    }

    public void monsterNewFrontHashUpdate(Monster monsterNewTempMon, ImageView monsterNewTempImg) {
        monsterToImageMap.put(monsterNewTempMon, monsterNewTempImg);
        imageToMonsterMap.put(monsterNewTempImg, monsterNewTempMon);
    }
}