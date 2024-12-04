package dungeonmania.entities.movingEntity.movementStrategy;

import dungeonmania.entities.movingEntity.Enemy;
import dungeonmania.game.Game;
import dungeonmania.util.Direction;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class ClockwiseStrategy extends MovementStrategy {

    private final List<Direction> movementTrajectory;
    private boolean initialMovement;
    private int nextPositionElement;

    public ClockwiseStrategy(int nextPositionElement) {
        super();
        super.setStrategyName("clockwise");

        this.initialMovement = true;
        this.movementTrajectory = new ArrayList<>() {{
            add(Direction.RIGHT);
            add(Direction.DOWN);
            add(Direction.DOWN);
            add(Direction.LEFT);
            add(Direction.LEFT);
            add(Direction.UP);
            add(Direction.UP);
            add(Direction.RIGHT);
        }};

        this.nextPositionElement = nextPositionElement;
    }

    public void setInitialMovment(boolean initialMovement) {
        this.initialMovement = initialMovement;
    }

    @Override
    public Direction getNextPosition(Game game, Enemy enemy) {

        if (isInitialMovement()) {
            setInitialMovment(false);
            return Direction.UP;
        }

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
            MovementStrategy newStrategy = new AntiClockwiseStrategy(7 - nextPositionElement);
            enemy.setStrategy(newStrategy);
            return newStrategy.getNextPosition(game, enemy);
        }
    }
}