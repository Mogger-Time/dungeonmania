package dungeonmania.goals;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeName;
import dungeonmania.entities.Entity;
import dungeonmania.entities.staticEntity.ZombieToastSpawner;
import dungeonmania.game.Game;

import java.util.List;
import java.util.stream.Collectors;


@JsonTypeName("enemies")
public class EnemiesGoal implements Goal {
    private int number;

    @JsonCreator
    public EnemiesGoal(@JsonProperty("number") int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
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
