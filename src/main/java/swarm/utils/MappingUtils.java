package swarm.utils;

public class MappingUtils {
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

    public static void convertThreesToOnes(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] == 3) {
                    matrix[i][j] = 1;
                }
            }
        }
    }

    public static int countMatchedCells(int[][] A, int[][] B) {
        int count = 0;
        for (int i = 0; i < A.length; i++) {
            for (int j = 0; j < A[i].length; j++) {
                if (A[i][j] == B[i][j]) {
                    count++;
                }
            }
        }
        return count;
    }

}
