package dungeonmania.game.Deserializer;

import com.google.gson.*;
import dungeonmania.entities.movingEntity.movementStrategy.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MovementStrategyDeserialilzer implements JsonDeserializer<MovementStrategy> {

    private final String strategyName;
    private final Gson gson;
    private final Map<String, Class<? extends MovementStrategy>> strategyMap;

    public MovementStrategyDeserialilzer(String strategyName) {
        this.strategyName = strategyName;
        this.gson = new Gson();
        this.strategyMap = new HashMap<>() {{
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
