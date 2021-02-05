package swarm.robotImplementations;

import swarm.robot.VirtualRobot;

public class MoveRobot extends VirtualRobot {

    public MoveRobot(int id, double x, double y, double heading) {
        super(id, x, y, heading);
    }

    public void setup(){
        super.setup();
    }

    public void loop(){
        super.loop();

        motion.move(255, 127, 1000);
        //delay(1000);
    }

}
