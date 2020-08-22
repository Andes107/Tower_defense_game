package MapObject;

import Monsters.*;
import Towers.*;

import java.util.*;

public class mapObject{ //seems at the end it will be bottom to top
    public static int ARENA_SIZE;
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

    /*Copy constructor*/
    public mapObject(mapObject copyMapObject) {
        this.x = copyMapObject.x;
        this.y = copyMapObject.y;
        this.monster = copyMapObject.monster;
        this.tower = copyMapObject.tower;
        this.towers = new ArrayList<Tower>(copyMapObject.towers);
    }

    public static mapObject[][] initializeMap() {
        mapObject[][] map = new mapObject[ARENA_SIZE] [ARENA_SIZE];
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                map[i][j] = new mapObject(i, j, null, null);
        return map;
    }

    public static Vector<mapObject> initializeMapWithoutMonster(mapObject[][] map) {
        Vector<mapObject> mapWithoutMonster = new Vector<mapObject>();
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                if (!(i == ARENA_SIZE - 1 && j == ARENA_SIZE - 1))
                    mapWithoutMonster.add(map[i][j]);
        return mapWithoutMonster;
    }
}
