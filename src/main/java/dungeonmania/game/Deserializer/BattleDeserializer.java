package dungeonmania.game.Deserializer;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import dungeonmania.Battle;
import dungeonmania.Round;
import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntity.Enemy;

import java.lang.reflect.Type;
import java.util.List;

public class BattleDeserializer implements JsonDeserializer<Battle> {

    private final Gson gson;

    public BattleDeserializer() {
        this.gson = new Gson();
    }

    @Override
    public Battle deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject jsonElement = json.getAsJsonObject();
        JsonElement roundsNameElement = jsonElement.get("rounds");
        String enemyString = jsonElement.get("enemy").toString();

        Battle battle = gson.fromJson(jsonElement, Battle.class);
        //battle.setRounds(loadRoundsFromJSON(roundsNameElement));
        battle.setEnemy(loadEnemyFromJSON(enemyString));

        return battle;
    }

    private Enemy loadEnemyFromJSON(String enemyString) {

        EntityDeserializer entityDeserializer = new EntityDeserializer("name");

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Entity.class, entityDeserializer)
                .create();

        Entity entity = gson.fromJson(enemyString, Entity.class);

        return (Enemy) entity;
    }

    private List<Round> loadRoundsFromJSON(JsonElement roundsNameElement) {

        RoundDeserializer deserializer = new RoundDeserializer();

        Gson gson = new GsonBuilder()
                .registerTypeAdapter(Round.class, deserializer)
                .create();

        return gson.fromJson(roundsNameElement, new TypeToken<List<Round>>() {
        }.getType());
    }
}
