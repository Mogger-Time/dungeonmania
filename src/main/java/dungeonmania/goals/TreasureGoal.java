package dungeonmania.goals;

import dungeonmania.game.Game;

public class TreasureGoal implements Goal {
    private final int number;

    public TreasureGoal(int number) {
        this.number = number;
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
