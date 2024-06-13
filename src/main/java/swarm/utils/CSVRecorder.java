package swarm.utils;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import static swarm.utils.SwarmUtils.getTime;

public class CSVRecorder {
    public static void addEmptyRowToCSV(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write("\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public static void addCSVHeader(String filePath){
//
//    }

    public static void addRuntimeInfo(String filePath, String robotType, int robotCount, String arenaType) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write("\n");
            writer.write("TIME:" + getTime() + ",ROBOT-TYPE:" + robotType +  ",ROBOT-COUNT:" + robotCount + ",ARENA-TYPE:<" + arenaType + ">\n");
            writer.write("timestamp,cycle,total-time,matched" + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public static void addCSVHeader(String filePath) {
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
//            String[] columnNames = {"timestamp", "explored cells", "unexplored cells"};
//            writer.write(String.join(",", columnNames) + "\n");
//            writer.write("\n");
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

    public static void recordExplorations(String filePath, String robotType, long timestamp, int count, long timeTaken){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
            writer.write(timestamp + "," + count + "," + timeTaken + "\n");
//            writer.write("\n");

            System.out.println(robotType + " cycle:" + count + ", total-time:" + timeTaken + " ms");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

//    public static void recordExplorations(String filePath, long timestamp, int explored, int unexplored){
//        String[] columnNames = {"timestamp", "explored cells", "unexplored cells"};
//
//        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))) {
//            writer.write(String.join(",", columnNames) + "\n");
//            writer.write(timestamp + "," + explored + "," + unexplored + "\n");
//            writer.write("\n");
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
