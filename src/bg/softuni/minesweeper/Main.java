package bg.softuni.minesweeper;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("view/main.fxml"));
        primaryStage.setTitle("Minesweeper");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        setMinMaxSize(primaryStage, primaryStage.getHeight(), primaryStage.getWidth());

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
