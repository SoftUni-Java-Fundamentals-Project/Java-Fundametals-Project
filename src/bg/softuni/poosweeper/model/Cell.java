package bg.softuni.poosweeper.model;

import java.util.Objects;

/**
 * A model class that represents a cell in a 2D matrix. Mostly useful
 * for hash-based collection uses.
 */
public class Cell {

    private final int row;
    private final int column;

    /**
     * Creates an instance with the given row and column coordinates.
     *
     * @param row    the zero-based index of the row the cell represents.
     * @param column the zero-based index of the column the cell represents.
     */
    public Cell(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * A getter for the {@link #row} field.
     *
     * @return the zero-based index of the row the cell represents.
     */
    public int getRow() {
        return this.row;
    }

    /**
     * A getter for the {@link #column} field.
     *
     * @return the zero-based index of the column the cell represents.
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Auto-generated override for the {@link Object#equals(Object)} method.
     * Necessary for hash-based collections' operations.
     *
     * @param object the object to test equality with.
     * @return {@code true} if the objects are equal; otherwise, {@code false}.
     */
    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        Cell cell = (Cell) object;

        return getRow() == cell.getRow() &&
                getColumn() == cell.getColumn();
    }

    /**
     * Auto-generated override for the {@link Object#hashCode()} method.
     * Necessary for hash-based collections' operations.
     *
     * @return a hash value of the sequence of object fields.
     */
    @Override
    public int hashCode() {
        return Objects.hash(getRow(), getColumn());
    }
}
