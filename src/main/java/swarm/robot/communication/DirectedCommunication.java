package swarm.robot.communication;

import swarm.mqtt.RobotMqttClient;
import swarm.mqtt.MqttMsg;
import swarm.robot.Robot;

import java.util.HashMap;

public class DirectedCommunication extends Communication {

    enum mqttTopic {COMMUNICATION_IN_DIR};
    private final HashMap<mqttTopic, String> topicsSub = new HashMap<mqttTopic, String>();

    public DirectedCommunication(int robotId, RobotMqttClient m){
        super(robotId, m);
        subscribe( "comm/in/dir" + robotId);
    }

    @Override
    void subscribe(String topic) {
        topicsSub.put(mqttTopic.COMMUNICATION_IN_DIR, topic);      // Put to the queue
        robotMqttClient.subscribe(topic);   // Subscribe through MqttHandler
    }

    @Override
    public void handleSubscription(Robot r, MqttMsg m) {
        // comm/in/
        String topic = m.topic;
        String msg = m.message;

        if (topic.equals(topicsSub.get(DirectedCommunication.mqttTopic.COMMUNICATION_IN_DIR))) {
            // comm/in/{id}
            // System.out.println("Received: " + topic + "> " + msg);
            r.communicationInterrupt(msg);

        } else {
            System.out.println("Received (unknown): " + topic + "> " + msg);
        }
    }
}