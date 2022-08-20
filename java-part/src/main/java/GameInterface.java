import Game.model.GameState;
import Game.model.ScoreT;
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
        final int[] steps = game.getScores().values().stream().mapToInt(Game.Score::steps).toArray();
        log.debug("steplength: {}",steps.length);
        g.setSteps(steps);
        g.setMaxSteps(game.getMaxSteps());
        g.setWidth(game.getBoardDimensions());
        g.setHeight(game.getBoardDimensions());
        g.setScores(game.getScores().values().stream().map(s->{
            ScoreT ret = new ScoreT();
            ret.setPlayer(s.player());
            ret.setComboScore(s.comboScore());
            ret.setSteps(s.steps());
            return ret;
        }).toArray(ScoreT[]::new));
        return g.serializeToBinary();
    }

    @SneakyThrows
    public static byte[] flood(String json) {
        if (game == null) {
            throw new NullPointerException("Game must be started first");
        }
        final GameStep gameStep = Shared.jsonMapper.readValue(json, GameStep.class);
        game.flood((short) gameStep.getColor(),gameStep.getPlayer());
        log.info("flooded {}",gameStep);
        return getGameState();
    }
}
