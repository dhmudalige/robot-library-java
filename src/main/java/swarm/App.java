package swarm;

import swarm.robot.Robot;
import swarm.mqtt.*;


public class App 
{
    public static void main( String[] args ){

        //Robot r = new Robot(1,0,0,0);
        //System.out.println(r.getId());

        MQTT m = new MQTT("68.183.188.135", 1883, "swarm_user", "swarm_usere15");

        m.publish("v1/test", "Hello");
        m.subscribe("hello");
    }
}
