package Towers;
import MapObject.*;
import Monsters.Monster;

public class Basic extends Tower {
    public Basic(int x, int y, mapObject[][] map) {
        super(x,y,5, 0, 10);
        if (map == null)
            throw new NullPointerException();
/*
* for now, will hardcode r1
* still not yet account for the fact of difficulty
* */
        for (int dy = 1; dy <= r1; ++dy)
            for (int dx = 1; dx <= (int) (Math.sqrt(r1 * r1 - dy * dy)) + 1; ++dx)
                super.markChordKillZone(this.x,this.y,dx,dy,map,this);
        for (int di = 1; di <= r1; ++di) {
            super.markRadiusKillZone(this.x,this.y,di,map,this);
        }
        map[this.x][this.y].towers.add(this);
    }

    @Override
    public void inflictDamage(mapObject[][] map) { //assumed monster is correctly plotted on the map
        Monster victim = null;
        int minDistance = 0;
        for (int dy = 1; dy <= r1; ++dy)
            for (int dx = 1; dx <= (int)(Math.sqrt(r1 * r1 - dx * dx)) + 1; ++dx){
                victim = super.findChordVictim(this.x, this.y, dx, dy, map, minDistance);
                minDistance = victim.x * victim.x + victim.y * victim.y;
            }
        for (int di = 1; di <= r1; ++di) {
            victim = super.findRadiusVictim(x, y, di, map, minDistance);
            minDistance = victim.x * victim.x + victim.y * victim.y;
        }
        if (victim != null)
            victim.health -= this.damage;
    }
}
