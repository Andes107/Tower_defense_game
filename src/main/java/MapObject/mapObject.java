package MapObject;

import Monsters.*;
import Towers.*;

import java.util.ArrayList;
import java.util.List;

public class mapObject extends Object{ //seems at the end it will be bottom to top
    public static final int ARENA_SIZE = 5;
    public int x;
    public int y;
    public Monster monster; //There will only be 1 monster
    public Tower tower; //There will only be 1 tower

    public mapObject prev; //for algorithm
    public mapObject next; //for algorithm

    public mapObject() {}
    public mapObject(int x, int y, Monster monster, Tower tower, mapObject prev, mapObject next){
        this.x = x;
        this.y = y;
        this.monster = monster;
        this.tower = tower;
        this.prev = prev;
        this.next = next;
    }

}
