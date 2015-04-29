package sample;

import javafx.application.Application;
import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

public class Main extends Application {
    private PazzleTableController pazzleTable0, pazzleTable1, pazzleTable2, pazzleTable3;
    private Solve allSearch, heuristic1Search, heuristic2Search, heuristic3Search;
    private Thread th0, th1, th2, th3;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //base pane load
        FXMLLoader loader = new FXMLLoader(getClass().getResource("base_grid.fxml"));
        Parent root = loader.load();
        BaseController baseController = loader.getController();

        //intialize window
        primaryStage.setTitle("8 Pazzle");
        Scene scene = new Scene(root, 700, 700);
        primaryStage.setScene(scene);
        primaryStage.show();

        //load table panels

        loader = new FXMLLoader(getClass().getResource("pazzle_table.fxml"));
        loader.load();
        pazzleTable0 = loader.getController();

        loader = new FXMLLoader(getClass().getResource("pazzle_table.fxml"));
        loader.load();
        pazzleTable1 = loader.getController();

        loader = new FXMLLoader(getClass().getResource("pazzle_table.fxml"));
        loader.load();
        pazzleTable2 = loader.getController();

        loader = new FXMLLoader(getClass().getResource("pazzle_table.fxml"));
        loader.load();
        pazzleTable3 = loader.getController();


        //create solve instances and set them to pazzleTableControllers.
        createMainInstances();

        baseController.init(new PazzleTableController[]{pazzleTable0, pazzleTable1, pazzleTable2, pazzleTable3});


        th0 = new Thread(allSearch);
        th0.setDaemon(true);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                if(th0.getState() == Thread.State.RUNNABLE)return;
                KeyCode key = event.getCode();
                if ((key == KeyCode.UP) || (key == KeyCode.LEFT) || (key == KeyCode.DOWN) || (key == KeyCode.RIGHT)) {
                    pazzleTable0.moveTile(key);
                }
                else if (key == KeyCode.S) {
                    th0 = new Thread(allSearch);
                    th0.setDaemon(true);
                    th0.start();


                    th1 = new Thread(heuristic1Search);
                    th1.setDaemon(true);
                    th1.start();

                    th2 = new Thread(heuristic2Search);
                    th2.setDaemon(true);
                    th2.start();

                    th3 = new Thread(heuristic3Search);
                    th3.setDaemon(true);
                    th3.start();

                }
                else if (key == KeyCode.B) {
                    createMainInstances();
                    int[] scrambled = allSearch.getScrambledTileStat(1000);
                    allSearch.setTileStat(scrambled);
                    heuristic1Search.setTileStat(scrambled);
                    heuristic2Search.setTileStat(scrambled);
                    heuristic3Search.setTileStat(scrambled);
                    pazzleTable0.updatePanel();
                    pazzleTable1.updatePanel();
                    pazzleTable2.updatePanel();
                    pazzleTable3.updatePanel();
                }
            }
        });

    }

    private void createMainInstances(){
        allSearch = new Solve(Solve.Mode.ALL, "All Search");
        pazzleTable0.init(allSearch);

        heuristic1Search = new Solve(Solve.Mode.HEURISTIC1, "Heuristic1");
        pazzleTable1.init(heuristic1Search);

        heuristic2Search = new Solve(Solve.Mode.HEURISTIC2, "Heuristic2");
        pazzleTable2.init(heuristic2Search);

        heuristic3Search = new Solve(Solve.Mode.HEURISTIC3, "Heuristic3");
        pazzleTable3.init(heuristic3Search);
    }


    public static void main(String[] args) {
        launch(args);
    }




}
