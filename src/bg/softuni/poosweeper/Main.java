package bg.softuni.poosweeper;

import bg.softuni.poosweeper.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main entry class.
 */
public class Main extends Application {

    private static final String TITLE = "Poosweeper";

    private MainController controller;

    /**
     * The main entry point for all JavaFX applications.
     * The start method is called after the init method has returned,
     * and after the system is ready for the application to begin running.
     *
     * @param primaryStage the primary stage for this application, onto which
     *                     the application scene can be set. The primary stage will be embedded in
     *                     the browser if the application was launched as an applet.
     *                     Applications may create other stages, if needed, but they will not be
     *                     primary stages and will not be embedded in the browser.
     */
    @Override
    public void start(Stage primaryStage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("view/main.fxml"));

        primaryStage.setTitle(TITLE);
        primaryStage.setScene(new Scene(fxmlLoader.load()));
        primaryStage.setResizable(false);
        primaryStage.sizeToScene();
        primaryStage.show();

        this.controller = fxmlLoader.getController();
        this.controller.setStage(primaryStage);
    }

    /**
     * This method is called when the application should stop, and provides a
     * convenient place to prepare for application exit and destroy resources.
     */
    @Override
    public void stop() throws Exception {
        this.controller.stop();
    }

    /**
     * Main entry method.
     *
     * @param args the command line arguments.
     */
    public static void main(String[] args) {
        launch(args);
    }
}
