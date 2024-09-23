package dungeonmania.goals;

import dungeonmania.entities.Entity;
import dungeonmania.entities.staticEntity.ZombieToastSpawner;
import dungeonmania.game.Game;

import java.util.List;
import java.util.stream.Collectors;


public class EnemiesGoal implements Goal {
    private final int number;

    public EnemiesGoal(int number) {
        this.number = number;
    }

    public boolean checkGoal(Game game) {
        List<Entity> spawners = game.getEntities().stream().filter(s -> (s instanceof ZombieToastSpawner)).collect(Collectors.toList());
        return (spawners.isEmpty() && (game.getInitialEnemies() - game.getEnemies().size()) >= number);
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
