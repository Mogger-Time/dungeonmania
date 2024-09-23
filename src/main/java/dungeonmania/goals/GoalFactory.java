package dungeonmania.goals;

import org.json.JSONObject;

import dungeonmania.game.GameLauncher;

public class GoalFactory {

    public static Goal createGoal(JSONObject object) {
        String type = object.getString("goal");
        switch (type) {
            case "enemies":
                return new EnemiesGoal(GameLauncher.getConfig().getInt("enemy_goal"));
            case "boulders":
                return new BouldersGoal();
            case "treasure":
                return new TreasureGoal(GameLauncher.getConfig().getInt("treasure_goal"));
            case "exit":
                return new ExitGoal();
            case "AND":
                return new AndGoal(object.getJSONArray("subgoals"));
            case "OR":
                return new OrGoal(object.getJSONArray("subgoals"));
        }
        return null;
    }
}
