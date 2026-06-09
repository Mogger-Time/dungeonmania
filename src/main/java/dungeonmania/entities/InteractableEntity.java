package dungeonmania.entities;

import dungeonmania.entities.movingEntity.Player;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.game.Game;

public interface InteractableEntity {
    void interact(Game game, Player player) throws InvalidActionException;
}
