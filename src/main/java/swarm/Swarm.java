package swarm;

import swarm.robot.Robot;

public class Swarm extends Thread {
    public static void main( String[] args ){

        int robotCount = 2;

        Robot[] r = new Robot[robotCount];

        for(int i=0;i<robotCount;i++){
            r[i] = new Robot(i,0,40*i,90);
            new Thread(r[i]).start();
        }
    }
}