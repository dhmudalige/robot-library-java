package swarm.robot.sensors;

import swarm.mqtt.MqttHandler;
import swarm.mqtt.MqttMsg;

import java.util.HashMap;

public abstract class AbstractSensor {

    protected MqttHandler mqttHandler;
    protected int robotId;

    public AbstractSensor(int robotId, MqttHandler m) {
        this.mqttHandler = m;
        this.robotId = robotId;
    }

    private void subscribe(String topic) {
        // This will subscribe to a given topic through mqttHandler
    }

    public void handleSubscription(MqttMsg m) {
        //  This will handle incoming messages with already subscribed topics
    }

}
