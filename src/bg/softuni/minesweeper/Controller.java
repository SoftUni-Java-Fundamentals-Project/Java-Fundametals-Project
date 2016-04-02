package bg.softuni.minesweeper;

import bg.softuni.minesweeper.model.Cell;
import bg.softuni.minesweeper.model.CellValue;
import bg.softuni.minesweeper.model.ElapsedTime;
import bg.softuni.minesweeper.model.Field;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

public class Controller {

    @FXML
    private Label timeLabel;
    @FXML
    private Label minesCountLabel;
    @FXML
    private GridPane visualField;

    private Field field;
    private ElapsedTime timer;

    private Button[][] visualButtons;

    public Controller() throws Exception {
        this.field = new Field(20, 20, 90);
        this.visualButtons = new Button[this.field.getRows()][this.field.getColumns()];
    }

    @FXML
    private void initialize() {

        this.timer = new ElapsedTime(this::updateTimeLabel);
        this.minesCountLabel.setText(Integer.toString(this.field.getMinesCount()));

        this.addVisualColumns();
        this.addVisualLines();
        this.addVisualCells();

        this.timer.start();
    }

    public void stop() {
        this.timer.stop();
    }

    private void updateTimeLabel(String value) {
        Platform.runLater(() -> this.timeLabel.setText(value));
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

    private EventHandler<MouseEvent> getMouseEventEventHandler(int row, int column) {
        return event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                if (this.field.isMine(row, column)) {
                    endGame(row, column);
                } else {
                    openCell(row, column);
                }
            } else if (event.getButton() == MouseButton.SECONDARY) {
                toggleFlag(row, column);
            }

        };
    }

    private void endGame(int row, int column) {
        Button cellButton = this.visualButtons[row][column];
        cellButton.setText(this.field.getCellValue(row, column).toString());
        cellButton.getStyleClass().add("boom");

        this.timer.stop();

        this.showEndGameAlert();
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
            cellButton.setText(this.field.getCellValue(cell).toString());
            cellButton.getStyleClass().add("clicked");
        }
    }

    private void toggleFlag(int row, int column) {
        Button cellButton = this.visualButtons[row][column];

        if (this.field.toggleFlag(row, column)) {
            cellButton.setText(CellValue.Flag.toString());
        } else {
            cellButton.setText("");
        }
        this.minesCountLabel.setText(Integer.toString(this.field.getMinesCount()));
    }
}
