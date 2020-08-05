package sample;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.event.*;
import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.geometry.Insets;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class MyController {
    @FXML
    private Button buttonNextFrame;

    @FXML
    private Button buttonSimulate;

    @FXML
    private Button buttonPlay;

    @FXML
    private AnchorPane paneArena;

    @FXML
    private Label labelBasicTower;

    @FXML
    private Label labelIceTower;

    @FXML
    private Label labelCatapult;

    @FXML
    private Label labelLaserTower;

    private static final int ARENA_WIDTH = 480;
    private static final int ARENA_HEIGHT = 480;
    private static final int GRID_WIDTH = 40;
    private static final int GRID_HEIGHT = 40;
    private static final int MAX_H_NUM_GRID = 12;
    private static final int MAX_V_NUM_GRID = 12;

    private Label grids[][] = new Label[MAX_V_NUM_GRID][MAX_H_NUM_GRID]; //the grids on arena
    private int x = -1, y = 0; //where is my monster
    /**
     * A dummy function to show how button click works
     */
    @FXML
    private void play() {
        for (Node node : paneArena.getChildren())
            if (node instanceof ImageView)
                System.out.println("nodex: " + ((ImageView)node).getX());
    }

    /**
     * A function that create the Arena
     */
    @FXML
    public void createArena() {
        if (grids[0][0] != null)
            return; //created already
        for (int i = 0; i < MAX_V_NUM_GRID; i++)
            for (int j = 0; j < MAX_H_NUM_GRID; j++) {
                Label newLabel = new Label();
                if (j % 2 == 0 || i == ((j + 1) / 2 % 2) * (MAX_V_NUM_GRID - 1))
                    newLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
                else
                    newLabel.setBackground(new Background(new BackgroundFill(Color.GREEN, CornerRadii.EMPTY, Insets.EMPTY)));
                newLabel.setLayoutX(j * GRID_WIDTH);
                newLabel.setLayoutY(i * GRID_HEIGHT);
                newLabel.setMinWidth(GRID_WIDTH);
                newLabel.setMaxWidth(GRID_WIDTH);
                newLabel.setMinHeight(GRID_HEIGHT);
                newLabel.setMaxHeight(GRID_HEIGHT);
                newLabel.setStyle("-fx-border-color: black;");
                grids[i][j] = newLabel;
                paneArena.getChildren().addAll(newLabel);
                System.out.println("size: " + paneArena.getChildren().size());
            }

//        setDragAndDrop();
    }

    @FXML
    private void nextFrame() {
        if (x == -1) {
            grids[0][0].setText("M");
            x = 0;
            return;
        }
        if (y == MAX_V_NUM_GRID - 1)
            return;
        grids[y++][x].setText("");
        grids[y][x].setText("M");
    }

    class Fox {
        SimpleIntegerProperty x;
        int y;
        public Fox(int x, int y) {
            this.x.set(x);
            this.y = y;
        }
        public SimpleIntegerProperty
    }

    private Fox testFox;

    @FXML
    public void initialize() {
        System.out.println("HI");
        System.out.println("size: " + paneArena.getChildren().size());
        testFox = new Fox(0,0);
        ImageView testImg = new ImageView(new Image(getClass().getResourceAsStream("/fox.png")));
        testImg.xProperty().bind(paneArena.widthProperty().subtract(testFox.x).divide(2));
        paneArena.getChildren().add(testImg);
        System.out.println("size: " + paneArena.getChildren().size());

    }

    /**
     * A function that demo how drag and drop works
     */
/*    private void setDragAndDrop() {
        AnchorPane target = paneArena;
        AnchorPane newPane = new AnchorPane();
        ImageView imgview1 = new ImageView(new Image(getClass().getResourceAsStream("/fox.png")));
        imgview1.setX(ARENA_HEIGHT/2);
        imgview1.setY(ARENA_HEIGHT/2);
        ImageView imgview2 = new ImageView(new Image(getClass().getResourceAsStream("/penguin.png")));
        imgview2.setX(0);
        imgview2.setY(0);
        newPane.getChildren().addAll(imgview1, imgview2);
        paneArena.getChildren().add(newPane);
        labelBasicTower.setOnDragDetected(mouseEvent -> {
            Dragboard db = labelBasicTower.startDragAndDrop(TransferMode.ANY);

            ClipboardContent content = new ClipboardContent();
            content.putString(labelBasicTower.getText());
            content.putImage(new Image(getClass().getResourceAsStream("/basicTower.png")));
            db.setContent(content);

            mouseEvent.consume();
        });
        paneArena.setOnDragDropped(dragEvent -> {
            for (Node node : newPane.getChildren())
                if (node instanceof ImageView && ((ImageView) node).getX() == 0)
                    newPane.getChildren().remove(node);
            Dragboard db = dragEvent.getDragboard();
            boolean success = false;
            System.out.println(dragEvent.getX());
            System.out.println("Are they equal? " + (dragEvent.getGestureSource() != labelBasicTower));
            if (db.hasString()) {
                ((Label)dragEvent.getGestureTarget()).setText(db.getString());
                success = true;
            }
            dragEvent.setDropCompleted(success);
            dragEvent.consume();
        }); //Define drop: release your mouse

        //well, you can also write anonymous class or even lambda
        //Anonymous class
        paneArena.setOnDragOver(event -> {
//                 data is dragged over the target
            System.out.println("size of pane: " + paneArena.getChildren().size());
            System.out.println("onDragOver");

*//*                 accept it only if it is  not dragged from the same node
             * and if it has a string data *//*
            if (event.getGestureSource() != target &&
                    event.getDragboard().hasString()) {
*//*                     allow for both copying and moving, whatever user chooses *//*
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }

            event.consume();});

        paneArena.setOnDragEntered(event -> {
*//*                 the drag-and-drop gesture entered the target *//*
            System.out.println("onDragEntered");
*//*                 show to the user that it is an actual gesture target *//*
            if (event.getGestureSource() != target &&
                    event.getDragboard().hasString()) {
                paneArena.setStyle("-fx-border-color: blue;");
            }

            event.consume();
        });
        //lambda
*//*        paneArena.setOnDragExited((event) -> {
*//**//*                 mouse moved away, remove the graphical cues *//**//*
                target.setStyle("-fx-border-color: black;");
                System.out.println("Exit");
                event.consume();
        });*//*
    }*/
}
