package swarm.robot.sensors;

import org.json.simple.parser.ParseException;
import swarm.mqtt.RobotMqttClient;

import java.util.HashMap;

import org.json.simple.JSONObject;
import swarm.mqtt.MqttMsg;
import swarm.robot.Robot;

public class ProximitySensor extends AbstractSensor {

    private enum mqttTopic {PROXIMITY_IN}

    private HashMap<mqttTopic, String> topicsSub = new HashMap<mqttTopic, String>();

    private double dist0, dist1, dist2, dist3, dist4;

    private double prox_value = 0;

    private final static int MQTT_TIMEOUT = 1000;

    public ProximitySensor(Robot robot, RobotMqttClient m) {
        super(robot, m);
        subscribe(mqttTopic.PROXIMITY_IN, "sensor/proximity/" + robotId);
    }

    private void subscribe(mqttTopic key, String topic) {
        topicsSub.put(key, topic);      // Put to the queue
        robotMqttClient.subscribe(topic);   // Subscribe through MqttHandler
    }

    @Override
    public void handleSubscription(Robot robot, MqttMsg m) {
        // sensor/proximity/
        String topic = m.topic;
        String msg = m.message;

        if (topic.equals(topicsSub.get(mqttTopic.PROXIMITY_IN))) {
            // sensor/proximity/{id}
            //System.out.println("Input>" + msg);

            // TODO: Handle Infinity
            if (msg.compareTo("Infinity") == 0) {
                // -1 will be returned as a fail-proof option. Should throw an exception
                dist0 = -1; //Double.POSITIVE_INFINITY;
                dist1 = -1; //Double.POSITIVE_INFINITY;
                dist2 = -1; //Double.POSITIVE_INFINITY;
                dist3 = -1; //Double.POSITIVE_INFINITY;
                dist4 = -1; //Double.POSITIVE_INFINITY;
            } else {
                String[] distance = msg.split(" ");
                dist0 = Double.parseDouble(distance[0]);
                dist1 = Double.parseDouble(distance[1]);
                dist2 = Double.parseDouble(distance[2]);
                dist3 = Double.parseDouble(distance[3]);
                dist4 = Double.parseDouble(distance[4]);
            }

            // robot.sensorInterrupt("proximity", msg);

        } else {
            System.out.println("Received (unknown): " + topic + "> " + msg);
        }

    }

    public void sendProximity(double d0,double d1,double d2,double d3,double d4) {
        // Only for test, virtual robots will not invoke this.

        JSONObject obj = new JSONObject();
        obj.put("id", robotId);
        obj.put("prox0", d0);
        obj.put("prox1", d1);
        obj.put("prox2", d2);
        obj.put("prox3", d3);
        obj.put("prox4", d4);

        robotMqttClient.publish("sensor/proximity/", obj.toJSONString());
    }
}
