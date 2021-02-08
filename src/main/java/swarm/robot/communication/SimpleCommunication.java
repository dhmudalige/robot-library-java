package swarm.robot.communication;

import org.json.simple.JSONObject;
import swarm.mqtt.RobotMqttClient;
import swarm.mqtt.MqttMsg;
import swarm.robot.Robot;
import swarm.robot.helpers.Coordinate;

import java.util.HashMap;

public class SimpleCommunication extends Communication  {

    enum mqttTopic {COMMUNICATION_IN};
    private final HashMap<mqttTopic, String> topicsSub = new HashMap<mqttTopic, String>();

    public SimpleCommunication(int robotId, RobotMqttClient m) {
        super(robotId, m);

        subscribe(SimpleCommunication.mqttTopic.COMMUNICATION_IN, "comm/in/" + robotId);
    }

    private void subscribe(mqttTopic key, String topic) {
        topicsSub.put(key, topic);      // Put to the queue
        robotMqttClient.subscribe(topic);   // Subscribe through MqttHandler
    }

    @Override
    void subscribe(String topic) {

    }

    @Override
    public void handleSubscription(Robot robot, MqttMsg m) {
        // comm/in/
        String topic = m.topic;
        String msg = m.message;

        if (topic.equals(topicsSub.get(mqttTopic.COMMUNICATION_IN))) {
            // comm/in/{id}
            // System.out.println("Received: " + topic + "> " + msg);
            robot.communicationInterrupt(msg);

        } else {
            System.out.println("Received (unknown): " + topic + "> " + msg);
        }

    }

    public void sendMessage(String msg) {
        // Only for test, virtual robots will not invoke this.

        JSONObject obj = new JSONObject();
        obj.put("id", robotId);
        obj.put("msg", msg);

        robotMqttClient.publish("comm/out/simple", obj.toJSONString());
    }
}

