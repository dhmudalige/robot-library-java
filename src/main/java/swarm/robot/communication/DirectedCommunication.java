package swarm.robot.communication;

import swarm.mqtt.RobotMqttClient;
import swarm.mqtt.MqttMsg;
import swarm.robot.Robot;

import java.util.HashMap;

public class DirectedCommunication extends Communication {

    enum mqttTopic {COMMUNICATION_IN};
    private final HashMap<SimpleCommunication.mqttTopic, String> topicsSub = new HashMap<SimpleCommunication.mqttTopic, String>();

    public DirectedCommunication(int robotId, RobotMqttClient m){
        super(robotId, m);

    }

    @Override
    void subscribe(String topic) {

    }

    @Override
    public void handleSubscription(Robot r, MqttMsg m) {

    }
}