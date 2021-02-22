package swarm.robot.exception;

public class ProximityException extends Exception {
    public ProximityException(String msg) {
        // TODO: what is the best test ?
        System.out.println("Proximity reading error: " + msg);
    }
}
