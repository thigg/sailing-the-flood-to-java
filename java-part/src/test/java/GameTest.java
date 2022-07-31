import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameTest {


    @Test
    public void testGame(){
        Game g  = new Game(2,4,"apple");
        assertArrayEquals(new short[]{0,0,0,3},g.getBoard());
        assertFalse(g.checkWin());
        assertEquals(2,g.getBoardDimensions());
        assertEquals(0,g.getSteps());
        g.flood((short) 3);
        assertArrayEquals(new short[]{3,3,3,3},g.getBoard());
        assertTrue(g.checkWin());

    }
}
