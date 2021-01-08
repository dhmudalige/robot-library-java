package swarm.robot.sensors;

import swarm.mqtt.MqttHandler;

import java.util.HashMap;

import org.json.simple.JSONObject;
import swarm.mqtt.MqttMsg;

public class DistanceSensor extends AbstractSensor {

    private enum mqttTopic {DISTANCE_IN, DISTANCE_LOOK}

    private HashMap<mqttTopic, String> topicsSub = new HashMap<mqttTopic, String>();

    public DistanceSensor(int robotId, MqttHandler m) {
        super(robotId, m);
        subscribe(mqttTopic.DISTANCE_IN, "sensor/distance/" + robotId);
        subscribe(mqttTopic.DISTANCE_LOOK, "sensor/distance/" + robotId + "/?");
    }

    private void subscribe(mqttTopic key, String topic) {
        topicsSub.put(key, topic);      // Put to the queue
        mqttHandler.subscribe(topic);   // Subscribe through MqttHandler
    }

    @Override
    public void handleSubscription(MqttMsg m) {
        // sensor/distance/
        String topic = m.topic;
        String msg = m.message;

        if (topic.equals(topicsSub.get(mqttTopic.DISTANCE_IN))) {
            // sensor/distance/{id}
            System.out.println("Received: " + topic + "> " + msg);

        } else if (topic.equals(topicsSub.get(mqttTopic.DISTANCE_LOOK))) {
            // sensor/distance/{id}/?
            System.out.println("Received: " + topic + "> " + msg);

        } else {
            System.out.println("Received (unknown): " + topic + "> " + msg);
        }

    }

    public float getDistance() {
        // TODO: implement a blocking call for this. -> @NuwanJ
        // Publish to v1/sensor/distance/ -> {id: this.id}
        // Wait until message received to v1/sensor/distance/{this.id}

        return 0;
    }

    public void sendDistance(double d) {
        // Only for test, virtual robots will not invoke this.

        JSONObject obj = new JSONObject();
        obj.put("id", robotId);
        obj.put("dist", d);

        mqttHandler.publish("sensor/distance/", obj.toJSONString());
    }

    public HashMap<mqttTopic, String> topicsSub() {
        return topicsSub;
    }
}
