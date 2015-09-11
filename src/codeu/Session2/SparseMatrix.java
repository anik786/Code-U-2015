package codeu.Session2;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class SparseMatrix implements Serializable{
    protected int rows;
    protected int columns;
    protected int size;
    private int nonZeroElements;
    protected HashMap<Integer, HashMap<Integer, Integer>> sparseMatrix = new HashMap<>();

    public SparseMatrix(int rows, int columns) {
        if (rows <= 0 || columns <= 0) {
            throw new IllegalArgumentException("Size of rows and columns must be greater than 0");
        }
        this.rows = rows;
        this.columns = columns;
        this.size = rows * columns;
    }

    public SparseMatrix(int[][] matrix) {
        this.rows = matrix.length;
        this.columns = matrix[0].length;
        this.size = rows * columns;

        for (int row = 0; row < matrix.length; row++) {
            if (matrix[row].length != this.columns) {
                throw new IllegalArgumentException("A matrix must have the same number of columns for each row");
            }
            for (int column = 0; column < matrix[row].length; column++) {
                int value = matrix[row][column];
                if (value != 0) {
                    set(row, column, value);
                }
            }
        }
    }

    protected SparseMatrix() {}

    int rowDimension() {
        return this.rows;
    }

    int columnDimension() {
        return this.columns;
    }

    int size() {
        return this.size;
    }

    int nonZeroElementsSize() {
        return this.nonZeroElements;
    }

    public double density() {
        return (double) nonZeroElementsSize() / size(); //TODO: check
    }

    public void set(int rowIndex, int columnIndex, int value) {
        checkIndexesAreValid(rowIndex, columnIndex);

        HashMap<Integer, Integer> row;
        row = sparseMatrix.get(rowIndex);

        if (row == null) {
            row = new HashMap<>();
            sparseMatrix.put(rowIndex, row);
        }

        row.put(columnIndex, value);
        this.nonZeroElements++;
    }

    public int get(int rowIndex, int columnIndex) {
        checkIndexesAreValid(rowIndex, columnIndex);

        Integer value = null;
        HashMap<Integer, Integer> row = sparseMatrix.get(rowIndex);
        if (row != null) {
            value = row.get(columnIndex);
        }

        if (value == null) {
            return 0;
        } else {
            return value;
        }
    }

    public SparseMatrix add(SparseMatrix matrix) throws Exception {
        if (this.rowDimension() != matrix.rowDimension() || this.columnDimension() != matrix.columnDimension()) {
            throw new IllegalArgumentException("Can only add matrices with the same dimensions");
        }

        SparseMatrix resultMatrix = new SparseMatrix(this.rows, this.columns);

        for(Map.Entry<Integer, HashMap<Integer, Integer>> row : this.sparseMatrix.entrySet()) {
            int rowIndex = row.getKey();
            for (Map.Entry<Integer, Integer> column: row.getValue().entrySet()) {
                int columnIndex = column.getKey();
                int value = column.getValue();
                int result = value + matrix.get(rowIndex, columnIndex);
                resultMatrix.set(rowIndex, columnIndex, result);
            }
        }

        // Iterate through other matrix for potential entries that this matrix doesn't have
        for(Map.Entry<Integer, HashMap<Integer, Integer>> row : matrix.sparseMatrix.entrySet()) {
            int rowIndex = row.getKey();
            for (Map.Entry<Integer, Integer> column: row.getValue().entrySet()) {
                int columnIndex = column.getKey();
                int value = column.getValue();
                if (this.get(rowIndex, columnIndex) == 0) {
//                    If zero entry has been placed in this matrix
                    resultMatrix.set(rowIndex, columnIndex, value);
                }
            }
        }

        return resultMatrix;
    }

    private void checkIndexesAreValid(int rowIndex, int columnIndex) {
        if (rowIndex > this.rows || columnIndex > this.columns || rowIndex < 0 || columnIndex < 0) {
            throw new IndexOutOfBoundsException("Invalid row & column index for matrix of size: " + rows + "x" + columns);
        }
    }

    public void printMatrix() {
        for (Map.Entry<Integer, HashMap<Integer, Integer>> row : this.sparseMatrix.entrySet()) {
            int rowIndex = row.getKey();
            for (Map.Entry<Integer, Integer> column : row.getValue().entrySet()) {
                int columnIndex = column.getKey();
                int value = column.getValue();
                System.out.println("[" + rowIndex + ", " + columnIndex + "] = " + value);
            }
        }
    }

    public int[][] extract(int xOrigin, int yOrigin, int xSize, int ySize) {
        return new int[0][0];
    }

    public int[][] replace(int xOrigin, int yOrigin, int[][] matrix) {
        return new int[0][0];
    }

    public static void main(String[] args) throws Exception {
        SparseMatrix matrix = new SparseMatrix(5, 5);
        matrix.set(1, 4, 5);
        matrix.set(1, 2, 6);
        matrix.set(3, 4, 6);
        matrix.printMatrix();
        System.out.println(matrix.density());

        SparseMatrix matrix2 = new SparseMatrix(5, 5);
        matrix2.set(1, 4, 10);
        matrix2.set(0, 0, 100);
        matrix2.printMatrix();
        System.out.println(matrix.density());

        SparseMatrix matrix3 = matrix.add(matrix2);
        matrix3.printMatrix();

        System.out.println();

        matrix = new SparseMatrix(new int[][]
                {
                        {0, 0, 0, 0},
                        {0, 6, 0, 0},
                        {8, 0, 0, 4},
                });
        matrix.printMatrix();

    }

}
