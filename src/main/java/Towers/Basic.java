package Towers;
import MapObject.*;
import Monsters.Monster;

public class Basic extends Tower {
    public Basic(int x, int y, int r1, int damage, mapObject[][] map) {
        super(x,y,r1,damage,0);
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
        Monster victim = new Monster(-1, -1, 5, 1);
        for (int dy = -r1; dy <= r1; ++dy)
            for (int dx = -(int)(Math.sqrt(r1*r1-dy*dy)); dx <= Math.abs((int)(Math.sqrt(r1*r1-dy*dy))); ++dx)
                if (this.x + dx >= 0 && this.x + dx < ARENA_SIZE && this.y + dy >= 0 && this.y + dy < ARENA_SIZE && map[this.x + dx][this.y + dy].monster != null && Math.hypot(ARENA_SIZE - map[this.x + dx][this.y + dy].monster.x, ARENA_SIZE - map[this.x + dx][this.y + dy].monster.y) < Math.hypot(ARENA_SIZE - victim.x, ARENA_SIZE - victim.y))
                    victim = map[this.x + dx][this.y + dy].monster;
        victim.health -= this.damage;
//        System.out.println("Basic inflicted: (" + victim.x + ", " + victim.y + " )");
    }

    @Override
    public void towerDelBackRemoveKillZone(mapObject[][] map){
        for (int dy = -r1; dy <= r1; ++dy)
            for (int dx = -(int)(Math.sqrt(r1*r1-dy*dy)); dx <= Math.abs((int)(Math.sqrt(r1*r1-dy*dy))); ++dx)
                if (x + dx >= 0 && x + dx < ARENA_SIZE && y + dy >= 0 && y+dy < ARENA_SIZE){
                    if (map[this.x + dx][this.y + dy].towers.contains(this) == false)
                        throw new IllegalArgumentException();
                    map[this.x + dx][this.y + dy].towers.remove(this);
                }
    }
}
