import Game.model.GameState;
import com.google.flatbuffers.FlatBufferBuilder;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import Game.model.GameStateT;
import Game.model.GameStep;
import Game.model.NewGameParams;

@Slf4j
public class GameInterface {

    static Game game;

    @SneakyThrows
    public static byte[] startGame(String json) {
        final NewGameParams newGameParams = Shared.jsonMapper.readValue(json, NewGameParams.class);
        game = new Game(newGameParams.getSize(), newGameParams.getNumColors());
        return getGameState();
    }

    private static byte[] getGameState() {
        GameStateT g = new GameStateT();
        g.setField(game.getBoard());
        g.setWon(game.checkWin());
        g.setSteps(game.getSteps());
        g.setMaxSteps(game.getMaxSteps());
        g.setWidth(game.getBoardDimensions());
        g.setHeight(game.getBoardDimensions());
        return g.serializeToBinary();
    }

    @SneakyThrows
    public static byte[] flood(String json) {
        if (game == null) {
            throw new NullPointerException("Game mut be started first");
        }
        final GameStep gameStep = Shared.jsonMapper.readValue(json, GameStep.class);
        game.flood((byte) gameStep.getColor());
        return getGameState();
    }
}
