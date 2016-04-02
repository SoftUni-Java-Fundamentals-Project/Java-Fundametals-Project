package bg.softuni.minesweeper;

import bg.softuni.minesweeper.model.Difficulty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;

public class NewGameController {

    private MainController parent;

    public void setParent(MainController parent) {
        this.parent = parent;
    }

    @FXML
    private void onEasyClicked(Event event) {
        this.parent.setField(Difficulty.Easy.createField());
        this.close(event);
    }

    @FXML
    private void onMediumClicked(Event event) {
        this.parent.setField(Difficulty.Medium.createField());
        this.close(event);
    }

    @FXML
    private void onHardClicked(Event event) {
        this.parent.setField(Difficulty.Hard.createField());
        this.close(event);
    }

    @FXML
    private void onExtremeClicked(Event event) {
        this.parent.setField(Difficulty.ExtremelyHard.createField());
        this.close(event);
    }

    private void close(Event event) {
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
