package swarm.robotImplementations;

import swarm.robot.VirtualRobot;

public class ColorRippleRobot extends VirtualRobot {

    private int currentHopId;
    private boolean colorUpdated;

    public ColorRippleRobot(int id, double x, double y, double heading) {
        super(id, x, y, heading);
    }

    public void setup() {
        super.setup();

        neoPixel.changeColor(0, 0, 0);
        colorUpdated = false;
        currentHopId = -1;
    }

    @Override
    public void loop() {
        super.loop();

        // Anything specially check in continuously
    }

    @Override
    public void execute() {
        motion.move(255, 127, 1000);
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
                // a reverse message, don't forward
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
                // neoPixel.changeColor(0, 0, 0);
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
