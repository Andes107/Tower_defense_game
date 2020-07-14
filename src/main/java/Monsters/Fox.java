package Monsters;

import MapObject.aNode;
import MapObject.mapObject;

import java.util.PriorityQueue;

public class Fox extends Monster{
    public Fox(int x, int y, aNode next) {
        super(x,y,next);
    }
    public void anotherNextAlgorithm(mapObject[][] map) {
        aNode[][] aNodeMap = super.newANodeMap(map, true); //1. create a new aNodeMap

        PriorityQueue<aNode> pq = super.newPriorityMap(aNodeMap[this.x][this.y], true); //2. create a new priority queue with starting point added
        aNode current;

        while (pq.isEmpty() == false) {
            current = pq.poll();
            if (super.endPointReached(current.x, current.y)) //3. Is the end point reached?
                break;
            aNode[] neighbours = super.findNeighbour(current.x, current.y, aNodeMap); //4. The end point isn't reached, find me the neigbours
            for (aNode neighbour : neighbours) //5. process all my neighbours
                super.processNeighbour(current, neighbour, pq, true);
            current.status = 2;
        }
        next = super.updateNext(aNodeMap[ARENA_SIZE - 1][ARENA_SIZE - 1], this.x, this.y); //6. Update my next after all these work
    }
}
