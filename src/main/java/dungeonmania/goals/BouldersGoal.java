package dungeonmania.goals;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.Entity;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.FloorSwitch;
import dungeonmania.game.Game;

public class BouldersGoal implements Goal {
    public BouldersGoal() {

    }

    public boolean checkGoal(Game game) {
        for (FloorSwitch fswitch : game.getFloorSwitches()) {
            List<Entity> allentities = game.getEntitiesinPos(fswitch.getPosition()).stream().filter(s->(s instanceof Boulder)).collect(Collectors.toList());
            if (allentities.size() < 1) {
                return false;
            }
        }
        return true;
    }

    public String printGoal() {
        return ":boulders";
    }

    public String printcurGoal(Game game) {
        if (!checkGoal(game)) {
            return printGoal();
        }
        return "";
    }
}
