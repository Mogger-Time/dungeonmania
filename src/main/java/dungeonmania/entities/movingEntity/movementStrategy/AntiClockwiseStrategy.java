package dungeonmania.entities.movingEntity.movementStrategy;

import dungeonmania.entities.movingEntity.Enemy;
import dungeonmania.game.Game;
import dungeonmania.util.Direction;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class AntiClockwiseStrategy extends MovementStrategy {

    private final List<Direction> movementTrajectory;
    private int nextPositionElement;

    public AntiClockwiseStrategy(int nextPositionElement) {
        super();
        super.setStrategyName("anticlockwise");

        this.movementTrajectory = new ArrayList<>() {{
            add(Direction.LEFT);
            add(Direction.DOWN);
            add(Direction.DOWN);
            add(Direction.RIGHT);
            add(Direction.RIGHT);
            add(Direction.UP);
            add(Direction.UP);
            add(Direction.LEFT);
        }};

        this.nextPositionElement = nextPositionElement;
    }

    @Override
    public Direction getNextPosition(Game game, Enemy enemy) {

        Direction nextDirection = movementTrajectory.get(nextPositionElement);

        if (game.noBouldersNext(nextDirection, enemy.getPosition())) {
            int currPos = nextPositionElement;
            if (nextPositionElement == 7) {
                nextPositionElement = 0;
            } else {
                nextPositionElement++;
            }
            return movementTrajectory.get(currPos);
        } else {
            MovementStrategy newStrategy = new ClockwiseStrategy(7 - nextPositionElement);
            enemy.setStrategy(newStrategy);
            return newStrategy.getNextPosition(game, enemy);
        }
    }
}
