package codeu.Session1.Ex4;

public class SpaceMatrix {

    /**
     * Outputs all non zero elements of a 2 dimensional matrix to the standard output stream in the following format:
     * [row_index, column_index] = matrix[row_index][column_index]
     *
     * @param matrix The 2 dimensional matrix
     */
    static void outputNonZeros(int[][] matrix) {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    System.out.println("[" + i + ", " + j + "]: " + matrix[i][j]);
                }
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("Test 1");
        /*
        Expected Output:
        [1, 1]: 6
        [2, 0]: 8
        [2, 3]: 4
         */
        outputNonZeros(new int[][] {
                {0, 0, 0, 0},
                {0, 6, 0, 0},
                {8, 0, 0, 4},
        });

        System.out.println();
        System.out.println("Test 2");
        /*
        Expected Output:
        [0, 3] = 554
        [1, 0] = 5
        [1, 1] = 9
        [1, 2] = 20
        [1, 3] = 20
        [2, 0] = 2147483647
        [2, 1] = -2147483648
         */
        outputNonZeros(new int[][] {
                {0, 0, 0, 554},
                {5, 9, 20, -45},
                {Integer.MAX_VALUE, Integer.MIN_VALUE, 0, 0},
                {0, 0, 0, 0},
        });
    }

}
