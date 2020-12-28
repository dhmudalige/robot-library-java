package swarm.robot.sensors;

import swarm.mqtt.MQTT;

import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONObject;

public class DistanceSensor {

    private List<String> topicsSub = new ArrayList<String>();
    private MQTT mqtt;
    private int robotId;

    public DistanceSensor(int robotId, MQTT m) {
        //m.publish("v1/distance/test", String.valueOf(robotId) );

        this.mqtt = m;
        this.robotId = robotId;

        topicsSub.add("v1/sensor/distance/" + robotId);
        topicsSub.add("v1/sensor/distance/" + robotId + "/?");

        subscribe();

    }

    private void subscribe() {

        for( String topic : topicsSub){
            mqtt.subscribe(topic);
            System.out.println("Subscribed to " + topic);
        }
    }

    public void sendDistance(double d) {
        // Only for test, virtual robots will not invoke this.

        JSONObject obj = new JSONObject();
        obj.put("id", robotId);
        obj.put("dist", d);

        mqtt.publish("v1/sensor/distance/", obj.toJSONString());
    }

    public List<String> topicsSub() {
        return topicsSub;
    }
}
