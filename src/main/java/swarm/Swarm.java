package swarm;

import swarm.robot.Robot;
import swarm.robot.VirtualRobot;

public class Swarm extends Thread {
    public static void main( String[] args ){

        int virtualRobotCount = 1;

        Robot[] vr = new VirtualRobot[virtualRobotCount];

        for(int i=0;i<virtualRobotCount;i++){
            vr[i] = new VirtualRobot(i,0,40*i,90);
            new Thread(vr[i]).start();
        }
    }
}