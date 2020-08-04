package Monsters;

import MapObject.*;
import java.util.PriorityQueue;
import java.util.Stack;

public class Monster {
    public static final int ARENA_SIZE = 5;
//    public static int timestamp = 0; //A technique against priority queue equal weight no FIFO

    public int x; //current position
    public int y; //current position
    public Stack<aNode> next; //this is a linked list where next stores next step's information plus what's next
    public Stack<aNode> newNext;
    public int maxCounter; //the monster will move after speed amount of game loops, adjust per difficulty
    public int counter; //counter means how many game loops left until next move
    public int health; //may reduce per game loop, may be negative, adjust per difficulty

    public Monster(int x, int y, int maxCounter, int health) {
        if (x < 0 || x >= ARENA_SIZE || y < 0 || y >= ARENA_SIZE || maxCounter <= 0 || health <= 0)
            throw new IllegalArgumentException();
        this.x = x;
        this.y = y;
        this.next = null;
        this.newNext = null;
        this.maxCounter = maxCounter;
        this.counter = this.maxCounter;
        this.health = health;
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
            System.out.println("x: " + curr.x + " y: " + curr.y);
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
            System.out.println("Index: " + (index));
            for (aNode neighbour: neighbours)
                if (neighbour != null)
                    System.out.println("x: " + neighbour.x + " y: " + neighbour.y);
        return neighbours;
    }

    public void processNeighbour(aNode curr, aNode neighbour, PriorityQueue<aNode> pq) {
        if (curr == null || neighbour == null || pq == null)
            return;
        if (curr.fromStart + neighbour.edgeCost < neighbour.fromStart) {
            System.out.println("curr.x: " + curr.x + " curr.y: " + curr.y + " neighbour.x: " + neighbour.x + " neighbour.y: " +neighbour.y);
            System.out.println();
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
}