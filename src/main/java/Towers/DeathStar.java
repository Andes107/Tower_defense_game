package Towers;
import MapObject.*;

import java.util.Random;

public class DeathStar extends Tower{
    public DeathStar(int x, int y, mapObject[][] map) {
        super(x,y,5, 0, super.ARENA_SIZE);
        for (int i = 0; i < super.ARENA_SIZE; ++i)
            for (int j = 0; j < super.ARENA_SIZE; ++j)
                map[i][j].towers.add(this);
    }

    @Override
    public void inflictDamage(mapObject[][] map) {
        int decision = new Random().nextInt(4);
        int startX = -1, startY = -1;
        switch (decision) {
            case 0:
                startX = 0;
                startY = new Random().nextInt(480);
                break;
            case 1:
                startX = 480;
                startY = new Random().nextInt(480);
                break;
            case 2:
                startX = new Random().nextInt(480);
                startY = 0;
            case 3:
                startX = new Random().nextInt(480);
                startY = 480;
        }
        if (startX == this.x) {
            for (int i = this.x - 1; i <= this.x + 1; ++i)// just that 1x points to every y
                for (int j = (startY < this.y ? startY : this.y); j < (startY > this.y ? startY : this.y); ++j)
                    if (map[i][j].monster != null)
                        map[i][j].monster.health -= this.damage;
        } else if (startY == this.y) { //just that all x point to 1 y
            for (int i = (startX < this.x ? startX : this.x); i < (startX > this.x ? startX : this.x); ++i)
                for (int j = this.y - 1; j <= this.y + 1; ++j)
                    if (map[i][j].monster != null)
                        map[i][j].monster.health -= this.damage;
        } else {
            float slope = (this.y - startY) / (this.x - startX);
            float intercept = this.y - slope * x;
            for (int i = (startX < this.x ? startX : this.x); i < (startX > this.x ? startX : this.x); ++i) {
                int y = (int) (slope * i + intercept);
                for (int j = y - 1; j <= y + 1; ++j)
                    if (map[i][j].monster != null)
                        map[i][j].monster.health -= this.damage;
            }
        }
    }
}
