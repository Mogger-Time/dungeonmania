package dungeonmania.entities.staticEntity;

import java.util.List;

import org.json.JSONObject;

import dungeonmania.entities.items.Item;
import dungeonmania.entities.items.Key;
import dungeonmania.entities.items.SunStone;
import dungeonmania.entities.movingEntity.Player;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public class Door extends StaticEntity {

    private int keyID;

    public Door() {
        super();
        setName("door");
        setCollision(true);
    }

    public int getKeyID() {
        return keyID;
    }

    public boolean unlockDoor(int keyID) {
        if (keyID == this.keyID || keyID == -1) {
            setCollision(false);
            return true;
        }
        return false;
    }

    @Override
    public void premove(Player player, Direction direction) {
        List<Item> inventory = player.getInventory();
        for (Item item : inventory) {
            if (item instanceof Key) {
                Key temp = (Key) item;
                if (unlockDoor(temp.getKeyid())) {
                    inventory.remove(item);
                    break;
                }
            }
            else if (item instanceof SunStone) {
                unlockDoor(-1); 
                break;
            }
        }
    }

    @Override
    public void setupEntity(JSONObject entityConfig, Position position) {
        this.keyID = entityConfig.getInt("key");
        setPosition(position);
    }
}
