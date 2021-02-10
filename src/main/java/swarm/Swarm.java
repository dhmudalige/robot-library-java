package swarm;

import swarm.robot.Robot;
import swarm.robot.VirtualRobot;
import swarm.robotImplementations.ColorRippleRobot;
import swarm.robotImplementations.MoveRobot;

public class Swarm extends Thread {
    public static void main(String[] args) {

        int[] robotList = {4};

        // Linear Robot Formation
        lineFormation(robotList, -75, 75, 90, 35, 0);

        // Circular Robot formation
        //circularFormation(robotList, 0, 0, 90, 60, 0, 45);

        // Spiral Robot Formation
        //spiralFormation(robotList, 0, 0, 90, 40, 12, 90, 30);

    }

    private static void lineFormation(int[] robotList, int startX, int startY, int heading, int incX, int incY) {
        Robot[] vr = new VirtualRobot[robotList.length];

        for (int i = 0; i < robotList.length; i++) {
            vr[i] = new MoveRobot(robotList[i], startX + incX * i, startY + incY * i, heading);
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

            vr[i] = new ColorRippleRobot(robotList[i], x, y, robotHeading);
            new Thread(vr[i]).start();
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

            vr[i] = new VirtualRobot(robotList[i], x, y, robotHeading);
            new Thread(vr[i]).start();
        }

    }
}
