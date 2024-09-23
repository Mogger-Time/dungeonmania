package dungeonmania.entities.items;

import dungeonmania.entities.movingEntity.playerStrategy.PlayerStrategy;

public abstract class Potion extends Item {
    private int duration;

    public Potion() {
        super();
    }

    public Potion(int duration) {
        super();
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public abstract PlayerStrategy consume();
}
