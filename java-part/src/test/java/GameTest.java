import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTest {



    @Test
    public void testGame(){
        Game g  = new Game(3,4,"apple");
        assertArrayEquals(new short[]{
                1, 2, 1,
                1, 0, 3,
                1, 3, 2},g.getBoard());
        assertFalse(g.checkWin());
        assertEquals(3,g.getBoardDimensions());
        assertEquals(0,g.getSteps());
        g.flood((short) 2,0);
        assertArrayEquals(new short[]{
                2, 2, 1,
                2, 0, 3,
                2, 3, 2},g.getBoard());
        assertFalse(g.checkWin());
        g.flood((short) 3,1);
        assertArrayEquals(new short[]{
                2, 2, 1,
                2, 0, 3,
                2, 3, 3},g.getBoard());
        assertEquals(g.getScores().get(0).comboScore(),1);
        assertEquals(g.getScores().get(1).comboScore(),4);
        assertEquals(g.getScores().get(0).steps(),1);
        assertEquals(g.getScores().get(1).steps(),1);

    }

    @Test
    public void comboScorePropagates() {
        //below
        Game g = new Game(3, 4, new short[]{
                0, 2, 2,
                1, 2, 2,
                2, 2, 3});
        g.flood(2, 0);
        assertEquals(36,g.getScores().get(0).comboScore());
    }

    @Test
    public void testMoves(){
        //below
        Game g  = new Game(3,4,new short[]{
                0, 2, 2,
                1, 2, 2,
                2, 2, 3});
        g.flood(1,0);
        assertArrayEquals(new short[]{
                1, 2, 2,
                1, 2, 2,
                2, 2, 3},g.getBoard());

        //right
        g  = new Game(3,4,new short[]{
                0, 1, 2,
                2, 2, 2,
                2, 2, 3});
        g.flood(1,0);
        assertArrayEquals(new short[]{
                1, 1, 2,
                2, 2, 2,
                2, 2, 3},g.getBoard());

        //left
        g  = new Game(3,4,new short[]{
                0, 2, 2,
                2, 2, 2,
                2, 1, 3});
        g.flood(1,1);
        assertArrayEquals(new short[]{
                0, 2, 2,
                2, 2, 2,
                2, 1, 1},g.getBoard());

        //below
        g  = new Game(3,4,new short[]{
                0, 2, 2,
                2, 2, 1,
                2, 2, 3});
        g.flood(1,1);
        assertArrayEquals(new short[]{
                0, 2, 2,
                2, 2, 1,
                2, 2, 1},g.getBoard());

        //multi
        g  = new Game(3,4,new short[]{
                0, 2, 1,
                1, 2, 1,
                1, 1, 1});
        g.flood(1,0);
        assertArrayEquals(new short[]{
                1, 2, 1,
                1, 2, 1,
                1, 1, 1},g.getBoard());
        g.flood(2,0);
        assertArrayEquals(new short[]{
                2, 2, 2,
                2, 2, 2,
                2, 2, 2},g.getBoard());
        assertTrue(g.checkWin());

    }
}
