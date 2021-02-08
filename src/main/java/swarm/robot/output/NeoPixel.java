package swarm.robot.output;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import swarm.mqtt.RobotMqttClient;
import swarm.mqtt.MqttMsg;
import swarm.robot.Robot;

import java.util.HashMap;

public class NeoPixel extends AbstractOutput {

    private enum mqttTopic {NEOPIXEL_IN}

    private final HashMap<NeoPixel.mqttTopic, String> topicsSub = new HashMap<NeoPixel.mqttTopic, String>();

    private int R, G, B;

    public NeoPixel(int robotId, RobotMqttClient m) {
        super(robotId, m);

        subscribe(NeoPixel.mqttTopic.NEOPIXEL_IN, "output/neopixel/" + robotId);
    }

    @Override
    protected void subscribe(String topic) {

    }

    protected void subscribe(NeoPixel.mqttTopic key, String topic) {
        topicsSub.put(key, topic);      // Put to the queue
        robotMqttClient.subscribe(topic);   // Subscribe through MqttHandler
    }

    @Override
    public void handleSubscription(Robot r, MqttMsg m) throws ParseException {
        String topic = m.topic;
        String msg = m.message;

        JSONParser parser = new JSONParser();

        if (topic.equals(topicsSub.get(mqttTopic.NEOPIXEL_IN))) {
            // output/neopixel/{id}
            //System.out.println("Received: " + topic + "> " + msg);

            String[] colors = msg.split(" ");
            R = Integer.parseInt(colors[0]);
            G = Integer.parseInt(colors[1]);
            B = Integer.parseInt(colors[2]);

            System.out.println("Received: " + topic + "> " + R + "," + G + "," + B);
            changeColor(R, G, B);

        } else {
            System.out.println("Received (unknown): " + topic + "> " + msg);
        }
    }

    public void changeColor(int r, int g, int b) {
        // TODO: Validate r,g,b to be between [0,255] @DDilshani
        R = r;
        G = g;
        B = b;

        JSONObject obj = new JSONObject();
        obj.put("id", robotId);
        obj.put("R", R);
        obj.put("G", G);
        obj.put("B", B);

        robotMqttClient.publish("output/neopixel", obj.toJSONString());
    }
}
