package swarm.robot.sensors;

import swarm.Interfaces.IMqttHandler;
import swarm.mqtt.RobotMqttClient;
import swarm.mqtt.MqttMsg;
import swarm.robot.Robot;
import swarm.robot.exception.RGBColorException;

public abstract class AbstractSensor implements IMqttHandler {

    protected RobotMqttClient robotMqttClient;
    protected int robotId;
    protected Robot robot;

    public AbstractSensor(Robot robot, RobotMqttClient m) {
        this.robotMqttClient = m;
        this.robotId = robot.getId();
        this.robot = robot;
    }

    private void subscribe(String topic) {
        // This will subscribe to a given topic through mqttHandler
    }

    public void handleSubscription(Robot r, MqttMsg m) throws RGBColorException {
        //  This will handle incoming messages with already subscribed topics
    }

}
