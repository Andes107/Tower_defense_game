package Monsters;

import MapObject.aNode;
import MapObject.mapObject;
import Towers.Tower;
import org.junit.Ignore;
import org.junit.Test;

import java.util.PriorityQueue;

import static org.junit.Assert.*;

public class FoxTest {
    private final int ARENA_SIZE = 5;
    private Fox testMonster;
    private mapObject[][] testMap;
    private boolean isFox;
    private aNode[][] aNodeTestMap;
    private PriorityQueue<aNode> pq;

    public void mapMonsterSetUp() {
        testMap = new mapObject[ARENA_SIZE][ARENA_SIZE];
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j) {
                testMap[i][j] = new mapObject(i, j, null, null);
            }
        testMonster = new Fox(0,0,null);
        testMap[0][0].monster = testMonster;
        isFox = true;
        testMap[1][1].towers.add(new Tower());
        testMap[2][2].towers.add(new Tower());
        testMap[3][3].towers.add(new Tower());
        testMap[1][2].towers.add(new Tower());
        testMap[1][3].towers.add(new Tower());
        testMap[2][3].towers.add(new Tower());
        testMap[2][1].towers.add(new Tower());
        testMap[3][1].towers.add(new Tower());
        testMap[3][2].towers.add(new Tower());
        testMap[4][0].towers.add(new Tower());

    }

    @Ignore
    @Test
    public void testNewANodeMap() { //check the function for producing anodes, also initialize the anodetestmap
        mapMonsterSetUp();
        System.out.println("testMonster != null: " + (testMonster != null) + " testMonster instanceof Fox: " + (testMonster instanceof  Fox));
        aNodeTestMap = testMonster.newANodeMap(testMap, isFox);
        for (int i = 0; i < ARENA_SIZE ; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j){
                assertEquals(i, aNodeTestMap[i][j].x);
                assertEquals(j, aNodeTestMap[i][j].y);
//                assertEquals(0, aNodeTestMap[i][j].h); //h is the estimated maximal damage
                assertEquals(0, aNodeTestMap[i][j].g); //g will not be used lmao
                assertEquals(Integer.MAX_VALUE, aNodeTestMap[i][j].f); //f will be the actual damage sucked up from starting point
            }
    }

    @Ignore
    @Test
    public void testNewPriorityMap() { //check initialization of priority, also initialize the priority
        testNewANodeMap();
        pq = testMonster.newPriorityMap(aNodeTestMap[testMonster.x][testMonster.y], isFox);
        assertEquals(1,pq.size());
        aNode node = pq.poll();
        assertEquals(testMonster.x,node.x);
        assertEquals(testMonster.y,node.y);
    }

//    @Ignore
    @Test
    public void testNextAlgorithm() {
        testNewANodeMap();
        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j) {
                int damage = 0;
                for (Tower tower : testMap[i][j].towers)
                    damage += tower.damage;
                System.out.print(damage + " ");
            }
            System.out.println();
        }
        testMonster.anotherNextAlgorithm(testMap);
        assertNotNull(testMonster.next);
        aNode current = testMonster.next;
        if (current == null)
            System.out.println("Well, you can't reach it");
        while (current != null) {
            System.out.print("(" + current.x + ", " + current.y + ") ");
            current = current.next;
        }
    }
}