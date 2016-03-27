package bg.softuni.minesweeper;

import bg.softuni.minesweeper.model.ElapsedTime;
import bg.softuni.minesweeper.model.Field;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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

    public Controller() {
        this.field = new Field(20, 20, 20);
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
                this.visualField.add(createVisualButton(column, row), column, row);
            }
        }
    }

    private Button createVisualButton(int column, int row) {

        Button cellButton = new Button();
        cellButton.setOnMouseClicked(getMouseEventEventHandler(cellButton, column, row));
        cellButton.setPrefWidth(25.0);
        cellButton.setPrefHeight(25.0);

        return cellButton;
    }

    private EventHandler<MouseEvent> getMouseEventEventHandler(Button cellButton, int column, int row) {
        return event -> {
            cellButton.setText(this.field.getCell(row, column).toString());
            cellButton.getStyleClass().add("clicked");
        };
    }
}
