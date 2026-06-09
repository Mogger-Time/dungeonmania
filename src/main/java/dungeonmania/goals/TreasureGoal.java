package dungeonmania.goals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import dungeonmania.game.Game;

@JsonTypeName("treasure")
public class TreasureGoal implements Goal {
    private int number;

    @JsonCreator
    public TreasureGoal(@JsonProperty("number") int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public boolean checkGoal(Game game) {
        return (game.getTreasures() >= number);
    }

    public String printGoal() {
        return ":treasure";
    }

    public String printcurGoal(Game game) {
        if (!checkGoal(game)) {
            return printGoal();
        }
        return "";
    }
}
