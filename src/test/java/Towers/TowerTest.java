package Towers;

import MapObject.*;

import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

public class TowerTest {
    /*
     * This test will start with all chord tests first
     * */
    private static final int ARENA_SIZE = Tower.ARENA_SIZE;

    @Test(expected = Exception.class)
    public void testMarkChordKillZone() {
        mapObject[][] map = new mapObject[ARENA_SIZE][ARENA_SIZE]; //Purpose: check
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                map[i][j] = new mapObject(i,j,null,null);
        Tower test = new Tower(); //Purpose: summon a method Consequence: made tower not abstract
        int denumerator = 10;
        test.markChordKillZone(ARENA_SIZE/2, ARENA_SIZE/2, ARENA_SIZE/denumerator, ARENA_SIZE/denumerator, map, test);
        /*for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j)
                System.out.print(map[i][j].towers.size() + " ");
            System.out.println();
        }*/
        test.markChordKillZone(0,0,ARENA_SIZE/denumerator,ARENA_SIZE/denumerator, map, test);
        test.markChordKillZone(0,ARENA_SIZE-1, ARENA_SIZE/denumerator, ARENA_SIZE/denumerator,map,test);
        test.markChordKillZone(ARENA_SIZE-1, 0, ARENA_SIZE/denumerator, ARENA_SIZE/denumerator, map, test);
        test.markChordKillZone(ARENA_SIZE-1,ARENA_SIZE-1, ARENA_SIZE/denumerator, ARENA_SIZE/denumerator,map,test);
        //These are all the valid test cases, so they don't throw exceptions, let's try some dirty
        test.markChordKillZone(-1,-1,ARENA_SIZE/denumerator, ARENA_SIZE/denumerator, map,test);
        test.markChordKillZone(ARENA_SIZE,ARENA_SIZE,ARENA_SIZE/denumerator,ARENA_SIZE/denumerator, map, test);
        test.markChordKillZone(ARENA_SIZE/2, ARENA_SIZE/2, ARENA_SIZE,ARENA_SIZE,map,test);
        test.markChordKillZone(Integer.MAX_VALUE, ARENA_SIZE/denumerator, ARENA_SIZE/denumerator,ARENA_SIZE/denumerator,map,test);
        test.markChordKillZone(ARENA_SIZE/2, ARENA_SIZE/2, Integer.MAX_VALUE, ARENA_SIZE/denumerator, map,test);
        test.markChordKillZone(ARENA_SIZE/denumerator, Integer.MAX_VALUE, ARENA_SIZE/denumerator,ARENA_SIZE/denumerator,map,test);
        test.markChordKillZone(ARENA_SIZE/2, ARENA_SIZE/2, ARENA_SIZE/denumerator,ARENA_SIZE/denumerator, null, test);
        //ok, so no bugs for incorrect input
    }

    @Test(expected = Exception.class)
    public void testMarkRadiusKillZone() {
        //Initialize map object
        mapObject[][] map = new mapObject[ARENA_SIZE][ARENA_SIZE]; //Purpose: check
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                map[i][j] = new mapObject(i,j,null,null);
        Tower test = new Tower(); //Purpose: summon a method Consequence: made tower not abstract
        int denumerator = 6;
        test.markRadiusKillZone(ARENA_SIZE/2, ARENA_SIZE/2,  ARENA_SIZE/denumerator, map, test);
/*
        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j)
                System.out.print(map[i][j].towers.size() + " ");
            System.out.println();
        }
*/
        test.markRadiusKillZone(0,0,ARENA_SIZE/denumerator, map, test);
        test.markRadiusKillZone(0,ARENA_SIZE-1, ARENA_SIZE/denumerator,map,test);
        test.markRadiusKillZone(ARENA_SIZE-1, 0,  ARENA_SIZE/denumerator, map, test);
        test.markRadiusKillZone(ARENA_SIZE-1,ARENA_SIZE-1, ARENA_SIZE/denumerator,map,test);
        //These are all the valid test cases, so they don't throw exceptions, let's try some dirty
        test.markRadiusKillZone(-1,-1, ARENA_SIZE/denumerator, map,test);
        test.markRadiusKillZone(ARENA_SIZE,ARENA_SIZE,ARENA_SIZE/denumerator, map, test);
        test.markRadiusKillZone(ARENA_SIZE/2, ARENA_SIZE/2, ARENA_SIZE,map,test);
        test.markRadiusKillZone(Integer.MAX_VALUE, ARENA_SIZE/2, ARENA_SIZE/10, map, test);
        test.markRadiusKillZone(ARENA_SIZE/2, ARENA_SIZE/2, Integer.MAX_VALUE, map,test);
        test.markRadiusKillZone(ARENA_SIZE/2, ARENA_SIZE/2, ARENA_SIZE/denumerator,null,null);
        //ok, so no bugs for incorrect input
    }

    @Test(expected = Exception.class)
    public void testBasicConstructor() {
        mapObject[][] map = new mapObject[ARENA_SIZE][ARENA_SIZE]; //Purpose: check
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                map[i][j] = new mapObject(i, j, null, null);
        Tower center = new Basic(ARENA_SIZE / 2, ARENA_SIZE / 2, map);
/*
        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j)
                System.out.print(map[i][j].towers.size() + " ");
        System.out.println();
        }
 */
        Tower topLeft = new Basic(0, 0, map);
        Tower topRight = new Basic(0, ARENA_SIZE - 1, map);
        Tower bottomLeft = new Basic(ARENA_SIZE - 1, 0, map);
        Tower bottomRight = new Basic(ARENA_SIZE - 1, ARENA_SIZE-1,map);
        /*incorrect input now*/
        Tower negative = new Basic(-1,-1,map);
        Tower nullMap = new Basic(ARENA_SIZE/2, ARENA_SIZE/2, null);
    }

    @Test(expected = Exception.class)
    public void testIceConstructor() {
        mapObject[][] map = new mapObject[ARENA_SIZE][ARENA_SIZE]; //Purpose: check
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                map[i][j] = new mapObject(i, j, null, null);
        Tower center = new Ice(ARENA_SIZE / 2, ARENA_SIZE / 2, map);

        Tower topLeft = new Ice(0, 0, map);

        Tower topRight = new Ice(0, ARENA_SIZE - 1, map);
        Tower bottomLeft = new Ice(ARENA_SIZE - 1, 0, map);
        Tower bottomRight = new Ice(ARENA_SIZE - 1, ARENA_SIZE-1,map);
/*
        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j)
                System.out.print(map[i][j].towers.size() + " ");
            System.out.println();
        }
*/
        /*incorrect input now*/
        Tower negative = new Ice(-1,-1,map);
        Tower nullMap = new Ice(ARENA_SIZE/2, ARENA_SIZE/2, null);
    }

    @Test(expected = Exception.class)
    public void testCatapultConstructor() {
        mapObject[][] map = new mapObject[ARENA_SIZE][ARENA_SIZE]; //Purpose: check
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                map[i][j] = new mapObject(i, j, null, null);
        Tower center = new Catapult(ARENA_SIZE / 2, ARENA_SIZE / 2, map);

        Tower topLeft = new Catapult(0, 0, map);
        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j)
                System.out.print(map[i][j].towers.size() + " ");
            System.out.println();
        }
        Tower topRight = new Catapult(0, ARENA_SIZE - 1, map);
        Tower bottomLeft = new Catapult(ARENA_SIZE - 1, 0, map);
        Tower bottomRight = new Catapult(ARENA_SIZE - 1, ARENA_SIZE-1,map);

        /*incorrect input now*/
        Tower negative = new Catapult(-1,-1,map);
        Tower nullMap = new Catapult(ARENA_SIZE/2, ARENA_SIZE/2, null);
    }
}