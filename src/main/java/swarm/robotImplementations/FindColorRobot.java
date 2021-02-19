package swarm.robotImplementations;

import swarm.robot.VirtualRobot;

public class FindColorRobot extends VirtualRobot {

    private int currentHopId;
    private boolean colorUpdated;

    public FindColorRobot(int id, double x, double y, double heading) {
        super(id, x, y, heading);
    }

    public void setup() {
        super.setup();
        neoPixel.changeColor(0, 0, 0);
    }

    @Override
    public void loop() throws Exception {
        super.loop();

        // Anything specially check in continuously
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

    }

    public void start() {
        super.start();

        // Things to do when start action
        neoPixel.changeColor(0, 0, 0);
    }

}
