package sample;

import java.util.Comparator;

/**
 * Created by daiki on 2015/04/28.
 */
public class StatComparator implements Comparator<Stat>{

    @Override
    public int compare(Stat o1, Stat o2) {
        return o1.heuristic + o1.cost < o2.heuristic + o2.cost ? -1 : 1; //optimum
        //return o1.heuristic  < o2.heuristic  ? -1 : 1; //fastest
    }
}
