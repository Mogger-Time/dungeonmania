package dungeonmania.goals;

import com.fasterxml.jackson.annotation.JsonTypeName;
import dungeonmania.entities.staticEntity.Exit;
import dungeonmania.game.Game;

@JsonTypeName("exit")
public class ExitGoal implements Goal {
    public boolean checkGoal(Game game) {
        return game.getPlayerEntities().stream().anyMatch(s -> (s instanceof Exit));
    }

    public String printGoal() {
        return ":exit";
    }

    public String printcurGoal(Game game) {
        if (!checkGoal(game)) {
            return printGoal();
        }
        return "";
    }
}
