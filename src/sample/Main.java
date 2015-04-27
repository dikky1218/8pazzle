package sample;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Main extends Application {
    private Solve solve;
    private PazzleTableController pazzleTableController;


    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("sample.fxml"));

        Parent root = loader.load();


        primaryStage.setTitle("8 Pazzle");
        Scene scene = new Scene(root, 300, 275);
        primaryStage.setScene(scene);
        primaryStage.show();

        solve = new Solve();

        pazzleTableController = loader.getController();
        pazzleTableController.init(solve);
        solve.setPazzleTableController(pazzleTableController);

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode key = event.getCode();
                if ((key == KeyCode.UP) || (key == KeyCode.LEFT) || (key == KeyCode.DOWN) || (key == KeyCode.RIGHT)) {
                    pazzleTableController.moveTile(key);
                }
                else if (key == KeyCode.S) {
                    if (solve.searchStart()) {
                        System.out.println("Discovered");
                    }else{
                        System.out.println("unDiscovered");
                    }
                }
            }
        });

    }


    public static void main(String[] args) {
        launch(args);
    }




}
