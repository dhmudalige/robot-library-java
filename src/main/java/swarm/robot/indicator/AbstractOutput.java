package swarm.robot.indicator;

import org.json.simple.parser.ParseException;
import swarm.mqtt.RobotMqttClient;
import swarm.mqtt.MqttMsg;
import swarm.robot.Robot;

public abstract class AbstractOutput {
    protected RobotMqttClient robotMqttClient;
    protected int robotId;

    public AbstractOutput(int robotId, RobotMqttClient m) {
        this.robotMqttClient = m;
        this.robotId = robotId;
    }

    // This will subscribe to a given topic through mqttHandler
    protected abstract void subscribe(String topic);

    //  This will handle incoming messages with already subscribed topics
    public abstract void handleSubscription(Robot r,MqttMsg m) throws ParseException;


}
