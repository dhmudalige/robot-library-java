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
        coordinates = new Coordinate(id, x, y, heading, mqttHandler.outQueue);

        this.pattern = new CircularMove(this);
        this.motion = new MotionController(coordinates);

        //System.out.println(id + "> Robot object created !");
    }

    public void setup() {
        //Subscribe to default topics
        mqttHandler.subscribe("robot/msg/" + getId());
        mqttHandler.subscribe("robot/msg/broadcast");

        distSensor = new DistanceSensor(id, mqttHandler);
        pattern.setup();

        System.out.println(id + "> Robot setup completed !");
    }

    public void loop() {
        //pattern.loop();
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
        // Handle the messages in incoming queue

        for (MqttMsg item : mqttHandler.inQueue) {
            MqttMsg m = mqttHandler.inQueue.poll();

            switch (m.topicGroups[0]) {
                case "robot":
                    // Robot messages
                    System.out.println("Robot message received");

                    break;
                case "sensor":
                    // Sensor messages

                    if (m.topicGroups[1].equals("distance")) {
                        // System.out.println("distance sensor message received");
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
        }
    }

    private void handlePublishQueue() {
        // Publish messages which are collected in the outgoing queue

        for (MqttMsg item : mqttHandler.outQueue) {
            MqttMsg m = mqttHandler.outQueue.poll();
            mqttHandler.publish(m.topic, m.message, m.QoS);
        }
    }
}