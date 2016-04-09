package bg.softuni.poosweeper.controller;

import bg.softuni.poosweeper.model.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    @FXML
    private Label timeLabel;
    @FXML
    private Label pooCountLabel;
    @FXML
    private GridPane visualField;

    private Field field;
    private ElapsedTime timer;
    private Button[][] visualButtons;
    private boolean isGameOver;
    private Stage stage;

    public void setField(Field field) {

        this.isGameOver = false;
        if (this.timer != null) {
            this.timer.stop();
        }

        this.field = field;
        this.visualButtons = new Button[field.getRows()][field.getColumns()];
        this.pooCountLabel.setText(Integer.toString(field.getPooCount()));
        this.timer = new ElapsedTime(this::updateTimeLabel);

        this.clearVisualGrid();
        this.addVisualColumns();
        this.addVisualLines();
        this.addVisualCells();

        this.timer.start();
        this.sizeToScene();
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private void initialize() {
        this.setField(Difficulty.Medium.createField());
    }

    public void stop() {
        this.timer.stop();
    }

    private void updateTimeLabel(String value) {
        Platform.runLater(() -> this.timeLabel.setText(value));
    }

    private void clearVisualGrid() {
        this.visualField.getColumnConstraints().clear();
        this.visualField.getRowConstraints().clear();
        this.visualField.getChildren().clear();
    }

    private void addVisualColumns() {
        for (int i = 0; i < this.field.getColumns(); i++) {
            this.visualField.getColumnConstraints().add(new ColumnConstraints());
        }
    }

    private void addVisualLines() {
        for (int i = 0; i < this.field.getRows(); i++) {
            this.visualField.getRowConstraints().add(new RowConstraints());
        }
    }

    private void addVisualCells() {
        for (int row = 0; row < this.field.getRows(); row++) {
            for (int column = 0; column < this.field.getColumns(); column++) {
                this.visualButtons[row][column] = createVisualButton(row, column);
                this.visualField.add(this.visualButtons[row][column], column, row);
            }
        }
    }

    private Button createVisualButton(int row, int column) {

        Button cellButton = new Button();
        cellButton.setOnMouseClicked(getMouseEventEventHandler(row, column));
        cellButton.setPrefWidth(30.0);
        cellButton.setPrefHeight(30.0);

        return cellButton;
    }

    private void sizeToScene() {
        if (this.stage != null) {
            this.stage.sizeToScene();
        }
    }

    private EventHandler<MouseEvent> getMouseEventEventHandler(int row, int column) {
        return event -> {
            if (this.isGameOver || this.field.isOpen(row, column)) {
                return;
            }

            if (event.getButton() == MouseButton.PRIMARY && !this.field.isFlaged(row, column)) {
                if (this.field.isPoo(row, column)) {
                    endGame(row, column);
                } else {
                    openCell(row, column);
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                toggleFlag(row, column);
            }
            this.pooCountLabel.setText(Integer.toString(this.field.getPooCount()));

            if (this.field.isSolved()) {
                this.winGame();
            }
        };
    }

    private void endGame(int row, int column) {

        this.showAllPoos();

        this.timer.stop();
        this.isGameOver = true;
     //   this.showEndGameAlert();
    }

    private void showAllPoos() {
        for (int row = 0; row < this.field.getRows(); row++) {
            for (int column = 0; column < this.field.getColumns(); column++) {
                if (this.field.isPoo(row, column)) {
                    this.visualButtons[row][column].getStyleClass().add("boom");
                }
            }
        }

    }

    private void showEndGameAlert() {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Game over");
        alert.setHeaderText("The game, you have lost");
        alert.show();
    }

    private void openCell(int row, int column) {
        for (Cell cell : this.field.getAdjacentCells(row, column)) {
            Button cellButton = this.visualButtons[cell.getRow()][cell.getColumn()];
            cellButton.setText(this.field.openCell(cell).toString());
            cellButton.getStyleClass().remove("flagged");
            cellButton.getStyleClass().add("clicked");
        }
    }

    private void toggleFlag(int row, int column) {

        Button cellButton = this.visualButtons[row][column];

        if (this.field.toggleFlag(row, column)) {
            cellButton.getStyleClass().add("flagged");
        } else {
            cellButton.getStyleClass().remove("flagged");
        }
    }

    private void winGame() {
        this.timer.stop();
        this.isGameOver = true;
        this.showWinGameAlert();
    }

    private void showWinGameAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("You are the winner!");
        alert.setHeaderText("The game you have won!");
        alert.show();
    }

    @FXML
    private void onNewGameEasyClicked(ActionEvent actionEvent) throws IOException {
        this.setField(Difficulty.Easy.createField());
    }

    @FXML
    private void onNewGameMediumClicked(ActionEvent actionEvent) throws IOException {

        this.setField(Difficulty.Medium.createField());
    }
    @FXML
    private void onNewGameHardClicked(ActionEvent actionEvent) throws IOException {

        this.setField(Difficulty.Hard.createField());
    }
    @FXML
    private void onNewGameInsaneClicked(ActionEvent actionEvent) throws IOException {
        this.setField(Difficulty.ExtremelyHard.createField());

    }

    private void onNewGameClicked(ActionEvent actionEvent) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../view/newGame.fxml"));

        Stage stage = new Stage();
        stage.setTitle("New Game");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(loader.load()));
        stage.show();
        ((NewGameController) loader.getController()).setParent(this);
    }

    @FXML
    private void onQuitClicked(ActionEvent actionEvent) {
        Platform.exit();
    }
}
