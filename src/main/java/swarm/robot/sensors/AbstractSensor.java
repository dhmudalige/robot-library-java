package swarm.robot.sensors;

import swarm.Interfaces.IMqttHandler;
import swarm.mqtt.RobotMqttClient;
import swarm.mqtt.MqttMsg;
import swarm.robot.Robot;

public abstract class AbstractSensor implements IMqttHandler {

    protected RobotMqttClient robotMqttClient;
    protected int robotId;

    public AbstractSensor(int robotId, RobotMqttClient m) {
        this.robotMqttClient = m;
        this.robotId = robotId;
    }

    private void subscribe(String topic) {
        // This will subscribe to a given topic through mqttHandler
    }

    public void handleSubscription(Robot r,MqttMsg m) {
        //  This will handle incoming messages with already subscribed topics
    }

}
