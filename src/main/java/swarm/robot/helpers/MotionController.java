package swarm.robot.helpers;

public class MotionController {

    static final private double RobotWidth = 8.75; // in cm
    static final private double WheelRadius = 3.5; // in cm

    static final private int maxInterval = 100; // This is the maximum interval allowed to coordinate calculation, smaller values increase the smoothness of the movement
    static final private double speedFactor = 1.0; // to be match with cm/s speed

    private Coordinate c;

    public MotionController(Coordinate c) {
        this.c = c;
    }

    public void move(int leftSpeed, int rightSpeed) {
        move(leftSpeed, rightSpeed, maxInterval);
    }

    // validate speeds to be between [-254, 255]
    public boolean speedIsInRange(int speed){
        if (-254 < speed && speed < 255) return true;
        else return false;
    }

    public void move(int leftSpeed, int rightSpeed, int interval) {
        if(speedIsInRange(leftSpeed) && speedIsInRange(rightSpeed)){
            System.out.println("Valid speed");
        }

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

            c.setCoordinate(x, y, Math.toDegrees(heading));
            //delay(stepInterval); // Adding a delay to make the movement in nearly realtime
        }
        c.print();
        c.publishCoordinate(); // Publish to visualizer through MQTT
    }

    public boolean goToGoal(double targetX, double targetY, int velocity) {
        return goToGoal(targetX, targetY, velocity, maxInterval);
    }

    public boolean goToGoal(double targetX, double targetY, int velocity, int interval) {
        // TODO: implement this

        double x = c.getX();
        double y = c.getY();
        double heading = c.getHeadingRad();
        double dx = targetX - x;
        double dy = targetY - y;
        double phiD = Math.atan2(dy, dx);
        double w = 0.2*(phiD - heading);

        System.out.println("dx:" + dx + " dy:" + dy + " head:"+ Math.toDegrees(heading) + " w:" + Math.toDegrees(w) + " phiD:" + phiD);

        c.setX(x + 5 * Math.cos(w));
        c.setY(y + 5 * Math.sin(w));
        c.setHeadingRad(heading + w);
        c.publishCoordinate(); // Publish to visualizer through MQTT

        //double vR = (2 * velocity/2 + w * RobotWidth) / 2 * WheelRadius;
        //double vL = (2 * velocity/2 - w * RobotWidth) / 2 * WheelRadius;

        //System.out.println("vL:" + vL + " vR:" + vR + "\n" );
        //move((int) vL, (int) vR, interval);

        return (c.getX() == targetX && c.getY() == targetY);
    }

    private double PID(double e) {
        return 1 * e;
    }

    private void delay(int interval) {
        try {
            Thread.sleep(interval);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    public static double getSlope(double x1, double y1, double x2, double y2) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        return Math.toDegrees(Math.atan2(dy, dx));
    }

}