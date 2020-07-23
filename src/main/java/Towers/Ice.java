package Towers;
import MapObject.*;
import Monsters.Monster;

public class Ice extends Tower {
    public Ice(int x, int y, int r1, mapObject[][] map) {
        super(x,y,0, 5, r1);
        if (map == null)
            throw new NullPointerException();
/*
        for (int dy = 1; dy <= r1; ++dy)
            for (int dx = 1; dx <= (int) (Math.sqrt(r1 * r1 - dy * dy)) + 1; ++dx)
                super.markChordKillZone(this.x,this.y,dx,dy,map,this);
*/
/*
        for (int di = 1; di <= r1; ++di) {
            super.markRadiusKillZone(this.x,this.y,di,map,this);
        }
*/
        for (int dy = -r1; dy <= r1; ++dy)
            for (int dx = -(int)(Math.sqrt(r1*r1-dy*dy)); dx <= Math.abs((int)(Math.sqrt(r1*r1-dy*dy))); ++dx)
                if (x + dx >= 0 && x + dx < ARENA_SIZE && y + dy >= 0 && y+dy < ARENA_SIZE)
                    map[this.x + dx][this.y + dy].towers.add(this);
    }

    @Override
    public void inflictDamage(mapObject[][] map) { //assumed monster is correctly plotted on the map
        if (map == null)
            throw new NullPointerException();
        Monster victim = null;
        for (int dy = -r1; dy <= r1; ++dy)
            for (int dx = -(int)(Math.sqrt(r1*r1-dy*dy)); dx <= Math.abs((int)(Math.sqrt(r1*r1-dy*dy))); ++dx)
                if (this.x + dx >= 0 && this.x + dx < ARENA_SIZE && this.y + dy >= 0 && this.y + dy < ARENA_SIZE && map[this.x + dx][this.y + dy].monster != null && Math.hypot(ARENA_SIZE - map[this.x + dx][this.y + dy].monster.x, ARENA_SIZE - map[this.x + dx][this.y + dy].monster.y) < Math.hypot(ARENA_SIZE - victim.x, ARENA_SIZE - victim.y))
                    victim = map[this.x + dx][this.y + dy].monster;
        if (victim != null)
            victim.health -= this.damage;
    }
}
