package dungeonmania.game.Deserializer;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import dungeonmania.entities.Entity;
import dungeonmania.entities.items.Arrow;
import dungeonmania.entities.items.Bomb;
import dungeonmania.entities.items.InvincibilityPotion;
import dungeonmania.entities.items.InvisibilityPotion;
import dungeonmania.entities.items.Key;
import dungeonmania.entities.items.SunStone;
import dungeonmania.entities.items.Sword;
import dungeonmania.entities.items.Treasure;
import dungeonmania.entities.items.Wood;
import dungeonmania.entities.movingEntity.Assassin;
import dungeonmania.entities.movingEntity.Enemy;
import dungeonmania.entities.movingEntity.Hydra;
import dungeonmania.entities.movingEntity.Mercenary;
import dungeonmania.entities.movingEntity.Player;
import dungeonmania.entities.movingEntity.Spider;
import dungeonmania.entities.movingEntity.ZombieToast;
import dungeonmania.entities.movingEntity.mercStrategy.MercStrat;
import dungeonmania.entities.movingEntity.movementStrategy.MovementStrategy;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.Door;
import dungeonmania.entities.staticEntity.Exit;
import dungeonmania.entities.staticEntity.FloorSwitch;
import dungeonmania.entities.staticEntity.Portal;
import dungeonmania.entities.staticEntity.SwampTile;
import dungeonmania.entities.staticEntity.Wall;
import dungeonmania.entities.staticEntity.ZombieToastSpawner;

public class EntityDeserializer implements JsonDeserializer<Entity> {

    private String entityName;
    private Gson gson;
    private Map<String, Class<? extends Entity>> entityMap;

    public EntityDeserializer(String entityName) {
        this.entityName = entityName;
        this.gson = new Gson();
        this.entityMap = new HashMap<String, Class<? extends Entity>>() {{
            
            put("player", Player.class);
            
            // Static entities
            put("wall", Wall.class);
            put("exit", Exit.class);
            put("boulder", Boulder.class);
            put("switch", FloorSwitch.class);
            put("door", Door.class);
            put("portal", Portal.class);
            put("swamp_tile", SwampTile.class);
            put("zombie_toast_spawner", ZombieToastSpawner.class);

            // Moving entities
            put("spider", Spider.class);
            put("zombie_toast", ZombieToast.class);
            put("mercenary", Mercenary.class);
            put("assassin", Assassin.class);
            put("hydra", Hydra.class);

            // Items
            put("treasure", Treasure.class);
            put("key", Key.class);
            put("invincibility_potion", InvincibilityPotion.class);
            put("invisibility_potion", InvisibilityPotion.class);
            put("wood", Wood.class);
            put("arrow", Arrow.class);
            put("bomb", Bomb.class);
            put("sword", Sword.class);
            put("sun_stone", SunStone.class);
        }};
    }

    @Override
    public Entity deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        JsonObject entityObject = json.getAsJsonObject();
        JsonElement entityNameElement = entityObject.get(entityName);
        
        Class<? extends Entity> entityType = entityMap.get(entityNameElement.getAsString());
        if (entityType == null) {
            throw new JsonParseException("Unknown entity type: " + entityNameElement.getAsString());
        }
        Entity entity = gson.fromJson(entityObject, entityType);
        if (entity instanceof Enemy) {
            ((Enemy) entity).setStrategy(loadMovementStrategyFromJSON(entityObject));
        }
        if (entity instanceof Mercenary) {
            ((Mercenary) entity).setAllystatus(loadMercStratFromJSON(entityObject));
        }
        return entity;
    }

    private static MovementStrategy loadMovementStrategyFromJSON(JsonObject jsonObject) {
        String strategyString = jsonObject.get("strategy").toString();
        
        MovementStrategyDeserialilzer deserializer = new MovementStrategyDeserialilzer("strategyName");

        Gson gson = new GsonBuilder()
            .registerTypeAdapter(MovementStrategy.class, deserializer)
            .create();

        return gson.fromJson(strategyString, MovementStrategy.class);
    }

    private static MercStrat loadMercStratFromJSON(JsonObject jsonObject) {
        String strategyString = jsonObject.get("allystatus").toString();

        MercStratDeserialilzer deserializer = new MercStratDeserialilzer("strategyName");

        Gson gson = new GsonBuilder()
            .registerTypeAdapter(MercStrat.class, deserializer)
            .create();

        return gson.fromJson(strategyString, MercStrat.class);
    }
}
