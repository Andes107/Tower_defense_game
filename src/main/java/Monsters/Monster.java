package Monsters;

import MapObject.*;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.List;
import java.util.PriorityQueue;
import java.util.Random;
import java.util.Stack;

public class Monster {
    public static int ARENA_SIZE;
//    public static int timestamp = 0; //A technique against priority queue equal weight no FIFO

    public int x; //current position
    public int y; //current position
    public SimpleIntegerProperty simpleX;
    public SimpleIntegerProperty simpleY;
    public int maxCounter; //the monster will move after speed amount of game loops, adjust per difficulty
    public int counter; //counter means how many game loops left until next move
    public int health; //may reduce per game loop, may be negative, adjust per difficulty

    public Stack<aNode> next; //this is a linked list where next stores next step's information plus what's next
    public Stack<aNode> newNext;

    public Monster(int x, int y, int maxCounter, int health) {
        if (x < -1 || x >= ARENA_SIZE || y < -1 || y >= ARENA_SIZE || maxCounter <= 0 || health <= 0)
            throw new IllegalArgumentException();
        this.x = x;
        this.y = y;
        this.simpleX = new SimpleIntegerProperty(this.x);
        this.simpleY = new SimpleIntegerProperty(this.y);
        System.out.println("this.simplY: " + simpleY.get());
        this.next = null;
        this.newNext = null;
        this.maxCounter = maxCounter;
        this.counter = this.maxCounter;
        this.health = health;
    }

    public static Monster monsterNewRanGen(List<mapObject> mapWithoutMonster, mapObject[][] map, int monsterNewHealth, int monsterNewCounter, int monsterNewHealthScalar, int monsterNewCounterScalar) {
        /*
        * Objectives:
        * 1. Remove the mapObject occupied with monster
        * 2. Fill in the monster in the appropriate mapObject
        * 3. How to know the monster though? Return one monster then!
        * 4. Find next for monster with current map
        * Penguin: less health, less speed
        * Unicorn: More health, less speed
        * Fox: Less health, less speed
        * */
        int monsterNewType = new Random().nextInt(3);
        int monsterNewRandom = new Random().nextInt(mapWithoutMonster.size());
        mapObject monsterNewTempObj = mapWithoutMonster.remove(monsterNewRandom);
        System.out.println("monsternewtempobj.y: " + monsterNewTempObj.y);
        monsterNewTempObj.monster = (monsterNewType == 0? new Penguin(monsterNewTempObj.x, monsterNewTempObj.y, monsterNewCounter, monsterNewHealth): (monsterNewType == 1? new Unicorn(monsterNewTempObj.x, monsterNewTempObj.y, monsterNewCounter, monsterNewHealth * monsterNewHealthScalar): new Fox(monsterNewTempObj.x, monsterNewTempObj.y, monsterNewCounter / monsterNewCounterScalar, monsterNewHealth)));
        monsterNewTempObj.monster.nextAlgorithm(map, monsterNewTempObj.monster instanceof Fox);
        System.out.println("monsternewtempobj.monster.y: " + monsterNewTempObj.monster.y);
        return monsterNewTempObj.monster;
    }

    public Stack<aNode> nextAlgorithm(mapObject[][] map, boolean isFox) {
        if (map == null)
            throw new IllegalArgumentException();
        aNode[][] aNodeSet = new aNode[ARENA_SIZE][ARENA_SIZE];
        for (int i = 0; i < ARENA_SIZE; ++i) {
            for (int j = 0; j < ARENA_SIZE; ++j) {
                aNodeSet[i][j] = new aNode(map[i][j], isFox);
            }
        }
        PriorityQueue<aNode> pq = new PriorityQueue<aNode>();
        pq.add(initializeStart(aNodeSet[this.x][this.y]));

        while (pq.isEmpty() == false && !(pq.peek().x == ARENA_SIZE - 1 && pq.peek().y == ARENA_SIZE - 1)) {
            aNode curr = pq.poll();
            for (aNode neighbour : findNeighbours(curr, aNodeSet))
                processNeighbour(curr, neighbour, pq);
        }
        return createStack(aNodeSet[ARENA_SIZE - 1][ARENA_SIZE - 1], this.x, this.y);
    }

    public aNode initializeStart(aNode start) {
        start.fromStart = 0;
        start.totalDistance = start.fromStart + start.minToEnd;
        return start;
    }

    public aNode[] findNeighbours(aNode curr, aNode[][] aNodeSet) {
        aNode[] neighbours = new aNode[4];
        int index = 0;
        if (curr.x + 1 < ARENA_SIZE && aNodeSet[curr.x+1][curr.y].monster == null && aNodeSet[curr.x+1][curr.y].tower == null)
            neighbours[index++] = aNodeSet[curr.x+1][curr.y];
        if (curr.x - 1 >= 0 && aNodeSet[curr.x-1][curr.y].monster == null && aNodeSet[curr.x-1][curr.y].tower == null)
            neighbours[index++] = aNodeSet[curr.x-1][curr.y];
        if (curr.y + 1 < ARENA_SIZE && aNodeSet[curr.x][curr.y+1].monster == null && aNodeSet[curr.x][curr.y+1].tower == null)
            neighbours[index++] = aNodeSet[curr.x][curr.y+1];
        if (curr.y - 1 >= 0  && aNodeSet[curr.x][curr.y-1].monster == null && aNodeSet[curr.x][curr.y-1].tower == null)
            neighbours[index++] = aNodeSet[curr.x][curr.y-1];
        return neighbours;
    }

    public void processNeighbour(aNode curr, aNode neighbour, PriorityQueue<aNode> pq) {
        if (curr == null || neighbour == null || pq == null)
            return;
        if (curr.fromStart + neighbour.edgeCost < neighbour.fromStart) {
            pq.remove(neighbour);
            neighbour.fromStart = curr.fromStart + neighbour.edgeCost;
            neighbour.totalDistance = neighbour.fromStart + neighbour.minToEnd;// + (++Monster.timestamp); //for Dijkstra, mintoend always 0; for A*, intended
            neighbour.prev = curr;
            pq.add(neighbour);
        }
    }

    public Stack<aNode> createStack(aNode curr, int monsterX, int monsterY) {
        if (curr.prev == null)
            return null;
        Stack<aNode> next = new Stack<aNode>();
        while (!(curr.x == monsterX && curr.y == monsterY)) {
            next.push(curr);
            curr = curr.prev;
        }
        return next;
    }

    public SimpleIntegerProperty simpleXProperty() {
        return simpleX;
    }

    public SimpleIntegerProperty simpleYProperty() {
        return simpleY;
    }

}