package dungeonmania.entities.movingEntity;

import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public abstract class MovingEntity extends Entity {

    private double health;
    private double damage;

    public MovingEntity() {
        super();
        super.setInteractable(true);
        super.setCollision(false);
    }

    public boolean isAlive() {
        return health > 0;
    }

    public void updatePosition(Direction direction) {
        super.setPosition(super.getNewPosition(direction));
    }

    public double takeDamage(double damage) {
        health = health - damage;
        return -damage;
    }
}
