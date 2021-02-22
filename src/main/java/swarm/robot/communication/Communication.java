package swarm.robot.communication;

import swarm.Interfaces.IMqttHandler;
import swarm.mqtt.RobotMqttClient;

public abstract class Communication implements IMqttHandler {
    protected RobotMqttClient robotMqttClient;
    protected int robotId;

    public Communication(int robotId, RobotMqttClient m) {
        this.robotMqttClient = m;
        this.robotId = robotId;
    }

    public abstract void sendMessage(String msg);

    public abstract void sendMessage(String msg, int distance);
}