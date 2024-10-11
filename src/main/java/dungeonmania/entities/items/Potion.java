package dungeonmania.entities.items;

import dungeonmania.entities.movingEntity.playerStrategy.PlayerStrategy;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class Potion extends Item {
    private int duration;

    public Potion() {
        super();
    }

    public Potion(int duration) {
        super();
        this.duration = duration;
    }

    public abstract PlayerStrategy consume();
}
