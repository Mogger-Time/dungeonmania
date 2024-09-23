package dungeonmania.game.Deserializer;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import dungeonmania.entities.movingEntity.movementStrategy.AntiClockwiseStrategy;
import dungeonmania.entities.movingEntity.movementStrategy.ClockwiseStrategy;
import dungeonmania.entities.movingEntity.movementStrategy.FearStrategy;
import dungeonmania.entities.movingEntity.movementStrategy.FollowStrategy;
import dungeonmania.entities.movingEntity.movementStrategy.MovementStrategy;
import dungeonmania.entities.movingEntity.movementStrategy.RandomStrategy;

public class MovementStrategyDeserialilzer implements JsonDeserializer<MovementStrategy> {

    private String strategyName;
    private Gson gson;
    private Map<String, Class<? extends MovementStrategy>> strategyMap;

    public MovementStrategyDeserialilzer(String strategyName) {
        this.strategyName = strategyName;
        this.gson = new Gson();
        this.strategyMap = new HashMap<String, Class<? extends MovementStrategy>>() {{
            put("fear", FearStrategy.class);
            put("random", RandomStrategy.class);
            put("follow", FollowStrategy.class);
            put("clockwise", ClockwiseStrategy.class);
            put("anticlockwise", AntiClockwiseStrategy.class);
        }};
    }

    @Override
    public MovementStrategy deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        
        JsonObject strategyObject = json.getAsJsonObject();
        JsonElement strategyNameElement = strategyObject.get(strategyName);

        Class<? extends MovementStrategy> strategyClass = strategyMap.get(strategyNameElement.getAsString());

        return gson.fromJson(strategyObject, strategyClass);
    }
}
