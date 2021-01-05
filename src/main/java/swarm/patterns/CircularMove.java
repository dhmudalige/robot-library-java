package swarm.patterns;

import swarm.robot.Robot;

public class CircularMove extends Pattern{
    public CircularMove(Robot r) {
        super(r);
    }

    public void setup(){

    }

    public void loop(){
        r.motion.move(100,70,100);
    }
}