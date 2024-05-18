package Robots.models;

public class RobotPosition {
    public int robotId;
    public double x, y;

    public RobotPosition(int robotId, double x, double y) {
        this.robotId = robotId;
        this.x = x;
        this.y = y;
    }
}