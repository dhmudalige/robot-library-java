package swarm.robot.Exception;

public class MotionControllerException extends Exception {
    public MotionControllerException(String s) {
        System.out.println("Error: " + s);
        //super(s);
    }
}
