package Monsters;

import MapObject.*;
import java.util.PriorityQueue;

public abstract class Monster {
    public static final int ARENA_SIZE = 480;

    public int health; //can be negative since it will then be removed
    public int speed;
    public int x; //current position
    public int y; //current position
    public mapObject next; //this is a linked list where next stores next step's information plus what's next
    /*
     * penguin and unicorn's next will hold aNode
     * fox next will hode dNode
     * penguins and unicorns use 1 algorithm for updating next based on current map
     * fox use another algorithm for updating next based on current map
     * so the question is: should i declare monster abstract or override it in fox?
     * override in fox then
     * */

    public Monster(int health, int speed, int x, int y, mapObject next) {
        this.health = health;
        this.speed = speed;
        this.x = x;
        this.y = y;
        this.next = next;
    }

    public void nextAlgorithm(mapObject[][] map) {
        aNode[][] aNodeMap = newANodeMap(map); //1. create a new aNodeMap

        PriorityQueue<aNode> pq = newPriorityMap(aNodeMap[this.x][this.y]); //2. create a new priority queue with starting point added
        aNode current;

        while (pq.isEmpty() == false) {
            current = pq.poll();
            if (endPointReached(current.x, current.y)) //3. Is the end point reached?
                break;
            aNode[] neighbours = findNeighbour(current.x, current.y, aNodeMap); //4. The end point isn't reached, find me the neigbours
            for (aNode neighbour : neighbours) //5. process all my neighbours
                processNeighbour(current, neighbour, pq);
        }
        next = updateNext(aNodeMap[ARENA_SIZE - 1][ARENA_SIZE - 1], this.x, this.y); //6. Update my next after all these work
    }

    public aNode[][] newANodeMap(mapObject[][] map) {
        aNode[][] aNodeMap = new aNode[ARENA_SIZE][ARENA_SIZE];
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                aNodeMap[i][j] = new aNode(map[i][j]);
        return aNodeMap;
    }

    public PriorityQueue<aNode> newPriorityMap(aNode node) {
        PriorityQueue<aNode> pq = new PriorityQueue<aNode>();
        node.g = 0;
        node.f = node.h;
        pq.add(node);
        node.status = 1;
        return pq;
    }

    public boolean endPointReached(int x, int y) {
        return (x == ARENA_SIZE - 1 && y == ARENA_SIZE - 1 ? true : false);
    }

    public aNode[] findNeighbour(int x, int y, aNode[][] aNodeMap) {
        aNode[] neighbour = new aNode[4];
        int index = 0;
        if (x + 1 < ARENA_SIZE && aNodeMap[x+1][y].monster == null && aNodeMap[x+1][y].tower == null)
            neighbour[index++] = aNodeMap[x + 1][y];
        if (y + 1 < ARENA_SIZE && aNodeMap[x+1][y].monster == null && aNodeMap[x][y+1].tower == null)
            neighbour[index++] = aNodeMap[x][y + 1];
        if (x - 1 >= 0 && aNodeMap[x+1][y].monster == null && aNodeMap[x-1][y].tower == null)
            neighbour[index++] = aNodeMap[x - 1][y + 1];
        if (y - 1 >= 0 && aNodeMap[x+1][y].monster == null && aNodeMap[x][y-1].tower == null)
            neighbour[index++] = aNodeMap[x][y - 1];
        return neighbour;
    }

    public void processNeighbour(aNode current, aNode neighbour, PriorityQueue<aNode> pq) {
        if (current.g + 1 < neighbour.g && neighbour.status != 2) {
            neighbour.g = current.g + 1;
            neighbour.f = neighbour.g + neighbour.h;
            neighbour.prev = current;
            if (neighbour.status == 0) {
                neighbour.status = 1;
                pq.add(neighbour);
            }
        }
    }

    public mapObject updateNext(mapObject endPoint, int start_x, int start_y) {
        mapObject current = endPoint;
        for (; current.x != start_x && current.y != start_y && current != null; current = current.prev)
            current.prev.next = current;
        return current.next;
    }
}