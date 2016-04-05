package bg.softuni.poosweeper.model;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FieldTest {

    private static final int ROWS = 20;
    private static final int COLUMNS = 25;
    private static final int POO_COUNT = 10;

    private Field field;

    @Before
    public void setUp() throws Exception {
        this.field = new Field(ROWS, COLUMNS, POO_COUNT);
    }

    @Test
    public void getRows() throws Exception {
        assertEquals(this.field.getRows(), ROWS);
    }

    @Test
    public void getColumns() throws Exception {
        assertEquals(this.field.getColumns(), COLUMNS);
    }

    @Test
    public void getPooCount() throws Exception {
        assertEquals(this.field.getPooCount(), POO_COUNT);
    }
}