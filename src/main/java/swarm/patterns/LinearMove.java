package swarm.patterns;

import swarm.robot.Robot;

public class LinearMove extends Pattern {

    public LinearMove(Robot r){
        super(r);
    }

    public void setup(){

    }

    public void loop(){
        r.motion.move(100,100,100);
    }


}
