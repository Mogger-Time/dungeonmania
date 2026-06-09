package dungeonmania.util;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

public class PositionTest {
    @Test
    public void equalsIgnoresLayer() {
        Position a = new Position(2, 3, 0);
        Position b = new Position(2, 3, 99);
        assertEquals(a, b);
    }

    @Test
    public void hashCodeMatchesEqualsForDifferentLayers() {
        Position a = new Position(5, 7, 0);
        Position b = new Position(5, 7, 2);
        assertEquals(a.hashCode(), b.hashCode());
    }

    @Test
    public void hashSetContainsEquivalentPositionAcrossLayers() {
        Set<Position> positions = new HashSet<>();
        positions.add(new Position(1, 1, 0));
        assertTrue(positions.contains(new Position(1, 1, 3)));
    }
}
