package Towers;
import MapObject.*;

public class DeathStar extends Tower{
    public DeathStar(int x, int y, mapObject[][] map) {
        super(x,y,5, 0, super.ARENA_SIZE);
        for (int i = 0; i < super.ARENA_SIZE; ++i)
            for (int j = 0; j < super.ARENA_SIZE; ++j)
                map[i][j].towers.add(this);

    }
}
