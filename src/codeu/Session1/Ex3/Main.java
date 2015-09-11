package codeu.Session1.Ex3;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.stream.LongStream;

class Collatz {

    static long comparisons = 0;
    static long cacheHits = 0;
    static long cacheLength = 0;

    // Consider a sequence of positive integers starting with x. If x is
    // even, the next integer in the sequence is x/2. If x is odd, the
    // next integer in the sequence is 3*x+1. The sequence stops when it
    // reaches 1.
    //
    // For example, if x is 7, the sequence is
    //
    // 7,22,11,34,17,52,26,13,40,20,10,5,16,8,4,2,1
    //
    // Fill in the function loopCount so that it returns the length of
    // the sequence starting from x.
//    static int loopCount(int x) {
//        int length = 1;
//        int value = x;
//        while (value != 1) {
//            comparisons++;
//            if (value < 0) {
//                System.err.println("Overflow error!");
//                System.exit(1);
//            }
//
//            if (value % 2 == 0) {
//                value /= 2;
//            } else {
//                value = 3 * value + 1;
//            }
//            length++;
//        }
//        return length;
//    }

    static long loopCount(long x) {
        long length = 1;
        long value = x;
        while (value != 1) {
            comparisons++;
            if (value < 0) {
                System.err.println("Overflow error!");
                System.exit(1);
            }

            if (value % 2 == 0) {
                value /= 2;
            } else {
                value = 3 * value + 1;
            }
            length++;
        }
        return length;
    }

    static long fastLoopCount(long x, HashMap<Long, Long> sequenceLengths) {
        long length = 1;
        long value = x;
        ArrayDeque<Long> sequence = new ArrayDeque<>();
        sequence.add(x);

        while (value != 1) {
            comparisons++;
            if (value < 0) {
                System.err.println("Overflow error!");
                System.exit(1);
            }

            // Check if length is already known for value
            Long cacheValue = sequenceLengths.get(value);
            if (cacheValue != null) {
                cacheHits++;
                cacheLength += sequenceLengths.get(value);
                sequence.removeLast(); //No need to replace length for last value
                length += cacheValue - 1;
                break;
            }

            if (value % 2 == 0) {
                value /= 2;
            } else {
                value = 3 * value + 1;
            }

            sequence.addLast(value);
            length++;
        }

        long storeLength = length;
        for(long n : sequence) {
            sequenceLengths.put(n, storeLength--);
        }

        return length;
    }

    // Using loopCount, fill in the function maxLoop so that it returns
    // the maximum sequence length for any sequence that starts with a
    // number greater than or equal to x and less than y.
//    static int maxLoop(int x, int y) {
//        HashMap<Integer, Integer> sequenceLengths = new HashMap<>();
//        comparisons = 0;
//        int max = Integer.MIN_VALUE;
//        for (int i = x; i < y; i++) {
////            max = Math.max(max, loopCount(i));
//            max = Math.max(max, fastLoopCount(i, sequenceLengths));
//        }
//        return max;
//    }

    static long maxLoop(long x, long y) {
        HashMap<Long, Long> sequenceLengths = new HashMap<>();
        comparisons = 0;
        long max = 0;

        LongStream.range(x, y)
                .parallel()
                .map(i -> fastLoopCount(i, sequenceLengths))
                .max()
                .getAsLong();

//        LongStream.range(x, y)
//                .parallel()

//                .map(i -> fastLoopCount(i, sequenceLengths))
//                .max()
//                .getAsLong();


        //                .forEach(i -> max = Math.max(max, fastLoopCount(i, sequenceLengths)));
        return max;

//        for (long i = x; i < y; i++) {
////            max = Math.max(max, loopCount(i));
//            max = Math.max(max, fastLoopCount(i, sequenceLengths));
//        }

//        for (long i = y - 1; i >= x; i--) {
////            max = Math.max(max, loopCount(i));
//            max = Math.max(max, fastLoopCount(i, sequenceLengths));
//        }

//        return max;
    }


    public static void main(String[] args) {
        long start = System.nanoTime();

//        System.out.println(maxLoop(1, 4110000)); //597
        System.out.println(maxLoop(1, 110000)); //597



//        System.out.println(maxLoop(1, 111000));

//        for (int i = 0; i < 100; i++) {
//            maxLoop(1, 1110000);
//        }
        long end = System.nanoTime();

        System.out.println("Time: " + (end - start));
        System.out.println("Comparisons: " + comparisons);
        System.out.println("Cache hits: " + cacheHits);
        System.out.println("Cache length: " + cacheLength);
    }
}

