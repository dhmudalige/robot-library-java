package swarm.patterns;

import swarm.robot.Robot;

public class GoToGoal extends Pattern{
    public GoToGoal(Robot r) {
        super(r);
    }

    public void setup(){

    }

    public void loop(){

        r.motion.goToGoal(100,100,50);
    }
}
