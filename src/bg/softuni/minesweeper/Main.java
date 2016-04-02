package bg.softuni.minesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private FXMLLoader fxmlLoader;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.fxmlLoader = new FXMLLoader();
        this.fxmlLoader.setLocation(getClass().getResource("view/main.fxml"));
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(new Scene(this.fxmlLoader.load()));
        primaryStage.show();
        setMinMaxSize(primaryStage, primaryStage.getHeight(), primaryStage.getWidth());
    }

    @Override
    public void stop() throws Exception {
        ((Controller) this.fxmlLoader.getController()).stop();
    }

    private void setMinMaxSize(Stage primaryStage, double height, double width) {
        primaryStage.setMinHeight(height);
        primaryStage.setMaxHeight(height);
        primaryStage.setMinWidth(width);
        primaryStage.setMaxWidth(width);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
