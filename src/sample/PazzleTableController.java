package sample;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;


/**
 * Created by daiki on 2015/04/27.
 */
public class PazzleTableController {
    @FXML GridPane pazzleTable;
    @FXML Label label00;
    @FXML Label label01;
    @FXML Label label02;
    @FXML Label label10;
    @FXML Label label11;
    @FXML Label label12;
    @FXML Label label20;
    @FXML Label label21;
    @FXML Label label22;

    private Solve solve;

    void init( Solve solve){
        this.solve = solve;
        updateTiles();
    }

    public void updateTiles(){
        int[] tiles = solve.getTilesStat();
        String[] str_tiles = new String[tiles.length];
        for(int i=0; i<tiles.length; i++){
            if(tiles[i]==-1){
                str_tiles[i]="";
            }
            else{
                str_tiles[i] = String.valueOf(tiles[i]);
            }
        }

        label00.setText(str_tiles[0]);
        label01.setText(str_tiles[1]);
        label02.setText(str_tiles[2]);
        label10.setText(str_tiles[3]);
        label11.setText(str_tiles[4]);
        label12.setText(str_tiles[5]);
        label20.setText(str_tiles[6]);
        label21.setText(str_tiles[7]);
        label22.setText(str_tiles[8]);
    }

    public void moveTile(KeyCode key){
        if(solve.move(key)){
            updateTiles();
        }
    }

}
