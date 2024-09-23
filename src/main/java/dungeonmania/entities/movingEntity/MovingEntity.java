package dungeonmania.entities.movingEntity;

import dungeonmania.entities.Entity;
import dungeonmania.util.Direction;

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

    public double getHealth() {
        return health;
    }

    public void setHealth(double health) {
        this.health = health;
    }

    public double getDamage() {
        return damage;
    }

    public void setDamage(double damage) {
        this.damage = damage;
    }

    public void updatePosition(Direction direction) {
        super.setPosition(super.getNewPosition(direction));
    }

    public double takeDamage(double damage) {
        health = health - damage;
        return -damage;
    }
}
