package bg.softuni.poosweeper.model;

/**
 * An enumeration that represents all possible states of a matrix like
 * {@link Field#pooCells}.
 */
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

    /**
     * Creates an enumeration member with the specified char value to represent it.
     *
     * @param value the character representation.
     */
    CellValue(Character value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }
}
