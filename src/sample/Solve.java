package sample;

import javafx.scene.input.KeyCode;

import java.util.*;

/**
 * Created by daiki on 2015/04/27.
 */
public class Solve {

    int[] answer = {1,2,3,8,-1,4,7,6,5};

	private int[] tilesStat = new int[9];

    ArrayDeque<int[]> statQueue = new ArrayDeque<int[]>();

    Solve(){
        setComplete();
        /*
		tilesStat[0] = 1;
		tilesStat[1] = 3;
		tilesStat[2] = 2;
		tilesStat[3] = 7;
		tilesStat[4] = -1;
		tilesStat[5] = 5;
		tilesStat[6] = 8;
		tilesStat[7] = 6;
		tilesStat[8] = 4;
		*/
        System.out.println(isComplete());
    }

    private void loadToTilesStat(int[] source){
        copyStat(source, tilesStat);
    }

    private void copyStat(int[] source, int[] destination){
        for(int i=0; i<9; i++){
            destination[i] = source[i];
        }
    }

    public boolean searchStart(){
        boolean isDiscovered = false;
        KeyCode[] directions = {KeyCode.UP,KeyCode.DOWN,KeyCode.RIGHT,KeyCode.LEFT};
        int[] currentStat = new int[9];
        List<int[]> searchedStat = new LinkedList<int[]>();

        while(!isDiscovered){

            copyStat(tilesStat, currentStat);

            if(isComplete()){
                isDiscovered = true;
                continue;
            }

            for(KeyCode direction : directions){
                loadToTilesStat(currentStat);
                if(move(direction)) {
                    /*if(!searchedStat.contains(tilesStat)){
                        searchedStat.add(tilesStat);
                    }else{
                        continue;
                    }
                    */
                    statQueue.add(tilesStat);
                }
            }

            if((tilesStat = statQueue.poll()) == null){
                isDiscovered = false;
                break;
            }

        }

        return isDiscovered;
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

    public int[] getTilesStat(){
        return tilesStat;
    }

    private int getTileByCoordinate(int x, int y) {
        return tilesStat[x + y*3];
    }

    private void swapTile(int tileName){
        int tilePos = getPosByTileName(tileName);
        int vacantPos = getPosByTileName(-1);
        tilesStat[tilePos] = -1;
        tilesStat[vacantPos] = tileName;
    }

    private int getVacantTileX(){
        return getPosByTileName(-1)%3;
    }
    private int getVacantTileY(){
        return getPosByTileName(-1)/3;
    }

    public void setComplete(){
        loadToTilesStat(answer);
    }

    public boolean isComplete(){
        for(int i=0; i<9; i++) {
            if(tilesStat[i]!=answer[i])return false;
        }
        return true;
    }

    public int getPosByTileName(int tileName){
        for(int i=0; i< 9; i++){
            if(tileName == tilesStat[i])return i;
        }
        return -100;
    }



}

