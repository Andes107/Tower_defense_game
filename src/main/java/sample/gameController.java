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
    private mapObject[][] map;
    private List<Monster> monsterList;

    @FXML
    public void initialize() {
        ARENA_SIZE = 480;
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
        towerSize = 5;
        initializeMap(map);
        initializeDragRelatedEvent();
    }

    public void initializeMap(mapObject[][] map) {
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
            updateX = (int)(ARENA_SIZE * dragEvent.getX() / leftAnchorPane.getWidth()); //this will be in the map
            updateY = (int)(ARENA_SIZE * dragEvent.getY() / leftAnchorPane.getHeight());
            dragEvent.setDropCompleted(true);
            dragEvent.consume();
        });
    }

    @FXML
    public void nextFrame(ActionEvent actionEvent) {
        gameLoop();
    }

    public void gameLoop() {
        towerInflictDamage();
        towerProcessNew();
    }

    public void towerInflictDamage() {
        for (Tower tower : towerList.keySet())
            tower.inflictDamage(map);
    }

    public void towerProcessNew() {
        if (backendIsMapAvailable())
            updateHashMaps(backendNewTower(),frontendNewImageView());
    }

    public void towerProcessUpgrade() {

    }

    public boolean backendIsMapAvailable() {
        for (int i = updateX; i <= updateX + towerSize; ++i)
            for (int j = updateY; j <= updateY  + towerSize; ++j)
                if (i < 0 || i >= ARENA_SIZE || j < 0 || j >= ARENA_SIZE || map[i][j].tower != null || map[i][j].monster != null)
                    return false;
        return true;
    }

    public Tower backendNewTower() {
        if (newTower == basicTower)
            return new Basic(updateX / 2, updateY / 2, basicr1, basicDamage,map);
         else if (newTower == iceTower)
            return new Ice(updateX / 2, updateY /2 , icer1, iceBump,map);
        else if (newTower == deathStar)
            return new DeathStar(updateX / 2, updateY / 2, ARENA_SIZE, deathStarDamage,map);
        else
            return new Catapult(updateX / 2, updateY / 2, catapultr1, catapultDamage, catapultr2,map);
    }

    public ImageView frontendNewImageView() {
        ImageView frontEndTower;
        if (newTower == basicTower)
            frontEndTower = new ImageView(new Image(getClass().getResourceAsStream("/basicTower")));
        else if (newTower == iceTower)
            frontEndTower = new ImageView(new Image(getClass().getResourceAsStream("/iceTower")));
        else if (newTower == deathStar)
            frontEndTower = new ImageView(new Image(getClass().getResourceAsStream("/deathStar")));
        else
            frontEndTower = new ImageView(new Image(getClass().getResourceAsStream("/catapult")));
        frontEndTower.fitWidthProperty().bind(leftAnchorPane.widthProperty().divide(ARENA_SIZE));
        frontEndTower.fitHeightProperty().bind(leftAnchorPane.heightProperty().divide(ARENA_SIZE));
        frontEndTower.xProperty().bind(leftAnchorPane.widthProperty().divide(ARENA_SIZE).multiply(updateX));
        frontEndTower.yProperty().bind(leftAnchorPane.heightProperty().divide(ARENA_SIZE).multiply(updateY));
        frontEndTower.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown())
                System.out.println("LEFT");
            else if (event.isSecondaryButtonDown())
                System.out.println("RIGHT");
        });
        return frontEndTower;
    }

    public void updateHashMaps(Tower backendTower, ImageView frontendTower) {
        towerList.put(backendTower,frontendTower);
        imageToTowerList.put(frontendTower,backendTower);
    }
}
