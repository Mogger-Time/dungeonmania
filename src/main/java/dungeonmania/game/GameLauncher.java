package dungeonmania.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import dungeonmania.dtos.ConfigDto;
import dungeonmania.dtos.DungeonsDto;
import dungeonmania.dtos.GoalConditionDto;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.goals.Goal;
import dungeonmania.goals.GoalFactory;
import dungeonmania.util.FileLoader;
import lombok.Getter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameLauncher {

    private static final List<String> savedGames = new ArrayList<>();
    private static final ObjectMapper objectMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    @Getter
    private static Game activeGame;
    @Getter
    private static ConfigDto config;
    @Getter
    private static List<Entity> entities = new ArrayList<>();

    public static Game createGame(String dungeonName, String configName) throws IOException {

        // Load the dungeon file
        DungeonsDto dungeon = loadDungeon(dungeonName);
        // Load the config file
        config = loadConfig(configName);
        // Load the entities
        entities = loadEntities(dungeon);
        // Load the goals
        Goal goal = loadGoals(dungeon.getGoalCondition());
        // Create the game
        activeGame = new Game(dungeonName, configName, entities, goal);

        return activeGame;
    }

    private static DungeonsDto loadDungeon(String dungeonName) throws IOException {
        return objectMapper.readValue(FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json"), DungeonsDto.class);
    }

    private static ConfigDto loadConfig(String configName) throws IOException {
        return objectMapper.readValue(FileLoader.loadResourceFile("/configs/" + configName + ".json"), ConfigDto.class);
    }

    private static List<Entity> loadEntities(DungeonsDto dungeonsDto) {
        List<Entity> entities = new ArrayList<>();
        dungeonsDto.getEntities().forEach(entity -> entities.add(EntityFactory.createEntity(entity)));
        return entities;
    }

    private static Goal loadGoals(GoalConditionDto goalConditionDto) {
        return GoalFactory.createGoal(goalConditionDto);
    }

    public static void saveGame(String gameName) {
        String gamesFolder = "games/";

        File directory = new File(gamesFolder);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        File file = new File(gamesFolder + gameName + ".json");

        try {
            objectMapper.writeValue(file, activeGame);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static Game loadGame(String gameName) {
        File file = new File("games/" + gameName + ".json");

        try {
            return objectMapper.readValue(file, Game.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Load the saved games
    public static List<String> loadSavedGames() {
        File file = new File("games/");
        File[] files = file.listFiles();
        assert files != null;
        for (File f : files) {
            savedGames.add(f.getName());
        }
        return savedGames;
    }

    public static void main(String[] args) {
        try {
            createGame("advanced", "M3_config");
            saveGame("advanced");
            Game game = loadGame("advanced");
            System.out.println(game.getPlayer());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}