import swarm.configs.MQTTSettings;
import swarm.robot.Robot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import Robots.*;

public class App extends Thread {

    public static void main(String[] args) {
        long setupStartTime = System.currentTimeMillis();
        try {
            // COMPLETE THIS BEFORE RUN
            // Read config properties from the file, src/resources/config/mqtt.properties
            // If it isn't there, please make one, as given sample in the
            // 'sample_mqtt.properties' file

//            File configFile = new File("src/resources/config/mqtt.properties");
            File configFile = new File("config/mqtt.properties");
            FileReader reader = new FileReader(configFile);
            Properties props = new Properties();
            props.load(reader);

            MQTTSettings.server = props.getProperty("server");
            MQTTSettings.port = Integer.parseInt(props.getProperty("port", "1883"));
            MQTTSettings.userName = props.getProperty("username");
            MQTTSettings.password = props.getProperty("password");
            MQTTSettings.channel = props.getProperty("channel", "v1");
            reader.close();

            Robot robot1 = new HeuristicRobot(9, 63, 63, 90);
            new Thread(robot1).start();

            Robot robot2 = new HeuristicRobot(10, -81, -81, 90);
            new Thread(robot2).start();

            Robot robot3 = new HeuristicRobot(10, -81, -81, 90);
            new Thread(robot3).start();
//
//            Robot robot4 = new HeuristicRobot(10, -81, -81, 90);
//            new Thread(robot4).start();

            long setupEndTime = System.currentTimeMillis();

            // Register the shutdown hook
            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                System.out.println("Process was terminated by the user");
                System.out.println("Total Execution Time: " + (setupEndTime - setupStartTime));

            }));

        } catch (FileNotFoundException ex) {
            // file does not exist
            System.out.println("Config file, `resources/config/mqtt.properties` Not Found !!!");

        } catch (IOException ex) {
            // I/O error
            System.out.println("IO Error !!!");
        }
    }
}
