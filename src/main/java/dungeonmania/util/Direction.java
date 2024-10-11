package dungeonmania.util;

import lombok.Getter;

@Getter
public enum Direction {
    UP(0, -1),
    DOWN(0, 1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    NONE(0, 0);

    private final Position offset;

    Direction(Position offset) {
        this.offset = offset;
    }

    Direction(int x, int y) {
        this.offset = new Position(x, y);
    }

}
