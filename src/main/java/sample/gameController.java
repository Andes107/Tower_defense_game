package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.layout.*;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

import Monsters.*;
import Towers.*;
import MapObject.*;
import javafx.util.Duration;

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
    private int basicr1;
    private int basicDamage;
    private int icer1;
    private int iceBump;
    private int deathStarDamage;
    private int catapultr1;
    private int catapultr2;
    private int catapultDamage;
    private int towerSize;

    private Label newTower;
    private int updateX;
    private int updateY;
    private HashMap<Tower, ImageView> towerList;
    private HashMap<ImageView, Tower> imageToTowerList;
    private ImageView upgradeTower;
    private ImageView deleteTower;
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
        catapultr1 = 2;
        catapultr2 = 4;
        catapultDamage = 10;
        towerSize = 5;
        newTower = null;
        towerList = new HashMap<Tower, ImageView>();
        imageToTowerList = new HashMap<ImageView, Tower>();
        monsterList = new Vector<Monster>();
        updateX = -1;
        updateY = -1;
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
            newTower = (Label)dragEvent.getGestureSource();
            System.out.println("label instanceof Label? " + (newTower == basicTower));
            updateX = (int)(ARENA_SIZE * dragEvent.getX() / leftAnchorPane.getWidth()); //this will be in the map
            System.out.println("updateX: " + updateX);
            updateY = (int)(ARENA_SIZE * dragEvent.getY() / leftAnchorPane.getHeight());
            System.out.println("updateY: " + updateY);
            dragEvent.setDropCompleted(true);
            dragEvent.consume();
        });
    }

    @FXML
    public void nextFrame(ActionEvent actionEvent) {
        System.out.println("nextFrame");
        gameLoop();
    }

    public void gameLoop() {
        System.out.println("gameloop");
//        towerInflictDamage();
        towerProcessNew();
        /*
        * towerProcessUpgrade();
        * towerProcessDelete();
        * monsterNextMove();
        * monsterRandomNew();
        * */
    }

    public void towerInflictDamage() {
        for (Tower tower : towerList.keySet())
            tower.inflictDamage(map);
    }

    public void towerProcessNew() {
        System.out.println("double true: " + backendIsMapAvailable() + " " + frontendIsThereNew());
        if (backendIsMapAvailable() && frontendIsThereNew())
            updateHashMaps(backendNewTower(),frontendNewImageView());
//        frontendRestore();
    }

    public void towerProcessUpgrade() {

    }

    public boolean backendIsMapAvailable() {
        System.out.println("backendismapavailable");
        for (int i = updateX; i <= updateX + towerSize; ++i)
            for (int j = updateY; j <= updateY  + towerSize; ++j)
                if (i < 0 || i >= ARENA_SIZE || j < 0 || j >= ARENA_SIZE || map[i][j].tower != null || map[i][j].monster != null)
                    return false;
        return true;
    }

    public boolean frontendIsThereNew() {
        return (newTower != null && updateX != -1 && updateY != -1);
    }

    public void frontendRestore() {
        newTower = null;
        updateX = -1;
        updateY = -1;
    }

    public Tower backendNewTower() {
        System.out.println("backendnewTower");
        if (newTower == basicTower)
            return new Basic(updateX , updateY, basicr1, basicDamage,map);
         else if (newTower == iceTower)
            return new Ice(updateX, updateY , icer1, iceBump,map);
        else if (newTower == deathStar)
            return new DeathStar(updateX, updateY, ARENA_SIZE, deathStarDamage,map);
        else
            return new Catapult(updateX, updateY, catapultr1, catapultDamage, catapultr2,map);
    }

    public ImageView frontendNewImageView() {
        System.out.println("frontendnewimageview");
        ImageView frontEndTower;
        if (newTower == basicTower)
            frontEndTower = new ImageView(new Image(getClass().getResourceAsStream("/basicTower.png")));
        else if (newTower == iceTower)
            frontEndTower = new ImageView(new Image(getClass().getResourceAsStream("/iceTower.png")));
        else if (newTower == deathStar)
            frontEndTower = new ImageView(new Image(getClass().getResourceAsStream("/deathStar.png")));
        else
            frontEndTower = new ImageView(new Image(getClass().getResourceAsStream("/catapult.png")));
        System.out.println("is null? " + (frontEndTower == null));
        frontEndTower.fitWidthProperty().bind(leftAnchorPane.widthProperty().divide(ARENA_SIZE).multiply(towerSize));
        frontEndTower.fitHeightProperty().bind(leftAnchorPane.heightProperty().divide(ARENA_SIZE).multiply(towerSize));
        frontEndTower.xProperty().bind(leftAnchorPane.widthProperty().divide(ARENA_SIZE).multiply(updateX));
        frontEndTower.yProperty().bind(leftAnchorPane.heightProperty().divide(ARENA_SIZE).multiply(updateY));
        frontEndTower.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown())
                upgradeTower = (ImageView)(event.getSource());
            else if (event.isSecondaryButtonDown())
                deleteTower = (ImageView)(event.getSource());
        });
        leftAnchorPane.getChildren().add(frontEndTower);
        System.out.println("imgview.x: " + frontEndTower.getX());
        return frontEndTower;
    }

    public void updateHashMaps(Tower backendTower, ImageView frontendTower) {
        towerList.put(backendTower,frontendTower);
        imageToTowerList.put(frontendTower,backendTower);
    }
}
