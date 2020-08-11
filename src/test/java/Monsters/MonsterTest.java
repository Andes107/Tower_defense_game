package Monsters;

import MapObject.*;
import Towers.Basic;
import Towers.*;
import org.apache.commons.lang3.ObjectUtils;
import org.junit.Assume;
import org.junit.*;

import java.util.PriorityQueue;

import static org.junit.Assert.*;

public class MonsterTest {
    @Test
    public void test() {
        /*
        * Purpose for the test
        * 1. The reason behind Zic-zac fox route Answer: Unclear, but the equal behavior is bizarre
        * 2. Is fox walking least damage route with presence of tower? Yes
        * 3. Just another case, multiple fox? Yes
        * */
        int ARENA_SIZE = 10;
        aNode.ARENA_SIZE = ARENA_SIZE;
        Monster.ARENA_SIZE = ARENA_SIZE;
        Tower.ARENA_SIZE = ARENA_SIZE;
        mapObject[][] map = new mapObject[ARENA_SIZE][ARENA_SIZE];
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                map[i][j] = new mapObject(i,j,null,null);

        /*Fox testFox = new Fox(0, ARENA_SIZE/2, 1, 10);
        map[0][ARENA_SIZE/2].monster = testFox;
        Fox testFox1 = new Fox(ARENA_SIZE/2, 0, 1, 10);
        map[ARENA_SIZE/2][0].monster = testFox1;


        map[1][2].tower = new Basic(1, 2, 1, 1, map);
        map[2][ARENA_SIZE - 1].tower = new Basic(2, ARENA_SIZE - 1, 1, 1, map);
        map[ARENA_SIZE - 1][1].tower = new Basic(ARENA_SIZE - 1, 1, 1, 1, map);


        testFox.next = testFox.nextAlgorithm(map, testFox instanceof Fox);
        int grid[][] = new int[ARENA_SIZE][ARENA_SIZE];
        if (testFox.next != null)
            for (aNode node : testFox.next)
                grid[node.x][node.y]++;
        grid[testFox.x][testFox.y] += 2;
        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j)
                System.out.print(grid[i][j] + " ");
            System.out.println();
        }*/
        map[ARENA_SIZE/2][ARENA_SIZE/2].tower = new Catapult(ARENA_SIZE/2, ARENA_SIZE/2, 1, 2, 4, 1, map);
        map[2][4].monster = new Fox(2,4,1,10);
        map[3][3].monster = new Fox(3,3,1,10);
        map[5][7].monster = new Penguin(5,7,1,10);
        map[7][7].monster = new Fox(7,7,1,10);

        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                if (map[i][j].tower != null)
                    map[i][j].tower.inflictDamage(map);
        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j)
                if (map[i][j].monster != null)
                    System.out.print(map[i][j].monster.health + " ");
                else
                    System.out.print(0 + " ");
            System.out.println();
        }

        System.out.println();
        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j)
                System.out.print(map[i][j].towers.size()+ " ");
            System.out.println();
        }
    }
}
