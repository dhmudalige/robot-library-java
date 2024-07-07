package Robots;

import swarm.robot.MappingRobot;
import swarm.utils.ArenaUtils;
import swarm.utils.MappingUtils;

import static swarm.utils.CSVRecorder.recordExplorations;

/*
 * HLWARobot ==> Mapping Robot based on Heuristic Least Cost Estimate
 */

public class HLCARobot extends MappingRobot {
    public static final String ROBOT_NAME = "<HLCA Robot>";
    public static final String CSV_PATH = "src/resources/csv-files/Swarm-Results.csv";

    // Size of a grid cell
    private final double GRID_SPACE = 18.000;

    // Robot's initial position
    int robotRow = 0;
    int robotCol = 0;
    int robotId = 0;
    int cellValue = 0;
    int[] cellValuesNESW = new int[4];

    int loopCount = 0;
    int matchedCells = 0;

    long startTime, endTime, timeTaken;

    public HLCARobot(int id, double x, double y, double heading) {
        super(id, x, y, heading);
        robotRow= (int) ((x+81)/18);
        robotCol= (int) ((y+81)/18);
        robotId=id;
    }

    int numRows=10;
    int numCols=10;
    int[][] occupancyGrid = new int[numRows][numCols];

    int rightTurns=0;
    int leftTurns=0;

    int rRow=0;
    int rCol=0;

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

        rRow = numRows-1-robotRow;
        rCol = robotCol;

        // Mark the starting cell as visited
        occupancyGrid[rRow][rCol] = -2;

        MappingUtils.printArray(occupancyGrid);
    }



    public void loop() throws Exception {
        super.loop();
        loopCount++;

        startTime = System.currentTimeMillis();

        if (state == robotState.RUN) {
            // Get present distances from robot's left,front and right
            int[] d = proximitySensor.getProximity().distances();

            // Determine the movement based on the sum of right and left turns modulo 4
            int direction = (rightTurns - leftTurns) % 4;
            
            // Adjust direction to ensure it's within the range of 0 to 3
            if (direction < 0) {
                direction += 4;
            }
            

            cellValue = occupancyGrid[rRow][rCol];
            // give a high value to cells robot moved
            for (int i = 0; i < occupancyGrid.length; i++) {
                for (int j = 0; j < occupancyGrid[i].length; j++) {
                    if (occupancyGrid[i][j] == -2) {
                        // occupancyGrid[i][j] = cellValue+5;   // worked for 1 robot
                        occupancyGrid[i][j] = cellValue+2;
                    }
                }
            }




            // start: mark explored cells and obstacles ----------------------------------------------------------------------
            // Robot rotating way :- if distance from (any side +6) > GRID_SPACE then robot
            // will rotate that side.
            if (d[PROXIMITY_RIGHT] + 6 > GRID_SPACE) {
                
                // System.out.println(d[PROXIMITY_RIGHT]);
                
                // Mark free spaces
                double proximityRangeRight = (d[PROXIMITY_RIGHT] + 6) / GRID_SPACE;
                switch (direction) {
                    case 0: // Facing north
                        for (int i = 0; i < proximityRangeRight; i++) {
                            // System.out.println(i);
                            occupancyGrid[rRow][rCol+(i)] += 1;
                        }
                        break;
                    case 1: // Facing east
                        for (int i = 0; i < proximityRangeRight; i++) {
                            occupancyGrid[rRow+i][rCol] += 1;
                        }
                        break;
                    case 2: // Facing south
                        for (int i = 0; i < proximityRangeRight; i++) {
                            occupancyGrid[rRow][rCol-(i)] += 1;
                        }
                        break;
                    case 3: // Facing west
                        for (int i = 0; i < proximityRangeRight; i++) {
                            occupancyGrid[rRow-i][rCol] += 1;
                        }
                        break;
                } 
            } 
            else {
                // Mark obstacles
                switch (direction) {
                    case 0: // Facing north
                        if (rCol != numCols-1 && occupancyGrid[rRow][rCol+1] == 0){
                            occupancyGrid[rRow][rCol+1] = -1;
                        }
                        break;
                    case 1: // Facing east
                        if (robotRow != 0 && occupancyGrid[rRow+1][rCol] == 0){
                            occupancyGrid[rRow+1][rCol] = -1;
                        }
                        break;
                    case 2: // Facing south
                        if (rCol != 0 && occupancyGrid[rRow][rCol-1] == 0){
                            occupancyGrid[rRow][rCol-1] = -1;
                        }
                        break;
                    case 3: // Facing west
                        if (robotRow != numRows-1 && occupancyGrid[rRow-1][rCol] == 0){
                            occupancyGrid[rRow-1][rCol] = -1;
                        }
                        break;
                } 
            }


            
            if (d[PROXIMITY_FRONT] + 6 > GRID_SPACE) {

                // Mark free spaces
                double proximityRangeFront = (d[PROXIMITY_FRONT] + 6) / GRID_SPACE;
                switch (direction) {
                    case 0: // Facing north
                        for (int i = 0; i < proximityRangeFront; i++) {
                            occupancyGrid[rRow-i][rCol] += 1;
                        }
                        break;
                    case 1: // Facing east
                        for (int i = 0; i < proximityRangeFront; i++) {
                            occupancyGrid[rRow][rCol+i] += 1;
                        }
                        break;
                    case 2: // Facing south
                        for (int i = 0; i < proximityRangeFront; i++) {
                            occupancyGrid[rRow+i][rCol] += 1;
                        }
                        break;
                    case 3: // Facing west
                        for (int i = 0; i < proximityRangeFront; i++) {
                            occupancyGrid[rRow][rCol-i] += 1;
                        }
                        break;
                } 
            } 
            else {
                // Mark obstacles
                switch (direction) {
                    case 0: // Facing north
                        if (robotRow != numRows-1 && occupancyGrid[rRow-1][rCol] == 0){
                            occupancyGrid[rRow-1][rCol] = -1;
                        }
                        break;
                    case 1: // Facing east
                        if (rCol != numCols-1 && occupancyGrid[rRow][rCol+1] == 0){
                            occupancyGrid[rRow][rCol+1] = -1;
                        }
                        break;
                    case 2: // Facing south
                        if (robotRow != 0 && occupancyGrid[rRow+1][rCol] == 0){
                            occupancyGrid[rRow+1][rCol] = -1;
                        }
                        break;
                    case 3: // Facing west
                        if (rCol != 0 && occupancyGrid[rRow][rCol-1] == 0){
                            occupancyGrid[rRow][rCol-1] = -1;
                        }
                        break;
                } 
            }


            if (d[PROXIMITY_LEFT] + 6 > GRID_SPACE) {

                // Mark free spaces
                double proximityRangeLeft = (d[PROXIMITY_LEFT] + 6) / GRID_SPACE;
                switch (direction) {
                    case 0: // Facing north
                        for (int i = 0; i < proximityRangeLeft; i++) {
                            occupancyGrid[rRow][rCol-i] += 1;
                        }
                        break;
                    case 1: // Facing east
                        for (int i = 0; i < proximityRangeLeft; i++) {
                            occupancyGrid[rRow-i][rCol] += 1;
                        }
                        break;
                    case 2: // Facing south
                        for (int i = 0; i < proximityRangeLeft; i++) {
                            occupancyGrid[rRow][rCol+i] += 1;
                        }
                        break;
                    case 3: // Facing west
                        for (int i = 0; i < proximityRangeLeft; i++) {
                            occupancyGrid[rRow+i][rCol] += 1;
                        }
                        break;
                } 
            } 
            else {
                // Mark obstacles
                switch (direction) {
                    case 0: // Facing north
                        if (rCol != 0 && occupancyGrid[rRow][rCol-1] == 0){
                            occupancyGrid[rRow][rCol-1] = -1;
                        }
                        break;
                    case 1: // Facing east
                        if (robotRow != numRows-1 && occupancyGrid[rRow-1][rCol] == 0){
                            occupancyGrid[rRow-1][rCol] = -1;
                        }
                        break;
                    case 2: // Facing south
                        if (rCol != numCols-1 && occupancyGrid[rRow][rCol+1] == 0){
                            occupancyGrid[rRow][rCol+1] = -1;
                        }
                        break;
                    case 3: // Facing west
                        if (robotRow != 0 && occupancyGrid[rRow+1][rCol] == 0){
                            occupancyGrid[rRow+1][rCol] = -1;
                        }
                        break;
                } 
            }

            
            if (d[PROXIMITY_BACK] + 6 > GRID_SPACE) {

                // Mark free spaces
                double proximityRangeBack = (d[PROXIMITY_BACK] + 6) / GRID_SPACE;
                switch (direction) {
                    case 0: // Facing north
                        for (int i = 0; i < proximityRangeBack; i++) {
                            occupancyGrid[rRow+i][rCol] += 1;
                        }
                        break;
                    case 1: // Facing east
                        for (int i = 0; i < proximityRangeBack; i++) {
                            occupancyGrid[rRow][rCol-i] += 1;
                        }
                        break;
                    case 2: // Facing south
                        for (int i = 0; i < proximityRangeBack; i++) {
                            occupancyGrid[rRow-i][rCol] += 1;
                        }
                        break;
                    case 3: // Facing west
                        for (int i = 0; i < proximityRangeBack; i++) {
                            occupancyGrid[rRow][rCol+i] += 1;
                        }
                        break;
                } 
            } 
            else {
                // Mark obstacles
                switch (direction) {
                    case 0: // Facing north
                        if (robotRow != numRows-1 && occupancyGrid[rRow-1][rCol] == 0){
                            occupancyGrid[rRow+1][rCol] = -1;
                        }
                        break;
                    case 1: // Facing east
                        if (rCol != numCols-1 && occupancyGrid[rRow][rCol+1] == 0){
                            occupancyGrid[rRow][rCol-1] = -1;
                        }
                        break;
                    case 2: // Facing south
                        if (robotRow != 0 && occupancyGrid[rRow+1][rCol] == 0){
                            occupancyGrid[rRow-1][rCol] = -1;
                        }
                        break;
                    case 3: // Facing west
                        if (rCol != 0 && occupancyGrid[rRow][rCol-1] == 0){
                            occupancyGrid[rRow][rCol+1] = -1;
                        }
                        break;
                } 
            }

            // printArray(occupancyGrid);
            // end: mark explored cells and obstacles ----------------------------------------------------------------------


            // start: get the min cellValue (finding the cell the robot should move in the next step)----------------------------------------------------------------------
            if (rRow!=0){
                cellValuesNESW[0]=occupancyGrid[rRow-1][rCol];
                // System.out.println(occupancyGrid[rRow-1][rCol]);
            } else {
                cellValuesNESW[0]=-1;  // obstacle
            }

            if (rCol!=numCols-1){
                cellValuesNESW[1]=occupancyGrid[rRow][rCol+1];
            } else {
                cellValuesNESW[1]=-1;  // obstacle
            }

            if (rRow!=numRows-1){
                cellValuesNESW[2]=occupancyGrid[rRow+1][rCol];
            } else {
                cellValuesNESW[2]=-1;  // obstacle
            }

            if (rCol!=0){
                cellValuesNESW[3]=occupancyGrid[rRow][rCol-1];
            } else {
                cellValuesNESW[3]=-1;  // obstacle
            }

            int minCellValue = Integer.MAX_VALUE; // Initialize with a very large value
            int minCellValueIndex =0;

            for (int i = 0; i < cellValuesNESW.length; i++) {
                // System.out.println(cellValuesNESW[i]);
                if (cellValuesNESW[i] >= 0 && cellValuesNESW[i] < minCellValue) {
                    minCellValue = cellValuesNESW[i];
                    minCellValueIndex = i;
                }
            }
            // System.out.println(minCellValue + " " + minCellValueIndex);

            // end: get the min cellValue (finding the cell the robot should move in the next step)----------------------------------------------------------------------



            // Move based on the calculated direction
            switch (direction) {
                case 0: // Facing north
                    switch (minCellValueIndex) {
                        case 0: // north
                            robotRow++;
                            break;
                        case 1: // east
                            motion.rotateDegree(ROTATING_SPEED, 90);
                            rightTurns++;
                            robotCol++;
                            break;
                        case 2: // south
                            motion.rotateDegree(ROTATING_SPEED, 180);
                            rightTurns++;rightTurns++;
                            robotRow--;
                            break;
                        case 3: // west
                            motion.rotateDegree(ROTATING_SPEED, -90);
                            leftTurns++;
                            robotCol--;
                            break;
                    }
                    break;
                case 1: // Facing east
                    switch (minCellValueIndex) {
                        case 0: // north
                            motion.rotateDegree(ROTATING_SPEED, -90);
                            leftTurns++;
                            robotRow++;
                            break;
                        case 1: // east
                            robotCol++;
                            break;
                        case 2: // south
                            motion.rotateDegree(ROTATING_SPEED, 90);
                            rightTurns++;
                            robotRow--;
                            break;
                        case 3: // west
                            motion.rotateDegree(ROTATING_SPEED, 180);
                            rightTurns++;rightTurns++;
                            robotCol--;
                            break;
                    }
                    break;
                case 2: // Facing south
                    switch (minCellValueIndex) {
                        case 0: // north
                            motion.rotateDegree(ROTATING_SPEED, 180);
                            rightTurns++;rightTurns++;
                            robotRow++;
                            break;
                        case 1: // east
                            motion.rotateDegree(ROTATING_SPEED, -90);
                            leftTurns++;
                            robotCol++;
                            break;
                        case 2: // south
                            robotRow--;
                            break;
                        case 3: // west
                            motion.rotateDegree(ROTATING_SPEED, 90);
                            rightTurns++;
                            robotCol--;
                            break;
                    }
                    break;
                case 3: // Facing west
                    switch (minCellValueIndex) {
                        case 0: // north
                            motion.rotateDegree(ROTATING_SPEED, 90);
                            rightTurns++;
                            robotRow++;
                            break;
                        case 1: // east
                            motion.rotateDegree(ROTATING_SPEED, 180);
                            rightTurns++;rightTurns++;
                            robotCol++;
                            break;
                        case 2: // south
                            motion.rotateDegree(ROTATING_SPEED, -90);
                            leftTurns++;
                            robotRow--;
                            break;
                        case 3: // west
                            robotCol--;
                            break;
                    }
                    break;
            } 

            // Robot move
            motion.moveDistance(MOVING_SPEED, GRID_SPACE);
            delay(1000);

            rRow = numRows-1-robotRow;
            rCol = robotCol;

            cellValue = occupancyGrid[rRow][rCol];
            occupancyGrid[rRow][rCol] = -2;

            // printArray(occupancyGrid);
            // printArraySimplified(occupancyGrid);


            // count_convertMap++;
            // if (count_convertMap==30){
            //     occupancyGrid = convertMap(occupancyGrid);
            //     count_convertMap=0;
            // }

            simpleComm.sendMessage(MappingUtils.arrayToString(occupancyGrid), 200);
        }

        endTime = System.currentTimeMillis();

        timeTaken = endTime - startTime;

        MappingUtils.convertThreesToOnes(occupancyGrid);

        matchedCells = MappingUtils.countMatchedCells(occupancyGrid, ArenaUtils.ARENA_OBSTACLE);

        recordExplorations(CSV_PATH, ROBOT_NAME, this.robotId, endTime, loopCount, timeTaken, matchedCells);
    }

    public void communicationInterrupt(String msg) {
        // System.out.println("Robot ID: " + robotId + " communicationInterrupt on " + id + " with msg:\n" + msg);
        // System.out.println();

        int[][] array = MappingUtils.stringToArray(msg);
        // printArray(array);

        occupancyGrid = MappingUtils.getMergedMap(occupancyGrid, array);

        MappingUtils.printArray(occupancyGrid);
        // printArraySimplified(occupancyGrid);
        System.out.println();
    }
}