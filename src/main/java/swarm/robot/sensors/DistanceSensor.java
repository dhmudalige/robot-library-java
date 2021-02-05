package swarm.robot.sensors;

import swarm.mqtt.RobotMqttClient;

import java.util.HashMap;

import org.json.simple.JSONObject;
import swarm.mqtt.MqttMsg;
import swarm.robot.Robot;

public class DistanceSensor extends AbstractSensor {

    private enum mqttTopic {DISTANCE_IN, DISTANCE_LOOK}

    private HashMap<mqttTopic, String> topicsSub = new HashMap<mqttTopic, String>();

    private boolean dist_lock = false;
    private int dist_value = 0;

    public DistanceSensor(int robotId, RobotMqttClient m) {
        super(robotId, m);
        subscribe(mqttTopic.DISTANCE_IN, "sensor/distance/" + robotId);
        subscribe(mqttTopic.DISTANCE_LOOK, "sensor/distance/" + robotId + "/?");
    }

    private void subscribe(mqttTopic key, String topic) {
        topicsSub.put(key, topic);      // Put to the queue
        robotMqttClient.subscribe(topic);   // Subscribe through MqttHandler
    }

    @Override
    public void handleSubscription(Robot robot, MqttMsg m) {
        // sensor/distance/
        String topic = m.topic;
        String msg = m.message;

        if (topic.equals(topicsSub.get(mqttTopic.DISTANCE_IN))) {
            // sensor/distance/{id}
            dist_lock = false;
            dist_value = Integer.parseInt(msg);

            // robot.sensorInterrupt("distance", msg);

        } else if (topic.equals(topicsSub.get(mqttTopic.DISTANCE_LOOK))) {
            // TODO: What we need to do in here ?

            // sensor/distance/{id}/?
            System.out.println("Received: " + topic + "> " + msg);

        } else {
            System.out.println("Received (unknown): " + topic + "> " + msg);
        }

    }

    public float getDistance() {
        // TODO: implement a blocking call for this. -> @NuwanJ
        // This is a blocking call
        // Publish to v1/sensor/distance/ -> {id: this.id}
        // Wait until message received to v1/sensor/distance/{robotId}
        dist_lock = true;

        robotMqttClient.publish("v1/sensor/distance", Integer.toString(robotId));
        while (dist_lock != false) {
            // TODO: add some timeout to avoid a lock
            // Need to execute MQTT Subscription Queue during this loop
        }

        return dist_value;
    }

    public void sendDistance(double d) {
        // Only for test, virtual robots will not invoke this.

        JSONObject obj = new JSONObject();
        obj.put("id", robotId);
        obj.put("dist", d);

        robotMqttClient.publish("sensor/distance/", obj.toJSONString());
    }

}
