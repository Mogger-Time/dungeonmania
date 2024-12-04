package dungeonmania.entities.items;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public abstract class BattleItem extends Item {
    private int durability;
    private int attack;
    private int defence;
    private int multiplier;

    public BattleItem() {
        super();
    }

    public void reduceDurability() {
        this.durability -= 1;
    }
}
