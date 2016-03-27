package bg.softuni.minesweeper.model;

import java.util.Random;

public class Field {

    private static Random random = new Random();

    private int rows;
    private int columns;
    private int minesCount;
    private bg.softuni.minesweeper.model.Cell[][] minesCells;

    public Field(int rows, int columns, int minesCount) {

        this.rows = rows;
        this.columns = columns;
        this.minesCount = minesCount;
        this.minesCells = new bg.softuni.minesweeper.model.Cell[rows][columns];

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
        return getCell(row, column) == bg.softuni.minesweeper.model.Cell.Mine;
    }

    public bg.softuni.minesweeper.model.Cell getCell(int row, int column) {

        if (row < 0 || row >= this.rows || column < 0 || column >= this.columns) {
            return bg.softuni.minesweeper.model.Cell.Empty;
        }

        return this.minesCells[row][column];
    }

    private void placeMines() {

        for (int minesPlaced = 0; minesPlaced < this.minesCount; ) {

            int row = random.nextInt(this.rows);
            int column = random.nextInt(this.columns);

            if (this.minesCells[row][column] != bg.softuni.minesweeper.model.Cell.Mine) {
                this.minesCells[row][column] = bg.softuni.minesweeper.model.Cell.Mine;
                minesPlaced++;
            }
        }
    }

    private void placeHints() {
        for (int row = 0; row < this.rows; row++) {
            for (int column = 0; column < this.columns; column++) {
                if (this.minesCells[row][column] != bg.softuni.minesweeper.model.Cell.Mine) {
                    this.minesCells[row][column] = this.minesNear(row, column);
                }
            }
        }
    }

    private bg.softuni.minesweeper.model.Cell minesNear(int row, int column) {

        int hintValue = 0;

        hintValue += this.getMine(row - 1, column - 1);  // NW
        hintValue += this.getMine(row - 1, column);      // N
        hintValue += this.getMine(row - 1, column + 1);  // NE
        hintValue += this.getMine(row, column - 1);      // W
        hintValue += this.getMine(row, column + 1);      // E
        hintValue += this.getMine(row + 1, column - 1);  // SW
        hintValue += this.getMine(row + 1, column);      // S
        hintValue += this.getMine(row + 1, column + 1);  // SE

        return bg.softuni.minesweeper.model.Cell.values()[hintValue];
    }

    private int getMine(int row, int column) {
        return this.isMine(row, column) ? 1 : 0;
    }
}
