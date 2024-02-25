package swarm.robot.sensors;

import swarm.mqtt.RobotMqttClient;
import swarm.robot.Robot;

/**
 * Compass Sensors Emulator class
 * 
 * @author TBD
 */
public class CompassSensor extends AbstractSensor {

    public CompassSensor(Robot robot, RobotMqttClient mqttClient) {
        super(robot, mqttClient);
    }

    public double readComapss() throws Exception {
        // To be implemented to return the heading direction of the robot

        return 0;
    }
}
