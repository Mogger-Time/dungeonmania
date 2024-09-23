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

import dungeonmania.entities.movingEntity.playerStrategy.InvincibleStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.InvisibleStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.NormalStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.PlayerStrategy;

public class PlayerStrategyDeserializer implements JsonDeserializer<PlayerStrategy> {

    private String strategyName;
    private Gson gson;
    private Map<String, Class<? extends PlayerStrategy>> strategyMap;

    public PlayerStrategyDeserializer(String strategyName) {
        this.strategyName = strategyName;
        this.gson = new Gson();
        this.strategyMap = new HashMap<String, Class<? extends PlayerStrategy>>() {{
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

