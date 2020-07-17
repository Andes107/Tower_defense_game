package Towers;

import MapObject.mapObject;

public class Tower {
    public static final int ARENA_SIZE = 5;

    public int damage = 5;
    public int bump;
    public int radius;
    public int x;
    public int y;
    /*
    * should i put in damage and bump final here? no, because it can be updated afterwards
    * should i give it a value during initialization? no
    * i should only pre-set it in the constructor
    * but who is to decide? i rli don't know
    * */
    public Tower(){} //This is just for my test case for now, should have fixed it afterwards

    public Tower(int x, int y, int damage, int bump, int radius) {
        this.x = x;
        this.y = y;
        this.damage = damage; //Need this when you use a basic tower's constructor!
        this.bump = bump;
    }

    public void markKillZone(int x, int y, int dx, int dy, mapObject[][] map, Tower target) {
        if (y + dy < ARENA_SIZE) {
            if (x + dx < ARENA_SIZE)
                map[x+dx][y+dy].towers.add(target);
            if (x - dx >= 0)
                map[x-dx][y+dy].towers.add(target);
        }
        if (y - dy >= 0) {
            if (x + dx < ARENA_SIZE)
                map[x+dx][y-dy].towers.add(target);
            if (x - dx >= 0)
                map[x-dx][y-dy].towers.add(target);
        }
    }
}
