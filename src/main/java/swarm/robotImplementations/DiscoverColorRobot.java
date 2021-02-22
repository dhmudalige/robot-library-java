package swarm.robotImplementations;

import org.json.simple.JSONObject;
import swarm.robot.VirtualRobot;
import swarm.robot.communication.SimpleCommunication;

public class DiscoverColorRobot extends VirtualRobot {
    int R,G,B;

    public DiscoverColorRobot(int id, double x, double y, double heading) {
        super(id, x, y, heading);
    }

    public void DiscoverColor(int R, int G, int B) {
        this.R=R;
        this.G=G;
        this.B=B;
        /*
        broadcast the color of the obstacle we are looking for by simple communication. discover_color

        while(1){
            move robot
            if a robot detect an obstacle{
                    stop
                    if (obstacle.color== discover_color){
                        broadcast the message as an interrupt
                        change color of all
                        break();
                    }else
                        move
            }
         }
        */


    }

    @Override
    public void loop() throws Exception {
        super.loop();

        // Anything specially check in continuously
    }

    @Override
    public void interrupt() {
        JSONObject obj = new JSONObject();
        obj.put("R", R);
        obj.put("G", G);
        obj.put("B", B);

        String message = obj.toJSONString();
        //broadcast the message that this color is discovered

        neoPixel.changeColor(R,G,B);
    }

    public void start() {
        super.start();

        // Things to do when start action
        neoPixel.changeColor(0, 0, 0);
    }



}
