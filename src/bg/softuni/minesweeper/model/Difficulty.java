package bg.softuni.minesweeper.model;


public enum Difficulty {

    Easy(8, 8, 10),
    Medium(16, 16, 40),
    Hard(16, 30, 99),
    ExtremelyHard(28, 60, 200);
    private int rows;
    private int columns;
    private int minesCount;

    Difficulty(int rows, int columns, int minesCount) {
        this.rows = rows;
        this.columns = columns;
        this.minesCount = minesCount;
    }

    public Field createField() {
        return new Field(rows, columns, minesCount);
    }
}
