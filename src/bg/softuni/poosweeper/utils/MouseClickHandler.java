package bg.softuni.poosweeper.utils;

import bg.softuni.poosweeper.controller.MainController;
import bg.softuni.poosweeper.model.Cell;
import bg.softuni.poosweeper.model.CellValue;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Labeled;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.util.Optional;

/**
 * The mouse click handler class for the game behavior.
 */
public class MouseClickHandler implements EventHandler<MouseEvent> {

    private static final String CLICKED_STYLE_CLASS = "clicked";

    private final int row;
    private final int column;
    private final MainController controller;

    /**
     * Creates an instance for the given row and column with a back-reference to
     * the main controller instance.
     *
     * @param row        the row of the button clicked.
     * @param column     the column of the button clicked.
     * @param controller the controller instance.
     */
    public MouseClickHandler(int row, int column, MainController controller) {
        this.row = row;
        this.column = column;
        this.controller = controller;
    }

    /**
     * Mouse event entry method, which is called by the system.
     *
     * @param event the event informational object.
     */
    @Override
    public void handle(MouseEvent event) {

        if (this.isNotClickable()) {
            return;
        }

        if (this.isValidLeftClick(event)) {
            if (this.isNotPoo()) {
                this.openCell();
            } else {
                this.loseGame();
            }
        } else if (this.isValidRightClick(event)) {
            this.toggleFlag();
        }

        this.controller.updatePooCountText();

        if (this.controller.getField().isSolved()) {
            this.winGame();
        }
    }

    /**
     * Checks if the button is clickable.
     *
     * @return {@code true} if the button is clickable; otherwise, {@code false}.
     */
    private boolean isNotClickable() {
        return this.controller.isGameOver() || this.controller.getField().isOpen(row, column);
    }

    /**
     * Checks if the left button is clicked and the button is not already flagged.
     *
     * @param event the event informational object.
     * @return {@code true} if the button state is valid; otherwise, {@code false}.
     */
    private boolean isValidLeftClick(MouseEvent event) {
        return event.getButton() == MouseButton.PRIMARY
                && !this.controller.getField().isFlagged(row, column);
    }

    /**
     * Returns if the button does not correspond to a poo field.
     *
     * @return {@code true} if the button is not a poo; otherwise, {@code false}.
     */
    private boolean isNotPoo() {
        return !this.controller.getField().isPoo(row, column);
    }

    /**
     * Updates the button styling so that it appears as "open".
     */
    private void openCell() {
        for (Cell cell : this.controller.getField().getAdjacentCells(row, column)) {
            Labeled cellButton = this.controller.getButton(cell.getRow(), cell.getColumn());
            CellValue cellValue = this.controller.getField().openCell(cell);
            cellButton.setText(cellValue.toString());
            cellButton.getStyleClass().remove(CellValue.Flag.getStyleClass());
            cellButton.getStyleClass().add(cellValue.getStyleClass());
            cellButton.getStyleClass().add(CLICKED_STYLE_CLASS);
        }
    }

    /**
     * Notifies the user that they have lost the game.
     */
    private void loseGame() {
        SoundHandler.playRandomFartClip();
        this.controller.getButton(row, column).getStyleClass().add("clickedBoom");
        this.controller.setGameOver();
        this.controller.showAllPoos();
    }

    /**
     * Checks if the right button is clicked.
     *
     * @param event the event informational object.
     * @return {@code true} if the button state is valid; otherwise, {@code false}.
     */
    private boolean isValidRightClick(MouseEvent event) {
        return event.getButton() == MouseButton.SECONDARY;
    }

    /**
     * Toggles the state of a flagged button.
     */
    private void toggleFlag() {
        Labeled cellButton = this.controller.getButton(row, column);
        if (this.controller.getField().toggleFlag(row, column)) {
            cellButton.getStyleClass().add(CellValue.Flag.getStyleClass());
        } else {
            cellButton.getStyleClass().remove(CellValue.Flag.getStyleClass());
        }
    }

    /**
     * Notifies the user they have won the game.
     */
    private void winGame() {
        SoundHandler.playWinningClip();
        this.controller.setGameOver();
        this.showWinGameAlert();
    }

    /**
     * Displays an alert box with a "winner" message.
     */
    private void showWinGameAlert() {

        Alert alert = new Alert(Alert.AlertType.NONE);

        alert.setTitle("You won!");
        alert.setHeaderText("All the toilet cells are clean!");
        alert.getDialogPane().setContent(
                new ImageView(
                        new Image("file:resources/images/giphy.gif")));

        ButtonType buttonContinue = new ButtonType("Continue", ButtonBar.ButtonData.CANCEL_CLOSE);
        ButtonType buttonQuit = new ButtonType("Quit", ButtonBar.ButtonData.RIGHT);
        alert.getButtonTypes().setAll(buttonContinue, buttonQuit);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonQuit) {
            Platform.exit();
        }
    }
}
