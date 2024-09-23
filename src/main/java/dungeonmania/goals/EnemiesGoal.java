package dungeonmania.goals;

import java.util.stream.Collectors;

import dungeonmania.game.Game;
import dungeonmania.entities.Entity;
import java.util.List;
import dungeonmania.entities.staticEntity.ZombieToastSpawner;


public class EnemiesGoal implements Goal {
    private int number;

    public EnemiesGoal(int number) {
        this.number = number;
    }

    public boolean checkGoal(Game game) {
        List<Entity> spawners = game.getEntities().stream().filter(s->(s instanceof ZombieToastSpawner)).collect(Collectors.toList());
        return (spawners.size() == 0 && (game.getInitialEnemies() - game.getEnemies().size()) >= number);
    }

    public String printGoal() {
        return ":enemies";
    }

    public String printcurGoal(Game game) {
        if (!checkGoal(game)) {
            return printGoal();
        }
        return "";
    }
}
