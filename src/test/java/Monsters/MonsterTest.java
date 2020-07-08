package Monsters;

import MapObject.*;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assume;
import org.junit.*;

import java.util.PriorityQueue;

import static org.junit.Assert.*;

public class MonsterTest {
    private static final int ARENA_SIZE = 480;
    private static mapObject[][] testMap;
    private static aNode[][] aNodeTestMap;
    private static Monster testMonster;

    @BeforeClass
    public static void setUp() {
        testMap = new mapObject[ARENA_SIZE][ARENA_SIZE];
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j) {
                testMap[i][j] = new mapObject(i,j,null);
            }
        testMonster = new Monster();
        aNodeTestMap = testMonster.newANodeMap(testMap);
        aNodeTestMap[4][4].monster = testMonster;
    }

    @Ignore
    @Test
    public void checkTestMap() {
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j){
                assertEquals(i,testMap[i][j].x);
                assertEquals(j,testMap[i][j].y);
            }
    }

    @Ignore
    @Test
    public void checknewANodeMap() { //check function
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j){
                assertEquals(i,aNodeTestMap[i][j].x);
                assertEquals(j,aNodeTestMap[i][j].y);
                assertEquals(null, aNodeTestMap[i][j].next);
                assertEquals((int)Math.floor(Math.sqrt(i*i + j*j)),(int)Math.floor(aNodeTestMap[i][j].h));
                assertEquals(ARENA_SIZE, (int)aNodeTestMap[i][j].g);
            }
        assertNotNull(aNodeTestMap[4][4].monster);
    }

    @Ignore
    @Test
    public void checkFindNeighbour() { //check function
        aNode[] neighbours = testMonster.findNeighbour(3,4,aNodeTestMap);
        assertNull(neighbours[3]);
//        Assume.assumeTrue(aNodeTestMap[3][4].monster != null); if the condition is true, then test case runs, otherwise ignore
    }

    @Ignore
    @Test(expected = Test.None.class)
    public void checkProcessNeighbour() { //check function
        PriorityQueue<aNode> pq = testMonster.newPriorityMap(aNodeTestMap[0][0]);
        assertEquals(0,(int)aNodeTestMap[0][0].g);
        testMonster.processNeighbour(aNodeTestMap[0][0],aNodeTestMap[1][0],pq);
        assertNotNull(aNodeTestMap[1][0].prev);
    }

//    @Ignore
    @Test
    public void testAlgorithm() { //check function
        testMonster.nextAlgorithm(testMap);
        assertNotNull(testMonster.next);
    }
}