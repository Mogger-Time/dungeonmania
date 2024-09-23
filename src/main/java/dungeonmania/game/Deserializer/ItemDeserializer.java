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

import dungeonmania.entities.items.Arrow;
import dungeonmania.entities.items.Bomb;
import dungeonmania.entities.items.Bow;
import dungeonmania.entities.items.InvincibilityPotion;
import dungeonmania.entities.items.InvisibilityPotion;
import dungeonmania.entities.items.Item;
import dungeonmania.entities.items.Key;
import dungeonmania.entities.items.Shield;
import dungeonmania.entities.items.SunStone;
import dungeonmania.entities.items.Sword;
import dungeonmania.entities.items.Treasure;
import dungeonmania.entities.items.Wood;

public class ItemDeserializer implements JsonDeserializer<Item> {

    private String itemName;
    private Gson gson;
    private Map<String, Class<? extends Item>> itemMap;

    public ItemDeserializer(String itemName) {
        this.itemName = itemName;
        this.gson = new Gson();
        this.itemMap = new HashMap<String, Class<? extends Item>>() {{
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
