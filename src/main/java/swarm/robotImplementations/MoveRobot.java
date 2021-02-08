package swarm.robotImplementations;

import org.json.simple.parser.ParseException;
import swarm.robot.VirtualRobot;

public class MoveRobot extends VirtualRobot {

    public MoveRobot(int id, double x, double y, double heading) {
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
            // if positive, rotate CW, otherwise rotate CCW an angle depends on the random number
            int random = -1000 + ((int) ((Math.random() * 2000)));

            System.out.println("\t Wall detected, go back and rotating " + random);

            // Go back a little
            motion.move(-200, -200, 1000);

            // rotate
            while(distSensor.getDistance() < 50) {
                motion.rotate(50 * Integer.signum(random), 1000);
            }

            // rotate furthermore
            motion.rotate(50 * Integer.signum(random), 2000);

        } else {
            motion.move(200, 200, 1000);
        }

    }

}
