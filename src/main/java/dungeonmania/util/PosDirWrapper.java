package dungeonmania.util;

import lombok.Getter;

@Getter
public class PosDirWrapper {
    private final Position position;
    private final Direction direction;

    public PosDirWrapper(Position pos, Direction dir) {
        position = pos;
        direction = dir;
    }

}
