package Monsters;

import MapObject.*;
import Towers.Tower;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assume;
import org.junit.*;

import java.util.PriorityQueue;

import static org.junit.Assert.*;

public class MonsterTest {
    private final int ARENA_SIZE = 5;
    private Monster testMonster;
    private mapObject[][] testMap;
    private aNode[][] aNodeTestMap;
    private PriorityQueue<aNode> pq;

    public void mapMonsterSetUp() {
        testMap = new mapObject[ARENA_SIZE][ARENA_SIZE];
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j) {
                testMap[i][j] = new mapObject(i, j, null, null);
            }
        testMonster = new Monster(0,0,null);
        testMap[0][0].monster = testMonster;
        testMap[ARENA_SIZE-1][ARENA_SIZE-1].tower = new Tower();
    }

    @Ignore
    @Test
    public void testNewANodeMap() { //check the function for producing anodes, also initialize the anodetestmap
        mapMonsterSetUp();
        aNodeTestMap = testMonster.newANodeMap(testMap);
        for (int i = 0; i < ARENA_SIZE ; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j){
                assertEquals(i, aNodeTestMap[i][j].x);
                assertEquals(j, aNodeTestMap[i][j].y);
//                assertNull(aNodeTestMap[i][j].tower);
                assertEquals((ARENA_SIZE-1-i)*(ARENA_SIZE-1-i) + (ARENA_SIZE-1-j)*(ARENA_SIZE-1-j), aNodeTestMap[i][j].h);
                assertEquals(ARENA_SIZE * ARENA_SIZE, aNodeTestMap[i][j].g);
            }
    }

    @Ignore
    @Test
    public void testNewPriorityMap() { //check initialization of priority, also initialize the priority
        mapMonsterSetUp();
        aNodeTestMap = testMonster.newANodeMap(testMap);
        pq = testMonster.newPriorityMap(aNodeTestMap[testMonster.x][testMonster.y]);
        assertEquals(1,pq.size());
        aNode node = pq.poll();
        assertEquals(testMonster.x,node.x);
        assertEquals(testMonster.y,node.y);
        assertEquals((ARENA_SIZE-testMonster.x)*(ARENA_SIZE-testMonster.x) + (ARENA_SIZE-testMonster.y)*(ARENA_SIZE-testMonster.y),node.h);
        assertEquals(0, node.g);
        assertEquals((ARENA_SIZE-testMonster.x)*(ARENA_SIZE-testMonster.x) + (ARENA_SIZE-testMonster.y)*(ARENA_SIZE-testMonster.y), node.f);
    }

    @Ignore
    @Test
    public void testEndPointReached() {
        mapMonsterSetUp();
        assertEquals(false, testMonster.endPointReached(testMonster.x,testMonster.y));
        assertEquals(true,testMonster.endPointReached(ARENA_SIZE- 1,ARENA_SIZE -1));
    }

    @Ignore
    @Test
    public void testFindNeighbours() {
        testNewANodeMap();
        aNode[] neighbours = testMonster.findNeighbour(ARENA_SIZE-1,ARENA_SIZE-1,aNodeTestMap);
        assertNotNull(neighbours[0]);
        assertNotNull(neighbours[1]);
        for (int i = 2; i < 4; ++i)
            assertNull(neighbours[i]);
    }

    @Test
    public void testNextAlgorithm() {
        testNewANodeMap();
        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j) {
                System.out.print(aNodeTestMap[i][j].h + " ");
            }
            System.out.println();
        }
        testMonster.nextAlgorithm(testMap);
        assertNull(testMonster.next);
        aNode current = testMonster.next;
        if (current == null)
            System.out.println("Well, you can't reach it");
        while (current != null) {
            System.out.print("(" + current.x + ", " + current.y + ") ");
            current = current.next;
        }
    }
}