package codeu.Session4;

import java.util.*;

public class Ex3 {

    public static void main(String[] args) {
        correctnessTest(1, 1000);

//        ### Performance Comparison Test
//        performanceTest(100_000, NaiveUglyNumbers::kthNumber); // 6.1E9
//        performanceTest(100_000, UglyNumbers::kthNumber); 3.48E6

//        ### Performance Test ###
//        performanceTest(10_000_000, UglyNumbers::kthNumber); //6.84E9

    }

    static void correctnessTest(int start, int end) {
        for (int i = start; i <= end; i++) {
            if (UglyNumbers.kthNumber(i) != NaiveUglyNumbers.kthNumber(i)) {
                System.out.println("FAILED at " + i);
                return;
            }
        }
        System.out.println("PASSED Correctness test from: " + start + " to " + end);
    }


    static void performanceTest(int numItems, Tester test) {
        double startTime = System.nanoTime();

        test.algorithm(numItems);

        double endTime = System.nanoTime();
        double timeTaken = endTime - startTime;
        System.out.println("TIME TAKEN: " + timeTaken);
    }

    interface Tester {
        int algorithm(int i);
    }
}


class UglyNumbers {

    static public int kthNumber(int k) {
        if (k <= 5) {
            return k;
        }

        //any multiples of ugly is also ugly
//        final int SIEVE_SIZE = 15; // FOR TESTING
        final int SIEVE_SIZE = Math.min(10_000_000, k * 2);
        boolean[] isUglyNumber = new boolean[SIEVE_SIZE];

        ArrayDeque<Integer> uglyNumbers = new ArrayDeque<>();
        isUglyNumber[1] = true;
        isUglyNumber[2] = true;
        isUglyNumber[3] = true;
        isUglyNumber[5] = true;

        uglyNumbers.add(1);

        int found = 1;
        int startIndex = 0;
        boolean first = true;

        while (true) {
            int i = 0;

            if (first) {
                i = 2;
                first = false;
            }

            for (; i < isUglyNumber.length; i++) {
                if (isUglyNumber[i]) {
//                    for (int j = i * 2; j + i - startIndex < isUglyNumber.length; j += i) { //TODO
//                        isUglyNumber[j] = true;
//                    }

                    // TODO: better efficiency
                    for (int j = (startIndex + i) * 2;
                         j - startIndex < isUglyNumber.length; j += i + startIndex) {

                        isUglyNumber[j - startIndex] = true;
                    }

                    uglyNumbers.add(i + startIndex);
                    found++;

                    if (found == k) {
                        return i + startIndex;
                    }
                }
            }

            isUglyNumber = new boolean[SIEVE_SIZE];
            startIndex += SIEVE_SIZE;

            for (int num : uglyNumbers) {
                if (num != 1) {
                    // get first j, when i > startIndex
                    for (int j = (int) Math.ceil((double) startIndex / num) * num;
                         j - startIndex < isUglyNumber.length;
                         j += num) {

                        isUglyNumber[j - startIndex] = true;
                    }
                }
            }
        }
    }
}

class NaiveUglyNumbers {

    static public int kthNumber(int k) {
        if (k <= 5) {
            return k;
        }

        int numFound = 0;
        int currentNumber = 1;
        boolean even = false;

        while (true) {
            if (even || isUglyNumber(currentNumber)) {
                numFound++;
                if (numFound == k) {
                    return currentNumber;
                }
            }
            currentNumber++;
            even = !even;
        }
    }

    static boolean isUglyNumber(int n) {
        if (n == 1 || n == 2 || n == 3 || n == 5) {
            return true;
        }

        if (n % 2 == 0) { // if even
            return true;
        }

        for (int i = 3; i < n - 1; i+= 2) {
            if (n % i == 0) {
                if (NaiveUglyNumbers.isUglyNumber(i)) {
                    return true;
                }
            }
        }

        return false;
    }

}
