package dungeonmania.entities.movingEntity;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntity.movementStrategy.MovementStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.PlayerStrategy;
import dungeonmania.entities.staticEntity.SwampTile;
import dungeonmania.game.Game;

public abstract class Enemy extends MovingEntity{

    private MovementStrategy strategy;
    private int movementstep = 0;

    public Enemy() {
        super();
    }

    @Override
    public boolean interact(Player player) {
        player.startBattle(this);
        if (getHealth() <= 0) {
            return true;
        }
        return false;
    }

    public void move(Game game, MovementStrategy strategy) {
        int movementfactor = 0;
        List<Entity> swamptiles = game.getEntitiesinPos(this.getPosition());
        for (Entity ent : swamptiles) {
            if (ent instanceof SwampTile) {
                SwampTile swamptile = (SwampTile) ent;
                movementfactor = swamptile.getFactor();
            }
        }
        if (movementstep < movementfactor) {
            movementstep++;
        } else {
            movementstep = 0;
            strategy.move(game, this);
        }
    }

    public void move(Game game) {
        move(game, this.strategy);
    }

    // Begin getters and setters
    public MovementStrategy getStrategy() {
        return strategy;
    }

    public void setStrategy(MovementStrategy strategy) {
        this.strategy = strategy;
    }

    public abstract void alert(PlayerStrategy strat);
}