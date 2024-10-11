package dungeonmania.entities.items;

import dungeonmania.dtos.EntitiesDto;
import dungeonmania.entities.movingEntity.Player;
import dungeonmania.util.Position;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class Key extends Item {
    private int keyid;

    public Key() {
        super();
        setName("key");
    }

    @Override
    public void setupEntity(EntitiesDto entitiesDto, Position position) {
        this.keyid = entitiesDto.getKey();
        super.setPosition(position);
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
