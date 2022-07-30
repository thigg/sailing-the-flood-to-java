import java.io.IOException;

import com.fasterxml.jackson.dataformat.cbor.databind.CBORMapper;
import model.GameState;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class InterfaceTest {

    CBORMapper cbor = new CBORMapper();
    @Test
    public void test() throws IOException {
        final GameState o = GameInterface.startGame("{\"size\":5,\"numColors\":4}");
        assertFalse(o.isWon());
        assertEquals(5, o.getField().length);
        final GameState oUnCBORd = cbor.readValue(cbor.writeValueAsBytes(o), GameState.class);
        assertFalse(oUnCBORd.isWon());
        assertEquals(5, oUnCBORd.getField().length);
        assertEquals(5, oUnCBORd.getField()[0].length);
    }
}
