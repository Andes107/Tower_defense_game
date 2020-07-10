package Monsters;

import MapObject.*;
import java.util.PriorityQueue;

public class Monster {
    public static final int ARENA_SIZE = 5;
    public int x; //current position
    public int y; //current position
    public mapObject next; //this is a linked list where next stores next step's information plus what's next

    public Monster() {

    }

    public Monster (int x, int y, mapObject next) {
        this.x = x;
        this.y = y;
        this.next = next;
    }

    public void nextAlgorithm(mapObject[][] map) {
        aNode[][] aNodeMap = newANodeMap(map); //1. create a new aNodeMap

        PriorityQueue<aNode> pq = newPriorityMap(aNodeMap[this.x][this.y]); //2. create a new priority queue with starting point added
        System.out.println("this.x: " + this.x + " this.y " + this.y);
        aNode current = new aNode();

        while (pq.isEmpty() == false) {
            current = pq.poll();
            System.out.println("(x,y)" + current.x + " , " + current.y);
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
        if (y + 1 < ARENA_SIZE && aNodeMap[x][y+1].monster == null && aNodeMap[x][y+1].tower == null)
            neighbour[index++] = aNodeMap[x][y + 1];
        if (x - 1 >= 0 && aNodeMap[x-1][y].monster == null && aNodeMap[x-1][y].tower == null)
            neighbour[index++] = aNodeMap[x - 1][y];
        if (y - 1 >= 0 && aNodeMap[x][y-1].monster == null && aNodeMap[x][y-1].tower == null)
            neighbour[index++] = aNodeMap[x][y - 1];
        return neighbour;
    }

    public void processNeighbour(aNode current, aNode neighbour, PriorityQueue<aNode> pq) {
        if (neighbour == null)
            return;
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
        if (endPoint.prev == null)
            return null;
        do {
            endPoint.prev.next = endPoint;
            endPoint = endPoint.prev;
        } while (endPoint.x != start_x || endPoint.y != start_y);
        System.out.println("endPoint.next.x: " + endPoint.next.x + " endPoint.next.y: " + endPoint.next.y);
        return endPoint.next;
    }
}