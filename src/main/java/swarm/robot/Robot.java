package swarm.robot;

import swarm.mqtt.MqttHandler;
import swarm.mqtt.MqttMsg;
import swarm.patterns.CircularMove;
import swarm.patterns.GoToGoal;
import swarm.patterns.Pattern;
import swarm.robot.helpers.Coordinate;
import swarm.robot.helpers.MotionController;
import swarm.robot.sensors.DistanceSensor;

public class Robot implements Runnable {

    // Sensors
    private DistanceSensor distSensor;
    private Pattern pattern;
    public MotionController motion;

    // Types
    public enum RobotType {UNDEFINED, PHYSICAL, VIRTUAL}

    public enum RobotState {IDEAL, MOVE}

    // Variables
    protected int id;
    protected Coordinate coordinates;
    protected RobotType type;

    public MqttHandler mqttHandler;

    public Robot(int id, double x, double y, double heading) {

        this.id = id;
        String channel = "v1";

        mqttHandler = new MqttHandler("68.183.188.135", 1883, "swarm_user", "swarm_usere15", channel);
        coordinates = new Coordinate(id, x, y, heading, mqttHandler);

        this.pattern = new CircularMove(this);
        this.motion = new MotionController(coordinates);

        //System.out.println(id + "> Robot object created !");
    }

    public void setup() {

        // TODO: Subscribe to default topics -> @DDilshani

        // ---------------------------------

        distSensor = new DistanceSensor(id, mqttHandler);
        pattern.setup();
        
        System.out.println(id + "> Robot setup completed !");
    }

    public void loop() {
        pattern.loop();
        //coordinates.print();
        delay(500);
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
            handleSubscribeQueue();
            handlePublishQueue();
        }
    }

    private void handleSubscribeQueue() {
        MqttMsg m = mqttHandler.inQueue();

        while (m != null) {
            switch (m.topicGroups[1]) {
                case "robot":
                    // Robot messages
                    System.out.println("Robot message received");

                    break;
                case "sensor":
                    // Sensor messages

                    if (m.topicGroups[2].equals("dist")) {
                        System.out.println("distance sensor message received");
                        distSensor.handleSubscription(m);
                    }

                    break;
                case "localization":
                    // Localization messages
                    System.out.println("localization message received");

                    break;
                case "comm":
                    // Communication messages
                    System.out.println("communication message received");

                    break;
            }

            m = mqttHandler.inQueue();
        }
    }

    private void handlePublishQueue() {
        // TODO: publish messages in outQueue -> @DDilshani

    }
}