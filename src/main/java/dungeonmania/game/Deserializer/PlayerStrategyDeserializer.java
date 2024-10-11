package dungeonmania.game.Deserializer;

import com.google.gson.*;
import dungeonmania.entities.movingEntity.playerStrategy.InvincibleStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.InvisibleStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.NormalStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.PlayerStrategy;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class PlayerStrategyDeserializer implements JsonDeserializer<PlayerStrategy> {

    private final String strategyName;
    private final Gson gson;
    private final Map<String, Class<? extends PlayerStrategy>> strategyMap;

    public PlayerStrategyDeserializer(String strategyName) {
        this.strategyName = strategyName;
        this.gson = new Gson();
        this.strategyMap = new HashMap<>() {{
            put("normal", NormalStrategy.class);
            put("invisible", InvisibleStrategy.class);
            put("invincible", InvincibleStrategy.class);
        }};
    }

    @Override
    public PlayerStrategy deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonObject = json.getAsJsonObject();
        JsonElement strategyElement = jsonObject.get(strategyName);

        Class<? extends PlayerStrategy> strategyClass = strategyMap.get(strategyElement.getAsString());

        return gson.fromJson(jsonObject, strategyClass);
    }
}

