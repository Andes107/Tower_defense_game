package MapObject;

import Towers.*;

public class aNode extends mapObject implements Comparable<aNode>{
    public int edgeCost; //isFox: 1, notFox: maxDamage; fixed
    public int fromStart; //assumed infinity for both cases; meaningful for both true and false
    public int minToEnd; //isFox: d(A-x,A-y), notFox: 0; fixed
    public int totalDistance; //assumed infinity for both cases; meaningless for Dijkstra while account for minToEnd for fox
    public aNode prev;

    public aNode(mapObject anMapObject, boolean isFox) {
        super(anMapObject.x, anMapObject.y,anMapObject.monster, anMapObject.tower);//so this copies what is needed
        if (anMapObject == null)
            throw new IllegalArgumentException();
        if (isFox) { //isFox == true then Dijkstra!!!
            int maxDamage = 0;
            for (Tower tower : anMapObject.towers)
                maxDamage += tower.damage;
            this.edgeCost = maxDamage;
        }
        else //isFox == false then A*!!! not fox -> then shortest path only!!! -> then A*!!!
            this.edgeCost = 1;
        this.fromStart = Integer.MAX_VALUE;
        this.minToEnd = (!isFox? (ARENA_SIZE - anMapObject.x)*(ARENA_SIZE - anMapObject.x) + (ARENA_SIZE - anMapObject.y)*(ARENA_SIZE - anMapObject.y) : 0); //fixed
        this.totalDistance = Integer.MAX_VALUE; //basically the same as fromstart, except considers mintoend for A*
        this.prev = null;
    }

    @Override
    public int compareTo(aNode anotherNode) { //aNode compareTo will be sorted according to
        if (this.totalDistance > anotherNode.totalDistance) //1 means greater
            return 1;
        else if (this.totalDistance < anotherNode.totalDistance) //-1 means smaller
            return -1;
        else
            return 0;
    }
}
