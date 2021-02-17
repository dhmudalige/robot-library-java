package swarm.Interfaces;

public interface IRobotState
{
    enum robotState {WAIT, RUN, BEGIN}

    public void loop() throws Exception;
    // public void execute();

    public void interrupt();

    // TODO: Implement more generalized approach than this
    public void sensorInterrupt(String sensor, String value);

    public void communicationInterrupt(String msg);

    public void start();
    public void stop();
    public void reset();
}
