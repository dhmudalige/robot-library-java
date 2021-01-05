package swarm.robot.helpers;

public class MotionController {

    final private double RobotWidth = 8.75; // in cm
    final private int maxInterval = 100; // This is the maximum interval allowed to coordinate calculation, smaller values increase the smoothness of the movement
    final private double speedFactor = 1.0; // to be match with cm/s speed

    private Coordinate c;

    public MotionController(Coordinate c) {
        this.c = c;
    }

    public void move(int leftSpeed, int rightSpeed, int interval) {
        int steps = (int) Math.ceil((double) interval / maxInterval);
        int stepInterval = interval / steps;

        for (int j = 0; j < steps; j++) {
            double dL = leftSpeed * speedFactor * (interval / 1000.0);
            double dR = rightSpeed * speedFactor * (interval / 1000.0);
            double d = (dL + dR) / 2.0;
            double h = c.getHeadingRad();

            double x = c.getX() + d * Math.cos(h);
            double y = c.getY() + d * Math.sin(h);
            double heading = (c.getHeadingRad() + (dR - dL) / RobotWidth); // in radians

            c.setCoordinate(x, y,  (heading * 180) / Math.PI);
            delay(stepInterval); // Adding a delay to make the movement in nearly realtime
        }
        c.print();
        c.publishCoordinate(); // Publish to visualizer through MQTT
    }

    private void delay(int interval) {
        try {
            Thread.sleep(interval);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

}