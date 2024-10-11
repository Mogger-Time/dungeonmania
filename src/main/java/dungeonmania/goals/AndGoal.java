package dungeonmania.goals;

import dungeonmania.dtos.GoalConditionDto;
import dungeonmania.game.Game;

import java.util.List;

public class AndGoal implements Goal {
    private final Goal x;
    private final Goal y;

    public AndGoal(List<GoalConditionDto> subgoals) {
        x = GoalFactory.createGoal(subgoals.getFirst());
        y = GoalFactory.createGoal(subgoals.getLast());
    }

    public boolean checkGoal(Game game) {
        return (x.checkGoal(game) && y.checkGoal(game));
    }

    public String printGoal() {
        return String.format("(%1$s AND %2$s)", x.printGoal(), y.printGoal());
    }

    public String printcurGoal(Game game) {
        String xstr = x.printcurGoal(game);
        String ystr = y.printcurGoal(game);
        if (xstr.isEmpty() && ystr.isEmpty()) {
            return "";
        } else if (xstr.isEmpty()) {
            return ystr;
        } else if (ystr.isEmpty()) {
            return xstr;
        } else {
            return String.format("(%1$s AND %2$s)", xstr, ystr);
        }
    }
}
