package dungeonmania.goals;

import dungeonmania.game.Game;

public interface Goal {
    public boolean checkGoal(Game game);

    public String printGoal();

    public String printcurGoal(Game game);
}
