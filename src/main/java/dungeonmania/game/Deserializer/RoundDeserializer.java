package dungeonmania.game.Deserializer;

import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import dungeonmania.Round;
import dungeonmania.game.GameLauncher;

public class RoundDeserializer implements JsonDeserializer<Round> {
    
    private Gson gson;

    public RoundDeserializer() {
        this.gson = new Gson();
    }

    @Override
    public Round deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {
        
        JsonObject jsonElement = json.getAsJsonObject();
        String itemString = jsonElement.get("itemsused").toString();
        
        Round round = gson.fromJson(jsonElement, Round.class);
        round.setItemsUsed(GameLauncher.loadItemsFromJSON(itemString));

        return round;
    }
}
