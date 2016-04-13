package bg.softuni.poosweeper.controller;

import bg.softuni.poosweeper.model.Difficulty;
import bg.softuni.poosweeper.model.ElapsedTime;
import bg.softuni.poosweeper.model.Field;
import bg.softuni.poosweeper.utils.MouseClickHandler;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

/**
 * Main controller class, which is responsible for UI and game logic binding.
 */
public class MainController {

    @FXML
    private Label timeLabel;
    @FXML
    private Label pooCountLabel;
    @FXML
    private GridPane visualGrid;

    private Field field;
    private ElapsedTime timer;
    private boolean gameOver;
    private Button[][] visualButtons;
    private Stage stage;

    /**
     * Getter for {@link #gameOver} field.
     *
     * @return the field value.
     */
    public boolean isGameOver() {
        return this.gameOver;
    }

    /**
     * Getter for {@link #field} field.
     *
     * @return the field value.
     */
    public Field getField() {
        return this.field;
    }

    /**
     * Sets a new game field and resets the UI state.
     *
     * @param field the game field instance of various difficulty and size.
     */
    private void setField(Field field) {

        this.gameOver = false;
        if (this.timer != null) {
            this.timer.stop();
        }

        this.field = field;
        this.visualButtons = new Button[field.getRows()][field.getColumns()];
        this.pooCountLabel.setText(Integer.toString(field.getPooCount()));
        this.pooCountLabel.getStyleClass().add("pooCountLabel");
        this.timeLabel.getStyleClass().add("timeLabel");
        this.timer = new ElapsedTime(this::updateTimeLabel);

        this.clearVisualGrid();
        this.addVisualColumns();
        this.addVisualRows();
        this.addVisualButtons();

        this.timer.start();
        this.sizeToScene();
    }

    /**
     * Returns the button instance corresponding to the row and column coordinates.
     *
     * @param row    the row of the button.
     * @param column the column of the button.
     * @return the button instance.
     */
    public Labeled getButton(int row, int column) {
        return this.visualButtons[row][column];
    }

    /**
     * Sets the primary stage created when the application starts in {@link
     * bg.softuni.poosweeper.Main#start(javafx.stage.Stage)}. This allows the
     * controller to manipulate the stage object in order to resize it appropriately.
     *
     * @param stage the primary stage for this application.
     */
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * JavaFX specific method, which is called after {@link
     * bg.softuni.poosweeper.Main#start(javafx.stage.Stage)}. Its main purpose is
     * to set the initial game field.
     */
    @FXML
    public void initialize() {
        this.setField(Difficulty.Medium.createField());
    }

    /**
     * This method is called when the application should stop, and provides a
     * convenient place to prepare for disposing controller resources (like
     * background timers).
     */
    public void stop() {
        this.setGameOver();
    }

    /**
     * Updates controller state for ended game.
     */
    public void setGameOver() {
        this.timer.stop();
        this.gameOver = true;
    }

    /**
     * Displays all poos on the visual grid.
     */
    public void showAllPoos() {
        for (int row = 0; row < this.field.getRows(); row++) {
            for (int column = 0; column < this.field.getColumns(); column++) {
                if (this.field.isPoo(row, column) && !this.field.isFlagged(row, column)) {
                    this.visualButtons[row][column].getStyleClass().add("boom");
                }

                if (!this.field.isPoo(row, column) && this.field.isFlagged(row, column)) {
                    this.visualButtons[row][column].getStyleClass().add("noPoo");
                }
            }
        }
    }

    /**
     * Updates poo count label with current count.
     */
    public void updatePooCountText() {
        this.pooCountLabel.setText(Integer.toString(this.field.getPooCount()));
    }

    /**
     * Delegate method which allows a background timer to update the {@link
     * #timeLabel} field. {@link Platform#runLater(Runnable)} is necessary to
     * update the label on the UI thread and avoid a JavaFX exception.
     *
     * @param value the provided elapsed time value.
     */
    private void updateTimeLabel(String value) {
        Platform.runLater(() -> this.timeLabel.setText(value));
    }

    /**
     * Removes all UI components from the {@link #visualGrid} field. This is
     * a required step for creating a new interactable game UI.
     *
     * @see GridPane
     */
    private void clearVisualGrid() {
        this.visualGrid.getChildren().clear();
    }

    /**
     * Creates and adds {@link ColumnConstraints} to the {@link #visualGrid}. Their
     * count equals the number of columns returned by {@link Field#getColumns()}.
     *
     * @see GridPane
     */
    private void addVisualColumns() {
        for (int i = 0; i < this.field.getColumns(); i++) {
            this.visualGrid.getColumnConstraints().add(new ColumnConstraints());
        }
    }

    /**
     * Creates and adds {@link RowConstraints} to the {@link #visualGrid}. Their
     * count equals the number of rows returned by {@link Field#getColumns()}.
     *
     * @see GridPane
     */
    private void addVisualRows() {
        for (int i = 0; i < this.field.getRows(); i++) {
            this.visualGrid.getRowConstraints().add(new RowConstraints());
        }
    }

    /**
     * Create and add {@link Button} objects to the {@link #visualGrid}. Their
     * instances are also saved for convenience in the {@link #visualButtons} matrix.
     */
    private void addVisualButtons() {
        for (int row = 0; row < this.field.getRows(); row++) {
            for (int column = 0; column < this.field.getColumns(); column++) {
                this.visualButtons[row][column] = createVisualButton(row, column);
                this.visualGrid.add(this.visualButtons[row][column], column, row);
            }
        }
    }

    /**
     * Creates a single {@link Button} instance with a click handler for its
     * respective place in the game {@link #field}.
     *
     * @param row    the zero-based index of the row the button represents.
     * @param column the zero-based index of the column the button represents.
     * @return the button instance.
     */
    private Button createVisualButton(int row, int column) {

        Button cellButton = new Button();
        cellButton.setOnMouseClicked(new MouseClickHandler(row, column, this));
        cellButton.setPrefWidth(30.0);
        cellButton.setPrefHeight(30.0);

        return cellButton;
    }

    /**
     * Resize the stage (window) to the current scene size. Used when
     * a new game is created.
     */
    private void sizeToScene() {
        if (this.stage != null) {
            this.stage.sizeToScene();
        }
    }

    @FXML
    private void onNewGameEasyClicked(ActionEvent actionEvent) {
        this.setField(Difficulty.Easy.createField());
    }

    @FXML
    private void onNewGameMediumClicked(ActionEvent actionEvent) {
        this.setField(Difficulty.Medium.createField());
    }

    @FXML
    private void onNewGameHardClicked(ActionEvent actionEvent) {
        this.setField(Difficulty.Hard.createField());
    }

    @FXML
    private void onNewGameInsaneClicked(ActionEvent actionEvent) {
        this.setField(Difficulty.ExtremelyHard.createField());
    }

    @FXML
    private void onQuitClicked(ActionEvent actionEvent) {
        Platform.exit();
    }

    @FXML
    private void onAboutClicked(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("About");
        alert.setHeaderText("Team \"Santiago\"");
        alert.setContentText("Elisaveta Nikolova\n" +
                "Evgeniya Lyubomirova\n" +
                "Mariya Vardalieva\n" +
                "Yaroslav Dimitrov");
        alert.show();
    }
}
