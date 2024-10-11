package dungeonmania.game.Deserializer;

import com.google.gson.*;
import dungeonmania.Round;
import dungeonmania.game.GameLauncher;

import java.lang.reflect.Type;

public class RoundDeserializer implements JsonDeserializer<Round> {

    private final Gson gson;

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
