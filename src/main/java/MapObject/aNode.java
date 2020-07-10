package MapObject;

public class aNode extends mapObject implements Comparable<aNode>{
    public int status; //0 never discovered, 1 has neighbours not discovered, 2 means both itself and neighbours discovered
    public int g; //the actual cost from starting point
    public int h; //heuristic cost, which is a minimal estimate
    public int f; //f = g + h

    public aNode() {

    }

    public aNode(mapObject anMapObject) {
        super(anMapObject.x, anMapObject.y,anMapObject.monster, anMapObject.tower, anMapObject.prev, anMapObject.next);//so this copies what is needed
        this.status = 0; //it is of fucking course not discovered
        this.h = (ARENA_SIZE-1-super.x)*(ARENA_SIZE-1-super.x) + (ARENA_SIZE-1-super.y)*(ARENA_SIZE-1-super.y);
        this.g = ARENA_SIZE * ARENA_SIZE;
        this.f = g + h;
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
