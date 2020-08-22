package Towers;

import MapObject.*;

import Monsters.Monster;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class TowerTest {
    /*
     * This test will start with all chord tests first
     * */
    private static final int ARENA_SIZE = Tower.ARENA_SIZE;
/*
//    @Ignore
    @Test
    public void rightBasicConstructor() {
        int r1 = 5;
        mapObject[][] map = new mapObject[ARENA_SIZE][ARENA_SIZE]; //Purpose: check
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                map[i][j] = new mapObject(i, j, null, null);
        Tower center = new Basic(ARENA_SIZE / 2, ARENA_SIZE / 2, r1, map);
*//*        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j)
                System.out.print(map[i][j].towers.size() + " ");
            System.out.println();
        }*//*
        Tower topLeft = new Basic(0, 0, r1, map);

        Tower topRight = new Basic(0, ARENA_SIZE - 1,r1,  map);

        Tower bottomLeft = new Basic(ARENA_SIZE - 1, 0, r1, map);
        Tower bottomRight = new Basic(ARENA_SIZE - 1, ARENA_SIZE-1, r1,map);


    }

//    @Ignore
    @Test(expected = Exception.class)
    public void wrongBasicConstructor() {
        int r1 = 5;
        mapObject[][] map = new mapObject[ARENA_SIZE][ARENA_SIZE]; //Purpose: check
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                map[i][j] = new mapObject(i, j, null, null);
        Tower negative = new Basic(-1,-1,r1, map);
        Tower nullMap = new Basic(ARENA_SIZE/2, ARENA_SIZE/2,r1, null);
        Tower negativeR1 = new Basic(ARENA_SIZE/2, ARENA_SIZE/2, -1, map);
    }


//    @Ignore
    @Test
    public void rightIceConstructor() {
        int r1 = 10;
        mapObject[][] map = new mapObject[ARENA_SIZE][ARENA_SIZE]; //Purpose: check
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                map[i][j] = new mapObject(i, j, null, null);
        Tower center = new Ice(ARENA_SIZE / 2, ARENA_SIZE / 2,r1,  map);

        Tower topLeft = new Ice(0, 0,r1,  map);
*//*        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j)
                System.out.print(map[i][j].towers.size() + " ");
            System.out.println();
        }*//*
        Tower topRight = new Ice(0, ARENA_SIZE - 1, r1, map);
        Tower bottomLeft = new Ice(ARENA_SIZE - 1, 0, r1, map);
        Tower bottomRight = new Ice(ARENA_SIZE - 1, ARENA_SIZE-1, r1,map);


    }

    @Test(expected =  Exception.class)
    public void wrongIceConstructor() {
        int r1 = 10;
        mapObject[][] map = new mapObject[ARENA_SIZE][ARENA_SIZE]; //Purpose: check
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                map[i][j] = new mapObject(i, j, null, null);
        *//*incorrect input now*//*
        Tower negative = new Ice(-1,-1, r1, map);
        Tower nullMap = new Ice(ARENA_SIZE/2, ARENA_SIZE/2, r1, null);
        Tower negativeR1 = new Ice(ARENA_SIZE/2, ARENA_SIZE/2, -1, map);
    }

    @Test
    public void rightDeathStarInflictedDamage() {
        mapObject[][] map = new mapObject[ARENA_SIZE][ARENA_SIZE]; //Purpose: check
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                map[i][j] = new mapObject(i, j, new Monster(i,j,null,5), null);
        Tower center = new DeathStar(2, 2,map);
        center.inflictDamage(map);
    }

//@Ignore
    @Test
    public void rightCatapultConstructor() {
        int r1 = 5, r2= 10;
        mapObject[][] map = new mapObject[ARENA_SIZE][ARENA_SIZE]; //Purpose: check
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                map[i][j] = new mapObject(i, j, null, null);
        Tower center = new Catapult(ARENA_SIZE / 2, ARENA_SIZE / 2, r1, r2, map);

        Tower topLeft = new Catapult(0, 0, r1, r2, map);

        Tower topRight = new Catapult(0, ARENA_SIZE - 1,r1, r2,  map);
        Tower bottomLeft = new Catapult(ARENA_SIZE - 1, 0,r1, r2,  map);
        Tower bottomRight = new Catapult(ARENA_SIZE - 1, ARENA_SIZE-1,r1, r2, map);
*//*        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j)
                System.out.print(map[i][j].towers.size() + " ");
            System.out.println();
        }*//*

    }

    @Test(expected = Exception.class)
    public void wrongCatapultConstructor() {
        int r1 = 5, r2= 10;
        mapObject[][] map = new mapObject[ARENA_SIZE][ARENA_SIZE]; //Purpose: check
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                map[i][j] = new mapObject(i, j, null, null);
        *//*incorrect input now*//*
        Tower negative = new Catapult(-1,-1,r1, r2, map);
        Tower nullMap = new Catapult(ARENA_SIZE/2, ARENA_SIZE/2, ARENA_SIZE/2, ARENA_SIZE/2+1, null);
        Tower wrongRadius = new Catapult(ARENA_SIZE/2, ARENA_SIZE/2, -1, Integer.MAX_VALUE, map);
    }

    @Test
    public void rightCatapultInflictedDamage() {
        int r1 = 5, r2= 10;
        mapObject[][] map = new mapObject[ARENA_SIZE][ARENA_SIZE]; //Purpose: check
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                map[i][j] = new mapObject(i, j, null, null);
        map[4][3].monster = new Monster(4,3,null,9);
        Tower center = new Catapult(ARENA_SIZE/2, ARENA_SIZE/2, r1, r2, map);
        center.inflictDamage(map);
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                if (map[i][j].monster != null && map[i][j].monster.health != 9)
                    System.out.print("i " + i + " j " + j);
        System.out.println();
        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j)
                System.out.print(map[i][j].towers.size() + " ");
            System.out.println();
        }
    }*/

}