package dungeonmania.goals;

import dungeonmania.dtos.GoalConditionDto;
import dungeonmania.game.GameLauncher;

public class GoalFactory {

    public static Goal createGoal(GoalConditionDto goalConditionDto) {
        switch (goalConditionDto.getGoal()) {
            case "enemies":
                return new EnemiesGoal(GameLauncher.getConfig().getEnemyGoal());
            case "boulders":
                return new BouldersGoal();
            case "treasure":
                return new TreasureGoal(GameLauncher.getConfig().getTreasureGoal());
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
