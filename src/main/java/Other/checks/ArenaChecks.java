package Other.checks;

import Other.models.Arena;

import java.io.*;

public class ArenaChecks {
    private static final Arena ARENA;

    static {
        try {
            ARENA = new Arena();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private double maxLength;

    public ArenaChecks() {
        // Assume square shape arena
        maxLength = ARENA.gridLength * ARENA.gridSpace;
    }

    public static boolean isInside(double x, double y) {
        return true;
    }


}
