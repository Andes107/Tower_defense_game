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

    @Ignore
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

    @Ignore
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

//    @Ignore
    @Test
    public void rightBasicConstructor() {
        int r1 = 5;
        mapObject[][] map = new mapObject[ARENA_SIZE][ARENA_SIZE]; //Purpose: check
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                map[i][j] = new mapObject(i, j, null, null);
        Tower center = new Basic(ARENA_SIZE / 2, ARENA_SIZE / 2, r1, map);
/*        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j)
                System.out.print(map[i][j].towers.size() + " ");
            System.out.println();
        }*/
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

    @Test
    public void rightBasicInflictDamage() {
        int r1 = 5;
        int health = 15;
        mapObject[][] map = new mapObject[ARENA_SIZE][ARENA_SIZE]; //Purpose: check
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                map[i][j] = new mapObject(i, j, new Monster(i,j,null, health), null);
        Tower center = new Basic(ARENA_SIZE/2, ARENA_SIZE/2, r1, map);
        center.inflictDamage(map);
        Tower topLeft = new Basic(0,0,r1,map);
        topLeft.inflictDamage(map);

        mapObject[][] oneMonsterMap = new mapObject[ARENA_SIZE][ARENA_SIZE];


        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                oneMonsterMap[i][j] = new mapObject(i, j, null, null);
        oneMonsterMap[0][0].monster = new Monster(0, 0, null, health);
        Tower centerForOneMonster = new Basic(ARENA_SIZE/2, ARENA_SIZE/2, r1, oneMonsterMap);

        centerForOneMonster.inflictDamage(oneMonsterMap);
/*        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j) {
                if ((ARENA_SIZE/2 - i) * (ARENA_SIZE/2 - i) + (ARENA_SIZE/2 - j) * (ARENA_SIZE/2 - j) <= r1 *r1 && oneMonsterMap[i][j].monster != null)
                    System.out.print(oneMonsterMap[i][j].monster.health + ",1 " );
                else
                    System.out.print("=====");
            }
            System.out.println();
        }*/

    }

    @Test
    public void wrongBasicInflictDamage() {
        mapObject[][] badMap = new mapObject[ARENA_SIZE][ARENA_SIZE];
        mapObject[][] goodMap = new mapObject[ARENA_SIZE][ARENA_SIZE];
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                goodMap[i][j] = new mapObject(i, j, null, null);
        int r1 = 5;
        Tower random = new Basic(ARENA_SIZE/2, ARENA_SIZE/2, r1, goodMap);
        random.inflictDamage(badMap);
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
/*        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j)
                System.out.print(map[i][j].towers.size() + " ");
            System.out.println();
        }*/
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
        /*incorrect input now*/
        Tower negative = new Ice(-1,-1, r1, map);
        Tower nullMap = new Ice(ARENA_SIZE/2, ARENA_SIZE/2, r1, null);
        Tower negativeR1 = new Ice(ARENA_SIZE/2, ARENA_SIZE/2, -1, map);
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
/*        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j)
                System.out.print(map[i][j].towers.size() + " ");
            System.out.println();
        }*/

    }

    @Test(expected = Exception.class)
    public void wrongCatapultConstructor() {
        int r1 = 5, r2= 10;
        mapObject[][] map = new mapObject[ARENA_SIZE][ARENA_SIZE]; //Purpose: check
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                map[i][j] = new mapObject(i, j, null, null);
        /*incorrect input now*/
        Tower negative = new Catapult(-1,-1,r1, r2, map);
        Tower nullMap = new Catapult(ARENA_SIZE/2, ARENA_SIZE/2, ARENA_SIZE/2, ARENA_SIZE/2+1, null);
        Tower wrongRadius = new Catapult(ARENA_SIZE/2, ARENA_SIZE/2, -1, Integer.MAX_VALUE, map);
    }

}