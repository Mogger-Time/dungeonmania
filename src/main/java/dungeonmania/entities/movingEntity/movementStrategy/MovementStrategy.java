package dungeonmania.entities.movingEntity.movementStrategy;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.movingEntity.Enemy;
import dungeonmania.entities.movingEntity.Mercenary;
import dungeonmania.entities.staticEntity.Portal;
import dungeonmania.game.Game;
import dungeonmania.util.Direction;

public class MovementStrategy {

    private String strategyName;

    public void setStrategyName(String strategyName) {
        this.strategyName = strategyName;
    }

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