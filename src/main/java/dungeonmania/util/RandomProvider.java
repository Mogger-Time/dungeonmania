package dungeonmania.util;

import java.util.Random;

public final class RandomProvider {
    private static Random random = new Random();

    private RandomProvider() {
    }

    public static int nextInt(int bound) {
        return random.nextInt(bound);
    }

    public static double nextDouble() {
        return random.nextDouble();
    }

    public static void setSeed(long seed) {
        random = new Random(seed);
    }

    public static void reset() {
        random = new Random();
    }
}
