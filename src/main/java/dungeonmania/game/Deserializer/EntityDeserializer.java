package dungeonmania.game.Deserializer;

import com.google.gson.*;
import dungeonmania.entities.Entity;
import dungeonmania.entities.items.*;
import dungeonmania.entities.movingEntity.*;
import dungeonmania.entities.movingEntity.mercStrategy.MercStrat;
import dungeonmania.entities.movingEntity.movementStrategy.MovementStrategy;
import dungeonmania.entities.staticEntity.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class EntityDeserializer implements JsonDeserializer<Entity> {

    private final String entityName;
    private final Gson gson;
    private final Map<String, Class<? extends Entity>> entityMap;

    public EntityDeserializer(String entityName) {
        this.entityName = entityName;
        this.gson = new Gson();
        this.entityMap = new HashMap<>() {{

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
}
