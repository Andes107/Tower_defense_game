package Monsters;

import MapObject.aNode;
import MapObject.mapObject;
import Towers.Basic;
import Towers.Tower;
import org.junit.Ignore;
import org.junit.Test;

import java.util.PriorityQueue;

import static org.junit.Assert.*;

public class FoxTest {
    private final int ARENA_SIZE = 5;

    @Test
    public void gamble() {
        int[][] print = new int[ARENA_SIZE][ARENA_SIZE];
        mapObject[][] testMap = new mapObject[ARENA_SIZE][ARENA_SIZE];
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j) {
                testMap[i][j] = new mapObject(i, j, null, null);
            }
/*        for (int i = 1; i < ARENA_SIZE; ++i)
             testMap[i][i].towers.add(new Basic(i,i,0,5,testMap));*/
        Fox testMonster = new Fox(0,0);
        testMap[0][0].monster = testMonster;
        testMonster.next = testMonster.nextAlgorithm(testMap, true);
        while (!testMonster.next.isEmpty()) {
            print[testMonster.next.peek().x][testMonster.next.peek().y]++;
            testMonster.next.pop();
        }
/*        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                if (testMap[i][j].towers.size() > 0)
                    print[i][j]++;*/
        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j)
                System.out.print(print[i][j] + " ");
            System.out.println();
        }
    }
}