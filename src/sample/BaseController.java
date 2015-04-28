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
    @FXML Pane territory0;
    @FXML Pane territory1;
    @FXML Pane territory2;
    @FXML Pane territory3;
    @FXML Label label0;
    @FXML Label label1;
    @FXML Label label2;
    @FXML Label label3;

    private PazzleTableController[] tables;
    private Solve[] solves;
    private Pane[] territories;
    private Label[] labels;
    private String[] strs = {"ALL Search", "Heuristic1", "Heuristic2", "Heuristic3"};


    public void init(PazzleTableController[] tables, Solve[] solves){
        this.tables = tables;
        this.solves = solves;
        territories = new Pane[]{territory0, territory1, territory2, territory3};
        labels = new Label[]{label0, label1, label2, label3};

        for(int i=0; i<4; i++) {
            String labelStr = String.format("< %s > isDiscovered : false, search count : %d", strs[i], 0);
            labels[i].setText(labelStr);
            territories[i].getChildren().add(tables[i].pazzleTable);
        }
    }

    public void displayResult(){
        for(PazzleTableController table : tables){
            table.updateTiles();
        }
        for(int i=0; i<4; i++) {
            String labelStr = String.format("< %s > isDiscovered : %s, search count : %d", strs[i], solves[i].isDiscovered(), solves[i].getSearchCount());
            labels[i].setText(labelStr);
        }
    }

}
