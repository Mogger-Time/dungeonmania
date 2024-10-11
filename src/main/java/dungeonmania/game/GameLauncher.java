package dungeonmania.game;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import dungeonmania.dtos.ConfigDto;
import dungeonmania.dtos.DungeonsDto;
import dungeonmania.dtos.GoalConditionDto;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.items.Item;
import dungeonmania.entities.movingEntity.Mercenary;
import dungeonmania.entities.movingEntity.Player;
import dungeonmania.entities.movingEntity.playerStrategy.PlayerStrategy;
import dungeonmania.game.Deserializer.EntityDeserializer;
import dungeonmania.game.Deserializer.ItemDeserializer;
import dungeonmania.game.Deserializer.PlayerStrategyDeserializer;
import dungeonmania.goals.Goal;
import dungeonmania.goals.GoalFactory;
import dungeonmania.util.FileLoader;
import lombok.Getter;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class GameLauncher {

    private static final List<String> savedGames = new ArrayList<>();
    private static final String gamePath = "./bin/";
    private static final ObjectMapper objectMapper = new ObjectMapper().setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
    @Getter
    private static Game activeGame;
    private static DungeonsDto dungeon;
    @Getter
    private static ConfigDto config;
    @Getter
    private static List<Entity> entities = new ArrayList<>();

    public static Game createGame(String dungeonName, String configName) throws IOException {

        // Load the dungeon file
        dungeon = loadDungeon(dungeonName);
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

    public static void gameToJSON(String gameName) throws IOException {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        String gamesFolder = gamePath + "games/";

        // Create the folder if it does not exists
        File folder = new File(gamesFolder);
        if (!folder.exists()) {
            folder.mkdir();
        }

        // Create the new game folder if it does not exists
        String savePath = gamesFolder + gameName;
        File gameFolder = new File(savePath);
        if (!gameFolder.exists()) {
            gameFolder.mkdir();
        }

        // Save the dungeon
        FileWriter writer = new FileWriter(savePath + "/dungeonName.json");
        gson.toJson(activeGame.getDungeonName(), writer);
        writer.close();

        // Save the config
        writer = new FileWriter(savePath + "/configName.json");
        gson.toJson(activeGame.getConfigName(), writer);
        writer.close();

        // Save the entities
        writer = new FileWriter(savePath + "/entities.json");
        gson.toJson(entities, writer);
        writer.close();

        // Save the goal
        writer = new FileWriter(savePath + "/goal.json");
        writer.write(dungeon.getGoalCondition().toString());
        writer.close();

        // Save the player
        writer = new FileWriter(savePath + "/player.json");
        gson.toJson(activeGame.getPlayer(), writer);
        writer.close();

        // save the player strategy
        writer = new FileWriter(savePath + "/playerStrategy.json");
        gson.toJson(activeGame.getPlayer().getStrategy(), writer);
        writer.close();

        // save the player queued strategies
        writer = new FileWriter(savePath + "/playerQueuedStrategies.json");
        gson.toJson(activeGame.getPlayer().getQueuedStrategies(), writer);
        writer.close();

        // Save the player inventory
        writer = new FileWriter(savePath + "/playerInventory.json");
        gson.toJson(activeGame.getPlayer().getInventory(), writer);
        writer.close();

        // Save the player battles
        // writer = new FileWriter(savePath + "/playerBattles.json");
        // gson.toJson(activeGame.getPlayer().getBattles(), writer);
        // writer.close();

        // Save the player ally mercenaries
        writer = new FileWriter(savePath + "/playerAlly.json");
        gson.toJson(activeGame.getPlayer().getAlly(), writer);
        writer.close();

        savedGames.add(gameName);
    }

    public static Game JSONtoGame(String gameName) throws IOException {
        Gson gson = new Gson();

        String savePath = gamePath + "games/" + gameName;

        // Load the dungeonName from the file
        Reader reader = new FileReader(savePath + "/dungeonName.json");
        String dungeonName = gson.fromJson(reader, String.class);
        dungeon = loadDungeon(dungeonName);

        // Load the configName from the file
        reader = new FileReader(savePath + "/configName.json");
        String configName = gson.fromJson(reader, String.class);
        config = loadConfig(configName);

        // Load the entities
        String entityString = Files.readString(Paths.get(savePath + "/entities.json"));
        List<Entity> savedEntities = loadEntitiesFromJSON(entityString);

        // Load the goal
        String goalString = Files.readString(Paths.get(savePath + "/goal.json"));
        Goal goal = GoalFactory.createGoal(new GoalConditionDto());

        // create new game
        Game game = new Game(dungeonName, configName, savedEntities, goal);

        // Load the player
        game.setPlayer(loadPlayerFromJSON(savePath, gameName));

        activeGame = game;

        return game;
    }

    private static Player loadPlayerFromJSON(String savePath, String gameName) throws IOException {
        // Load the player
        Gson gson = new Gson();

        Reader reader = new FileReader(savePath + "/player.json");
        Player player = gson.fromJson(reader, Player.class);

        // Load the player inventory
        String inventoryString = Files.readString(Paths.get(savePath + "/playerInventory.json"));
        player.setInventory(loadItemsFromJSON(inventoryString));

        // Load the player strategy
        String strategyString = Files.readString(Paths.get(savePath + "/playerStrategy.json"));
        player.setStrategy(loadPlayerStrategyFromJSON(strategyString));

        // Load the player queued strategies
        String queuedStrategiesString = Files.readString(Paths.get(savePath + "/playerQueuedStrategies.json"));
        player.setQueuedStrategies(loadPlayerStrategiesFromJSON(queuedStrategiesString));

        // Load the player ally mercenaries
        String allyString = Files.readString(Paths.get(savePath + "/playerAlly.json"));
        player.setAlly(loadAllyFromJSON(allyString));

        // // Load the player battles
        // String playerBattlesString = FileLoader.loadResourceFile("/games/" + gameName + "/playerBattles.json");
        // player.setBattles(loadBattlesFromJSON(playerBattlesString));

        return player;
    }

    // private static List<Battle> loadBattlesFromJSON(String playerBattlesString) {

    //     BattleDeserializer deserializer = new BattleDeserializer();

    //     Gson gson = new GsonBuilder()
    //         .registerTypeAdapter(Battle.class, deserializer)
    //         .create();

    //     List<Battle> playerBattles = gson.fromJson(playerBattlesString, new TypeToken<List<Battle>>() {}.getType());

    //     return playerBattles;
    // }

    private static List<Mercenary> loadAllyFromJSON(String playerAllyString) {

        EntityDeserializer deserializer = new EntityDeserializer("name");

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Entity.class, deserializer)
                .create();

        return gson.fromJson(playerAllyString, new TypeToken<List<Mercenary>>() {
        }.getType());
    }

    private static List<PlayerStrategy> loadPlayerStrategiesFromJSON(String strategyString) {

        PlayerStrategyDeserializer deserializer = new PlayerStrategyDeserializer("strategyName");

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(PlayerStrategy.class, deserializer)
                .create();

        return gson.fromJson(strategyString, new TypeToken<List<PlayerStrategy>>() {
        }.getType());
    }

    public static List<Item> loadItemsFromJSON(String itemString) {

        ItemDeserializer itemDeserializer = new ItemDeserializer("name");

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Item.class, itemDeserializer)
                .create();

        return gson.fromJson(itemString, new TypeToken<List<Item>>() {
        }.getType());
    }

    private static PlayerStrategy loadPlayerStrategyFromJSON(String playerStrategyString) {

        PlayerStrategyDeserializer deserializer = new PlayerStrategyDeserializer("strategyName");

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(PlayerStrategy.class, deserializer)
                .create();

        return gson.fromJson(playerStrategyString, PlayerStrategy.class);
    }

    private static List<Entity> loadEntitiesFromJSON(String entityString) {

        EntityDeserializer entityDeserializer = new EntityDeserializer("name");

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Entity.class, entityDeserializer)
                .create();

        return gson.fromJson(entityString, new TypeToken<List<Entity>>() {
        }.getType());
    }

    public static int getSpiderSpawnRate() {
        return config.getSpiderSpawnRate();
    }

    // Load the saved games
    public static List<String> loadSavedGames() {
        File file = new File(gamePath);
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                savedGames.add(f.getName());
            }
        }
        return savedGames;
    }

    public static void main(String[] args) {
        try {
            createGame("advanced", "M3_config");
            gameToJSON("advanced");
            Game game = JSONtoGame("advanced");
            System.out.println(game.getPlayer());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}