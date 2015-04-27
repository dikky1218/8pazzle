package sample;

import com.sun.jmx.remote.internal.ArrayQueue;
import javafx.scene.input.KeyCode;

import java.util.*;

/**
 * Created by daiki on 2015/04/27.
 */
public class Solve {
    private PazzleTableController pazzleTableController;

    private HashMap<Integer, Coordinate> tilesStat = new HashMap<Integer, Coordinate>();
    int[] answer = {1,2,3,8,-1,4,7,6,5};

    ArrayDeque<HashMap<Integer, Coordinate>> statQueue = new ArrayDeque<HashMap<Integer, Coordinate>>();

    Solve(){
      //  setComplete();
        tilesStat.clear();
        tilesStat.put( 5, new Coordinate(0,0));
        tilesStat.put( 3, new Coordinate(1,0));
        tilesStat.put( 2, new Coordinate(2,0));
        tilesStat.put( 6, new Coordinate(0,1));
        tilesStat.put( 8, new Coordinate(1,1));
        tilesStat.put( 4, new Coordinate(2,1));
        tilesStat.put( 7, new Coordinate(0,2));
        tilesStat.put( -1, new Coordinate(1,2));
        tilesStat.put( 1, new Coordinate(2,2));
    }

    public boolean searchStart(){
        boolean isDiscovered = false;
        KeyCode[] directions = {KeyCode.UP,KeyCode.DOWN,KeyCode.RIGHT,KeyCode.LEFT};
        HashMap<Integer, Coordinate> currentStat = (HashMap<Integer, Coordinate>) tilesStat.clone();
        List<int[]> searchedStat = new LinkedList<int[]>();
        int[] tmpStatArray = new int[9];

        while(!isDiscovered){
            tilesStat = (HashMap<Integer, Coordinate>)currentStat.clone();



            int[] queueTop = getTilesPosition();
            if(isComplete()){
                isDiscovered = true;
                pazzleTableController.updateTiles();
                continue;
            }

            for(KeyCode direction : directions){
                tilesStat = (HashMap<Integer, Coordinate>)currentStat.clone();
                if(move(direction)) {
                    if(!searchedStat.contains(tmpStatArray = getTilesPosition())){
                        searchedStat.add(tmpStatArray);
                    }else{
                        continue;
                    }
                    statQueue.add(tilesStat);
                    pazzleTableController.updateTiles();
                }
            }

            if((currentStat = statQueue.poll()) == null){
                isDiscovered = false;
                break;
            }

        }

        return isDiscovered;
    }



    public int[] getTilesPosition(){
        int[] ret = new int[9];

        for(int i=0; i<9; i++){
            int x = tilesStat.get(answer[i]).getX();
            int y = tilesStat.get(answer[i]).getY();
            ret[x+y*3] = answer[i];
        }

        return ret;
    }

    public boolean move(KeyCode direction){
        return myIsMovable(direction, true);
    }

    public boolean isMovable(KeyCode direction){
        return myIsMovable(direction, false);
    }

    private boolean myIsMovable(KeyCode direction, boolean isMove){
        int x = getVacantTileX();
        int y = getVacantTileY();

        if((direction == KeyCode.UP) && y!=2){
            if(isMove)swapTile(getTileByCoordinate(x, y + 1));
            return true;
        }
        else if((direction == KeyCode.DOWN) && y!=0){
            if(isMove)swapTile(getTileByCoordinate(x, y-1));
            return true;
        }
        else if((direction == KeyCode.RIGHT) && x!=0){
            if(isMove)swapTile(getTileByCoordinate(x-1, y));
            return true;
        }
        else if((direction == KeyCode.LEFT) && x!=2){
            if(isMove)swapTile(getTileByCoordinate(x+1, y));
            return true;
        }
        else{
            return false;
        }
    }

    private Integer getTileByCoordinate(int x, int y) {
        for(int i=0; i<9; i++){
            if(tilesStat.get(answer[i]).equals(x,y)){
                return answer[i];
            }
        }
        return -3;
    }

    private void swapTile(int tile){
        Coordinate tmp = tilesStat.get(tile);
        tilesStat.remove(tile);
        tilesStat.put(tile, getVacantTileCoordinate());
        tilesStat.remove(-1);
        tilesStat.put(-1, tmp);
    }

    private Coordinate getVacantTileCoordinate(){
        return tilesStat.get(-1);
    }
    private int getVacantTileX(){
        return tilesStat.get(-1).getX();
    }
    private int getVacantTileY(){
        return tilesStat.get(-1).getY();
    }

    public void setComplete(){
        tilesStat.clear();
        for(int i=0; i<answer.length; i++) {
            tilesStat.put(answer[i], new Coordinate(i % 3, i / 3));
        }
    }

    public boolean isComplete(){
        for(int i=0; i<answer.length; i++) {
            Coordinate pos = tilesStat.get(answer[i]);
            if(!pos.equals(i % 3, i / 3)){
                return false;
            }
        }
        return true;
    }

    public void setPazzleTableController(PazzleTableController pazzleTableController) {
        this.pazzleTableController = pazzleTableController;
    }


    class Coordinate{
        private int x,y;
        Coordinate(int x, int y){
            this.x = x;
            this.y = y;
        }

        public int getX(){
            return x;
        }
        public int getY(){
            return y;
        }

        public boolean equals(int x, int y) {
           return (x==this.x) && (y==this.y);
        }
    }
}

