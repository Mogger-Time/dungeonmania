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

import dungeonmania.entities.movingEntity.mercStrategy.BribedStrat;
import dungeonmania.entities.movingEntity.mercStrategy.ControlledStrat;
import dungeonmania.entities.movingEntity.mercStrategy.EnemyStrat;
import dungeonmania.entities.movingEntity.mercStrategy.MercStrat;

public class MercStratDeserialilzer implements JsonDeserializer<MercStrat> {

    private String strategyName;
    private Gson gson;
    private Map<String, Class<? extends MercStrat>> strategyMap;

    public MercStratDeserialilzer(String strategyName) {
        this.strategyName = strategyName;
        this.gson = new Gson();
        this.strategyMap = new HashMap<String, Class<? extends MercStrat>>() {{
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
