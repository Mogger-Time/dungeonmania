package dungeonmania.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Position {
    private final int x, y, layer;

    public Position(int x, int y, int layer) {
        this.x = x;
        this.y = y;
        this.layer = layer;
    }

    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.layer = 0;
    }

    /**
     * Calculates the position vector of b relative to a (ie. the direction from a
     * to b)
     *
     * @return The relative position vector
     */
    public static Position calculatePositionBetween(Position a, Position b) {
        return new Position(b.x - a.x, b.y - a.y);
    }

    public static Direction calculateDirectionTo(Position a, Position b) {
        Position vector = calculatePositionBetween(a, b);
        if (vector.equals(Direction.LEFT.getOffset())) {
            return Direction.LEFT;
        } else if (vector.equals(Direction.RIGHT.getOffset())) {
            return Direction.RIGHT;
        } else if (vector.equals(Direction.UP.getOffset())) {
            return Direction.UP;
        } else if (vector.equals(Direction.DOWN.getOffset())) {
            return Direction.DOWN;
        } else {
            return null;
        }
    }

    public static boolean isAdjacent(Position a, Position b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y) == 1;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, layer);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Position other = (Position) obj;

        // z doesn't matter
        return x == other.x && y == other.y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getLayer() {
        return layer;
    }

    public Position asLayer(int layer) {
        return new Position(x, y, layer);
    }

    // (Note: doesn't include z)

    public Position translateBy(int x, int y) {
        return this.translateBy(new Position(x, y));
    }

    public Position translateBy(Direction direction) {
        return this.translateBy(direction.getOffset());
    }

    public Position translateBy(Position position) {
        return new Position(this.x + position.x, this.y + position.y, this.layer + position.layer);
    }

    // (Note: doesn't include z)
    public Position scale(int factor) {
        return new Position(x * factor, y * factor, layer);
    }

    @Override
    public String toString() {
        return "Position [x=" + x + ", y=" + y + ", z=" + layer + "]";
    }

    // Return Adjacent positions in an array list with the following element positions:
    // 0 1 2
    // 7 p 3
    // 6 5 4
    public List<Position> getAdjacentPositions() {
        List<Position> adjacentPositions = new ArrayList<>();
        adjacentPositions.add(new Position(x - 1, y - 1));
        adjacentPositions.add(new Position(x, y - 1));
        adjacentPositions.add(new Position(x + 1, y - 1));
        adjacentPositions.add(new Position(x + 1, y));
        adjacentPositions.add(new Position(x + 1, y + 1));
        adjacentPositions.add(new Position(x, y + 1));
        adjacentPositions.add(new Position(x - 1, y + 1));
        adjacentPositions.add(new Position(x - 1, y));
        return adjacentPositions;
    }

    public List<Position> getCardAdjPositions() {
        List<Position> adjacentPositions = new ArrayList<>();
        adjacentPositions.add(new Position(x, y - 1));
        adjacentPositions.add(new Position(x + 1, y));
        adjacentPositions.add(new Position(x, y + 1));
        adjacentPositions.add(new Position(x - 1, y));
        return adjacentPositions;
    }
}
