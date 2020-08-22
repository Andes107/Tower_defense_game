package MapObject;

import Towers.*;

public class aNode /*extends mapObject */implements Comparable<aNode>{
    /*Important information from mapObject*/
    public static int ARENA_SIZE;
    public int x;
    public int y;
    public boolean isMonster;
    public boolean isTower;
    /*Important information from mapObject*/

    public int edgeCost; //isFox: 1, notFox: maxDamage; fixed
    public int fromStart; //assumed infinity for both cases; meaningful for both true and false
    public int minToEnd; //notFox: d(A-x,A-y), isFox: 0; fixed
    public int totalDistance; //assumed infinity for both cases; meaningless for Dijkstra while account for minToEnd for fox
    public aNode prev;

    public aNode(mapObject anMapObject, boolean isFox) {
        /*super(anMapObject.x, anMapObject.y,anMapObject.monster, anMapObject.tower);*/
        if (anMapObject == null)
            throw new IllegalArgumentException();
        /*Important information from mapObject*/
        this.x = anMapObject.x;
        this.y = anMapObject.y;
        this.isMonster = (anMapObject.monster == null? false : true);
        this.isTower = (anMapObject.tower == null? false : true);
        /*Important information from mapObject*/
        if (isFox) { //isFox == true then Dijkstra!!!
            int maxDamage = 0;
            for (Tower tower : anMapObject.towers)
                maxDamage += tower.damage;
            this.edgeCost = maxDamage;
        }
        else //isFox == false then A*!!! not fox -> then shortest path only!!! -> then A*!!!
            this.edgeCost = 1;
        this.fromStart = Integer.MAX_VALUE;
        this.minToEnd = ((!isFox)? (ARENA_SIZE - anMapObject.x)*(ARENA_SIZE - anMapObject.x) + (ARENA_SIZE - anMapObject.y)*(ARENA_SIZE - anMapObject.y) : 0); //fixed
        this.totalDistance = Integer.MAX_VALUE; //basically the same as fromstart, except considers mintoend for A*
        this.prev = null;
    }

    public static aNode[][] aNodeMap(mapObject[][] map, boolean isFox) {
        aNode[][] duplicate = new aNode[ARENA_SIZE][ARENA_SIZE];
        for (int i = 0; i < ARENA_SIZE; ++i)
            for (int j = 0; j < ARENA_SIZE; ++j)
                duplicate[i][j] = new aNode(map[i][j], isFox);
        return duplicate;
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
