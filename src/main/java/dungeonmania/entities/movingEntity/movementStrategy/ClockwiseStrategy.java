package dungeonmania.entities.movingEntity.movementStrategy;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.movingEntity.Enemy;
import dungeonmania.game.Game;
import dungeonmania.util.Direction;

public class ClockwiseStrategy extends MovementStrategy {

    private boolean         initialMovement;
    private List<Direction> movementTrajectory;
    private int             nextPositionElement;
    
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

    public boolean isInitialMovement() {
        return initialMovement;
    }

    public void setInitialMovment(boolean initialMovement) {
        this.initialMovement = initialMovement;
    }

    public List<Direction> getMovementTrajectory() {
        return movementTrajectory;
    }

    public int getNextPositionElement() {
        return nextPositionElement;
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