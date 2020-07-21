package Towers;

import MapObject.mapObject;
import Monsters.Monster;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Tower {
    public static final int ARENA_SIZE = 5;

    public int damage = 5;
    public int bump;
    public int r1;
    public int x;
    public int y;
    /*
    * should i put in damage and bump final here? no, because it can be updated afterwards
    * should i give it a value during initialization? no
    * i should only pre-set it in the constructor
    * but who is to decide? i rli don't know
    * */
    public Tower(){} //This is just for my test case for now, should have fixed it afterwards

    public Tower(int x, int y, int damage, int bump, int r1) {
        this.x = x;
        this.y = y;
        this.damage = damage; //Need this when you use a basic tower's constructor!
        this.bump = bump;
        this.r1 = r1;
    }

    public void markChordKillZone(int x, int y, int dx, int dy, mapObject[][] map, Tower target) {
        if (y + dy < ARENA_SIZE) {
            if (x + dx < ARENA_SIZE)
                map[x+dx][y+dy].towers.add(target);
            if (x - dx >= 0)
                map[x-dx][y+dy].towers.add(target);
        }
        if (y - dy >= 0) {
            if (x + dx < ARENA_SIZE)
                map[x+dx][y-dy].towers.add(target);
            if (x - dx >= 0)
                map[x-dx][y-dy].towers.add(target);
        }
    }

    public void markRadiusKillZone(int x, int y, int di, mapObject[][] map, Tower target) {
        if (y + di < ARENA_SIZE)
            map[x][y+di].towers.add(target);
        if (y - di >= 0)
            map[x][y-di].towers.add(target);
        if (x + di < ARENA_SIZE)
            map[x+di][y].towers.add(target);
        if (x- di >= 0)
            map[x-di][y].towers.add(target);
    }

    public abstract void inflictDamage(mapObject[][] map);

    public Monster findChordVictim(int x, int y, int dx, int dy, mapObject[][] map, int minDistance) {
        Monster victim = null;
        int trueMinDistance = minDistance;
        if (y + dy < ARENA_SIZE) {
            if (x + dx < ARENA_SIZE && map[x + dx][y + dy].monster != null && map[x + dx][y + dy].monster.x * map[x + dx][y + dy].monster.x + map[x + dx][y + dy].monster.y * map[x + dx][y + dy].monster.y < trueMinDistance) {
                victim = map[x + dx][y + dy].monster;
                trueMinDistance = victim.x*victim.x + victim.y*victim.y;
            }
            if (x - dx >= 0 && map[x-dx][y+dy].monster != null && map[x-dx][y+dy].monster.x * map[x-dx][y+dy].monster.x + map[x-dx][y+dy].monster.y * map[x-dx][y+dy].monster.y < trueMinDistance){
                victim = map[x - dx][y + dy].monster;
                trueMinDistance = victim.x*victim.x + victim.y*victim.y;
            }
        }
        if (y - dy >= 0) {
            if (x + dx < ARENA_SIZE && map[x + dx][y - dy].monster != null && map[x + dx][y - dy].monster.x * map[x + dx][y - dy].monster.x + map[x + dx][y - dy].monster.y * map[x + dx][y - dy].monster.y < trueMinDistance) {
                victim = map[x + dx][y - dy].monster;
                trueMinDistance = victim.x*victim.x + victim.y*victim.y;
            }
            if (x - dx >= 0 && map[x-dx][y-dy].monster != null && map[x-dx][y-dy].monster.x * map[x-dx][y-dy].monster.x + map[x-dx][y-dy].monster.y * map[x-dx][y-dy].monster.y < trueMinDistance){
                victim = map[x - dx][y - dy].monster;
                trueMinDistance = victim.x*victim.x + victim.y*victim.y;
            }
        }
        return victim;
    }

    public Monster findRadiusVictim(int x, int y, int di, mapObject[][] map, int minDistance) {
        Monster victim = null;
        int trueMinDistance = minDistance;
        if (y + di < ARENA_SIZE && map[x][y + di].monster != null && map[x][y + di].monster.x * map[x][y + di].monster.x + map[x][y + di].monster.y * map[x][y + di].monster.y < trueMinDistance) {
            victim = map[x][y + di].monster;
            trueMinDistance = victim.x*victim.x + victim.y*victim.y;
        }
        if (y - di >= 0 && map[x][y - di].monster != null && map[x][y - di].monster.x * map[x][y - di].monster.x + map[x][y - di].monster.y * map[x][y - di].monster.y < trueMinDistance) {
            victim = map[x][y - di].monster;
            trueMinDistance = victim.x*victim.x + victim.y*victim.y;
        }
        if (x + di < ARENA_SIZE && map[x + di][y].monster != null && map[x+di][y].monster.x * map[x+di][y].monster.x + map[x+di][y].monster.y * map[x+di][y].monster.y < trueMinDistance) {
            victim = map[x+di][y].monster;
            trueMinDistance = victim.x*victim.x + victim.y*victim.y;
        }
        if (x - di >= 0 && map[x - di][y].monster != null && map[x-di][y].monster.x * map[x-di][y].monster.x + map[x-di][y].monster.y * map[x-di][y].monster.y < trueMinDistance) {
            victim = map[x-di][y].monster;
            trueMinDistance = victim.x*victim.x + victim.y*victim.y;
        }
        return victim;
    }

    public void initChordList(int x, int y, int dx, int dy, HashMap<Integer, ArrayList<Monster>> monsterDistribution) {
        if (x + dx < ARENA_SIZE && y + dy < ARENA_SIZE)
            monsterDistribution.put((x+dx) * ARENA_SIZE + y + dy, new ArrayList<Monster>());
        if (x + dx < ARENA_SIZE && y - dy >= 0)
            monsterDistribution.put((x+dx) * ARENA_SIZE + y - dy, new ArrayList<Monster>());
        if (x - dx >= 0 && y + dy < ARENA_SIZE)
            monsterDistribution.put((x-dx) * ARENA_SIZE + y + dy, new ArrayList<Monster>());
        if (x - dx >= 0 && y - dy >= 0)
            monsterDistribution.put((x-dx) * ARENA_SIZE + y - dy, new ArrayList<Monster>());
    }

    public void initRadiusList(int x,int y, int di, HashMap<Integer, ArrayList<Monster>> monsterDistribution) {
        if (y + di < ARENA_SIZE)
            monsterDistribution.put(x * ARENA_SIZE + y + di, new ArrayList<Monster>());
        if (y - di >= 0)
            monsterDistribution.put(x * ARENA_SIZE + y - di, new ArrayList<Monster>());
        if (x + di < ARENA_SIZE)
            monsterDistribution.put((x+di) * ARENA_SIZE + y, new ArrayList<Monster>());
        if (x - di >= 0)
            monsterDistribution.put((x-di) * ARENA_SIZE + y, new ArrayList<Monster>());
    }

    public int individualCensus(int index, int x, int y, mapObject[][] map, HashMap<Integer, ArrayList<Monster>> monsterDistribution) {
        int hitRadius = 25;
        /*
        * remember, you want x and y satisfy
        * 1. ARENA_SIZE
        * 2. catapult radius
        * 3. monster radius
        * to facilitate this, i would like to have a square
        * */
        int trueIndex = index;
        for (int i = x - 25; i <= x + 25; ++i)
            for (int j = y - 25; j <= y + 25; ++j)//this process can violate all the above!!!
                if (i >=0 && j >= 0 && i < ARENA_SIZE && j < ARENA_SIZE && monsterDistribution.containsKey(i*ARENA_SIZE + j)){
                    monsterDistribution.get(i*ARENA_SIZE + j).add(map[i][j].monster);
                    trueIndex = (monsterDistribution.get(trueIndex).size() < monsterDistribution.get(i*ARENA_SIZE + j).size()? i* ARENA_SIZE + j : trueIndex);
                }
        return trueIndex;
    }

    public int findChordDensity(int index, int x, int y, int dx, int dy, mapObject[][] map, HashMap<Integer, ArrayList<Monster>> monsterDistribution) {
        int trueIndex = index;
        if (x + dx < ARENA_SIZE && y + dy < ARENA_SIZE && map[x+dx][y+dy].monster != null)
            //so you find a monster on a sector, you get the index for most dense index
            trueIndex = individualCensus(trueIndex,x+dx, y+dy, map, monsterDistribution);
        if (x + dx < ARENA_SIZE && y - dy >= 0 && map[x+dx][y-dy].monster != null)
            trueIndex = individualCensus(trueIndex,x+dx, y-dy, map, monsterDistribution);
        if (x - dx >= 0 && y + dy < ARENA_SIZE && map[x-dx][y+dy].monster != null)
            trueIndex = individualCensus(trueIndex,x-dx, y+dy, map, monsterDistribution);
        if (x - dx >= 0 && y - dy >= 0 && map[x-dx][y-dy].monster != null)
            trueIndex = individualCensus(trueIndex,x-dx, y-dy, map, monsterDistribution);
        return trueIndex;
    }

    public int findRadiusDensity(int index, int x, int y, int di, mapObject[][] map, HashMap<Integer, ArrayList<Monster>> monsterDistribution) {
        int trueIndex = index;
        if (x + di < ARENA_SIZE && map[x+di][y].monster != null)
            trueIndex = individualCensus(trueIndex,x+di, y, map, monsterDistribution);
        if (x - di >= 0 && map[x-di][y].monster != null)
            trueIndex = individualCensus(trueIndex,x-di, y, map, monsterDistribution);
        if (y + di < ARENA_SIZE && map[x][y+di].monster != null)
            trueIndex = individualCensus(trueIndex,x, y+di, map, monsterDistribution);
        if (y - di >= 0 && map[x][y-di].monster != null)
            trueIndex = individualCensus(trueIndex,x, y-di, map, monsterDistribution);
        return trueIndex;

    }
}