package dungeonmania.entities.items;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntity.Player;

import java.util.List;

public abstract class Item extends Entity {

    public Item() {
        super();
    }

    @Override
    public boolean interact(Player player) {
        if (this instanceof Key) {
            List<Item> inv = player.getInventory();
            for (Item item : inv) {
                if (item instanceof Key) {
                    return false;
                }
            }
        }

        player.addItem(this);
        return true;
    }
}
