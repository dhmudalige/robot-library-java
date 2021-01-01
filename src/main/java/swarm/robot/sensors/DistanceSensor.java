package swarm.robot.sensors;

import swarm.mqtt.MqttHandler;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;
import swarm.mqtt.MqttMsg;

public class DistanceSensor {

    private List<String> topicsSub = new ArrayList<String>();
    private MqttHandler mqttHandler;
    private int robotId;

    public DistanceSensor(int robotId, MqttHandler m) {
        //m.publish("v1/distance/test", String.valueOf(robotId) );

        this.mqttHandler = m;
        this.robotId = robotId;

        topicsSub.add("v1/sensor/distance/" + robotId);
        topicsSub.add("v1/sensor/distance/" + robotId + "/?");

        subscribe();

    }

    private void subscribe() {
        for (String topic : topicsSub) {
            mqttHandler.subscribe(topic);
            System.out.println("Subscribed to " + topic);
        }
    }

    public void handleSubscription(MqttMsg m) {
        // TODO: handle logic for the message,
    }

    public float getDistance() {
        // TODO: implement a blocking call for this.
        // Publish to v1/sensor/distance/ -> {id: this.id}
        // Wait until message received to v1/sensor/distance/{this.id}

        return 0;
    }

    public void sendDistance(double d) {
        // Only for test, virtual robots will not invoke this.

        JSONObject obj = new JSONObject();
        obj.put("id", robotId);
        obj.put("dist", d);

        mqttHandler.publish("v1/sensor/distance/", obj.toJSONString());
    }

    public List<String> topicsSub() {
        return topicsSub;
    }
}
