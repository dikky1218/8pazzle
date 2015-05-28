package sample;

import java.util.Comparator;

/**
 * Created by daiki on 2015/04/28.
 */
public class Stat{
    int[] tileStat;
    int heuristic;
    int cost;

    Stat(int cost, int heuristic, int[] tileStat){
        this.cost = cost;
        this.tileStat = tileStat;
        this.heuristic = heuristic;
    }


}
