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
        subscribe("comm/in/dir/" + robotId);
    }

    @Override
    void subscribe(String topic) {
        topicsSub.put(SimpleCommunication.mqttTopic.COMMUNICATION_IN, topic);      // Put to the queue
        robotMqttClient.subscribe(topic);   // Subscribe through MqttHandler
    }

    @Override
    public void handleSubscription(Robot r, MqttMsg m) {
        // comm/in/
        String topic = m.topic;
        String msg = m.message;

        if (topic.equals(topicsSub.get(SimpleCommunication.mqttTopic.COMMUNICATION_IN))) {
            // comm/in/{id}
            // System.out.println("Received: " + topic + "> " + msg);
            r.communicationInterrupt(msg);

        } else {
            System.out.println("Received (unknown): " + topic + "> " + msg);
        }
    }
}