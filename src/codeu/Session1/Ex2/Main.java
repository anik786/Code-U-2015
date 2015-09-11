package codeu.Session1.Ex2;

import java.util.ArrayList;

class Filter {
    // Write a function named "evens" that takes as input an array of
    // ints and returns a different array of ints containing
    // only the even elements of the input.
    public static int[] evens(int[] input) {
        // Here are some reminders:
        //
        // You can find input's length using input.length.
        //
        // You can find the remainder of a division using %. For instance,
        //   11 % 3 ⇒ 2
        //   25 % 4 ⇒ 1
        //
        // You can declare a new array of integers with the syntax:
        //   int[] var_name = new int[n];
        //
        // For example:
        //   int[] clown = new int[10];
        // creates an array named clown of 10 integers (clown[0] through clown[9])
        //


        ArrayList<Integer> list = new ArrayList<>();
//        Add value to list if it is even
        for (int v : input) {
            if (v % 2 == 0) {
                list.add(v);
            }
        }

        // Convert list of Integers back to an primitive array of ints
//        return list.toArray();
        int[] result = new int[list.size()];
        int i = 0;
        for (Integer v: list) {
            result[i++] = v;
        }
        return result;
        
//        --- In JAVA 8 ---
//        return IntStream.of(input).parallel().filter(v -> v % 2 == 0).toArray();
    }

    public static void main(String[] args) {
        //
        // Expected output:
        //  test1 results:
        //  8
        //  6
        //  0
        //  test2 results:
        //  2
        //  18
        //  28
        //  18
        //  28
        //  90
        //
        // STUDENTS, ADD ADDITIONAL TEST CASES BELOW
        //
        int[] test1 = {8,6,7,5,3,0,9};
        int[] ans = evens(test1);
        System.out.println("test1 results:");
        for (int i = 0; i < ans.length; ++i) {
            System.out.println(ans[i]);
        }
        int [] test2 = {2,7,18,28,18,28,45,90,45};
        ans = evens(test2);
        System.out.println("test2 results:");
        for (int i = 0; i < ans.length; ++i) {
            System.out.println(ans[i]);
        }

        // Normal
        test(3, new int[]{1, 2, 1, 4, 6}, new int[]{2, 4, 6}); // Positives
        test(4, new int[]{-1, -2, -200}, new int[]{-2, -200}); // Negative
        test(5, new int[]{20, 40, -10, 5, 1, 1, 4}, new int[]{20, 40, -10, 4}); // Mixed

        // Extremes
        test(5, new int[]{}, new int[]{});
        test(6, new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE, 0, Integer.MAX_VALUE - 1, Integer.MIN_VALUE + 1},
                new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE - 1});

    }

    static void test(int testNum, int[] input, int[] expected) {
        System.out.println("test" + testNum + " results:");
        int[] ans = evens(input);

        boolean pass = true;
        for (int i = 0; i < ans.length; i++) {
            System.out.println(ans[i]);
            if (ans[i] != expected[i]) {
                pass = false;
            }
        }

        if (pass && ans.length == expected.length) {
            System.out.println("=== Test Passed! ===");
        } else {
            System.out.println("=== Test Failed! ===");
        }
        System.out.println();
    }

}

