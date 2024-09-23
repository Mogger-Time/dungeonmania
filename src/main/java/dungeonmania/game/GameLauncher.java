package dungeonmania.game;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import dungeonmania.Battle;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.items.Item;
import dungeonmania.entities.movingEntity.Mercenary;
import dungeonmania.entities.movingEntity.Player;
import dungeonmania.entities.movingEntity.playerStrategy.PlayerStrategy;
import dungeonmania.game.Deserializer.BattleDeserializer;
import dungeonmania.game.Deserializer.EntityDeserializer;
import dungeonmania.game.Deserializer.ItemDeserializer;
import dungeonmania.game.Deserializer.PlayerStrategyDeserializer;
import dungeonmania.util.FileLoader;
import dungeonmania.goals.Goal;
import dungeonmania.goals.GoalFactory;

public class GameLauncher {

    private static Game activeGame;
    private static JSONObject dungeon;
    private static JSONObject config;
    private static List<Entity> entities = new ArrayList<Entity>();
    private static Goal goal;
    private static List<String> savedGames = new ArrayList<>();
    private static String gamePath = "./bin/";

    public static Game createGame(String dungeonName, String configName) throws IOException {
        // Load the dungone file
        dungeon = loadDungeon(dungeonName);
        // Load the config file
        config = loadConfig(configName);
        // Load the entities
        entities = loadEntities(dungeon);
        // Load the goals
        goal = loadGoals(dungeon);
        // Create the game
        activeGame = new Game(dungeonName, configName, entities, goal);

        return activeGame;
    }

    private static JSONObject loadDungeon(String dungeonName) throws IOException {
        String dungeonFileString = FileLoader.loadResourceFile("/dungeons/" + dungeonName + ".json");
        return new JSONObject(dungeonFileString);
    }

    private static JSONObject loadConfig(String configName) throws IOException {
        String configFileString = FileLoader.loadResourceFile("/configs/" + configName + ".json");
        return new JSONObject(configFileString);
    }

    private static List<Entity> loadEntities(JSONObject dungeon) {
        List<Entity> entities = new ArrayList<Entity>();

        JSONArray entitiesArray = dungeon.getJSONArray("entities");

        for (int i = 0; i < entitiesArray.length(); i++) {

            JSONObject entityObject = entitiesArray.getJSONObject(i);

            Entity entity = EntityFactory.createEntity(entityObject);

            entities.add(entity);
        }
        return entities;
    }

    private static Goal loadGoals(JSONObject dungeon) {
        return GoalFactory.createGoal(dungeon.getJSONObject("goal-condition"));
    }

    public static Game getActiveGame() {
        return activeGame;
    }

    public static JSONObject getDungeon() {
        return dungeon;
    }

    public static JSONObject getConfig() {
        return config;
    }

    public static List<Entity> getEntities() {
        return entities;
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
        writer.write(dungeon.getJSONObject("goal-condition").toString());
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
        String entityString = new String(Files.readAllBytes(Paths.get(savePath + "/entities.json")), StandardCharsets.UTF_8);
        List<Entity> savedEntities = loadEntitiesFromJSON(entityString);

        // Load the goal
        String goalString = new String(Files.readAllBytes(Paths.get(savePath + "/goal.json")), StandardCharsets.UTF_8);
        Goal goal = GoalFactory.createGoal(new JSONObject(goalString));

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
        String inventoryString = new String(Files.readAllBytes(Paths.get(savePath + "/playerInventory.json")), StandardCharsets.UTF_8);
        player.setInventory(loadItemsFromJSON(inventoryString));

        // Load the player strategy
        String strategyString = new String(Files.readAllBytes(Paths.get(savePath + "/playerStrategy.json")), StandardCharsets.UTF_8);
        player.setStrategy(loadPlayerStrategyFromJSON(strategyString));

        // Load the player queued strategies
        String queuedStrategiesString = new String(Files.readAllBytes(Paths.get(savePath + "/playerQueuedStrategies.json")), StandardCharsets.UTF_8);
        player.setQueuedStrategies(loadPlayerStrategiesFromJSON(queuedStrategiesString));

        // Load the player ally mercenaries
        String allyString = new String(Files.readAllBytes(Paths.get(savePath + "/playerAlly.json")), StandardCharsets.UTF_8);
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

        List<Mercenary> ally = gson.fromJson(playerAllyString, new TypeToken<List<Mercenary>>() {}.getType());

        return ally;
    }

    private static List<PlayerStrategy> loadPlayerStrategiesFromJSON(String strategyString) {
        
        PlayerStrategyDeserializer deserializer = new PlayerStrategyDeserializer("strategyName");

        Gson gson = new GsonBuilder()
            .registerTypeAdapter(PlayerStrategy.class, deserializer)
            .create();

        List<PlayerStrategy> strategies = gson.fromJson(strategyString, new TypeToken<List<PlayerStrategy>>() {}.getType());

        return strategies;
    }

    public static List<Item> loadItemsFromJSON(String itemString) {
        
        ItemDeserializer itemDeserializer = new ItemDeserializer("name");
        
        Gson gson = new GsonBuilder()
            .registerTypeAdapter(Item.class, itemDeserializer)
            .create();

        List<Item> items = gson.fromJson(itemString, new TypeToken<List<Item>>() {}.getType());

        return items;
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

        List<Entity> entities = gson.fromJson(entityString, new TypeToken<List<Entity>>() {}.getType());

        return entities;
    }

    public static int getSpiderSpawnRate() {
        return config.getInt("spider_spawn_rate");
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