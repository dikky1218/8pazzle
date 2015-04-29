package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

/**
 * Created by daiki on 2015/04/28.
 */
public class BaseController {
    @FXML GridPane baseGrid;

    //private PazzleTableController[] tables;
    private String[] strs = {"ALL Search", "Heuristic1", "Heuristic2", "Heuristic3"};


    public void init(PazzleTableController[] tables){
        for(int i=0; i<4; i++) {
            baseGrid.add(tables[i].pazzleTable, i%2, i/2);
            tables[i].updatePanel();
        }
    }

}
