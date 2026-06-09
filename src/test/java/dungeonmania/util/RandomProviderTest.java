package dungeonmania.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomProviderTest {
    @Test
    public void seededSequenceIsDeterministic() {
        RandomProvider.setSeed(1234L);
        int first = RandomProvider.nextInt(1000);
        double second = RandomProvider.nextDouble();

        RandomProvider.setSeed(1234L);
        assertEquals(first, RandomProvider.nextInt(1000));
        assertEquals(second, RandomProvider.nextDouble());
    }
}
