package bg.softuni.minesweeper.model;

import java.util.*;

public class Field {

    private static Random random = new Random();

    private int rows;
    private int columns;
    private int minesCount;
    private CellValue[][] minesCells;

    public Field(int rows, int columns, int minesCount) {

        this.rows = rows;
        this.columns = columns;
        this.minesCount = minesCount;
        this.minesCells = new CellValue[rows][columns];

        placeMines();
        placeHints();
    }

    public int getRows() {
        return this.rows;
    }

    public int getColumns() {
        return this.columns;
    }

    public int getMinesCount() {
        return this.minesCount;
    }

    public boolean isMine(int row, int column) {
        return getCellValue(row, column) == CellValue.Mine;
    }

    public CellValue getCellValue(Cell cell) {
        return this.getCellValue(cell.getRow(), cell.getColumn());
    }

    public CellValue getCellValue(int row, int column) {

        if (this.isOutside(row, column)) {
            return CellValue.Empty;
        }

        return this.minesCells[row][column];
    }

    public Collection<Cell> getAdjacentCells(int row, int column) {

        if (this.isMine(row, column)) {
            throw new IllegalStateException("Cannot get adjacent cells for a mine.");
        }

        Collection<Cell> result = new HashSet<>();
        Queue<Cell> queue = new LinkedList<>();
        Cell current = new Cell(row, column);
        queue.add(current);

        while (!queue.isEmpty()) {

            current = queue.remove();
            row = current.getRow();
            column = current.getColumn();

            if (this.minesCells[row][column] == CellValue.Empty && !result.contains(current)) {
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

    private void placeMines() {

        for (int minesPlaced = 0; minesPlaced < this.minesCount; ) {

            int row = random.nextInt(this.rows);
            int column = random.nextInt(this.columns);

            if (this.minesCells[row][column] != CellValue.Mine) {
                this.minesCells[row][column] = CellValue.Mine;
                minesPlaced++;
            }
        }
    }

    private void placeHints() {
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                if (this.minesCells[row][column] != CellValue.Mine) {
                    this.minesCells[row][column] = this.minesNear(row, column);
                }
            }
        }
    }

    private boolean isOutside(int row, int column) {
        return row < 0 || row >= this.rows || column < 0 || column >= this.columns;
    }

    private CellValue minesNear(int row, int column) {

        int hintValue = 0;

        hintValue += this.getMine(row - 1, column - 1);  // NW
        hintValue += this.getMine(row - 1, column);      // N
        hintValue += this.getMine(row - 1, column + 1);  // NE
        hintValue += this.getMine(row, column - 1);      // W
        hintValue += this.getMine(row, column + 1);      // E
        hintValue += this.getMine(row + 1, column - 1);  // SW
        hintValue += this.getMine(row + 1, column);      // S
        hintValue += this.getMine(row + 1, column + 1);  // SE

        return CellValue.values()[hintValue];
    }

    private int getMine(int row, int column) {
        return this.isMine(row, column) ? 1 : 0;
    }

    private void addAdjacentCell(Queue<Cell> queue, int row, int column) {

        if (isOutside(row, column) || isMine(row, column)) {
            return;
        }

        queue.add(new Cell(row, column));
    }
}
