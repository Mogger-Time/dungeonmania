package dungeonmania.entities.staticEntity.logicalEntity;


import dungeonmania.entities.Entity;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.FloorSwitch;
import dungeonmania.game.Game;
import dungeonmania.game.GameRuntimeContext;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Wire extends LogicalEntity {

    private boolean isActivated;
    private boolean isVisited = false;

    public Wire() {
        super();
        setName("wire");
        super.setInteractable(false);
    }

    public boolean isActivated() {
        return isActivated;
    }

    public boolean isVisited() {
        return isVisited;
    }

    public void checkActivated() {
        Game activeGame = GameRuntimeContext.getActiveGame();
        this.isVisited = false;
        this.isActivated = isPoweredFrom(activeGame, this.getPosition(), new HashSet<>());
    }

    private boolean isPoweredFrom(Game game, Position current, Set<Position> visited) {
        if (!visited.add(current)) {
            return false;
        }

        for (Position pos : getAdjacent(current)) {
            List<Entity> entities = game.getEntitiesinPos(pos);
            boolean hasBoulder = false;
            boolean hasSwitch = false;
            for (Entity entity : entities) {
                if (entity instanceof Boulder) {
                    hasBoulder = true;
                } else if (entity instanceof FloorSwitch) {
                    hasSwitch = true;
                } else if (entity instanceof Wire && isPoweredFrom(game, entity.getPosition(), visited)) {
                    return true;
                }
            }
            if (hasBoulder && hasSwitch) {
                return true;
            }
        }
        return false;
    }

    private List<Position> getAdjacent(Position pos) {
        int x = pos.getX();
        int y = pos.getY();
        List<Position> adjacent = new ArrayList<>();
        adjacent.add(new Position(x - 1, y));
        adjacent.add(new Position(x + 1, y));
        adjacent.add(new Position(x, y - 1));
        adjacent.add(new Position(x, y + 1));
        return adjacent;
    }


}
