package Towers;
import MapObject.*;

public class Basic extends Tower {
    public Basic(int x, int y, mapObject[][] map) {
        super(x,y,5, 0, 65);
        int r = 65;
        /*
        * I should not hardcode here, but for fuck's sake, let's do it for now
        * But i will use a variable in the rest of the program in case things change
        * reallly don't want to stone myself
        * */
        for (int dy = 1; dy <= r; ++dy)
            for (int dx = 1; dx <= (int)(Math.sqrt(r * r - dx * dx)) + 1; ++dx)
                super.markKillZone(this.x,this.y,dx,dy,map,this);
        for (int di = 1; di <= r; ++di) {
            if (y + di < ARENA_SIZE)
                map[this.x][this.y+di].towers.add(this);
            if (y - di >= 0)
                map[this.x][this.y-di].towers.add(this);
            if (x + di < ARENA_SIZE)
                map[this.x+di][this.y].towers.add(this);
            if (x- di >= 0)
                map[this.x-di][this.y].towers.add(this);
        }
        map[this.x][this.y].towers.add(this);
    }
}
