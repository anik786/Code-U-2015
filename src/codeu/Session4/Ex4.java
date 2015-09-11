package codeu.Session4;

import java.util.HashMap;

public class Ex4 {

    public static void main(String[] args) {

        System.out.println(ReturningChange.makeChange(5, new int[]{1, 2, 3})); //5
        System.out.println(ReturningChange.makeChange(4, new int[]{1, 2, 3})); //4
        System.out.println(ReturningChange.makeChange(10, new int[]{2, 5, 3, 6})); //4
        System.out.println(ReturningChange.makeChange(1, new int[]{2, 3, 5})); //0
        System.out.println(ReturningChange.makeChange(10, new int[]{2, 3, 5})); //4

        System.out.println(ReturningChange.makeChange(10, new int[]{2, 3, 5})); //4

        System.out.println(ReturningChange.makeChange(2700, new int[]{70, 50, 13}));

    }

}


class ReturningChange {

    public static long makeChange(int n, int[] m) {
        HashMap<Integer, HashMap<Integer, Long>> cache = new HashMap<>();
        return makeChange(n, m, m.length - 1, cache);
    }


    private static long makeChange(int n, int[] m, int i, HashMap<Integer, HashMap<Integer, Long>> cache) {
        // If reached target
        if (n == 0) {
            return 1;
        }

        // If missed target or run out of coins
        if (n < 0 || i < 0) {
            return 0;
        }

        // Return cached value, if exists
        // NOTE TO MARKERS: Is there a more elegant way of checking & instantiating null values in HashMaps
        HashMap<Integer, Long> nCache = cache.get(n);

        if (nCache == null) {
            nCache = new HashMap<>();
            cache.put(n, nCache);
        } else {
            Long cacheValue = nCache.get(i);
            if (cacheValue != null) {
                return cacheValue;
            }
        }

        long answer = makeChange(n - m[i], m, i, cache) + // Use coin at m[i]
                makeChange(n, m, --i, cache); // Don't use coin at m[i]

        if (answer < 0) {
            throw new ArithmeticException("Answer larger than what can be contained in a long: " + Long.MAX_VALUE);
        }

        nCache.put(i, answer);

        return answer;
    }

}