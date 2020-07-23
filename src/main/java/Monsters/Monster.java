package Monsters;

import MapObject.*;
import java.util.PriorityQueue;

public class Monster {
    public static final int ARENA_SIZE = 5;

    public int x; //current position
    public int y; //current position
    public aNode next; //this is a linked list where next stores next step's information plus what's next
    public int speed; //the monster will move after speed amount of game loops, adjust per difficulty
    public int counter; //counter means how many game loops left until next move
    public int health; //may reduce per game loop, may be negative, adjust per difficulty

    public Monster() {

    }

    public Monster (int x, int y, aNode next, int health) {
        this.x = x;
        this.y = y;
        this.next = next;
        this.health = health;
    }

    public void nextAlgorithm(mapObject[][] map, boolean isFox) {
        aNode[][] aNodeMap = newANodeMap(map, isFox); //1. create a new aNodeMap

        PriorityQueue<aNode> pq = newPriorityMap(aNodeMap[this.x][this.y], isFox); //2. create a new priority queue with starting point added
        aNode current;

        while (pq.isEmpty() == false) {
            current = pq.poll();
            if (endPointReached(current.x, current.y)) //3. Is the end point reached?
                break;
            aNode[] neighbours = findNeighbour(current.x, current.y, aNodeMap); //4. The end point isn't reached, find me the neigbours
            for (aNode neighbour : neighbours) //5. process all my neighbours
                processNeighbour(current, neighbour, pq, false);
            current.status = 2; //6. the current node has processed all neighbours!
        }
        next = updateNext(aNodeMap[ARENA_SIZE - 1][ARENA_SIZE - 1], this.x, this.y); //6. Update my next after all these work
    }

    public aNode[][] newANodeMap(mapObject[][] map, boolean isFox) {
        aNode[][] aNodeMap = new aNode[ARENA_SIZE][ARENA_SIZE];
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                aNodeMap[i][j] = new aNode(map[i][j],isFox);
        return aNodeMap;
    }

    public PriorityQueue<aNode> newPriorityMap(aNode node, boolean isFox) {
        PriorityQueue<aNode> pq = new PriorityQueue<aNode>();
        node.status = 1;
        if (isFox == false) {
            node.g = 0;
            node.f = node.h;
        } else
            //g has nothing to mark since we need no such thing
            //h marks the maximal estimated damage by staying in this grid, no change either
            node.f = node.h; //f marks the maximal damage sucked as of starting point
        pq.add(node);
        return pq;
    }

    public boolean endPointReached(int x, int y) {
        return (x == ARENA_SIZE - 1 && y == ARENA_SIZE - 1 ? true : false);
    }

    public aNode[] findNeighbour(int x, int y, aNode[][] aNodeMap) {
        aNode[] neighbour = new aNode[4];
        int index = 0;
        if (x + 1 < ARENA_SIZE && aNodeMap[x+1][y].monster == null && aNodeMap[x+1][y].tower == null && aNodeMap[x+1][y].status != 2)
            neighbour[index++] = aNodeMap[x + 1][y];
        if (y + 1 < ARENA_SIZE && aNodeMap[x][y+1].monster == null && aNodeMap[x][y+1].tower == null && aNodeMap[x][y+1].status != 2)
            neighbour[index++] = aNodeMap[x][y + 1];
        if (x - 1 >= 0 && aNodeMap[x-1][y].monster == null && aNodeMap[x-1][y].tower == null && aNodeMap[x-1][y].status != 2)
            neighbour[index++] = aNodeMap[x - 1][y];
        if (y - 1 >= 0 && aNodeMap[x][y-1].monster == null && aNodeMap[x][y-1].tower == null && aNodeMap[x][y-1].status != 2)
            neighbour[index++] = aNodeMap[x][y - 1];
        return neighbour;
    }

    public void processNeighbour(aNode current, aNode neighbour, PriorityQueue<aNode> pq, boolean isFox) {
        if (neighbour == null || neighbour.status == 2) //so what remains must be in status 0 or 1: not discovered or neighbours not spanned
            return;
        if (isFox == false) {
            if (current.g + 1 < neighbour.g) { //Relaxation in general monster A* algorithm
                pq.remove(neighbour); //This neighbour is either not discovered or not spanned its neighbours, but remove it be4 relax
                neighbour.g = current.g + 1;
                neighbour.f = neighbour.g + neighbour.h;
                neighbour.prev = current;
            }
        }
        else {
            if (current.f + neighbour.h < neighbour.f) { //Relaxation in fox Dikjastra algorithm
                pq.remove(neighbour); //Because it will be relaxed, so remove it first!
                neighbour.f = current.f + neighbour.h;//f is the max damage sucked up so far, h is just the estimated dmg for this node
                neighbour.prev = current;
            }
        }
        neighbour.status = (neighbour.status == 0? 1 : neighbour.status); //if 0, now explored; if 1, now relaxed, no one the wiser
        pq.add(neighbour); //either 0 or 1, fox or monster, requires to be added after relaxed parameters
    }

    public aNode updateNext(aNode endPoint, int start_x, int start_y) {
        if (endPoint.prev == null)
            return null;
        do {
            endPoint.prev.next = endPoint;
            endPoint = endPoint.prev;
        } while (endPoint.x != start_x || endPoint.y != start_y);
        return endPoint.next;
    }
}