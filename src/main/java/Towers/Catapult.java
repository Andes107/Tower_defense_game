package Towers;

import MapObject.mapObject;
import Monsters.Monster;

import java.util.ArrayList;
import java.util.HashMap;

public class Catapult extends Tower{
    public int r2;
    /*
    * it's just radius with inner radius
    * */
    public Catapult(int x, int y, mapObject[][] map) {
        super(x,y,5, 0, 5);
        r2 = 10;
        /*
         * I should not hardcode here, but for fuck's sake, let's do it for now
         * But i will use a variable in the rest of the program in case things change
         * reallly don't want to stone myself
         * */
        if (map == null)
            throw new NullPointerException();
        for (int dy = 1; dy <= r2; ++dy)
            for (int dx = (r1 > dy? (int)(Math.sqrt(r1 * r1 - dy * dy)) : 1); dx <= (int)(Math.sqrt(r2 * r2 - dy * dy)) + 1; ++dx)
                super.markChordKillZone(this.x,this.y,dx,dy,map,this);
        for (int di = r1; di <= r2; ++di)
            super.markRadiusKillZone(x,y,di,map,this);
    }

    @Override
    public void inflictDamage(mapObject[][] map) {
        HashMap<Integer, ArrayList<Monster>> monsterDistribution = new HashMap<Integer, ArrayList<Monster>>();
        for (int dy = r1; dy <= r2; ++dy)
            for (int dx = r1; dx <= (int) (Math.sqrt(r2 * r2 - dx * dx)) + 1; ++dx)
                super.initChordList(this.x, this.y, dx, dy, monsterDistribution);
        for (int di = r1; di <= r2; ++di)
            super.initRadiusList(this.x, this.y, di, monsterDistribution);

        int index = -1;

        for (int dy = r1; dy <= r2; ++dy)
            for (int dx = r1; dx <= (int) (Math.sqrt(r2 * r2 - dx * dx)) + 1; ++dx)
                index = super.findChordDensity(index, this.x, this.y, dx, dy, map, monsterDistribution);
        for (int di = r1; di <= r2; ++di)
            index = super.findRadiusDensity(index, this.x, this.y, di, map, monsterDistribution);

        if (index >= 0 && index < ARENA_SIZE)
            for (Monster monster: monsterDistribution.get(index))
                monster.health -= this.damage;
    }
}
