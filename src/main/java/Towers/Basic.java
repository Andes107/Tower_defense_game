package Towers;
import MapObject.*;
import Monsters.Monster;

public class Basic extends Tower {
    public Basic(int x, int y, int r1, mapObject[][] map) {
        super(x,y,5, 0, r1);
        if (map == null)
            throw new NullPointerException();
        for (int dy = -r1; dy <= r1; ++dy)
            for (int dx = -(int)(Math.sqrt(r1*r1-dy*dy)); dx <= Math.abs((int)(Math.sqrt(r1*r1-dy*dy))); ++dx)
                if (x + dx >= 0 && x + dx < ARENA_SIZE && y + dy >= 0 && y+dy < ARENA_SIZE)
                    map[this.x + dx][this.y + dy].towers.add(this);
    }

    @Override
    public void inflictDamage(mapObject[][] map) { //assumed monster is correctly plotted on the map
        if (map == null)
            throw new NullPointerException();
        Monster victim = new Monster(-1, -1, null, 0);
/*
        for (int dy = 1; dy <= r1; ++dy)
            for (int dx = 1; dx <= (int)(Math.sqrt(r1 * r1 - dx * dx)) + 1; ++dx){
                victim = super.findChordVictim(this.x, this.y, dx, dy, map, minDistance);
                minDistance = victim.x * victim.x + victim.y * victim.y;
            }
*/
/*
        for (int di = 1; di <= r1; ++di) {
            victim = super.findRadiusVictim(x, y, di, map, minDistance);
            minDistance = victim.x * victim.x + victim.y * victim.y;
        }
*/
        for (int dy = -r1; dy <= r1; ++dy)
            for (int dx = -(int)(Math.sqrt(r1*r1-dy*dy)); dx <= Math.abs((int)(Math.sqrt(r1*r1-dy*dy))); ++dx)
                if (this.x + dx >= 0 && this.x + dx < ARENA_SIZE && this.y + dy >= 0 && this.y + dy < ARENA_SIZE && map[this.x + dx][this.y + dy].monster != null && Math.hypot(ARENA_SIZE - map[this.x + dx][this.y + dy].monster.x, ARENA_SIZE - map[this.x + dx][this.y + dy].monster.y) < Math.hypot(ARENA_SIZE - victim.x, ARENA_SIZE - victim.y))
                    victim = map[this.x + dx][this.y + dy].monster;
        victim.health -= this.damage;
    }
}
