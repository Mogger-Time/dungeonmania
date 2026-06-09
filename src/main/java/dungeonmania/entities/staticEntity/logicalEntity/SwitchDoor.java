package dungeonmania.entities.staticEntity.logicalEntity;

import dungeonmania.dtos.EntitiesDto;
import dungeonmania.entities.items.Item;
import dungeonmania.entities.items.Key;
import dungeonmania.entities.movingEntity.Player;
import dungeonmania.game.GameRuntimeContext;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;
import lombok.Getter;

import java.util.List;

public class SwitchDoor extends LogicalEntity {
    private String logic;
    @Getter
    private int keyID;

    public SwitchDoor() {
        super();
        setName("switch_door");
        super.setInteractable(false);
        setCollision(true);
    }

    public boolean unlockDoor(int keyID) {
        if (keyID == this.keyID || keyID == -1) {
            setCollision(false);
            return true;
        }
        return false;
    }

    public void checkActivated() {
        setCollision(!LogicActivationEvaluator.isActivated(logic, GameRuntimeContext.getActiveGame(), getPosition()));
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
        }
    }

    @Override
    public void setupEntity(EntitiesDto entitiesDto, Position position) {
        this.logic = entitiesDto.getLogic();
        this.keyID = entitiesDto.getKey();
        setPosition(position);
    }

}
