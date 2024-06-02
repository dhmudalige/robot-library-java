package Robots;

import swarm.robot.VirtualRobot;

import java.util.ArrayList;
import java.util.Random;

public class RandomMappingRobot extends VirtualRobot {

    // Size of a grid cell
    private final double GRID_SPACE = 18.000;

    // The default movement speed
    private final int defaultMoveSpeed = 200;

    // The default rotate speed
    private final int defaultRotateSpeed = 200;

    // Proximity Sensor options
    // Angles for left,front and right side rotating
    private int[] proximityAngles = { -90, 0, 90 };

    // Index to get left proximity angle
    public static int PROXIMITY_LEFT = 0;

    // Index to get front proximity angle
    public static int PROXIMITY_FRONT = 1;

    // Index to get right proximity angle
    public static int PROXIMITY_RIGHT = 2;

    // Robot's initial position
    double robotRow = 0;
    double robotCol = 0;
    int robotId = 0;

    public RandomMappingRobot(int id, double x, double y, double heading) {
        super(id, x, y, heading);
        robotRow=(x+81)/18;
        robotCol=(y+81)/18;
        robotId=id;
    }

    int numRows=10;
    int numCols=10;
    int[][] occupancyGrid = new int[numRows][numCols];

    int rightTurns=0;
    int leftTurns=0;

    public static void printArray(int[][] array) {
        for (int[] row : array) {
            for (int element : row) {
                System.out.print(element + " ");
            }
            System.out.println();
        }
    }

    public static String arrayToString(int[][] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                sb.append(arr[i][j]);
                if (j < arr[i].length - 1) {
                    sb.append(" "); // Add space between elements in the same row
                }
            }
            if (i < arr.length - 1) {
                sb.append("\n"); // Add newline between rows
            }
        } 
        return sb.toString();
    }

    public static int[][] stringToArray(String arrayAsString) {
        String[] rows = arrayAsString.split("\n");
        int numRows = rows.length;
        int[][] array = new int[numRows][];
        for (int i = 0; i < numRows; i++) {
            String[] elements = rows[i].split(" ");
            int numCols = elements.length;
            array[i] = new int[numCols];
            for (int j = 0; j < numCols; j++) {
                array[i][j] = Integer.parseInt(elements[j]);
            }
        }
        return array;
    }

    public static int[][] getMergedMap(int[][] arr1, int[][] arr2) {
        int rows = arr1.length;
        int cols = arr1[0].length;

        int[][] mergedMap = new int[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                mergedMap[i][j] = Math.max(arr1[i][j], arr2[i][j]);
            }
        }
        return mergedMap;
    }

    public void setup() {
        System.out.println("My Test Robot Started");

        super.setup();

        // Setup proximity sensor with 3 angles
        proximitySensor.setAngles(proximityAngles);

        // Start immediately after the setup
        state = robotState.RUN;


        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                occupancyGrid[row][col] = 0; // set to unoccupied
            }
        }

        // Mark the starting cell as visited
        occupancyGrid[numRows-1-(int)robotRow][(int)robotCol] = 3;

        // Print the array
        // System.out.println("Robot ID: " + robotId);
        // printArray(occupancyGrid);
        // System.out.println();
    }

    public void loop() throws Exception {
        super.loop();

        if (state == robotState.RUN) {
            // Get present distances from robot's left,front and right
            int[] d = proximitySensor.getProximity().distances();

            // Determine the movement based on the sum of right and left turns modulo 4
            int direction = (rightTurns - leftTurns) % 4;
            
            // Adjust direction to ensure it's within the range of 0 to 3
            if (direction < 0) {
                direction += 4;
            }

            ArrayList<Integer> intList = new ArrayList<>();

            // Robot rotating way :- if distance from (any side +6) > GRID_SPACE then robot
            // will rotate that side.
            if (d[PROXIMITY_RIGHT] + 6 > GRID_SPACE) {
                intList.add(1);
                // System.out.println(d[PROXIMITY_RIGHT]);
                
                // Mark free spaces
                switch (direction) {
                    case 0: // Facing north
                        int count=0;
                        for (int i = 0; i < (d[PROXIMITY_RIGHT] + 6) / GRID_SPACE; i++) {
                            // System.out.println(i);
                            occupancyGrid[numRows-1-(int)robotRow][(int)robotCol+(i)] = 1;
                            count++;
                        }
                        break;
                    case 1: // Facing east
                        count=0;
                        for (int i = 0; i < (d[PROXIMITY_RIGHT] + 6) / GRID_SPACE; i++) {
                            occupancyGrid[numRows-1-((int)robotRow-(i))][(int)robotCol] = 1;
                            count++;
                        }
                        break;
                    case 2: // Facing south
                        count=0;
                        for (int i = 0; i < (d[PROXIMITY_RIGHT] + 6) / GRID_SPACE; i++) {
                            occupancyGrid[numRows-1-(int)robotRow][(int)robotCol-(i)] = 1;
                            count++;
                        }
                        break;
                    case 3: // Facing west
                        count=0;
                        for (int i = 0; i < (d[PROXIMITY_RIGHT] + 6) / GRID_SPACE; i++) {
                            occupancyGrid[numRows-1-((int)robotRow+(i))][(int)robotCol] = 1;
                            count++;
                        }
                        break;
                } 
            } 
            else {
                // Mark obstacles
                switch (direction) {
                    case 0: // Facing north
                        if ((int)robotCol != numCols-1 && occupancyGrid[numRows-1-(int)robotRow][(int)robotCol+1] == 0){
                            occupancyGrid[numRows-1-(int)robotRow][(int)robotCol+1] = 2;
                        }
                        break;
                    case 1: // Facing east
                        if ((int)robotRow != 0 && occupancyGrid[numRows-1-((int)robotRow-1)][(int)robotCol] == 0){
                            occupancyGrid[numRows-1-((int)robotRow-1)][(int)robotCol] = 2;
                        }
                        break;
                    case 2: // Facing south
                        if ((int)robotCol != 0 && occupancyGrid[numRows-1-(int)robotRow][(int)robotCol-1] == 0){
                            occupancyGrid[numRows-1-(int)robotRow][(int)robotCol-1] = 2;
                        }
                        break;
                    case 3: // Facing west
                        if ((int)robotRow != numRows-1 && occupancyGrid[numRows-1-((int)robotRow+1)][(int)robotCol] == 0){
                            occupancyGrid[numRows-1-((int)robotRow+1)][(int)robotCol] = 2;
                        }
                        break;
                } 
            }
            
            if (d[PROXIMITY_FRONT] + 6 > GRID_SPACE) {
                intList.add(2);

                // Mark free spaces
                switch (direction) {
                    case 0: // Facing north
                        int count=0;
                        for (int i = 0; i < (d[PROXIMITY_FRONT] + 6) / GRID_SPACE; i++) {
                            occupancyGrid[numRows-1-((int)robotRow+i)][(int)robotCol] = 1;
                            count++;
                        }
                        break;
                    case 1: // Facing east
                        count=0;
                        for (int i = 0; i < (d[PROXIMITY_FRONT] + 6) / GRID_SPACE; i++) {
                            occupancyGrid[numRows-1-(int)robotRow][(int)robotCol+i] = 1;
                            count++;
                        }                        
                        break;
                    case 2: // Facing south
                        count=0;
                        for (int i = 0; i < (d[PROXIMITY_FRONT] + 6) / GRID_SPACE; i++) {
                            occupancyGrid[numRows-1-((int)robotRow-i)][(int)robotCol] = 1;
                            count++;
                        }       
                        break;
                    case 3: // Facing west
                        count=0;
                        for (int i = 0; i < (d[PROXIMITY_FRONT] + 6) / GRID_SPACE; i++) {
                            occupancyGrid[numRows-1-(int)robotRow][(int)robotCol-i] = 1;
                            count++;
                        }       
                        break;
                } 
            } 
            else {
                // Mark obstacles
                switch (direction) {
                    case 0: // Facing north
                        if ((int)robotRow != numRows-1 && occupancyGrid[numRows-1-((int)robotRow+1)][(int)robotCol] == 0){
                            occupancyGrid[numRows-1-((int)robotRow+1)][(int)robotCol] = 2;
                        }
                        break;
                    case 1: // Facing east
                        if ((int)robotCol != numCols-1 && occupancyGrid[numRows-1-(int)robotRow][(int)robotCol+1] == 0){
                            occupancyGrid[numRows-1-(int)robotRow][(int)robotCol+1] = 2;
                        }
                        break;
                    case 2: // Facing south
                        if ((int)robotRow != 0 && occupancyGrid[numRows-1-((int)robotRow-1)][(int)robotCol] == 0){
                            occupancyGrid[numRows-1-((int)robotRow-1)][(int)robotCol] = 2;
                        }
                        break;
                    case 3: // Facing west
                        if ((int)robotCol != 0 && occupancyGrid[numRows-1-(int)robotRow][(int)robotCol-1] == 0){
                            occupancyGrid[numRows-1-(int)robotRow][(int)robotCol-1] = 2;
                        }
                        break;
                } 
            }
            
            if (d[PROXIMITY_LEFT] + 6 > GRID_SPACE) {
                intList.add(3);

                // Mark free spaces
                switch (direction) {
                    case 0: // Facing north
                        int count=0;
                        for (int i = 0; i < (d[PROXIMITY_LEFT] + 6) / GRID_SPACE; i++) {
                            occupancyGrid[numRows-1-(int)robotRow][(int)robotCol-i] = 1;
                            count++;
                        }       
                        break;
                    case 1: // Facing east
                        count=0;
                        for (int i = 0; i < (d[PROXIMITY_LEFT] + 6) / GRID_SPACE; i++) {
                            occupancyGrid[numRows-1-((int)robotRow+i)][(int)robotCol] = 1;
                            count++;
                        }       
                        break;
                    case 2: // Facing south
                        count=0;
                        for (int i = 0; i < (d[PROXIMITY_LEFT] + 6) / GRID_SPACE; i++) {
                            occupancyGrid[numRows-1-(int)robotRow][(int)robotCol+i] = 1;
                            count++;
                        }       
                        break;
                    case 3: // Facing west
                        count=0;
                        for (int i = 0; i < (d[PROXIMITY_LEFT] + 6) / GRID_SPACE; i++) {
                            occupancyGrid[numRows-1-((int)robotRow-i)][(int)robotCol] = 1;
                            count++;
                        }       
                        break;
                } 
            } 
            else {
                // Mark obstacles
                switch (direction) {
                    case 0: // Facing north
                        if ((int)robotCol != 0 && occupancyGrid[numRows-1-(int)robotRow][(int)robotCol-1] == 0){
                            occupancyGrid[numRows-1-(int)robotRow][(int)robotCol-1] = 2;
                        }
                        break;
                    case 1: // Facing east
                        if ((int)robotRow != numRows-1 && occupancyGrid[numRows-1-((int)robotRow+1)][(int)robotCol] == 0){
                            occupancyGrid[numRows-1-((int)robotRow+1)][(int)robotCol] = 2;
                        }
                        break;
                    case 2: // Facing south
                        if ((int)robotCol != numCols-1 && occupancyGrid[numRows-1-(int)robotRow][(int)robotCol+1] == 0){
                            occupancyGrid[numRows-1-(int)robotRow][(int)robotCol+1] = 2;
                        }
                        break;
                    case 3: // Facing west
                        if ((int)robotRow != 0 && occupancyGrid[numRows-1-((int)robotRow-1)][(int)robotCol] == 0){
                            occupancyGrid[numRows-1-((int)robotRow-1)][(int)robotCol] = 2;
                        }
                        break;
                } 
            }

            Random rand = new Random();
            int randomIndex = rand.nextInt(intList.size()); // Generate a random index
            int randomElement = intList.get(randomIndex); // Get the element at the random index    

            if (randomElement==1) {
                // Right
                motion.rotateDegree(defaultRotateSpeed, 90);
                rightTurns++;

            } else if (randomElement==2) {
                // Front

            } else if (randomElement==3) {
                // Turn Left
                motion.rotateDegree(defaultRotateSpeed, -90);
                leftTurns++;
            } else {
                // If robot can't go left,right and front then robot will rotate to back.
                motion.rotateDegree(defaultRotateSpeed, 180);
            }

            // Robot move
            motion.moveDistance(defaultMoveSpeed, GRID_SPACE);
            delay(1000);

            // Determine the movement based on the sum of right and left turns modulo 4
            direction = (rightTurns - leftTurns) % 4;

            // Adjust direction to ensure it's within the range of 0 to 3
            if (direction < 0) {
                direction += 4;
            }

            // Move based on the calculated direction
            switch (direction) {
                case 0: // Facing north
                    robotRow++;
                    break;
                case 1: // Facing east
                    robotCol++;
                    break;
                case 2: // Facing south
                    robotRow--;
                    break;
                case 3: // Facing west
                    robotCol--;
                    break;
            }     

            // Change entries with value 3 to 1
            for (int i = 0; i < occupancyGrid.length; i++) {
                for (int j = 0; j < occupancyGrid[i].length; j++) {
                    if (occupancyGrid[i][j] == 3) {
                        occupancyGrid[i][j] = 1;
                    }
                }
            }

            occupancyGrid[numRows-1-(int)robotRow][(int)robotCol] = 3;
            
            // Print the array
            // System.out.println("Robot ID: " + robotId);
            // printArray(occupancyGrid);
            // System.out.println();

            simpleComm.sendMessage(arrayToString(occupancyGrid), 200);
        }
    }

    public void communicationInterrupt(String msg) {
        // System.out.println("Robot ID: " + robotId + " communicationInterrupt on " + id + " with msg:\n" + msg);
        // System.out.println();

        int[][] array = stringToArray(msg);
        // printArray(array);

        occupancyGrid = getMergedMap(occupancyGrid, array);
        printArray(occupancyGrid);
        System.out.println();
    }
}
