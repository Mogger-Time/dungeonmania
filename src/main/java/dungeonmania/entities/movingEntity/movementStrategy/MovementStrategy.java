package dungeonmania.entities.movingEntity.movementStrategy;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntity.Enemy;
import dungeonmania.entities.movingEntity.Mercenary;
import dungeonmania.entities.staticEntity.Portal;
import dungeonmania.game.Game;
import dungeonmania.util.Direction;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MovementStrategy {

    private String strategyName;

    public void move(Game game, Enemy enemy) {
        Direction nextdir = getNextPosition(game, enemy);
        if (nextdir == null) {
            return;
        }
        enemy.updatePosition(nextdir);
        if (enemy instanceof Mercenary) {
            List<Entity> entities = game.getEntitiesinPos(enemy.getPosition());
            for (Entity ent : entities) {
                if (ent instanceof Portal) {
                    Portal portal = (Portal) ent;
                    portal.enterPortal(enemy, nextdir);
                }
            }
        }
    }

    public Direction getNextPosition(Game game, Enemy enemy) {
        return Direction.NONE;
    }
}