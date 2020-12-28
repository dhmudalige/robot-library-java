package swarm.robot;

import swarm.mqtt.MQTT;
import swarm.robot.helpers.Coordinates;
import swarm.robot.sensors.DistanceSensor;

public class Robot implements Runnable {

    // Sensors
    private DistanceSensor distSensor;

    // Types
    public enum RobotType {PHYSICAL, VIRTUAL}
    public enum RobotState {IDEAL, MOVE}

    // Variables
    private int id;
    private Coordinates coordinates;
    private RobotType type;

    // TODO: Encapsulate this
    public MQTT mqtt;

    public Robot(int id, double x, double y, double heading) {

        this.id = id;
        coordinates = new Coordinates(x,y,heading);

        //TODO: Need to implement this
        this.type = (id < 100) ? RobotType.PHYSICAL : RobotType.VIRTUAL;

        //System.out.println(id + "> Robot object created !");
    }

    public void setup() {
        // TODO: add the things need to be setup at the beginning

        mqtt = new MQTT("68.183.188.135", 1883, "swarm_user", "swarm_usere15");

        //m.publish("v1/test", "Hello");
        //m.subscribe("hello");

        distSensor = new DistanceSensor(id, mqtt);

        System.out.println(id + "> Robot setup completed !");
    }

    public void loop() {
        //System.out.println("id: " + getId());
        //distSensor.sendDistance(100);

        delay(1000);
    }

    private void delay(int milliseconds) {
        try {
            Thread.sleep(1000);

        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }


    // -----------------------------------------------------------------------------

    public int getId() {
        return this.id;
    }

    public RobotType getType() {
        return this.type;
    }


    @Override
    public void run() {

        setup();

        while (true) {
            loop();
        }
    }

}