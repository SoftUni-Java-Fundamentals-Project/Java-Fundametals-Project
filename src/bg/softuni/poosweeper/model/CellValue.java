package bg.softuni.poosweeper.model;

/**
 * An enumeration that represents all possible states of a matrix like
 * {@link Field#pooCells}.
 */
public enum CellValue {

    Empty(' ', ""),
    One('1', "one"),
    Two('2', "two"),
    Three('3', "three"),
    Four('4', "four"),
    Five('5', "five"),
    Six('6', "six"),
    Seven('7', "seven"),
    Eight('8', "eight"),
    Poo('*', "boom"),
    Flag('P', "flagged");

    private final Character value;
    private final String styleClass;

    /**
     * Creates an enumeration member with the specified char value to represent it.
     *
     * @param value      the character representation.
     * @param styleClass the style class for the cell value.
     */
    CellValue(Character value, String styleClass) {
        this.value = value;
        this.styleClass = styleClass;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

    public String getStyleClass() {
        return this.styleClass;
    }
}
