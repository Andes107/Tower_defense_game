package Towers;
import MapObject.*;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Random;

public class DeathStar extends Tower{
    public DeathStar(int x, int y, mapObject[][] map) {
        super(x,y,5, 0, ARENA_SIZE);
        if (map == null)
            throw new NullPointerException();
    }

    @Override
    public void inflictDamage(mapObject[][] map) {
        if (map == null)
            throw new NullPointerException();
        int R = new Random().nextInt(2);
        int x1 = (R == 0? new Random().nextInt(2) * ARENA_SIZE : new Random().nextInt(ARENA_SIZE));
        int y1 = (R == 1? new Random().nextInt(2) * ARENA_SIZE : new Random().nextInt(ARENA_SIZE));

        if (x1 == this.x)
            for (int i = x1 - 3; i < x1 + 3; ++i)
                for (int j = Math.min(this.y, y1) - 3; j <= Math.max(this.y, y1) + 3; ++j)
                    if (i >= 0 && i < ARENA_SIZE && j >= 0 && j < ARENA_SIZE && map[i][j].monster != null)
                        map[i][j].monster.health -= this.damage;
        else if (y1 == this.y)
            for (int u = y1 - 3; u < y1 + 3; ++u)
                for (int v = Math.min(this.x, x1) - 3; v <= Math.max(this.y, y1) + 3; ++v)
                    if (u >= 0 && u < ARENA_SIZE && v >= 0 && v < ARENA_SIZE && map[u][v].monster != null)
                        map[u][v].monster.health -= this.damage;
        else{
            float m = (y1 - this.y) / (x1 - this.x), c = this.y - m * this.x;
            for (int a = Math.min(this.x, x1) - 3; a <= Math.max(this.x, x1) + 3; ++a)
                for (int b = (int)(m*a+c) - 3; b <= (int)(m*a+c) + 3; ++b)
                    if (a >= 0 && a < ARENA_SIZE && b >= 0 && b < ARENA_SIZE && map[a][b].monster != null)
                        map[a][b].monster.health -= this.damage;
        }
    }
}
