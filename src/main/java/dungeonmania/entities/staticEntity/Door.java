package dungeonmania.entities.staticEntity;

import dungeonmania.dtos.EntitiesDto;
import dungeonmania.entities.items.Item;
import dungeonmania.entities.items.Key;
import dungeonmania.entities.items.SunStone;
import dungeonmania.entities.movingEntity.Player;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import lombok.Getter;

import java.util.List;

@Getter
public class Door extends StaticEntity {

    private int keyID;

    public Door() {
        super();
        setName("door");
        setCollision(true);
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
            } else if (item instanceof SunStone) {
                unlockDoor(-1);
                break;
            }
        }
    }

    @Override
    public void setupEntity(EntitiesDto entitiesDto, Position position) {
        this.keyID = entitiesDto.getKey();
        setPosition(position);
    }
}
