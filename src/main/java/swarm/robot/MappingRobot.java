package swarm.robot;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class MappingRobot extends VirtualRobot {
    private static File propertyFile = new File("src/resources/config/main.properties");
    private static FileReader reader;
    private static Properties props = new Properties();

    // Robot properties
    public final int MOVING_SPEED = 200;
    public final int ROTATING_SPEED = 200;

    // Proximity Sensor options
    // Angles for left,front and right side rotating
    public final int[] proximityAngles = {-90, 0, 90, 180};

    // Index to get left proximity angle
    public final int PROXIMITY_LEFT = 0;

    // Index to get front proximity angle
    public final int PROXIMITY_FRONT = 1;

    // Index to get right proximity angle
    public final int PROXIMITY_RIGHT = 2;

    // Index to get back proximity angle
    public final int PROXIMITY_BACK = 3;

    /**
     * MappingRobot class
     *
     * @param id      robot Id
     * @param x       X coordinate as double
     * @param y       Y coordinate as double
     * @param heading Heading direction in degrees, as double
     */
    public MappingRobot(int id, double x, double y, double heading) {
        super(id, x, y, heading);
    }
}
