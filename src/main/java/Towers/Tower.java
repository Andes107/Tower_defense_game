package Towers;

import MapObject.mapObject;
import Monsters.Monster;
import org.graalvm.compiler.nodes.calc.IsNullNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public abstract class Tower {
    public static int ARENA_SIZE;

    public int x;
    public int y;
    public int r1;
    public int damage;
    public int bump;

    public Tower(int x, int y, int r1, int damage, int bump) {
        if (x < 0 || x >= ARENA_SIZE || y < 0 || y >= ARENA_SIZE || damage < 0 || bump < 0 || r1 < 0 || r1 > ARENA_SIZE)
            throw new IllegalArgumentException();
        this.x = x;
        this.y = y;
        this.r1 = r1;
        this.damage = damage; //Need this when you use a basic tower's constructor!
        this.bump = bump;
    }

    public abstract void inflictDamage(mapObject[][] map);

    public abstract void towerDelBackRemoveKillZone(mapObject[][] map);

    public int updateDistribution(int monsterX, int monsterY, int towerX, int towerY, int radius1, int radius2, int innerRadius, int index, mapObject[][] map, int[][] distribution) {
        //x and y are the location of that monster
        int trueIndex = index;
        for (int monsterDy = -innerRadius; monsterDy <= innerRadius; ++monsterDy) //
            for (int monsterDx = -(int) (Math.sqrt(innerRadius * innerRadius - monsterDy * monsterDy)); monsterDx <= (int) (Math.sqrt(innerRadius * innerRadius - monsterDy * monsterDy)); ++monsterDx)
                if (monsterX + monsterDx >= 0 && monsterX + monsterDx < ARENA_SIZE && monsterY + monsterDy >= 0 && monsterY + monsterDy < ARENA_SIZE && Math.hypot((monsterX + monsterDx - towerX), (monsterY + monsterDy - towerY)) > radius1 && Math.hypot((monsterX + monsterDx - towerX), (monsterY + monsterDy - towerY)) <= radius2){
                    distribution[monsterX + monsterDx][monsterY + monsterDy]++;
                    if (distribution[index / ARENA_SIZE][index % ARENA_SIZE] < distribution[monsterX + monsterDx][monsterY + monsterDy])
                        trueIndex = (monsterX + monsterDx)*ARENA_SIZE + (monsterY + monsterDy);
                }
        return trueIndex;
    }

    public static void towerInflictDamage(Set<Tower> towerFromController, mapObject[][] mapFromController) {
        for (Tower tower : towerFromController)
            tower.inflictDamage(mapFromController);
    }

    public static boolean towerNewBackMapAva(int towerNewBackXFrontY, int towerNewBackYFrontX, int towerNewBackFrontSize, mapObject[][] map) {
        System.out.println("backendismapavailable");
        for (int i = towerNewBackXFrontY - towerNewBackFrontSize / 2; i <= towerNewBackXFrontY + towerNewBackFrontSize / 2; ++i)
            for (int j = towerNewBackYFrontX - towerNewBackFrontSize / 2; j <= towerNewBackYFrontX + towerNewBackFrontSize / 2; ++j)
                if (i < 0 || i >= ARENA_SIZE || j < 0 || j >= ARENA_SIZE || map[i][j].tower != null || map[i][j].monster != null)
                    return false;
        return true;
    }

    public static void towerNewBackFillMap(Tower newTower, int towerNewBackXFrontY, int towerNewBackYFrontX, int towerNewBackFrontSize, mapObject[][] map) {
        System.out.println("backendFillMap");
        for (int i = towerNewBackXFrontY - towerNewBackFrontSize / 2; i <= towerNewBackXFrontY + towerNewBackFrontSize / 2; ++i)
            for (int j = towerNewBackYFrontX - towerNewBackFrontSize / 2; j <= towerNewBackYFrontX + towerNewBackFrontSize / 2; ++j) {
                if (map[i][j].tower != null)
                    throw new IllegalArgumentException();
                map[i][j].tower = newTower;
            }
    }

    public static void towerDelBackRemoveMap(Tower delTower, int towerDelBackFrontSize, mapObject[][] map) {
        System.out.println("BackRemoveMap");
        for (int i = delTower.x - towerDelBackFrontSize / 2; i <= delTower.x + towerDelBackFrontSize / 2; ++i)
            for (int j = delTower.y - towerDelBackFrontSize / 2; j <= delTower.y + towerDelBackFrontSize / 2; ++j) {
                if (map[i][j].tower == null)
                    throw new IllegalArgumentException();
                map[i][j].tower = null;
            }
    }
}