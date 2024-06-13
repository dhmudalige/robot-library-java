package swarm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SwarmUtils {
    public static Date date = new Date();
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    // Format the current date
    public static String formattedDate = dateFormat.format(date);

    public static long getTime() {
        return date.getTime();
    }

    public static String getDate() {
        return formattedDate;
    }

    private static boolean isObstacle(int x, int y, int[][] obstacles) {
        for (int[] pair : obstacles) {
            if (pair[0] == x && pair[1] == y) {
                return true;
            }
        }
        return false;
    }
}
