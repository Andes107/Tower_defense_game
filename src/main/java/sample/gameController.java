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
    private int towerNewBackFrontSize;
    private Label towerNewFrontLabel;
    /*=======towerDel Starts=======*/
    private ImageView towerDelFrontImgView;

    private HashMap<Tower, ImageView> towerToImageMap;
    private HashMap<ImageView, Tower> imageToTowerMap;
    private ImageView upgradeTower;
    private mapObject[][] map;
    private List<Monster> monsterList;

    @FXML
    public void initialize() {
        ARENA_SIZE = 25;
        Tower.ARENA_SIZE = this.ARENA_SIZE;
        basicr1 = 5;
        basicDamage = 5;
        icer1 = 5;
        iceBump = 5;
        deathStarDamage = 10;
        catapultr1 = 5;
        catapultr2 = 10;
        catapultDamage = 10;
        towerNewBackFrontSize = 5;
        towerNewFrontLabel = null;
        towerToImageMap = new HashMap<Tower, ImageView>();
        imageToTowerMap = new HashMap<ImageView, Tower>();
        monsterList = new Vector<Monster>();
        towerNewBackXFrontY = -1;
        towerNewBackYFrontX = -1;
        initializeMap();
        initializeDragRelatedEvent();
        System.out.println("map is null: " + (map == null));
    }

    public void initializeMap() {
        map = new mapObject[ARENA_SIZE] [ARENA_SIZE];
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                map[i][j] = new mapObject(i,j,null,null);
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
            System.out.println("label instanceof Label? " + (towerNewFrontLabel == basicTower));
            towerNewBackXFrontY = (int)(ARENA_SIZE * dragEvent.getY() / leftAnchorPane.getHeight());
            System.out.println("backend updateX: " + towerNewBackXFrontY);
            towerNewBackYFrontX = (int)(ARENA_SIZE * dragEvent.getX() / leftAnchorPane.getWidth()); //this will be in the map
            System.out.println("backend updateY: " + towerNewBackYFrontX);
            dragEvent.setDropCompleted(true);
            dragEvent.consume();
        });
    }

    @FXML
    public void nextFrame(ActionEvent actionEvent) {
        System.out.println("nextFrame");
        gameLoop();
        printMap();
    }

    public void printMap() {
        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j)
                System.out.print((map[i][j].tower != null) + " ");
            System.out.println();
        }

        System.out.println();

        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j)
                System.out.print(map[i][j].towers.size() + " ");
            System.out.println();
        }
    }

    public void gameLoop() {
        System.out.println("gameloop");
        Tower.towerInflictDamage(towerToImageMap.keySet(), map);
        towerNew();
        towerDel();
        /*
        * towerUp(); Remark: Stuck in UI design, will come back later after some concrete work is done here.
        * checkCuasalty();
        * monsterNextMove();
        * monsterRandomNew();
        * */
    }

    public void towerNew() {
        System.out.println("double true: " + Tower.towerNewBackMapAva(towerNewBackXFrontY, towerNewBackYFrontX, towerNewBackFrontSize, map) + " " + towerNewFrontIsNew());
        if (Tower.towerNewBackMapAva(towerNewBackXFrontY, towerNewBackYFrontX, towerNewBackFrontSize, map) && towerNewFrontIsNew()) {
            Tower towerNewTempTower = towerNewGenTower();
            Tower.towerNewBackFillMap(towerNewTempTower, towerNewBackXFrontY, towerNewBackYFrontX, towerNewBackFrontSize, map);
            towerNewFrontHashUpdate(towerNewTempTower, towerNewFrontGenImgView());
            System.out.println("hash map size: " + towerToImageMap.size() + " " + imageToTowerMap.size());
            towerNewFrontRestore();
            System.out.println("backend UpdateX: " + towerNewBackXFrontY + " backend UpdateY : " + towerNewBackYFrontX);
        }
    }

    public boolean towerNewFrontIsNew() {
        return (towerNewFrontLabel != null && towerNewBackXFrontY != -1 && towerNewBackYFrontX != -1);
    }

    public Tower towerNewGenTower() {
        System.out.println("backendnewTower");
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
        System.out.println("frontendnewimageview");
        ImageView frontEndTower;
        if (towerNewFrontLabel == basicTower)
            frontEndTower = new ImageView(new Image(getClass().getResourceAsStream("/basicTower.png")));
        else if (towerNewFrontLabel == iceTower)
            frontEndTower = new ImageView(new Image(getClass().getResourceAsStream("/iceTower.png")));
        else if (towerNewFrontLabel == deathStar)
            frontEndTower = new ImageView(new Image(getClass().getResourceAsStream("/deathStar.png")));
        else
            frontEndTower = new ImageView(new Image(getClass().getResourceAsStream("/catapult.png")));
        System.out.println("is null? " + (frontEndTower == null));
        frontEndTower.fitWidthProperty().bind(leftAnchorPane.widthProperty().divide(ARENA_SIZE).multiply(towerNewBackFrontSize));
        frontEndTower.fitHeightProperty().bind(leftAnchorPane.heightProperty().divide(ARENA_SIZE).multiply(towerNewBackFrontSize));
        frontEndTower.xProperty().bind(leftAnchorPane.widthProperty().divide(ARENA_SIZE).multiply(towerNewBackYFrontX - towerNewBackFrontSize /2));
        frontEndTower.yProperty().bind(leftAnchorPane.heightProperty().divide(ARENA_SIZE).multiply(towerNewBackXFrontY - towerNewBackFrontSize /2));
        frontEndTower.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown()) {
                System.out.println("Left button hit!!!");
                upgradeTower = (ImageView) (event.getSource());
                System.out.println("X: " + upgradeTower.getX() + " Y: " + upgradeTower.getY() + " Ratio: " + upgradeTower.getX() / leftAnchorPane.getWidth());
            } else if (event.isSecondaryButtonDown()) {
                System.out.println("Right button hit!!!");
                towerDelFrontImgView = (ImageView) (event.getSource());
                System.out.println("X: " + towerDelFrontImgView.getX() + " Y: " + towerDelFrontImgView.getY() + " Ratio: " + towerDelFrontImgView.getX() / leftAnchorPane.getWidth());
            }
        });
        leftAnchorPane.getChildren().add(frontEndTower);
        System.out.println("imgview.x: " + frontEndTower.getX());
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
        System.out.println("towerDel");
        if (towerDelFrontImgView == null) {
            System.out.println("towerDel deleteTower == null");
            return;
        }
        towerDelFrontDelImgView();
        Tower.towerDelBackRemoveMap(imageToTowerMap.get(towerDelFrontImgView), towerNewBackFrontSize, map);
        imageToTowerMap.get(towerDelFrontImgView).towerDelBackRemoveKillZone(map);
        towerDelFrontHashDel();
        towerDelFrontRestore();
    }

    public void towerDelFrontDelImgView() {
        System.out.println("towerDelFrontDel");
        leftAnchorPane.getChildren().remove(towerDelFrontImgView);
    }

    public void towerDelFrontHashDel() {
        System.out.println("imagetotestmap.size(): " + imageToTowerMap.size() + " towertoimageMap.size(): " + towerToImageMap.size());
        Tower tempDelTower = imageToTowerMap.get(towerDelFrontImgView);
        imageToTowerMap.remove(towerDelFrontImgView);
        towerToImageMap.remove(tempDelTower);
        System.out.println("imagetotestmap.size(): " + imageToTowerMap.size() + " towertoimageMap.size(): " + towerToImageMap.size());
    }

    public void towerDelFrontRestore() {
        towerDelFrontImgView = null;
    }
}
