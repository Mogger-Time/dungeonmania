package dungeonmania.entities.movingEntity.movementStrategy;

import dungeonmania.entities.movingEntity.Enemy;
import dungeonmania.game.Game;
import dungeonmania.util.Direction;
import dungeonmania.util.PosDirWrapper;
import dungeonmania.util.Position;

import java.util.List;
import java.util.Map;

public class FollowStrategy extends MovementStrategy {

    public FollowStrategy() {
        super();
        super.setStrategyName("follow");
    }

    @Override
    public Direction getNextPosition(Game game, Enemy enemy) {
        if (enemy.getPosition().equals(game.getPlayerPosition())) {
            return Direction.NONE;
        }
        Position initial = game.getPlayerPosition();
        Map<Position, PosDirWrapper> paths = game.getPath(enemy, initial);
        while (paths.get(initial) != null) {
            Position prevPos = paths.get(initial).getPosition();
            if (prevPos.equals(enemy.getPosition())) {
                break;
            } else {
                initial = prevPos;
            }
        }
        //paths.get will be null if no path to player or if player and enemy are standing in same place
        if (paths.get(initial) == null) {
            List<Direction> randomdirections = game.getValidAdjacentTiles(enemy.getPosition());
            if (randomdirections.isEmpty()) {
                return null;
            }
            double random = Math.random() * randomdirections.size();
            int randomdir = (int) random;
            return randomdirections.get(randomdir);
        }
        return paths.get(initial).getDirection();
    }
}
