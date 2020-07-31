package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.Initializable;
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

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class gameController {

    @FXML
    private AnchorPane leftAnchorPane;

    @FXML
    private AnchorPane rightAnchorPane;

    @FXML
    private VBox centerVBox;

    @FXML
    private VBox topVBox;

    @FXML
    private VBox leftVBox;

    @FXML
    private VBox bottomVBox;

    @FXML
    private Button Test;

    @FXML
    void actionForClicked(ActionEvent event) {
        System.out.println("HI");
    }

}


/*public class MyController {
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

    private mapObject[][] map = new mapObject[ARENA_HEIGHT][ARENA_WIDTH];

    private static final int ARENA_WIDTH = 480;
    private static final int ARENA_HEIGHT = 480;
    private static final int GRID_WIDTH = 40;
    private static final int GRID_HEIGHT = 40;
    private static final int MAX_H_NUM_GRID = 12;
    private static final int MAX_V_NUM_GRID = 12;

    private Label grids[][] = new Label[MAX_V_NUM_GRID][MAX_H_NUM_GRID]; //an array of label! god damn it!
    private int x = -1, y = 0; //where is my monster
    private newFox subject1;

    *//**
     * A dummy function to show how button click works
     *//*
    @FXML
    private void play() {
        subject1 = new newFox();
        ImageView imageView = new ImageView(new Image(getClass().getResourceAsStream("/fox.png")));
        imageView.setFitHeight(20);
        imageView.setFitWidth(20);
        subject1.setX(new Random().nextInt(ARENA_HEIGHT));
        imageView.xProperty().bind(subject1.xProperty());
        imageView.yProperty().bind(subject1.xProperty());
        paneArena.getChildren().add(imageView);

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(5), ev -> {
            subject1.setX(new Random().nextInt(ARENA_HEIGHT));
        }));
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    class newFox {
        private IntegerProperty x = new SimpleIntegerProperty();

        public IntegerProperty xProperty() {
            return x;
        }

        public void setX(int x) {
            this.x.set(x);
        }
    }


    *//**
     * A function that create the Arena
     *//*
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
            }
        setDragAndDrop();
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

    *//**
     * A function that demo how drag and drop works
     *//*
    private void setDragAndDrop() {
        Label target = grids[3][3];
        target.setText("Drop\nHere");
        Label source1 = labelBasicTower;
        Label source2 = labelIceTower;
        source1.setOnDragDetected(new DragEventHandler(source1));
        source2.setOnDragDetected(new DragEventHandler(source2));

        target.setOnDragDropped(new DragDroppedEventHandler());

        //well, you can also write anonymous class or even lambda
        //Anonymous class
        target.setOnDragOver(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                *//* data is dragged over the target *//*
                System.out.println("onDragOver");

                *//* accept it only if it is  not dragged from the same node
                 * and if it has a string data *//*
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasString()) {
                    *//* allow for both copying and moving, whatever user chooses *//*
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }

                event.consume();
            }
        });

        target.setOnDragEntered(new EventHandler<DragEvent>() {
            public void handle(DragEvent event) {
                *//* the drag-and-drop gesture entered the target *//*
                System.out.println("onDragEntered");
                *//* show to the user that it is an actual gesture target *//*
                if (event.getGestureSource() != target &&
                        event.getDragboard().hasString()) {
                    target.setStyle("-fx-border-color: blue;");
                }

                event.consume();
            }
        });
        //lambda
        target.setOnDragExited((event) -> {
            *//* mouse moved away, remove the graphical cues *//*
            target.setStyle("-fx-border-color: black;");
            System.out.println("Exit");
            event.consume();
        });
    }
}

class DragEventHandler implements EventHandler<MouseEvent> {
    private Label source;
    public DragEventHandler(Label e) {
        source = e;
    }
    @Override
    public void handle (MouseEvent event) {
        Dragboard db = source.startDragAndDrop(TransferMode.ANY);

        ClipboardContent content = new ClipboardContent();
        content.putString(source.getText());
        db.setContent(content);

        event.consume();
    }
}

class DragDroppedEventHandler implements EventHandler<DragEvent> {
    @Override
    public void handle(DragEvent event) {
        System.out.println("xx");
        Dragboard db = event.getDragboard();
        boolean success = false;
        System.out.println(db.getString());
        if (db.hasString()) {
            ((Label)event.getGestureTarget()).setText(db.getString());
            success = true;
        }
        event.setDropCompleted(success);
        event.consume();

    }
}*/
