package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {
    private PazzleTableController pazzleTable0, pazzleTable1, pazzleTable2, pazzleTable3;
    private BaseController baseController;
    private Solve allSearch, heuristic1Search, heuristic2Search, heuristic3Search;


    @Override
    public void start(Stage primaryStage) throws Exception{

        FXMLLoader loader = new FXMLLoader(getClass().getResource("base_grid.fxml"));

        Parent root = loader.load();
        baseController = loader.getController();


        primaryStage.setTitle("8 Pazzle");
        Scene scene = new Scene(root, 800, 800);
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        loader = new FXMLLoader(getClass().getResource("pazzle_table.fxml"));
        loader.load();
        pazzleTable0 = loader.getController();
        allSearch = new Solve(Solve.Mode.ALL);
        pazzleTable0.init(allSearch);

        loader = new FXMLLoader(getClass().getResource("pazzle_table.fxml"));
        loader.load();
        pazzleTable1 = loader.getController();
        heuristic1Search = new Solve(Solve.Mode.HEURISTIC1);
        pazzleTable1.init(heuristic1Search);

        loader = new FXMLLoader(getClass().getResource("pazzle_table.fxml"));
        loader.load();
        pazzleTable2 = loader.getController();
        heuristic2Search = new Solve(Solve.Mode.HEURISTIC2);
        pazzleTable2.init(heuristic2Search);

        loader = new FXMLLoader(getClass().getResource("pazzle_table.fxml"));
        loader.load();
        pazzleTable3 = loader.getController();
        heuristic3Search = new Solve(Solve.Mode.HEURISTIC3);
        pazzleTable3.init(heuristic3Search);


        baseController.init(new PazzleTableController[]{pazzleTable0, pazzleTable1, pazzleTable2, pazzleTable3}, new Solve[]{allSearch, heuristic1Search, heuristic2Search, heuristic3Search});

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode key = event.getCode();
                if ((key == KeyCode.UP) || (key == KeyCode.LEFT) || (key == KeyCode.DOWN) || (key == KeyCode.RIGHT)) {
                    pazzleTable0.moveTile(key);
                }
                else if (key == KeyCode.S) {
                    Thread th0 = new Thread(allSearch);
                    th0.start();
                    /*
                    Thread th1 = new Thread(heuristic1Search);
                    Thread th2 = new Thread(heuristic2Search);
                    Thread th3 = new Thread(heuristic3Search);
                    */
                    heuristic1Search.searchStart();
                    heuristic2Search.searchStart();
                    heuristic3Search.searchStart();
                    baseController.displayResult();

                    /*
                    th1.start();
                    th2.start();
                    th3.start();
                    */
                    try{
                        th0.join();
                        baseController.displayResult();
                    } catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
                else if (key == KeyCode.B) {
                    int[] scrambled = allSearch.getScrambledTileStat(1000);
                    allSearch.setTileStat(scrambled);
                    heuristic1Search.setTileStat(scrambled);
                    heuristic2Search.setTileStat(scrambled);
                    heuristic3Search.setTileStat(scrambled);
                    baseController.displayResult();
                }
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }




}
