package bg.softuni.minesweeper.model;

public enum Cell {

    Empty(' '),
    One('1'),
    Two('2'),
    Three('3'),
    Four('4'),
    Five('5'),
    Six('6'),
    Seven('7'),
    Eight('8'),
    Mine('*');

    private final Character value;

    Cell(Character value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
