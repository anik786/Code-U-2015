package codeu.Session1.Ex1;

import java.util.stream.IntStream;

class Zip {
    // Fill in the method "join". It returns a boolean array. The ith
    // value is that array (i.e., array[i]) should be true if the ith
    // value in the first argument to join is divisible by the ith value
    // in the second argument to join. The returned boolean array should
    // be exactly as long as the shorter of the two arguments.
    //
    // Reminders:
    //
    // 1. An integer p is said to be "divisible by" an integer q when there
    // is some integer k such that q*k = p. This is the same as saying
    // "the remainder of p when divided by q is 0". The remainder
    // operator is Java is written with a percent sign: "a % b" is the
    // remainder of a when divided by b.
    //
    // 2. The length of an array bar is stored in bar.length.
    //
    // 3. New arrays are declared with the syntax:
    //        float[] foo = new float[8];
    //
    static boolean[] join(int[] y, int[] z) {
        boolean[] result = new boolean[Math.min(y.length, z.length)];
//        for(int i = 0; i < result.length; ++i) {
//            result[i] = z[i] != 0 && (y[i] % z[i] == 0);
//        }

//        return result;

        IntStream.range(0, result.length)
                .forEach(i -> result[i] = z[i] != 0 && (y[i] % z[i] == 0));

        return result;

    }

    public static void main(String[] args) {
        //
        // Expected output:
        //  false
        //  false
        //  false
        //  false
        //  true
        //  false
        //  true
        //
        // STUDENTS, ADD ADDITIONAL TEST CASES BELOW
        //
        int euler[] = {2, 7, 18, 28, 18, 28, 45, 90, 45};
        int jenny[] = {8, 6, 7, 5, 3, 0, 9};
        boolean divisibles[] = join(euler, jenny);
        for (int i = 0; i < divisibles.length; ++i) {
            System.out.println(divisibles[i]);
        }

        test(new int[] {}, new int[] {}, new boolean[] {});

        test(new int[] {2, 4, 6, 5, 20, 100000},
                new int[] {1, 2, 5, 5, 3},
                new boolean[] {true, true, false, true, false});

        test(new int[] {5 , 0},
                new int[] {0, 5},
                new boolean[] {false, false});

        test(new int[] {-5 , -200, 120, -7, -4, 15},
                new int[] {-5, 10, -20, 3, 9, -6},
                new boolean[] {true, true, true, false, false, false});
    }


    static void test(int[] y, int[] z, boolean[] expected) {
        boolean[] divisibles = join(y, z);

        boolean pass = true;
        for (int i = 0; i < divisibles.length; i++) {
            System.out.println(divisibles[i]);
            if (divisibles[i] != expected[i]) {
                pass = false;
            }
        }

        if (pass && divisibles.length == expected.length) {
            System.out.println("=== Test Passed! ===");
        } else {
            System.out.println("=== Test Failed! ===");
        }
        System.out.println();
    }

}
