package MapObject;

import Monsters.*;
import Towers.*;

import java.util.ArrayList;
import java.util.List;

public class mapObject { //seems at the end it will be bottom to top
    public static final int ARENA_SIZE = 480;
    public int x;
    public int y;
    public Monster monster; //There will only be 1 monster
    public Tower tower; //There will only be 1 tower
    public List<Tower> towerList = new ArrayList<Tower>(); //A list of towers that can influence this region

    public mapObject prev; //for algorithm
    public mapObject next; //for algorithm

    public mapObject() {}
    public mapObject(int x, int y, Monster monster){
        this.x = x;
        this.y = y;
        this.monster = monster;
    }
}
