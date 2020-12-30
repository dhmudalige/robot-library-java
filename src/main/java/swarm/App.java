package swarm;

import swarm.robot.Robot;
import swarm.mqtt.*;

class MyRunnable implements Runnable {
    private Robot robot;
    public MyRunnable(Robot r) {
        robot= r;
    }
    public void run() {
        while(true) robot.run();
    }
}

public class App extends Thread {
    public static void main( String[] args ){
        MQTT m = new MQTT("68.183.188.135", 1883, "swarm_user", "swarm_usere15");
        m.publish("v1/test", "Hello");
        m.subscribe("hello");

        Robot r1 = new Robot(1,0,0,90);
        Runnable t1 = new MyRunnable(r1);
        new Thread(t1).start();

        Robot r2 = new Robot(1,0,0,80);
        Runnable t2 = new MyRunnable(r2);
        new Thread(t2).start();
    }
}