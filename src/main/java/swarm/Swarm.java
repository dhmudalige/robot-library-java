package swarm;

import swarm.robot.Robot;
import swarm.robot.VirtualRobot;
import swarm.robot.communication.Communication;
import swarm.robot.exception.RGBColorException;
import swarm.robot.types.RGBColorType;
import swarm.robotImplementations.ColorRippleRobot;
import swarm.robotImplementations.DiscoverColorRobot;
import swarm.robotImplementations.ObstacleAvoidRobot;

public class Swarm extends Thread {

    public static void main(String[] args) throws RGBColorException {

        int[] robotList = {0, 1, 2, 3, 4, 5};
        // int[] robotList = {10};

        // Linear Robot Formation
        //lineFormation(robotList, -70, 0, 90, 30, 0);

        // Circular Robot formation
//        circularFormation(robotList, 0, 0, 0, 60, 0, 36);

        // Spiral Robot Formation
        // spiralFormation(robotList, 0, 0, 10, 10, 15, 90, 20);

        //Discover colored obstacles
        //RGBColorType obstacleColor = new RGBColorType(255,0,0);
        //discoverColor(robotList,0,0,0,60,15,obstacleColor);

        discoverColor();
    }

    private static void discoverColor() throws RGBColorException {

        Robot[] vr = new VirtualRobot[10];
        RGBColorType obstacleColor = new RGBColorType(255, 0, 0);

        try {
            obstacleColor = new RGBColorType(255, 0, 0);
        } catch (RGBColorException e) {
            e.printStackTrace();
        }

        vr[0] = new DiscoverColorRobot(10, -52, 32, 45, obstacleColor);
        vr[1] = new DiscoverColorRobot(11, -32, -12, -20, obstacleColor);
        vr[2] = new DiscoverColorRobot(12, 49, -23, 3, obstacleColor);
        vr[3] = new DiscoverColorRobot(13, 54, 65, -70, obstacleColor);
        vr[4] = new DiscoverColorRobot(14, 0, -40, 90, obstacleColor);

        vr[5] = new DiscoverColorRobot(0, 80, 20, 90, obstacleColor);
        vr[6] = new DiscoverColorRobot(1, 40, -80, -90, obstacleColor);
        vr[7] = new DiscoverColorRobot(2, -40, -70, 45, obstacleColor);
        vr[8] = new DiscoverColorRobot(6, -50, -50, -90, obstacleColor);
        vr[9] = new DiscoverColorRobot(7, -70, 0, -45, obstacleColor);

        //robots start to move
        for (Robot robot : vr) {
//            vr[i] = new DiscoverColorRobot(robotList[i], startX + incX * i, startY + incY * i, heading);
//            vr[i].discover(obstacleColor);
            new Thread(robot).start();
        }
    }

    private static void lineFormation(int[] robotList, int startX, int startY, int heading, int incX, int incY) {
        Robot[] vr = new VirtualRobot[robotList.length];

        for (int i = 0; i < robotList.length; i++) {
            vr[i] = new ObstacleAvoidRobot(robotList[i], startX + incX * i, startY + incY * i, heading);
            new Thread(vr[i]).start();
        }
    }

    private static void circularFormation(int[] robotList, int centerX, int centerY, int headingOffset, int radius, int startAngle, int deltaAngle) {
        Robot[] vr = new VirtualRobot[robotList.length];

        int x, y, robotHeading;

        for (int i = 0; i < robotList.length; i++) {
            double a = (startAngle + i * deltaAngle);
            x = (int) (radius * Math.cos(a * Math.PI / 180));
            y = (int) (radius * Math.sin(a * Math.PI / 180));
            robotHeading = (int) (a + headingOffset);

            if (i == 0 || i == 1 || i == 2 || i == 6 | i == 7) {
                // phy
            } else {
                vr[i] = new ColorRippleRobot(robotList[i], x, y, robotHeading);
                new Thread(vr[i]).start();
            }

        }
    }

    private static void spiralFormation(int[] robotList, int centerX, int centerY, int headingOffset, int startRadius, int deltaRadius, int startAngle, int deltaAngle) {
        Robot[] vr = new VirtualRobot[robotList.length];

        int x, y, robotHeading;

        for (int i = 0; i < robotList.length; i++) {
            double a = (startAngle + i * deltaAngle);
            double r = startRadius + (deltaRadius * i);
            x = (int) (r * Math.cos(a * Math.PI / 180));
            y = (int) (r * Math.sin(a * Math.PI / 180));
            robotHeading = (int) (a + headingOffset);

            vr[i] = new ColorRippleRobot(robotList[i], x, y, robotHeading);
            new Thread(vr[i]).start();
        }
    }
}