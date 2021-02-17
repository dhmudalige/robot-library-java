package swarm.robotImplementations;

import org.json.simple.parser.ParseException;
import swarm.robot.VirtualRobot;

public class ObstacleAvoidRobot extends VirtualRobot {

    public ObstacleAvoidRobot(int id, double x, double y, double heading) {
        super(id, x, y, heading);
    }

    public void setup() {
        super.setup();
    }

    public void loop() throws Exception {
        super.loop();

        double dist = distSensor.getDistance();

        if (dist < 25) {

            // Generate a random number in [-1000,1000] range
            // if even, rotate CW, otherwise rotate CCW an angle depends on the random number
            int random = -1000 + ((int) ((Math.random() * 2000)));
            int sign = (random % 2 == 0) ? 1 : -1;

            System.out.println("\t Wall detected, go back and rotating " + random);

            // Go back a little
            motion.move(-100, -100, 900);

            // rotate
            int loopCount = 0; // to avoid infinity loop
            while (distSensor.getDistance() < 50 && loopCount < 5) {
                motion.rotate(50 * sign, 1000);
                loopCount++;
            }
            // TODO: What happens if coordinates are out of the arena range ?

            // rotate a little
            motion.rotate(50 * sign, 500);

        } else {
            motion.move(100, 100, 1000);
        }

    }

}
