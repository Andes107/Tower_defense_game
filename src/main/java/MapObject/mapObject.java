package MapObject;

import Monsters.*;
import Towers.*;

import java.util.ArrayList;
import java.util.List;

public class mapObject{ //seems at the end it will be bottom to top
    public static final int ARENA_SIZE = 5;
    public int x;
    public int y;
    public Monster monster; //There will only be 1 monster
    public Tower tower; //There will only be 1 tower
    public List<Tower> towers;

    public mapObject() {}
    public mapObject(int x, int y, Monster monster, Tower tower){
        this.x = x;
        this.y = y;
        this.monster = monster;
        this.tower = tower;
        this.towers = new ArrayList<Tower>();
    }

}
