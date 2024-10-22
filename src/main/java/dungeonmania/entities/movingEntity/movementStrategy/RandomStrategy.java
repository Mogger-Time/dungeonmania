package dungeonmania.entities.movingEntity.movementStrategy;

import java.util.ArrayList;
import java.util.Collections;

import dungeonmania.entities.movingEntity.Enemy;
import dungeonmania.entities.movingEntity.playerStrategy.InvincibleStrategy;
import dungeonmania.game.Game;
import dungeonmania.util.Direction;

public class RandomStrategy extends MovementStrategy {
    
    public RandomStrategy() {
        super();
        super.setStrategyName("random");
    }
    
    @Override
    public Direction getNextPosition(Game game, Enemy enemy) {
        ArrayList<Direction> possiblePositions = game.getValidAdjacentTiles(enemy.getPosition());

        if (possiblePositions.size() == 0) {
            return Direction.NONE;
        } else if (game.getPlayerStrategy() instanceof InvincibleStrategy) {
            MovementStrategy newStrategy = new FearStrategy();
            enemy.setStrategy(newStrategy);
            return newStrategy.getNextPosition(game, enemy);
        } else {
            Collections.shuffle(possiblePositions);
            for (Direction possibleDirection : possiblePositions) {
                if (game.noBouldersNext(possibleDirection, enemy.getPosition())) {
                    return possibleDirection;
                }
            }
        }
        return Direction.NONE;
    }
}
