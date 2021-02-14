package swarm.Interfaces;
import swarm.mqtt.MqttMsg;
import swarm.robot.Robot;
import swarm.robot.exception.RGBColorException;

import java.util.HashMap;

public interface IMqttHandler {

    public void handleSubscription(Robot r, MqttMsg m) throws RGBColorException;

}
