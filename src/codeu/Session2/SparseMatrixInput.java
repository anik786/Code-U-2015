package codeu.Session2;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SparseMatrixInput {

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        int rows;
        int columns;

        System.out.println("--- Size of matrix ---");

        System.out.print("Number of Rows: ");
        rows = scn.nextInt();

        System.out.print("Number of Columns: ");
        columns = scn.nextInt();
        scn.nextLine();

        System.out.println();
        System.out.println("Size of Matrix: " + rows + " X " + columns);
        System.out.println();

        int[][] matrix = new int[rows][columns];

        System.out.println("--- Entries ---");
        System.out.println("Write entries in the form of \"[x, y]: value\"");
        System.out.println("End entries with a blank line");
        System.out.println();

        Pattern entryPattern = Pattern.compile("\\[([0-9]+), ([0-9]+)\\]: ([0-9]+)");
        while(true) {
            String entry = scn.nextLine();
            Matcher entryMatch = entryPattern.matcher(entry);

            if (entry.equals("")) {
                System.out.println("--- End of Input ---");
                break;
            } else if (entryMatch.find()) {
                int x = Integer.parseInt(entryMatch.group(1));
                int y = Integer.parseInt(entryMatch.group(2));
                int value = Integer.parseInt(entryMatch.group(3));

                if (x >= rows || y >= columns) {
                    System.out.println("Invalid Row/ Column Number for matrix of size: " +
                            rows + "x" + columns + "!");
                } else {
                    matrix[x][y] = value;
                }
            } else {
                System.out.println("Invalid format!");
            }
        }

//        Print out matrix
        for (int i = 0; i < rows; i++) {
            System.out.print("|");
            for (int j = 0; j < columns; j++) {
                System.out.print("  " + matrix[i][j] + "  ");
            }
            System.out.println("|");
        }

    }

}
