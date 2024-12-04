package dungeonmania.goals;

import dungeonmania.game.Game;

public interface Goal {
    boolean checkGoal(Game game);

    String printGoal();

    String printcurGoal(Game game);
}
