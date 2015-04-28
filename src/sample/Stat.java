package sample;

import java.util.Comparator;

/**
 * Created by daiki on 2015/04/28.
 */
public class Stat{
    int[] tileStat;
    int heuristic;

    Stat(int heuristic, int[] tileStat){
        this.tileStat = tileStat;
        this.heuristic = heuristic;
    }


}
