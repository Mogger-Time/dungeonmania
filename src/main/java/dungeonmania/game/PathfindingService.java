package dungeonmania.game;

import dungeonmania.entities.Entity;
import dungeonmania.entities.staticEntity.Portal;
import dungeonmania.entities.staticEntity.SwampTile;
import dungeonmania.util.Direction;
import dungeonmania.util.PosDirWrapper;
import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class PathfindingService {
    public List<Integer> getDimensions(Game game, Position destination) {
        int leftmost = 0;
        int rightmost = 0;
        int upmost = 0;
        int downmost = 0;
        for (Entity entity : game.getEntities()) {
            Position entpos = entity.getPosition();
            if (entpos.getX() < leftmost) {
                leftmost = entpos.getX();
            } else if (entpos.getX() > rightmost) {
                rightmost = entpos.getX();
            }
            if (entpos.getY() < upmost) {
                upmost = entpos.getY();
            } else if (entpos.getY() > downmost) {
                downmost = entpos.getY();
            }
        }
        if (destination.getX() < leftmost) {
            leftmost = destination.getX();
        } else if (destination.getX() > rightmost) {
            rightmost = destination.getX();
        }
        if (destination.getY() < upmost) {
            upmost = destination.getY();
        } else if (destination.getY() > downmost) {
            downmost = destination.getY();
        }
        List<Integer> dimensions = new ArrayList<>();
        dimensions.add(leftmost - 1);
        dimensions.add(upmost - 1);
        dimensions.add(rightmost + 1);
        dimensions.add(downmost + 1);
        return dimensions;
    }

    public Map<Position, PosDirWrapper> getPath(Game game, Entity source, Position destination) {
        List<Integer> grid = getDimensions(game, destination);
        int leftbound = grid.get(0);
        int rightbound = grid.get(2);
        int upbound = grid.get(1);
        int downbound = grid.get(3);
        Map<Position, Double> distance = new HashMap<>();
        Map<Position, PosDirWrapper> previous = new HashMap<>();
        List<Position> visited = new ArrayList<>();
        List<Position> queue = new ArrayList<>();
        for (int i = leftbound; i <= rightbound; i++) {
            for (int j = upbound; j <= downbound; j++) {
                distance.put(new Position(i, j), Double.MAX_VALUE);
            }
        }
        distance.put(source.getPosition(), 0.0);
        queue.add(source.getPosition());
        while (!queue.isEmpty()) {
            Position temp = null;
            Double smallestdistance = Double.MAX_VALUE;
            for (Position pos : queue) {
                if (distance.get(pos) < smallestdistance) {
                    temp = pos;
                    smallestdistance = distance.get(pos);
                }
            }
            visited.add(temp);
            queue.remove(temp);
            if (temp.equals(destination)) {
                break;
            }
            for (Entry<Position, Direction> entry : game.posDirTranslation(temp).entrySet()) {
                Position adjPos = entry.getKey();
                Direction dirto = entry.getValue();
                if (leftbound <= adjPos.getX() && adjPos.getX() <= rightbound
                        && upbound <= adjPos.getY() && adjPos.getY() <= downbound) {
                    int cost = 1;
                    List<Entity> entitiesatPos = game.getEntitiesinPos(adjPos);
                    for (Entity possibleportal : entitiesatPos) {
                        if (possibleportal instanceof Portal) {
                            adjPos = ((Portal) possibleportal).getnewLoc(dirto, game);
                        }
                        if (possibleportal.getCollision()) {
                            adjPos = null;
                        }
                        if (possibleportal instanceof SwampTile) {
                            cost = cost + ((SwampTile) possibleportal).getFactor();
                        }
                    }
                    if (adjPos == null) {
                        continue;
                    }
                    if (!visited.contains(adjPos)) {
                        if (!queue.contains(adjPos)) {
                            queue.add(adjPos);
                        }
                        if (distance.get(temp) + cost < distance.get(adjPos)) {
                            distance.put(adjPos, distance.get(temp) + cost);
                            previous.put(adjPos, new PosDirWrapper(temp, dirto));
                        }
                    }
                }
            }
        }
        return previous;
    }
}
