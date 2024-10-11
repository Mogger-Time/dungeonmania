package dungeonmania.game.Deserializer;

import com.google.gson.*;
import dungeonmania.entities.items.*;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class ItemDeserializer implements JsonDeserializer<Item> {

    private final String itemName;
    private final Gson gson;
    private final Map<String, Class<? extends Item>> itemMap;

    public ItemDeserializer(String itemName) {
        this.itemName = itemName;
        this.gson = new Gson();
        this.itemMap = new HashMap<>() {{
            put("treasure", Treasure.class);
            put("key", Key.class);
            put("invincibility_potion", InvincibilityPotion.class);
            put("invisibility_potion", InvisibilityPotion.class);
            put("wood", Wood.class);
            put("arrow", Arrow.class);
            put("bomb", Bomb.class);
            put("sword", Sword.class);
            put("sun_stone", SunStone.class);
            put("shield", Shield.class);
            put("bow", Bow.class);
            // put("midnight_armour", MidnightArmour.class);
            // put("sceptre", Sceptre.class);
        }};
    }

    @Override
    public Item deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
            throws JsonParseException {

        JsonObject itemObject = json.getAsJsonObject();
        JsonElement itemNameElement = itemObject.get(itemName);

        Class<? extends Item> itemClass = itemMap.get(itemNameElement.getAsString());

        return gson.fromJson(json, itemClass);
    }

}
