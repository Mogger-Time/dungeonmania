package dungeonmania.entities.movingEntity;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntity.movementStrategy.MovementStrategy;
import dungeonmania.entities.movingEntity.playerStrategy.PlayerStrategy;
import dungeonmania.entities.staticEntity.SwampTile;
import dungeonmania.game.Game;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public abstract class Enemy extends MovingEntity {

    // Begin getters and setters
    @Setter
    @Getter
    private MovementStrategy strategy;
    private int movementstep = 0;

    public Enemy() {
        super();
    }

    @Override
    public boolean interact(Player player) {
        player.startBattle(this);
        return getHealth() <= 0;
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

    public abstract void alert(PlayerStrategy strat);
}