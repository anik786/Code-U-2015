package codeu.Session2;

import java.util.ArrayDeque;
import java.util.HashMap;

public class CollatzGraph {
    HashMap<Integer, Integer> sequenceLengths = new HashMap<>();

    CollatzGraph() {
        sequenceLengths.put(1, 1);
    }

    int loopCount(int x) {
        int length = 0;
        int value = x;


        ArrayDeque<Integer> sequence = new ArrayDeque<>();

        while(sequenceLengths.get(value) == null) {
            // while the length of the sequence of the current value is not known

            sequence.addLast(value);
            length++;

            if (value % 2 == 0) { // If even
                value /= 2;
            } else { // if odd
                value = 3 * value + 1;
            }
        }
        length += sequenceLengths.get(value); // Add the stored length of the sequence

        // Update the lengths of all the elements of the sequences that are not yet stored in the hash map
        int storeLength = length;
        for(int n : sequence) {
            sequenceLengths.put(n, storeLength);
            storeLength--;
        }

        return length;
    }

    // Using loopCount, fill in the function maxLoop so that it returns
    // the maximum sequenceLengths length for any sequenceLengths that starts with a
    // number greater than or equal to x and less than y.
    int maxLoop(int x, int y) {
        int max = 0;
        for (int i = x; i < y; i++) {
            max = Math.max(max, loopCount(i));
        }
        return max;
    }

    int naiveLoopCount(int x) {
        int length = 1;
        while (x != 1) {
            if (x % 2 == 0) {
                x /= 2;
            } else {
                x = 3 * x + 1;
            }
            length++;
        }
        return length;
    }

    int naiveMaxLoop(int x, int y) {
        int max = 0;
        for (int i = x; i < y; i++) {
            max = Math.max(max, loopCount(i));
        }
        return max;
    }

    public int numberOfNodes() {
        return this.sequenceLengths.size();
    }

    public static void main(String[] args) {
        CollatzGraph graph;
//        graph.initialize(); // Used constructor instead


//        Performance test: Oddly, the naive way is faster than the optimised way
        long start = System.nanoTime();
        for (int i = 0; i < 100; i++) {
            graph = new CollatzGraph();
            graph.maxLoop(1, 100000);
//            graph.naiveMaxLoop(1, 100000);
        }
        long end = System.nanoTime();
        System.out.println(end - start);

        // Consistent results test
        boolean passed = true;
        mainLoop: for (int i = 1; i < 1000; i++) {
            for (int j = i; j < 1000; j++) {
                graph = new CollatzGraph();
                if (graph.maxLoop(i, j) != graph.naiveMaxLoop(i, j)) {
                    passed = false;
                    System.out.println("Inconsistent result on: " + i + ", " + j);
                    break mainLoop;
                }
            }
        }

        if (passed) {
            System.out.println("PASSED!");
        } else {
            System.out.println("FAILED");
        }

    }

}
