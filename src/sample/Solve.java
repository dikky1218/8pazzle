package sample;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.input.KeyCode;

import java.util.*;

/**
 * Created by daiki on 2015/04/27.
 */
public class Solve extends Task<Integer> {
    private final KeyCode[] directions = {KeyCode.UP,KeyCode.DOWN,KeyCode.RIGHT,KeyCode.LEFT};
    private PazzleTableController pazzleTable;


    @Override
    protected Integer call() throws Exception {
        searchStart();
        return getSearchCount();
    }


    static enum Mode  {ALL, HEURISTIC1, HEURISTIC2, HEURISTIC3 }
    private Mode mode;
    private String name;

    private int[] answer = {1,2,3,8,9,4,7,6,5};

	private int[] tilesStat = new int[9];
    private boolean isDiscovered = false;
    private int searchCnt=0;


    public String getName() {
        return name;
    }

    Solve(Mode mode, String name){
        this.name = name;
        this.mode = mode;
        setComplete();
    }


    public void setPazzleTable(PazzleTableController pazzleTable){
        this.pazzleTable = pazzleTable;
    }

    public void scramble(int cnt){
        int i=0;
        Random rand = new Random(System.currentTimeMillis());
        while(i<cnt){
            if (move(directions[rand.nextInt(4)]))i++;
        }
        updateProgress(0);
    }

    public int[] getScrambledTileStat(int cnt){
        int[] tmp = tilesStat.clone();
        scramble(cnt);
        int[] scrambled = getTilesStat();
        tilesStat = tmp;
        return scrambled;
    }

    private void loadToTilesStat(int[] source){
        copyStat(source, tilesStat);
    }

    private void copyStat(int[] source, int[] destination){
        System.arraycopy(source, 0, destination, 0, 9);
    }

    public boolean searchStart(){
        isDiscovered = false;
        int[] currentStat = new int[9];
        ArrayList<Integer> searchedHash = new ArrayList<Integer>();
        int hash;
        int cost = 0;
        searchCnt = 0;
        ArrayList<Stat> statQueue = new ArrayList<Stat>();
        Comparator<Stat> statComparator = new StatComparator();

        while(!isDiscovered){
            if(isComplete()){
                isDiscovered = true;
                System.out.println(name + ": Discovered");
                break;
            }

            cost++;

            copyStat(tilesStat, currentStat);

            for(KeyCode direction : directions){
                loadToTilesStat(currentStat);
                if(move(direction)) {
                    if(!searchedHash.contains((hash = getHash(tilesStat)))){
                        searchedHash.add(hash);
                        switch(mode) {
                            case ALL:
                                statQueue.add(new Stat(cost, 0, tilesStat.clone()));
                                Collections.sort(statQueue, statComparator);
                                break;
                            case HEURISTIC1:
                                statQueue.add(new Stat(cost, getHeuristic1(), tilesStat.clone()));
                                Collections.sort(statQueue, statComparator);
                                break;
                            case HEURISTIC2:
                                statQueue.add(new Stat(cost, getHeuristic2(), tilesStat.clone()));
                                Collections.sort(statQueue, statComparator);
                                break;
                            case HEURISTIC3:
                                statQueue.add(new Stat(cost, getHeuristic3(), tilesStat.clone()));
                                Collections.sort(statQueue, statComparator);
                                break;
                        }
                        searchCnt++;
                    }
                }
            }

            if(statQueue.isEmpty()){
                isDiscovered = false;
                tilesStat = new int[9];
                System.out.println(name + ": unDiscovered");
                break;
            }
            else{
                tilesStat = statQueue.get(0).tileStat;
                cost = statQueue.get(0).cost;
                statQueue.remove(0);
                updateProgress(cost);
                updateScreen();
            }

        }

        System.out.println("search count : " + searchCnt);
        System.out.println("total cost : " + cost);
        setComplete();
        updateProgress(cost);
        updateScreen();
        return isDiscovered;
    }

    private void updateProgress(int cost){
        String labelStr = String.format("<%s> search count: %d, total cost: %d", name, searchCnt, cost);
        updateMessage(labelStr);
        updateProgress( searchCnt, 362880);

    }

    private void updateScreen(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                pazzleTable.updatePanel();

            }
        });
    }

    public int getSearchCount(){
        return searchCnt;
    }

    public int getHeuristic1(){
        int cst=0;
        for(int i=0; i<9; i++){
            if(tilesStat[i] != answer[i])cst++;
        }

        return cst;
    }

    public int getHeuristic2(){
        int cst=0;
        for(int i=0; i<9; i++){
            if(tilesStat[i] != answer[i]){
                cst += getDistanceBtwnTiles(i, getAnswerPosOfTile(tilesStat[i]));
            }
        }
        return cst;
    }

    public int getHeuristic3(){
        int cst=0;
        int[] backupTileStat = tilesStat.clone();
        int vacantPos;

        while(!isComplete()) {
            if ((vacantPos = getPosByTileName(9)) == 4) {
                int i;
                for(i=0; i<9; i++){
                    if(answer[i] != tilesStat[i])break;
                }
                swapByTileName(tilesStat[i]);
            } else {
                swapByTileName(answer[vacantPos]);
            }
            cst++;
        }
        tilesStat = backupTileStat;

        return cst;
    }

    public int getAnswerPosOfTile(int tile){
        int i;
        for(i=0; i<9; i++){
            if(answer[i] == tile)return i;
        }
        return -2;
    }


    public int getDistanceBtwnTiles(int pos1, int pos2){
        int x1 = pos1%3;
        int y1 = pos1/3;
        int x2 = pos2%3;
        int y2 = pos2/3;

        return Math.abs(x1-x2) + Math.abs(y1-y2);
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
            if(isMove) swapByTileName(getTileByCoordinate(x, y + 1));
            return true;
        }
        else if((direction == KeyCode.DOWN) && y!=0){
            if(isMove) swapByTileName(getTileByCoordinate(x, y - 1));
            return true;
        }
        else if((direction == KeyCode.RIGHT) && x!=0){
            if(isMove) swapByTileName(getTileByCoordinate(x - 1, y));
            return true;
        }
        else if((direction == KeyCode.LEFT) && x!=2){
            if(isMove) swapByTileName(getTileByCoordinate(x + 1, y));
            return true;
        }
        else{
            return false;
        }
    }

    public int[] getTilesStat(){
        return tilesStat;
    }

    public void setTileStat(int[] tilesStat1){
        tilesStat = tilesStat1.clone();
    }

    private int getTileByCoordinate(int x, int y) {
        return tilesStat[x + y*3];
    }

    private void swapByTileName(int tileName){
        int tilePos = getPosByTileName(tileName);
        int vacantPos = getPosByTileName(9);
        tilesStat[tilePos] = 9;
        tilesStat[vacantPos] = tileName;
    }

    private int getVacantTileX(){
        return getPosByTileName(9)%3;
    }
    private int getVacantTileY(){
        return getPosByTileName(9)/3;
    }

    public void setComplete(){
        loadToTilesStat(answer);
        String labelStr = String.format("<%s> isDiscovered: %s, search count: %d", name, isDiscovered, searchCnt);
        updateMessage(labelStr);
        updateProgress(0,1);
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

        for(int k=0; k<n; k++){
            top = r_n[k];

            order=0;
            for(int j=k+1; j<n; j++){
                if(top > r_n[j])
                    order++;
            }
            hash += order * factrial(n-k-1);
        }

        return hash;
    }

    private int factrial(int n){
        if(n<=1)return 1;
        return n * factrial(n-1);
    }

}

