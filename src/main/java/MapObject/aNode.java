package MapObject;

public class aNode extends mapObject implements Comparable<aNode>{
    public int status;
    public double g; //the actual cost from starting point
    public double h; //heuristic cost, which is a minimal estimate
    public double f; //f = g + h

    public aNode(mapObject anMapObject) {
        super(anMapObject.x, anMapObject.y, anMapObject.towerList, anMapObject.monster);//so this copies what is needed
        this.status = 0; //it is of fucking course not discovered
        this.h = Math.sqrt(super.x*super.x + super.y*super.y);
        this.g = super.ARENA_SIZE;
        this.f = f + h;
    }

    @Override
    public int compareTo(aNode anotherNode) { //aNode compareTo will be sorted according to
        if (this.f > anotherNode.f)
            return 1;
        else if (this.f < anotherNode.f)
            return -1;
        else
            return 0;
    }
}
