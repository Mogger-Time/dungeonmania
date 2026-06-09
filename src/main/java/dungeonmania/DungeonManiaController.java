package dungeonmania;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.game.Game;
import dungeonmania.game.GameLauncher;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;

import java.io.IOException;
import java.util.List;

public class DungeonManiaController {

    private static Game activeGame;

    /**
     * /dungeons
     */
    public static List<String> dungeons() {
        return FileLoader.listFileNamesInResourceDirectory("dungeons");
    }

    /**
     * /configs
     */
    public static List<String> configs() {
        return FileLoader.listFileNamesInResourceDirectory("configs");
    }

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    /**
     * /game/new
     */
    public DungeonResponse newGame(String dungeonName, String configName) throws IllegalArgumentException {
        try {
            activeGame = GameLauncher.createGame(dungeonName, configName);
            return activeGame.getDungeonResponse();
        } catch (IOException e) {
            throw new IllegalArgumentException("Invalid dungeon or config name");
        }
    }

    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {
        return activeGame.getDungeonResponse();
    }

    /**
     * /game/tick/item
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {
        activeGame.useItem(itemUsedId);
        activeGame.updateGame();

        return activeGame.getDungeonResponse();
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        activeGame.move(movementDirection);
        activeGame.updateGame();

        return activeGame.getDungeonResponse();
    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        activeGame.build(buildable);
        return activeGame.getDungeonResponse();
    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        activeGame.interact(entityId);
        return activeGame.getDungeonResponse();
    }

    /**
     * /game/save
     */
    public DungeonResponse saveGame(String name) throws IllegalArgumentException {
        GameLauncher.saveGame(name);
        return activeGame.getDungeonResponse();
    }

    /**
     * /game/load
     */
    public DungeonResponse loadGame(String name) throws IllegalArgumentException {
        Game loadedGame;
        loadedGame = GameLauncher.loadGame(name);
        activeGame = loadedGame;
        return loadedGame.getDungeonResponse();
    }

    /**
     * /games/all
     */
    public List<String> allGames() {
        return GameLauncher.loadSavedGames();
    }
}
