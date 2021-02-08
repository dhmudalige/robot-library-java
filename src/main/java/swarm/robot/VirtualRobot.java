package swarm.robot;

public class VirtualRobot extends Robot {

    public VirtualRobot(int id, double x, double y, double heading) {

        super(id, x, y, heading, 'V');

    }

    @Override
    public void interrupt() {

    }

    @Override
    public void sensorInterrupt(String sensor, String value) {

    }

    @Override
    public void communicationInterrupt(String msg) {

    }
}
