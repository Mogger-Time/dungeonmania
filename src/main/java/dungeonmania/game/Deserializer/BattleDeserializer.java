package dungeonmania.game.Deserializer;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import dungeonmania.Battle;
import dungeonmania.Round;
import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntity.Enemy;

public class BattleDeserializer implements JsonDeserializer<Battle> {

    private Gson gson;

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

        List<Round> battleRounds = gson.fromJson(roundsNameElement, new TypeToken<List<Round>>() {}.getType());

        return battleRounds;
    }
}
