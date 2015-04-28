package sample;

import javafx.scene.input.KeyCode;

import java.util.*;

/**
 * Created by daiki on 2015/04/27.
 */
public class Solve {
    private final KeyCode[] directions = {KeyCode.UP,KeyCode.DOWN,KeyCode.RIGHT,KeyCode.LEFT};

    private int[] answer = {1,2,3,8,-1,4,7,6,5};

	private int[] tilesStat = new int[9];

    private ArrayDeque<int[]> statQueue = new ArrayDeque<int[]>();

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
    }

    public void scramble(int cnt){
        int i=0;
        Random rand = new Random(System.currentTimeMillis());
        while(i<cnt){
            if (move(directions[rand.nextInt(4)]))i++;
        }
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
        int[] currentStat = new int[9];
        ArrayList<Integer> searchedHash = new ArrayList<Integer>();
        int hash;

        while(!isDiscovered){
            if(isComplete()){
                isDiscovered = true;
                continue;
            }

            copyStat(tilesStat, currentStat);

            for(KeyCode direction : directions){
                loadToTilesStat(currentStat);
                if(move(direction)) {
                    if(!searchedHash.contains((hash = getHash(tilesStat)))){
                        searchedHash.add(hash);
                        statQueue.add(tilesStat.clone());
                    }
                }
            }

            if((tilesStat = statQueue.poll()) == null){
                isDiscovered = false;
                tilesStat = new int[9];
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


    private int getHash(int[] r_n){
        int n = r_n.length;
        int top;
        int order;
        int hash=0;
        int i;
        for(i=0; i<n; i++){
            if(r_n[i] == -1){
                r_n[i] = 9;
                break;
            }
        }

        for(int k=0; k<n; k++){
            top = r_n[k];

            order=0;
            for(int j=k+1; j<n; j++){
                if(top > r_n[j])
                    order++;
            }
            hash += order * factrial(n-k-1);
        }

        r_n[i]=-1;
        return hash;
    }

    private int factrial(int n){
        if(n<=1)return 1;
        return n * factrial(n-1);
    }

}

