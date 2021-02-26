package swarm.robotImplementations;

import swarm.robot.VirtualRobot;
import swarm.robot.types.RGBColorType;

public class DiscoverColorRobot extends VirtualRobot {

    private int currentHopId;
    private boolean colorUpdated;
    private RGBColorType obstacleColor;

    public DiscoverColorRobot(int id, double x, double y, double heading) {
        super(id, x, y, heading);
    }

    public void setup() {
        super.setup();
        neoPixel.changeColor(0, 0, 0);
    }

    @Override
    public void discover(RGBColorType obstacleColor){
        this.obstacleColor = obstacleColor;
    }

    @Override
    public void loop() throws Exception {
        super.loop();

        double dist = distSensor.getDistance();
        RGBColorType color = colorSensor.getColor();

        if (dist < 25) {
            System.out.println("\t An obstacle detected, stop and observe the color");

            if(obstacleColor == color){
                System.out.println("\t Matching obstacle detected");
                //communicationInterrupt("message");
            }else {
                // Generate a random number in [-1000,1000] range
                // if even, rotate CW, otherwise rotate CCW an angle depends on the random number
                int random = -1000 + ((int) ((Math.random() * 2000)));
                int sign = (random % 2 == 0) ? 1 : -1;

                System.out.println("\t Not the obstacle we are looking for, go back and rotate " + ((sign > 0) ? "CW" : "CCW"));

                // Go back a little
                motion.move(-100, -100, 900);

                // rotate
                int loopCount = 0; // to avoid infinity loop
                while (distSensor.getDistance() < 35 && loopCount < 5) {
                    motion.rotate(50 * sign, 1000);
                    loopCount++;
                }
                // TODO: This is a temp update to restrict the robot into arena
                if (coordinates.getX() >= 150) coordinates.setX(145);
                if (coordinates.getX() <= -150) coordinates.setX(-145);
                if (coordinates.getY() >= 150) coordinates.setY(145);
                if (coordinates.getY() <= -150) coordinates.setY(-145);

                // rotate a little
                motion.rotate(50 * sign, 500);

            }
        } else {
            motion.move(100, 100, 1000);
        }

    }

    @Override
    public void interrupt() {

    }

    @Override
    public void sensorInterrupt(String sensor, String value) {

        switch (sensor) {
            case "distance":
                System.out.println("Distance sensor interrupt on " + id);
                break;

            case "color":
                System.out.println("Color sensor interrupt on " + id);
                break;

            case "proximity":
                System.out.println("Proximity sensor interrupt on " + id);
                break;

            default:
                // TODO: make an exception other than println
                System.out.println("Unknown sensor type");
        }
    }

    @Override
    public void communicationInterrupt(String msg) {
        System.out.println("communicationInterrupt on " + id + " with msg:" + msg);

        // split the message
        String[] s = msg.split(" ");

        if (s.length == 4) {
            int hopId = Integer.parseInt(s[0]);

            if (colorUpdated) {
                // a returning message, don't forward
                neoPixel.changeColor(0, 0, 0);

            } else if (hopId > currentHopId) {
                int hopR = Integer.parseInt(s[1]);
                int hopG = Integer.parseInt(s[2]);
                int hopB = Integer.parseInt(s[3]);
                neoPixel.changeColor(hopR, hopG, hopB);
                currentHopId = hopId;
                colorUpdated = true;

                delay(2000);

                // Send it to the next robot
                simpleComm.sendMessage((hopId + 1) + " " + hopR + " " + hopG + " " + hopB);
                neoPixel.changeColor(0, 0, 0);
            }
        } else {
            System.out.println("Invalid msg received");
        }
    }

    public void start() {
        super.start();

        // Things to do when start action
        neoPixel.changeColor(0, 0, 0);
        colorUpdated = false;
        currentHopId = -1;
    }

}
