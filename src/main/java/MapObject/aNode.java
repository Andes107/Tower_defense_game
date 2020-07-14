package MapObject;

import Towers.*;

public class aNode extends mapObject implements Comparable<aNode>{
    public int status; //0 never discovered, 1 has neighbours not discovered, 2 means both itself and neighbours discovered
    public int g; //the actual cost from starting point; will mark nothing for fox
    public int h; //heuristic cost, which is a minimal estimate; h will be edge cost for fox
    public int f; //f = g + h; f will be actual cost for fox

    public aNode prev; //for algorithm
    public aNode next; //for algorithm

    public aNode() {

    }

    public aNode(mapObject anMapObject, boolean isFox) {
        super(anMapObject.x, anMapObject.y,anMapObject.monster, anMapObject.tower);//so this copies what is needed
        this.prev = null;
        this.next = null;
        this.status = 0; //it is of fucking course not discovered
        if (isFox == true) {
            this.h = 0;
            for (Tower tower : anMapObject.towers)
                this.h += tower.damage;
            this.g = 0;
            this.f = Integer.MAX_VALUE; //actual cost should be assumed infinity at first
        } else {
            this.h = (ARENA_SIZE-1-super.x)*(ARENA_SIZE-1-super.x) + (ARENA_SIZE-1-super.y)*(ARENA_SIZE-1-super.y);
            this.g = ARENA_SIZE * ARENA_SIZE;
            this.f = g + h;
        }
    }

    @Override
    public int compareTo(aNode anotherNode) { //aNode compareTo will be sorted according to
        if (this.f > anotherNode.f) //1 means greater
            return 1;
        else if (this.f < anotherNode.f) //-1 means smaller
            return -1;
        else
            return 0;
    }
}
