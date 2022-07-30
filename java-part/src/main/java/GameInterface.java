import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import model.GameState;
import model.GameStep;
import model.NewGameParams;

@Slf4j
public class GameInterface {

    static Game game;

    @SneakyThrows
    public static GameState startGame(String json) {
        final NewGameParams newGameParams = Shared.jsonMapper.readValue(json,NewGameParams.class);
        game = new Game(newGameParams.getSize(), newGameParams.getNumColors());
        return getGameState();
    }

    private static GameState getGameState() {
        return new GameState(game.getBoard(), game.checkWin(),game.getSteps(),game.getMaxSteps());
    }

    @SneakyThrows
    public static GameState flood(String json) {
        if(game==null)throw new NullPointerException("Game mut be started first");
        final GameStep gameStep = Shared.jsonMapper.readValue(json,GameStep.class);
        game.flood(gameStep.getColor());
        return getGameState();
    }
}
