package dungeonmania.goals;

import dungeonmania.dtos.GoalConditionDto;
import dungeonmania.game.Game;

import java.util.List;

public class OrGoal implements Goal {
    private final Goal x;
    private final Goal y;

    public OrGoal(List<GoalConditionDto> subgoals) {
        x = GoalFactory.createGoal(subgoals.getFirst());
        y = GoalFactory.createGoal(subgoals.getLast());
    }

    public boolean checkGoal(Game game) {
        return (x.checkGoal(game) || y.checkGoal(game));
    }

    public String printGoal() {
        return String.format("(%1$s OR %2$s)", x.printGoal(), y.printGoal());
    }

    public String printcurGoal(Game game) {
        String xstr = x.printcurGoal(game);
        String ystr = y.printcurGoal(game);
        if (xstr.isEmpty() || ystr.isEmpty()) {
            return "";
        } else {
            return String.format("(%1$s OR %2$s)", xstr, ystr);
        }
    }
}
