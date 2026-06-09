package dungeonmania.entities.movingEntity.movementStrategy;

import dungeonmania.entities.movingEntity.Enemy;
import dungeonmania.game.Game;
import dungeonmania.util.Direction;
import dungeonmania.util.PosDirWrapper;
import dungeonmania.util.Position;

import java.util.List;
import java.util.Map;

public class FearStrategy extends MovementStrategy {

    public FearStrategy() {
        super();
        super.setStrategyName("fear");
    }

    @Override
    public Direction getNextPosition(Game game, Enemy enemy) {
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
        Direction closer = paths.get(initial).getDirection();
        List<Direction> directions = game.getValidAdjacentTiles(enemy.getPosition());
        if (closer != null) {
            directions.remove(closer);
        }
        double random = Math.random() * directions.size();
        int randomdir = (int) random;
        if (closer == Direction.UP && directions.contains(Direction.DOWN)) {
            return Direction.DOWN;
        } else if (closer == Direction.DOWN && directions.contains(Direction.UP)) {
            return Direction.UP;
        } else if (closer == Direction.LEFT && directions.contains(Direction.RIGHT)) {
            return Direction.RIGHT;
        } else if (closer == Direction.RIGHT && directions.contains(Direction.LEFT)) {
            return Direction.LEFT;
            //if no way to reach player or if no way to run away
        } else if (!directions.isEmpty()) {
            return directions.get(randomdir);
        }
        return null;
    }
}