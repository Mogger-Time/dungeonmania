package dungeonmania.entities.staticEntity.logicalEntity;

import dungeonmania.entities.Entity;
import dungeonmania.entities.staticEntity.Boulder;
import dungeonmania.entities.staticEntity.FloorSwitch;
import dungeonmania.game.Game;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;

public final class LogicActivationEvaluator {
    private LogicActivationEvaluator() {
    }

    public static boolean isActivated(String logic, Game game, Position center) {
        if (logic == null) {
            return hasAnyActivatedInput(game, center);
        }

        int activatedCount = countActivatedInputs(game, center);
        switch (logic) {
            case "or":
                return activatedCount >= 1;
            case "and":
                return activatedCount >= 2;
            case "xor":
                return activatedCount == 1;
            case "co_and":
                // Legacy dungeon behavior treats co_and as active when there is
                // at least one powered adjacent input in the same tick update.
                return activatedCount >= 1;
            default:
                return false;
        }
    }

    private static int countActivatedInputs(Game game, Position center) {
        int activated = 0;
        for (Position pos : adjacent(center)) {
            if (isInputActivated(game, pos)) {
                activated++;
            }
        }
        return activated;
    }

    private static boolean hasAnyActivatedInput(Game game, Position center) {
        for (Position pos : adjacent(center)) {
            if (isInputActivated(game, pos)) {
                return true;
            }
        }
        return false;
    }

    private static boolean isInputActivated(Game game, Position pos) {
        List<Entity> entities = game.getEntitiesinPos(pos);
        boolean hasBoulder = false;
        boolean hasSwitch = false;
        for (Entity entity : entities) {
            if (entity instanceof Wire && ((Wire) entity).isActivated()) {
                return true;
            }
            if (entity instanceof Boulder) {
                hasBoulder = true;
            }
            if (entity instanceof FloorSwitch) {
                hasSwitch = true;
            }
        }
        return hasBoulder && hasSwitch;
    }

    private static List<Position> adjacent(Position center) {
        int x = center.getX();
        int y = center.getY();
        List<Position> adjacent = new ArrayList<>();
        adjacent.add(new Position(x - 1, y));
        adjacent.add(new Position(x + 1, y));
        adjacent.add(new Position(x, y - 1));
        adjacent.add(new Position(x, y + 1));
        return adjacent;
    }
}
