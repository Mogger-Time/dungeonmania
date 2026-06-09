package dungeonmania.game;

import dungeonmania.dtos.ConfigDto;

public final class GameRuntimeContext {
    private static ConfigDto config;
    private static Game activeGame;

    private GameRuntimeContext() {
    }

    public static ConfigDto getConfig() {
        return config;
    }

    public static void setConfig(ConfigDto config) {
        GameRuntimeContext.config = config;
    }

    public static Game getActiveGame() {
        return activeGame;
    }

    public static void setActiveGame(Game activeGame) {
        GameRuntimeContext.activeGame = activeGame;
    }
}
