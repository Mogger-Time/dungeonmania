package dungeonmania.goals;

import org.json.JSONArray;

import dungeonmania.game.Game;

public class OrGoal implements Goal {
    private Goal x;
    private Goal y;

    public OrGoal(JSONArray subgoals) {
        x = GoalFactory.createGoal(subgoals.getJSONObject(0));
        y = GoalFactory.createGoal(subgoals.getJSONObject(1));
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
        if (xstr.equals("") || ystr.equals("")) {
            return ""; 
        } else {
            return String.format("(%1$s OR %2$s)", xstr, ystr);
        }
    }
}
