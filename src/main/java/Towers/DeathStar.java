package Towers;
import MapObject.*;
import com.gargoylesoftware.htmlunit.javascript.host.canvas.rendering.AwtRenderingBackend;
import org.apache.commons.lang3.ObjectUtils;

import java.util.Random;

public class DeathStar extends Tower{
    public DeathStar(int x, int y, int r1, int damage, mapObject[][] map) {
        super(x,y,r1,damage,0);
        if (map == null)
            throw new NullPointerException();
    }

    @Override
    public void inflictDamage(mapObject[][] map) {
        if (map == null)
            throw new NullPointerException();
        int fallout = 3;
        int R = new Random().nextInt(2);
        int x1 = (R == 0 ? new Random().nextInt(2) * (ARENA_SIZE - 1) : new Random().nextInt(ARENA_SIZE));
        int y1 = (R == 1 ? new Random().nextInt(2) * (ARENA_SIZE - 1) : new Random().nextInt(ARENA_SIZE));
        if (x1 == this.x) {
            for (int i = x1 - fallout; i <= x1 + fallout; ++i)
                for (int j = Math.min(this.y, y1) - fallout; j <= Math.max(this.y, y1); ++j)
                    if (i >= 0 && i < ARENA_SIZE && j >= 0 && j < ARENA_SIZE && map[i][j].monster != null)
                        map[i][j].monster.health -= this.damage;
        } else if (y1 == this.y) {
            for (int u = y1 - fallout; u <= y1 + fallout; ++u)
                for (int v = Math.min(this.x, x1) - 3; v <= Math.max(this.x, x1); ++v)
                    if (u >= 0 && u < ARENA_SIZE && v >= 0 && v < ARENA_SIZE && map[u][v].monster != null)
                        map[u][v].monster.health -= this.damage;
        } else {
            int numerator = y1 - this.y, denumerator = x1 - this.x, c = this.y - numerator * this.x / denumerator;
            for (int a = Math.min(this.x, x1); a <= Math.max(this.x, x1); ++a) {
                for (int b = (int) (numerator * a / denumerator + c) - fallout; b <= (int) (numerator * a / denumerator + c) + fallout; ++b)
                    if (a >= 0 && a < ARENA_SIZE && b >= 0 && b < ARENA_SIZE && map[a][b].monster != null)
                        map[a][b].monster.health -= this.damage;
            }
        }
    }
}
