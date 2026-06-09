package dungeonmania.goals;

import dungeonmania.dtos.GoalConditionDto;
import dungeonmania.game.GameRuntimeContext;

public class GoalFactory {

    public static Goal createGoal(GoalConditionDto goalConditionDto) {
        switch (goalConditionDto.getGoal()) {
            case "enemies":
                return new EnemiesGoal(GameRuntimeContext.getConfig().getEnemyGoal());
            case "boulders":
                return new BouldersGoal();
            case "treasure":
                return new TreasureGoal(GameRuntimeContext.getConfig().getTreasureGoal());
            case "exit":
                return new ExitGoal();
            case "AND":
                return new AndGoal(goalConditionDto.getSubgoals());
            case "OR":
                return new OrGoal(goalConditionDto.getSubgoals());
        }
        return null;
    }
}
