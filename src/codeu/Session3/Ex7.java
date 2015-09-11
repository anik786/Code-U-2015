package codeu.Session3;


import codeu.Session2.SparseMatrix;

import java.io.*;

public class Ex7 {
    public static void main(String[] args) throws Exception {

        final String path = "/home/yahya/Documents/java-sandbox/_outputs/sparse-matrix";
        SparseMatrix2 matrix = new SparseMatrix2(5, 5);
        matrix.set(1, 1, 3);
        matrix.set(1, 2, 7);
        matrix.set(1, 3, 150);
        matrix.set(1, 4, 5);
        matrix.set(1, 5, 250);
        matrix.set(2, 1, 6);
        matrix.set(3, 4, 6);
        matrix.printMatrix();
        System.out.println(matrix.density());

        matrix.saveData(path);

        SparseMatrix2 matrix2 = new SparseMatrix2(path);
        matrix2.printMatrix();
    }
}

class SparseMatrix2 extends SparseMatrix implements Serializable {
    public SparseMatrix2(int rows, int columns) {
        super(rows, columns);
    }

    public SparseMatrix2(int[][] matrix) {
        super(matrix);
    }

    public SparseMatrix2(String path) throws IOException, ClassNotFoundException {
        loadData(path);
    }

    public void saveData(String filePath) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(filePath, false);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
        objectOutputStream.writeObject(this);
        objectOutputStream.close();
    }

    @SuppressWarnings("unchecked")
    public static SparseMatrix2 loadData(String filePath) throws IOException, ClassNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
        return (SparseMatrix2) objectInputStream.readObject();
    }

}

