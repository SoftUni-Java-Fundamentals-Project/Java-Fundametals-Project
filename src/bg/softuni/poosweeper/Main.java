package bg.softuni.poosweeper;

import bg.softuni.poosweeper.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    public static final String TITLE = "Poosweeper";

    private MainController controller;

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("view/main.fxml"));
        primaryStage.setTitle(TITLE);
        primaryStage.setScene(new Scene(fxmlLoader.load()));
        primaryStage.show();
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();

        this.controller = fxmlLoader.getController();
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
