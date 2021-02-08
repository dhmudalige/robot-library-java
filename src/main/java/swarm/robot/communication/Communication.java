package swarm.robot.communication;

import swarm.Interfaces.IMqttHandler;
import swarm.mqtt.RobotMqttClient;
import swarm.robot.helpers.Coordinate;

import java.util.HashMap;

public abstract class Communication implements IMqttHandler {
    protected RobotMqttClient robotMqttClient;
    protected int robotId;

    public Communication(int robotId, RobotMqttClient m){
        this.robotMqttClient = m;
        this.robotId = robotId;
    }

    void subscribe(String topic) {

    }
}