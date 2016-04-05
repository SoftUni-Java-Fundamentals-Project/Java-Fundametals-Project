package bg.softuni.poosweeper.model;


public enum Difficulty {

    Easy(8, 8, 10),
    Medium(16, 16, 40),
    Hard(16, 30, 99),
    ExtremelyHard(28, 60, 200);
    private int rows;
    private int columns;
    private int pooCount;

    Difficulty(int rows, int columns, int pooCount) {
        this.rows = rows;
        this.columns = columns;
        this.pooCount = pooCount;
    }

    public Field createField() {
        return new Field(rows, columns, pooCount);
    }
}
