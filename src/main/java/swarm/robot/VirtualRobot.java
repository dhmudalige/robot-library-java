package swarm.robot;

import swarm.robot.Robot;

public class VirtualRobot extends Robot {

    public VirtualRobot(int id, double x, double y, double heading) {
        super(id, x, y, heading);
        this.type = RobotType.VIRTUAL;
    }
}
