package swarm.robot.exception;

public class MotionControllerException extends Exception {
    public MotionControllerException(String s) {
        System.out.println("Motion Error: " + s);
        //super(s);
    }
}
