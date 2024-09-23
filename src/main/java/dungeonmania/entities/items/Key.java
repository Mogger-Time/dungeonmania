package dungeonmania.entities.items;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.entities.movingEntity.Player;
import dungeonmania.util.Position;

public class Key extends Item {
    private int keyid;

    public Key () {
        super();
        setName("key");
    }

    public int getKeyid() {
        return this.keyid;
    }

    public void setKeyid(int keyid) {
        this.keyid = keyid;
    }

    @Override 
    public void setupEntity(JSONObject entityConfig, Position position) {
        super.setPosition(position);
        this.keyid = entityConfig.getInt("key");
    }

    @Override
    public boolean interact(Player player) {
        List<Item> inv = player.getInventory();
        for (Item item : inv) {
            if (item instanceof Key) {
                return false;
            }
        }
        return super.interact(player);
    }
}
