package Robots;

import swarm.robot.MappingRobot;
import swarm.utils.MappingUtils;

import java.util.ArrayList;
import java.util.Random;

public class RandomMappingRobot extends MappingRobot {
    public final double GRID_SPACE = 18.000;

    // Robot's initial position
    double robotRow = 0;
    double robotCol = 0;
    int robotId = 0;

    public RandomMappingRobot(int id, double x, double y, double heading) {
        super(id, x, y, heading);
        robotRow = (x + 81) / 18;
        robotCol = (y + 81) / 18;
        robotId = id;
    }

    int numRows = 10;
    int numCols = 10;
    int[][] occupancyGrid = new int[numRows][numCols];

    int rightTurns = 0;
    int leftTurns = 0;

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
        occupancyGrid[numRows - 1 - (int) robotRow][(int) robotCol] = 3;

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
                        int count = 0;
                        for (int i = 0; i < (d[PROXIMITY_RIGHT] + 6) / GRID_SPACE; i++) {
                            // System.out.println(i);
                            occupancyGrid[numRows - 1 - (int) robotRow][(int) robotCol + (i)] = 1;
                            count++;
                        }
                        break;
                    case 1: // Facing east
                        count = 0;
                        for (int i = 0; i < (d[PROXIMITY_RIGHT] + 6) / GRID_SPACE; i++) {
                            occupancyGrid[numRows - 1 - ((int) robotRow - (i))][(int) robotCol] = 1;
                            count++;
                        }
                        break;
                    case 2: // Facing south
                        count = 0;
                        for (int i = 0; i < (d[PROXIMITY_RIGHT] + 6) / GRID_SPACE; i++) {
                            occupancyGrid[numRows - 1 - (int) robotRow][(int) robotCol - (i)] = 1;
                            count++;
                        }
                        break;
                    case 3: // Facing west
                        count = 0;
                        for (int i = 0; i < (d[PROXIMITY_RIGHT] + 6) / GRID_SPACE; i++) {
                            occupancyGrid[numRows - 1 - ((int) robotRow + (i))][(int) robotCol] = 1;
                            count++;
                        }
                        break;
                }
            } else {
                // Mark obstacles
                switch (direction) {
                    case 0: // Facing north
                        if ((int) robotCol != numCols - 1 && occupancyGrid[numRows - 1 - (int) robotRow][(int) robotCol + 1] == 0) {
                            occupancyGrid[numRows - 1 - (int) robotRow][(int) robotCol + 1] = 2;
                        }
                        break;
                    case 1: // Facing east
                        if ((int) robotRow != 0 && occupancyGrid[numRows - 1 - ((int) robotRow - 1)][(int) robotCol] == 0) {
                            occupancyGrid[numRows - 1 - ((int) robotRow - 1)][(int) robotCol] = 2;
                        }
                        break;
                    case 2: // Facing south
                        if ((int) robotCol != 0 && occupancyGrid[numRows - 1 - (int) robotRow][(int) robotCol - 1] == 0) {
                            occupancyGrid[numRows - 1 - (int) robotRow][(int) robotCol - 1] = 2;
                        }
                        break;
                    case 3: // Facing west
                        if ((int) robotRow != numRows - 1 && occupancyGrid[numRows - 1 - ((int) robotRow + 1)][(int) robotCol] == 0) {
                            occupancyGrid[numRows - 1 - ((int) robotRow + 1)][(int) robotCol] = 2;
                        }
                        break;
                }
            }

            if (d[PROXIMITY_FRONT] + 6 > GRID_SPACE) {
                intList.add(2);

                // Mark free spaces
                switch (direction) {
                    case 0: // Facing north
                        int count = 0;
                        for (int i = 0; i < (d[PROXIMITY_FRONT] + 6) / GRID_SPACE; i++) {
                            occupancyGrid[numRows - 1 - ((int) robotRow + i)][(int) robotCol] = 1;
                            count++;
                        }
                        break;
                    case 1: // Facing east
                        count = 0;
                        for (int i = 0; i < (d[PROXIMITY_FRONT] + 6) / GRID_SPACE; i++) {
                            occupancyGrid[numRows - 1 - (int) robotRow][(int) robotCol + i] = 1;
                            count++;
                        }
                        break;
                    case 2: // Facing south
                        count = 0;
                        for (int i = 0; i < (d[PROXIMITY_FRONT] + 6) / GRID_SPACE; i++) {
                            occupancyGrid[numRows - 1 - ((int) robotRow - i)][(int) robotCol] = 1;
                            count++;
                        }
                        break;
                    case 3: // Facing west
                        count = 0;
                        for (int i = 0; i < (d[PROXIMITY_FRONT] + 6) / GRID_SPACE; i++) {
                            occupancyGrid[numRows - 1 - (int) robotRow][(int) robotCol - i] = 1;
                            count++;
                        }
                        break;
                }
            } else {
                // Mark obstacles
                switch (direction) {
                    case 0: // Facing north
                        if ((int) robotRow != numRows - 1 && occupancyGrid[numRows - 1 - ((int) robotRow + 1)][(int) robotCol] == 0) {
                            occupancyGrid[numRows - 1 - ((int) robotRow + 1)][(int) robotCol] = 2;
                        }
                        break;
                    case 1: // Facing east
                        if ((int) robotCol != numCols - 1 && occupancyGrid[numRows - 1 - (int) robotRow][(int) robotCol + 1] == 0) {
                            occupancyGrid[numRows - 1 - (int) robotRow][(int) robotCol + 1] = 2;
                        }
                        break;
                    case 2: // Facing south
                        if ((int) robotRow != 0 && occupancyGrid[numRows - 1 - ((int) robotRow - 1)][(int) robotCol] == 0) {
                            occupancyGrid[numRows - 1 - ((int) robotRow - 1)][(int) robotCol] = 2;
                        }
                        break;
                    case 3: // Facing west
                        if ((int) robotCol != 0 && occupancyGrid[numRows - 1 - (int) robotRow][(int) robotCol - 1] == 0) {
                            occupancyGrid[numRows - 1 - (int) robotRow][(int) robotCol - 1] = 2;
                        }
                        break;
                }
            }

            if (d[PROXIMITY_LEFT] + 6 > GRID_SPACE) {
                intList.add(3);

                // Mark free spaces
                switch (direction) {
                    case 0: // Facing north
                        int count = 0;
                        for (int i = 0; i < (d[PROXIMITY_LEFT] + 6) / GRID_SPACE; i++) {
                            occupancyGrid[numRows - 1 - (int) robotRow][(int) robotCol - i] = 1;
                            count++;
                        }
                        break;
                    case 1: // Facing east
                        count = 0;
                        for (int i = 0; i < (d[PROXIMITY_LEFT] + 6) / GRID_SPACE; i++) {
                            occupancyGrid[numRows - 1 - ((int) robotRow + i)][(int) robotCol] = 1;
                            count++;
                        }
                        break;
                    case 2: // Facing south
                        count = 0;
                        for (int i = 0; i < (d[PROXIMITY_LEFT] + 6) / GRID_SPACE; i++) {
                            occupancyGrid[numRows - 1 - (int) robotRow][(int) robotCol + i] = 1;
                            count++;
                        }
                        break;
                    case 3: // Facing west
                        count = 0;
                        for (int i = 0; i < (d[PROXIMITY_LEFT] + 6) / GRID_SPACE; i++) {
                            occupancyGrid[numRows - 1 - ((int) robotRow - i)][(int) robotCol] = 1;
                            count++;
                        }
                        break;
                }
            } else {
                // Mark obstacles
                switch (direction) {
                    case 0: // Facing north
                        if ((int) robotCol != 0 && occupancyGrid[numRows - 1 - (int) robotRow][(int) robotCol - 1] == 0) {
                            occupancyGrid[numRows - 1 - (int) robotRow][(int) robotCol - 1] = 2;
                        }
                        break;
                    case 1: // Facing east
                        if ((int) robotRow != numRows - 1 && occupancyGrid[numRows - 1 - ((int) robotRow + 1)][(int) robotCol] == 0) {
                            occupancyGrid[numRows - 1 - ((int) robotRow + 1)][(int) robotCol] = 2;
                        }
                        break;
                    case 2: // Facing south
                        if ((int) robotCol != numCols - 1 && occupancyGrid[numRows - 1 - (int) robotRow][(int) robotCol + 1] == 0) {
                            occupancyGrid[numRows - 1 - (int) robotRow][(int) robotCol + 1] = 2;
                        }
                        break;
                    case 3: // Facing west
                        if ((int) robotRow != 0 && occupancyGrid[numRows - 1 - ((int) robotRow - 1)][(int) robotCol] == 0) {
                            occupancyGrid[numRows - 1 - ((int) robotRow - 1)][(int) robotCol] = 2;
                        }
                        break;
                }
            }

            Random rand = new Random();
            int randomIndex = rand.nextInt(intList.size()); // Generate a random index
            int randomElement = intList.get(randomIndex); // Get the element at the random index    

            if (randomElement == 1) {
                // Right
                motion.rotateDegree(ROTATING_SPEED, 90);
                rightTurns++;

            } else if (randomElement == 2) {
                // Front

            } else if (randomElement == 3) {
                // Turn Left
                motion.rotateDegree(ROTATING_SPEED, -90);
                leftTurns++;
            } else {
                // If robot can't go left,right and front then robot will rotate to back.
                motion.rotateDegree(ROTATING_SPEED, 180);
            }

            // Robot move
            motion.moveDistance(MOVING_SPEED, GRID_SPACE);
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

            occupancyGrid[numRows - 1 - (int) robotRow][(int) robotCol] = 3;

            // Print the array
            // System.out.println("Robot ID: " + robotId);
            // printArray(occupancyGrid);
            // System.out.println();

            simpleComm.sendMessage(MappingUtils.arrayToString(occupancyGrid), 200);
        }
    }

    public void communicationInterrupt(String msg) {
        // System.out.println("Robot ID: " + robotId + " communicationInterrupt on " + id + " with msg:\n" + msg);
        // System.out.println();

        int[][] array = MappingUtils.stringToArray(msg);
        // printArray(array);

        occupancyGrid = MappingUtils.getMergedMap(occupancyGrid, array);
        MappingUtils.printArray(occupancyGrid);
        System.out.println();
    }
}
