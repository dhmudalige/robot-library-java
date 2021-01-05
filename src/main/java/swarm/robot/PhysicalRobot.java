package swarm.robot;

public class PhysicalRobot extends Robot {

    public PhysicalRobot(int id, double x, double y, double heading) {
        super(id, x, y, heading);
        this.type = RobotType.PHYSICAL;
    }
}
