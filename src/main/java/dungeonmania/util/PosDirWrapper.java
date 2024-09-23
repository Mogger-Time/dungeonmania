package dungeonmania.util;

public class PosDirWrapper {
    private Position position;
    private Direction direction;

    public PosDirWrapper(Position pos, Direction dir) {
        position = pos;
        direction = dir;
    }

    public Position getPosition() {
        return position;
    }

    public Direction getDirection() {
        return direction;
    }
}
