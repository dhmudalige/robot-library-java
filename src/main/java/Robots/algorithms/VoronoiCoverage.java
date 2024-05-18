package Robots.algorithms;

import Robots.models.RobotPosition;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VoronoiCoverage {
    /*
     * p - Number of robots
     * r - Radius of robots
     */

    List<RobotPosition> rPositionList;
    Random random = new Random();
    public static final int OFFSET = 0;

    public VoronoiCoverage() {
        this.rPositionList = new ArrayList<>();
    }

    public List<RobotPosition> placeRobots(int p, double r) {
        // Generate initial random points
        for (int i = 0; i < p; i++) {
            /*
            double x = random.nextDouble();
            double y = random.nextDouble();
            rPositionList.add(new RobotPosition(i, x, y));
            System.out.println("R" + i + "=(" + x + "," + y + ")");
            */

            double delta = random.nextInt() % 10;
            int sign = (random.nextInt() % 2 == 0) ? 1 : -1;
            rPositionList.add(new RobotPosition(i, OFFSET + Math.pow(sign * delta, 3) % 36, OFFSET + sign * delta * 2));
        }

        // Iteratively refine points
        boolean changed;
        int count = 0;
        do {
            changed = false;
            for (RobotPosition robot : rPositionList) {
                double newX = robot.x;
                double newY = robot.y;

                for (RobotPosition otherRobot : rPositionList) {
                    if (robot != otherRobot) {
                        double X = robot.x;
                        double otherX = otherRobot.x;

                        double Y = robot.y;
                        double otherY = otherRobot.y;

                        double distance = Math.sqrt(Math.pow(X - otherX, 2) + Math.pow(Y - otherY, 2));
                        if (distance < 2 * r) {
                            // Move the robot away from the other robot
                            double moveX = (X - otherX) * (2 * r - distance) / distance;
                            double moveY = (Y - otherY) * (2 * r - distance) / distance;
                            newX += moveX;
                            newY += moveY;

//                            System.out.println("R" + robot.robotId + " CHANGED --> (" + newX + "," + newY + ")");
                            changed = true;
                            count++;
                        }
                    }
                }
                // Update robot position
                robot.x = Math.round(newX);
                robot.y = Math.round(newY);
            }
        } while (changed && (count > 1000));

        return rPositionList;
    }
}