package swarm.robot;

public class Robot {

    private int id;
    private double x, y, heading;
    private RobotType type;

   public enum RobotType {PHYSICAL, VIRTUAL}

    public enum RobotState {IDEAL, MOVE}
  
    public void run() {
        int id=getId();
        double x=getX();
        double y=getY();
        double heading= getHeading();
        System.out.println("id: "+id+" x: "+x+" y: "+y+" heading:"+heading);
    }

    public enum State {
        IDEAL,
        MOVE
    }


    public Robot(int id, double x, double y, double heading) {

        this.id = id;
        this.x = x;
        this.y = y;
        this.heading = heading;

        //TODO: Need to implement this
        this.type = (id < 100) ? RobotType.PHYSICAL : RobotType.VIRTUAL;

        System.out.println("Robot object created !");
    }

    public int getId() {
        return this.id;
    }

    public RobotType getType() {
        return this.type;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
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

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void delay(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException ex) {

        }
    }


}
