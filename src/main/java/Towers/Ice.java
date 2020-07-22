package Towers;
import MapObject.*;
import Monsters.Monster;

public class Ice extends Tower {
    public Ice(int x, int y, mapObject[][] map) {
        super(x,y,0, 5, 10);
        /*
         * I should not hardcode here, but for fuck's sake, let's do it for now
         * But i will use a variable in the rest of the program in case things change
         * reallly don't want to stone myself
         * */
        if (map == null)
            throw new NullPointerException();
        for (int dy = 1; dy <= r1; ++dy)
            for (int dx = 1; dx <= (int)(Math.sqrt(r1 * r1 - dy * dy)) + 1; ++dx)
                super.markChordKillZone(this.x,this.y,dx,dy,map,this);
        for (int di = 1; di <= r1; ++di) {
            super.markRadiusKillZone(x,y,di,map,this);
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
            victim.counter += this.bump;
    }
}
