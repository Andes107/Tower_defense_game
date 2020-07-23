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
    public Catapult(int x, int y, int r1, int r2, mapObject[][] map) {
        super(x,y,5, 0, r1);
        if (map == null)
            throw new NullPointerException();
        if (r2 < 0 || r2 > ARENA_SIZE || r1 > r2)
            throw new IllegalArgumentException();
        this.r2 = r2;
/*        for (int dy = 1; dy <= r2; ++dy)
            for (int dx = (r1 > dy? (int)(Math.sqrt(r1 * r1 - dy * dy)) : 1); dx <= (int)(Math.sqrt(r2 * r2 - dy * dy)) + 1; ++dx)
                super.markChordKillZone(this.x,this.y,dx,dy,map,this);
        for (int di = r1; di <= r2; ++di)
            super.markRadiusKillZone(x,y,di,map,this);*/
        for (int dy = -r2; dy <= r2; ++dy)
            for (int dx = -(int) (Math.sqrt(r2 * r2 - dy * dy)); dx <= Math.abs((int) (Math.sqrt(r2 * r2 - dy * dy))); ++dx)
                if ((dx*dx + dy*dy >= this.r1*this.r1) && this.x + dx >= 0 && this.x + dx < ARENA_SIZE && this.y + dy >= 0 && this.y+dy < ARENA_SIZE)
                    map[this.x + dx][this.y + dy].towers.add(this);
    }

    @Override
    public void inflictDamage(mapObject[][] map) {
        if (map == null)
            throw new NullPointerException();
        int[][] distribution = new int[ARENA_SIZE][ARENA_SIZE];
        int index = (this.x*ARENA_SIZE) + this.y, ir = 2; //ir refers to a stone's fallout radius, index initialized to this coz can't hit
        for (int dy = -this.r2; dy <= this.r2; ++dy)
            for (int dx = -(int) (Math.hypot(this.r2,dy)); dx <= (int) (Math.hypot(this.r2,dy)); ++dx)
                if ((dx*dx + dy*dy > r1*r1) && x + dx >= 0 && x + dx < ARENA_SIZE && y + dy >= 0 && y+dy < ARENA_SIZE && map[this.x + dx][this.y + dy].monster != null)
                    index = updateDistribution(x+dx, y+dy, this.r1, this.r2, ir, index, map, distribution);
        if (index != (this.x*ARENA_SIZE) + this.y)
            for (int dy = -ir; dy <= ir; ++dy)
                for (int dx = -(int) (Math.hypot(ir, dy)); dx <= -(int) (Math.hypot(ir, dy)); ++dx)
                    if (x + dx >= 0 && x + dx < ARENA_SIZE && y + dy >= 0 && y + dy < ARENA_SIZE && (dx*dx + dy*dy) > this.r1*this.r1 &&  (dx*dx + dy*dy) <= this.r2*this.r2 && map[x+dx][y+dy].monster != null)
                        map[index / ARENA_SIZE + dx][index % ARENA_SIZE + dy].monster.health -= this.damage;
/*        HashMap<Integer, ArrayList<Monster>> monsterDistribution = new HashMap<Integer, ArrayList<Monster>>();
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
                monster.health -= this.damage;*/
    }
}
