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

    private Tower newTower;
    private List<Tower> towerList;
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
        towerList = new Vector<Tower>();
        monsterList = new Vector<Monster>();
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
            if (dragEvent.getGestureSource() == basicTower)
                newTower = new Basic((int)(ARENA_SIZE * dragEvent.getX() / leftAnchorPane.getWidth()), (int)(ARENA_SIZE * dragEvent.getY() / leftAnchorPane.getHeight()), basicr1, basicDamage,map);
            else if (dragEvent.getGestureSource() == iceTower)
                newTower = new Ice((int)(ARENA_SIZE * dragEvent.getX() / leftAnchorPane.getWidth()), (int)(ARENA_SIZE * dragEvent.getY() / leftAnchorPane.getHeight()), icer1, iceBump,map);
            else if (dragEvent.getGestureSource() == deathStar)
                newTower = new DeathStar((int)(ARENA_SIZE * dragEvent.getX() / leftAnchorPane.getWidth()), (int)(ARENA_SIZE * dragEvent.getY() / leftAnchorPane.getHeight()), ARENA_SIZE, deathStarDamage,map);
            else
                newTower = new Catapult((int)(ARENA_SIZE * dragEvent.getX() / leftAnchorPane.getWidth()), (int)(ARENA_SIZE * dragEvent.getY() / leftAnchorPane.getHeight()), catapultr1, catapultDamage, catapultr2,map);
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
        for (Tower tower : towerList)
            tower.inflictDamage(map);
    }

    public void towerProcessNew() {
        Tower innerNewTower = newTower
        if (innerNewTower == null)
            return;
        if (isSpaceForTower(innerNewTower) && isTowerNotBlock(innerNewTower)) {
            setTower(innerNewTower);
        }

    }

    public boolean isSpaceForTower(Tower target) {
        if (target.x - 2 < 0 || target.x + 2 >= ARENA_SIZE || target.y - 2 < 0 || target.y + 2 >= ARENA_SIZE)
            return false;
        else
            for (int i = target.x-2; i < target.x + 2; ++i)
                for (int j = target.y - 2; j < target.y + 2; ++j)
                    if (map[i][j].tower != null)
                        return false;
        return true;
    }

    public boolean isTowerNotBlock(Tower target) {
        for (Monster monster : monsterList)
            if ((monster.newNext = monster.nextAlgorithm(map, (monster instanceof Fox))) == null)
                return false;
        return true;
    }

    public void setTower(Tower target) {
        for (int i = target.x-2; i < target.x + 2; ++i)
            for (int j = target.y - 2; j < target.y + 2; ++j)
                map[i][j].tower = newTower;
    }

    public void setTowerImageView(Tower target) {
        ImageView towerImg = newImageByTower(target);
        towerImg.fitWidthProperty().bind(leftAnchorPane.widthProperty().divide(ARENA_SIZE));
        towerImg.fitHeightProperty().bind(leftAnchorPane.heightProperty().divide(ARENA_SIZE));
        towerImg.xProperty().bind();
/* coord: x/AREN -> width - width/AREN
    size: width/AREN
        img.setX(leftAnchorPane.getWidth() - leftAnchorPane.getWidth()/10);
        img.fitWidthProperty().bind(leftAnchorPane.widthProperty().divide(ARENA_SIZE));
        img.fitHeightProperty().bind(leftAnchorPane.heightProperty().divide(ARENA_SIZE));
        img.xProperty().bind(leftAnchorPane.widthProperty().divide(2));
        img.yProperty().bind(leftAnchorPane.heightProperty().divide(2));
        img.setOnMousePressed(event -> {
            if (event.isPrimaryButtonDown())
                System.out.println("LEFT");
            else if (event.isSecondaryButtonDown())
                System.out.println("RIGHT");
        });
        leftAnchorPane.getChildren().add(img);
*/
    }
    public ImageView newImageByTower(Tower target) {
        if (target instanceof Basic)
            return new ImageView(new Image(getClass().getResourceAsStream("/basicTower.png")));
        else if (target instanceof Ice)
            return new ImageView(new Image(getClass().getResourceAsStream("/iceTower.png")));
        else if (target instanceof DeathStar)
            return new ImageView(new Image(getClass().getResourceAsStream("/deathStar.png")));
        else if (target instanceof Catapult)
            return new ImageView(new Image(getClass().getResourceAsStream("/catapult.png")));
    }
}
