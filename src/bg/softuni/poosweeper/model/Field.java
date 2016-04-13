package bg.softuni.poosweeper.model;

import java.util.*;

public class Field {

    private static final Random random = new Random();

    private final int rows;
    private final int columns;
    private final int pooCount;
    private final int totalCount;

    private final CellValue[][] pooCells;
    private final HashSet<Cell> flagsCells;
    private final HashSet<Cell> openedCells;

    /**
     * Creates an instance with a given rows, columns and number of poos
     *
     * @param rows     represents the number of rows on the field
     * @param columns  represents the number of columns on the field
     * @param pooCount represents the number of poos on the field
     * @throws IllegalArgumentException
     */
    public Field(int rows, int columns, int pooCount) throws IllegalArgumentException {

        this.rows = rows;
        this.columns = columns;
        this.pooCount = pooCount;
        this.totalCount = rows * columns;
        this.pooCells = new CellValue[rows][columns];
        this.flagsCells = new HashSet<>();
        this.openedCells = new HashSet<>();

        placePoo();
        placeHints();
    }

    /**
     * A getter for the {@link #rows} field
     *
     * @return the number of the rows of the field
     */
    public int getRows() {
        return this.rows;
    }

    /**
     * A getter for the {@link #columns} field
     *
     * @return the number of the columns of the field
     */
    public int getColumns() {
        return this.columns;
    }

    /**
     * A getter for the {@link #pooCount} field
     *
     * @return how many poos there are on the field
     */
    public int getPooCount() {
        return this.pooCount - this.flagsCells.size();
    }

    /**
     * Sets/removes flag from the cell with the given coordinates
     *
     * @param row    represents the row of the cell
     * @param column represents the column of the cell
     * @return True if flag is set. False if flag is removed
     */
    public boolean toggleFlag(int row, int column) {

        Cell cell = new Cell(row, column);

        if (this.flagsCells.contains(cell)) {
            this.flagsCells.remove(cell);
            return false;
        } else if (this.getPooCount() > 0) {
            this.flagsCells.add(cell);
            return true;
        }
        return false;
    }

    /**
     * Checks if there is a Poo on the cell with the given coordinates
     * @param row       represents the row of the cell
     * @param column    represents the column of the cell
     * @return True if the cell is Poo and False if it isn't
     */
    public boolean isPoo(int row, int column) {
        return getCellValue(row, column) == CellValue.Poo;
    }

    /**
     *
     * @param cell
     * @return
     */
    public CellValue openCell(Cell cell) {
        this.openedCells.add(cell);
        this.flagsCells.remove(cell);
        return this.getCellValue(cell.getRow(), cell.getColumn());
    }

    /**
     * Checks if the Cell with the give coordinates is opened
     *
     * @param row   represents the row of the cell
     * @param col   represents the column of the cell
     * @return True if the cell is opened. False if the cell is not opened
     */
    public boolean isOpen(int row, int col) {
        Cell cell = new Cell(row, col);
        return this.openedCells.contains(cell);
    }

    public boolean isFlagged(int row, int col) {
        Cell cell = new Cell(row, col);
        return this.flagsCells.contains(cell);
    }

    private CellValue getCellValue(int row, int column) {

        if (this.isOutside(row, column)) {
            return CellValue.Empty;
        }

        return this.pooCells[row][column];
    }

    public boolean isSolved() {
        return this.openedCells.size() + this.pooCount == this.totalCount;
    }

    public Collection<Cell> getAdjacentCells(int row, int column) {

        if (this.isPoo(row, column)) {
            throw new IllegalStateException("Cannot get adjacent cells for a poo.");
        }

        Collection<Cell> result = new HashSet<>();
        Queue<Cell> queue = new LinkedList<>();
        Cell current = new Cell(row, column);
        queue.add(current);

        while (!queue.isEmpty()) {

            current = queue.remove();
            row = current.getRow();
            column = current.getColumn();

            if (this.pooCells[row][column] == CellValue.Empty && !result.contains(current)) {
                this.addAdjacentCell(queue, row - 1, column - 1);
                this.addAdjacentCell(queue, row - 1, column);
                this.addAdjacentCell(queue, row - 1, column + 1);
                this.addAdjacentCell(queue, row, column - 1);
                this.addAdjacentCell(queue, row, column + 1);
                this.addAdjacentCell(queue, row + 1, column - 1);
                this.addAdjacentCell(queue, row + 1, column);
                this.addAdjacentCell(queue, row + 1, column + 1);
            }

            result.add(current);
        }

        return result;
    }

    private void placePoo() throws IllegalArgumentException {

        if (this.totalCount < this.getPooCount()) {
            throw new IllegalArgumentException("The poo's count can't be greater than the count of filed cells!");
        }
        for (int pooPlaced = 0; pooPlaced < this.pooCount; ) {

            int row = random.nextInt(this.rows);
            int column = random.nextInt(this.columns);

            if (this.pooCells[row][column] != CellValue.Poo) {
                this.pooCells[row][column] = CellValue.Poo;
                pooPlaced++;
            }
        }
    }

    private void placeHints() {
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                if (this.pooCells[row][column] != CellValue.Poo) {
                    this.pooCells[row][column] = this.pooNear(row, column);
                }
            }
        }
    }

    private boolean isOutside(int row, int column) {
        return row < 0 || row >= this.rows || column < 0 || column >= this.columns;
    }

    private CellValue pooNear(int row, int column) {

        int hintValue = 0;

        hintValue += this.getPoo(row - 1, column - 1);  // NW
        hintValue += this.getPoo(row - 1, column);      // N
        hintValue += this.getPoo(row - 1, column + 1);  // NE
        hintValue += this.getPoo(row, column - 1);      // W
        hintValue += this.getPoo(row, column + 1);      // E
        hintValue += this.getPoo(row + 1, column - 1);  // SW
        hintValue += this.getPoo(row + 1, column);      // S
        hintValue += this.getPoo(row + 1, column + 1);  // SE

        return CellValue.values()[hintValue];
    }

    private int getPoo(int row, int column) {
        return this.isPoo(row, column) ? 1 : 0;
    }

    private void addAdjacentCell(Queue<Cell> queue, int row, int column) {

        if (isOutside(row, column) || isPoo(row, column)) {
            return;
        }

        queue.add(new Cell(row, column));
    }


}
