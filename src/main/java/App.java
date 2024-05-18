import Robots.algorithms.VoronoiCoverage;
import Robots.models.RobotPosition;
import swarm.configs.MQTTSettings;
import swarm.robot.Robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.Properties;

import Robots.samples.SampleRobot;
import swarm.robot.VirtualRobot;

import java.util.Random;

import static Other.checks.RobotPositionChecks.checkLimit;

public class App {
    static File configFile;
    static FileReader reader;

    static Properties props;

    public static final int ROBOTS_COUNT;
    public static final double COVERAGE_RADIUS;

    static {
        try {
//            configFile = new File("src/resources/algo/voronoi.properties");
            configFile = new File("algo/voronoi.properties");
            reader = new FileReader(configFile);
            props = new Properties();
            props.load(reader);

            ROBOTS_COUNT = Integer.parseInt(props.getProperty("count"));
            COVERAGE_RADIUS = Double.parseDouble(props.getProperty("radius"));
            reader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        long setupStartTime = System.currentTimeMillis();

        try {
//            configFile = new File("src/resources/config/mqtt.properties");
            configFile = new File("config/mqtt.properties");
            reader = new FileReader(configFile);
            props = new Properties();
            props.load(reader);

            MQTTSettings.server = props.getProperty("server");
            MQTTSettings.port = Integer.parseInt(props.getProperty("port", "1883"));
            MQTTSettings.userName = props.getProperty("username");
            MQTTSettings.password = props.getProperty("password");
            MQTTSettings.channel = props.getProperty("channel", "v1");
            reader.close();

        } catch (FileNotFoundException ex) {
            // file does not exist
            System.out.println("<VoronoiCoverageTest> File Not Found !!!");

        } catch (IOException ex) {
            // I/O error
            System.out.println("IO Error !!!");
        }

        long setupEndTime = System.currentTimeMillis();
        System.out.println("##################################################");
        System.out.println("Project Setup Time: " + (setupEndTime - setupStartTime) + " milliseconds");
        System.out.println("##################################################");

        // Start a robot swarm
        VoronoiCoverage vc = new VoronoiCoverage();


        long voronoiStartTime = System.currentTimeMillis();

        List<RobotPosition> rPositions = vc.placeRobots(ROBOTS_COUNT, COVERAGE_RADIUS);

        long voronoiEndTime = System.currentTimeMillis();

        System.out.println("##################################################");
        System.out.println("Voronoi Coverage Process Time: " + (voronoiEndTime - voronoiStartTime) + " milliseconds");
        System.out.println("##################################################");

        int startX = 0, startY = 0, startHeading = 90;

        Robot[] vr = new VirtualRobot[ROBOTS_COUNT];
        Random delta = new Random();
//            Random random = new Random();

        long swarmCreationStartTime = System.currentTimeMillis();
        for (int i = 0; i < ROBOTS_COUNT; i++) {
            RobotPosition p = rPositions.get(i);

            double newX = checkLimit(startX + p.x);
            double newY = checkLimit(startY - p.y);

            System.out.println("Robot "+ p.robotId + ": x=" + newX + ", y=" + newY);

            vr[i] = new SampleRobot(p.robotId, newX, newY,startHeading + delta.nextInt(4) * 90);
//                vr[i].delay(1000);

            new Thread(vr[i]).start();
        }
        long swarmCreationEndTime = System.currentTimeMillis();

            /*for (int i = 0; i < ROBOTS_COUNT; i++) {
                int delta = random.nextInt() % 18;
                int sign = (random.nextInt() % 2 == 0) ? 1 : -1;

                vr[i] = new SampleRobot(i, startX + Math.pow(sign * delta, 3) % 81, startY - sign * delta * 2,
                        startHeading + delta);
                new Thread(vr[i]).start();
            }*/

        System.out.println("##################################################");
        System.out.println("Swarm Creation Time: " + (swarmCreationEndTime - swarmCreationStartTime) + " milliseconds");
        System.out.println("##################################################");


        System.out.println("##################################################");
        System.out.println("Total Process Execution Time: " + (swarmCreationEndTime - setupStartTime) + " milliseconds");
        System.out.println("##################################################");

    }

}
