package dungeonmania.goals;

import dungeonmania.game.Game;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "goalType")
@JsonSubTypes({
        @JsonSubTypes.Type(value = AndGoal.class, name = "and"),
        @JsonSubTypes.Type(value = OrGoal.class, name = "or"),
        @JsonSubTypes.Type(value = BouldersGoal.class, name = "boulders"),
        @JsonSubTypes.Type(value = EnemiesGoal.class, name = "enemies"),
        @JsonSubTypes.Type(value = TreasureGoal.class, name = "treasure"),
        @JsonSubTypes.Type(value = ExitGoal.class, name = "exit")
})
public interface Goal {
    boolean checkGoal(Game game);

    String printGoal();

    String printcurGoal(Game game);
}
