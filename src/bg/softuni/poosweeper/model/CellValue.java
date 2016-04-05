package bg.softuni.poosweeper.model;

public enum CellValue {

    Empty(' '),
    One('1'),
    Two('2'),
    Three('3'),
    Four('4'),
    Five('5'),
    Six('6'),
    Seven('7'),
    Eight('8'),
    Poo('*'),
    Flag('P');

    private final Character value;

    CellValue(Character value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
