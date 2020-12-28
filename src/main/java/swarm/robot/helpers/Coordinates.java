package swarm.robot.helpers;

public class Coordinates {
    private double x, y, heading;

    public Coordinates(double x, double y, double heading){
        this.x = x;
        this.y = y;
        this.heading = heading;
    }

    public double getX() {
        return this.x;
    }
    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }
    public void setY(double y) {
        this.y = y;
    }

    public double getHeading() {
        return this.heading;
    }

    public void setHeading(double heading) {
        // TODO: map between [-180,180]
        this.heading = heading;
    }

    public void setCoordinate(double x, double y) {
        this.x = x;
        this.y = y;
    }





}
