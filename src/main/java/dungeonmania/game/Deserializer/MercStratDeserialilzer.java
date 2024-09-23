package dungeonmania.game.Deserializer;

import com.google.gson.*;
import dungeonmania.entities.movingEntity.mercStrategy.BribedStrat;
import dungeonmania.entities.movingEntity.mercStrategy.ControlledStrat;
import dungeonmania.entities.movingEntity.mercStrategy.EnemyStrat;
import dungeonmania.entities.movingEntity.mercStrategy.MercStrat;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class MercStratDeserialilzer implements JsonDeserializer<MercStrat> {

    private final String strategyName;
    private final Gson gson;
    private final Map<String, Class<? extends MercStrat>> strategyMap;

    public MercStratDeserialilzer(String strategyName) {
        this.strategyName = strategyName;
        this.gson = new Gson();
        this.strategyMap = new HashMap<>() {{
            put("bribed", BribedStrat.class);
            put("controlled", ControlledStrat.class);
            put("enemy", EnemyStrat.class);
        }};
    }

    @Override
    public MercStrat deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject strategyObject = json.getAsJsonObject();
        JsonElement strategyNameElement = strategyObject.get(strategyName);

        Class<? extends MercStrat> strategyClass = strategyMap.get(strategyNameElement.getAsString());

        return gson.fromJson(strategyObject, strategyClass);
    }
}
