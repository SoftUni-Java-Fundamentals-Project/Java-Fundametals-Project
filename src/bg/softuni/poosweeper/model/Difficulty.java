package bg.softuni.poosweeper.model;

/**
 * An enumeration that represents some possible initial states of a
 * {@link Field}.
 */
public enum Difficulty {

    Easy(8, 8, 10),
    Medium(16, 16, 40),
    Hard(16, 30, 99),
    ExtremelyHard(28, 60, 200);
    private int rows;
    private int columns;
    private int pooCount;

    /**
     * Creates an enumeration member with the specified values to represent it.
     *
     * @param rows     the total number of field rows.
     * @param columns  the total number of field columns.
     * @param pooCount the number of cells that will be given a {@link
     *                 CellValue#Poo} state.
     */
    Difficulty(int rows, int columns, int pooCount) {
        this.rows = rows;
        this.columns = columns;
        this.pooCount = pooCount;
    }

    /**
     * Invokes the {@link Field#Field(int, int, int)} constructor with the
     * pre-defined enum instance values.
     *
     * @return the new field instance.
     */
    public Field createField() {
        return new Field(rows, columns, pooCount);
    }
}
