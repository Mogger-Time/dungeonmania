package dungeonmania.game;

import dungeonmania.entities.Entity;
import dungeonmania.entities.InteractableEntity;
import dungeonmania.exceptions.InvalidActionException;

public class InteractionService {
    public void interact(Game game, String entityId) throws InvalidActionException {
        Entity found = null;
        for (Entity entity : game.getEntities()) {
            if (entityId.equals(entity.getEntityID())) {
                found = entity;
                break;
            }
        }
        if (found == null) {
            throw new IllegalArgumentException("No entity exists");
        }
        if (!(found instanceof InteractableEntity)) {
            throw new InvalidActionException("Entity can not be interacted with");
        }
        ((InteractableEntity) found).interact(game, game.getPlayer());
    }
}
