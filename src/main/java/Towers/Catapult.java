package Towers;

import MapObject.mapObject;

public class Catapult extends Tower{
    public int r2;
    /*
    * it's just radius with inner radius
    * */
    public Catapult(int x, int y, int r1, int damage, int r2, mapObject[][] map) {
        super(x,y,r1,damage,0);
        if (map == null)
            throw new NullPointerException();
        if (r2 <= 0 || r2 > ARENA_SIZE || r1 > r2)
            throw new IllegalArgumentException();
        this.r2 = r2;
        for (int dy = -r2; dy <= r2; ++dy)
            for (int dx = -(int) (Math.sqrt(r2 * r2 - dy * dy)); dx <= Math.abs((int) (Math.sqrt(r2 * r2 - dy * dy))); ++dx)
                if ((dx*dx + dy*dy > this.r1*this.r1) && this.x + dx >= 0 && this.x + dx < ARENA_SIZE && this.y + dy >= 0 && this.y+dy < ARENA_SIZE)
                    map[this.x + dx][this.y + dy].towers.add(this);
    }

    @Override
    public void inflictDamage(mapObject[][] map) {
        if (map == null)
            throw new NullPointerException();
        int[][] distribution = new int[ARENA_SIZE][ARENA_SIZE];
        int index = (this.x*ARENA_SIZE) + this.y, ir = 2; //ir refers to a stone's fallout radius, index initialized to this coz can't hit
        for (int dy = -this.r2; dy <= this.r2; ++dy)
            for (int dx = -(int) (Math.sqrt(r2 * r2 - dy * dy)); dx <= (int) (Math.sqrt(r2 * r2 - dy * dy)); ++dx)
                if ((dx*dx + dy*dy > this.r1*this.r1) && this.x + dx >= 0 && this.x + dx < ARENA_SIZE && this.y + dy >= 0 && this.y+dy < ARENA_SIZE && map[this.x + dx][this.y + dy].monster != null)
                    index = updateDistribution(this.x + dx, this.y + dy, this.x, this.y, this.r1, this.r2, ir, index, map, distribution);
        if (index != (this.x * ARENA_SIZE) + this.y) {
            int killX = index / ARENA_SIZE, killY = index % ARENA_SIZE;
            for (int killDy = -ir; killDy <= ir; ++killDy)
                for (int killDx = -(int) (Math.sqrt(ir * ir - killDy * killDy)); killDx <= (int) (Math.sqrt(ir * ir - killDy * killDy)); ++killDx)
                    if (killX + killDx >= 0 && killX + killDx < ARENA_SIZE && killY + killDy >= 0 && killY + killDy < ARENA_SIZE && Math.hypot(killX + killDx - this.x, killY + killDy - this.y) > this.r1 && Math.hypot(killX + killDx - this.x, killY + killDy - this.y) <= this.r2 && map[killX + killDx][killY + killDy].monster != null)
                        map[killX + killDx][killY + killDy].monster.health -= this.damage;
        }
    }
}
