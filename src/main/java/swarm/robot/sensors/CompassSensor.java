package swarm.robot.sensors;

import org.json.simple.parser.ParseException;
import swarm.mqtt.MqttMsg;
import swarm.mqtt.RobotMqttClient;
import swarm.robot.Robot;
import swarm.robot.exception.SensorException;

import java.util.HashMap;

/**
 * Compass Sensors Emulator class
 * 
 * @author Dinuka Mudalige
 */
public class CompassSensor extends AbstractSensor {
    private enum mqttTopic {
        COMPASS_OUT
    }

    private final static int MQTT_TIMEOUT = 2000;

    private boolean compassLock = false;
    private HashMap<CompassSensor.mqttTopic, String> topicsSub = new HashMap<>();
//    private CompassReadingType compass;
    private double heading;

    public CompassSensor(Robot robot, RobotMqttClient mqttClient) {
        super(robot, mqttClient);
        subscribe(CompassSensor.mqttTopic.COMPASS_OUT, "sensor/compass/" + robotId + "/?");
    }

    /**
     * Subscribe to a MQTT topic
     *
     * @param key   Subscription topic key
     * @param topic Subscription topic value
     */
    private void subscribe(CompassSensor.mqttTopic key, String topic) {
        topicsSub.put(key, topic);
        robotMqttClient.subscribe(topic);
    }

    /**
     * Handle compassSensor related MQTT subscriptions
     *
     * @param robot Robot object
     * @param m     Subscription topic received object
     */
    @Override
    public void handleSubscription(Robot robot, MqttMsg m) throws RuntimeException{
        heading = Integer.parseInt(m.message);
    }

    /**
     * Get the emulated compass sensor reading from the simulator
     *
     * @return heading as double
     * @throws SensorException
     */
    public double readCompass() throws Exception {
        compassLock = true; // Acquire the compass sensor lock

        long startTime = System.currentTimeMillis();
        boolean timeout = false;

        while (compassLock && !timeout) {
            try {
                robot.handleSubscribeQueue();
            } catch (ParseException e) {
                e.printStackTrace();
            }

            robot.delay(100);
            timeout = (System.currentTimeMillis() - startTime > MQTT_TIMEOUT);
        }

        if (timeout) {
            throw new SensorException("Compass sensor timeout");
        }

        return heading;
    }
}
