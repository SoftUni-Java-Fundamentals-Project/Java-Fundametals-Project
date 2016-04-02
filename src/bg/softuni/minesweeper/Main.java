package bg.softuni.minesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private FXMLLoader fxmlLoader;
    private MainController controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.fxmlLoader = new FXMLLoader();
        this.fxmlLoader.setLocation(getClass().getResource("view/main.fxml"));
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(new Scene(this.fxmlLoader.load()));
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        this.controller = this.fxmlLoader.getController();
        this.controller.setStage(primaryStage);
    }

    @Override
    public void stop() throws Exception {
        this.controller.stop();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
