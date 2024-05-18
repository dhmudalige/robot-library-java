package Other.checks;

import Other.models.Arena;

import java.io.IOException;

public class RobotPositionChecks {
    private static final Arena ARENA;

    static {
        try {
            ARENA = new Arena();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // Assume square shape arena
    private static final double DELTA = 2 * ARENA.gridSpace;
    private static final double LIMIT = (ARENA.gridLength * ARENA.gridSpace)/2 - DELTA;

    public RobotPositionChecks() {
    }

    public static double checkLimit(double givenVal) {
        if (givenVal < 0) {
            return Math.max(givenVal + DELTA, -1 * LIMIT);
        } else {
            return Math.min(givenVal - DELTA, LIMIT);
        }
    }
}
