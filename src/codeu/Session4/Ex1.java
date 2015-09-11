package codeu.Session4;

public class Ex1 {
    public static void main(String[] args) {
        // ## Correctness Tests ##
        correctnessTest(0, 1000);
        correctnessTest(5000);
        correctnessTest(10000);
        correctnessTest(50000);

//        System.out.println(Palindrome.nthPalindrome(98302));

        // ## Comparing Performance Tests ##
        // Algorithm is significantly faster for larger inputs compared to naive version
        // In the below case, about 1000x faster
//        performanceTest(2000, Palindrome::naivePalindrome);
//        performanceTest(2000, Palindrome::nthPalindrome);

//         ## Large values performance test ##
//        double startTime = System.nanoTime();
//        System.out.println(Palindrome.nthPalindrome(Integer.MAX_VALUE));
//        double endTime = System.nanoTime();
//        double timeTaken = endTime - startTime;
//        System.out.println("TIME TAKEN: " + timeTaken);
    }

    static void correctnessTest(int i) {
        correctnessTest(i, i);
    }

    static void correctnessTest(int start, int end) {
        for (int i = start; i <= end; i++) {
            if (Palindrome.nthPalindrome(i) != Palindrome.naivePalindrome(i)) {
                System.out.println("FAILED at " + i);
                return;
            }
        }
        System.out.println("PASSED!");
    }


    static void performanceTest(int numItems, Tester test) {
        double startTime = System.nanoTime();

        for (int i = 0; i < numItems; i++) {
            test.algorithm(i);
        }

        double endTime = System.nanoTime();
        double timeTaken = endTime - startTime;
        System.out.println("TIME TAKEN: " + timeTaken);
    }

    interface Tester {
        Object algorithm(int i);
    }
}

class Palindrome {
    /**
     * Finds and returns the k'th positive integer whose binary representation is a palindrome, with leading zeros
     * ignored, except for the number 0. A palindrome is a sequence that is the same if it is reversed.
     *
     * Example:
     *      nthPalindrome(0) = 0  ('0'),
     *      nthPalindrome(1) = 1  ('1'),
     *      nthPalindrome(2) = 3 (‘11’),
     *      nthPalindrome(3) = 5 (‘101’),
     *      nthPalindrome(4) = 7 (‘111’)
     *
     * @param k The kth palindrome to find. k must be >= 0
     * @return The k'th integer whose binary representation is a palindrome
     */
    static long nthPalindrome(int k) {
        if (k < 0) {
            throw new IndexOutOfBoundsException("k must be >= 0 for 'nthPalindrome'");
        }

        if (k == 0 || k == 1) {
            return k;
        }

        // start at k = 1;
        long numCharacters = 1; // How many characters exists in the current binary string
        boolean evenNumOfChars = false;
        int numberOfPalindromesInCurrent = 1; // Number of palindromes the length `numCharacters`
        long ub = 1; // The index of the last palindrome with at most length `numCharacters`

        while (ub < k) { // Continue until we have gone past the index we are looking for
            //e.g. `numberOfPalindromesInCurrent` counts like so: 1, 2, 2, 4, 4, 8, 8, 16, 16, 32, 32 ...
            numCharacters++;
            evenNumOfChars = !evenNumOfChars;
            if (!evenNumOfChars) {
                numberOfPalindromesInCurrent *= 2;
            }
//            System.out.println(numCharacters + ": " + numberOfPalindromesInCurrent);
            ub += numberOfPalindromesInCurrent;
        }

        // calculate the index of the first word with length: `numberOfPalindromesInCurrent`
        long lb = ub - numberOfPalindromesInCurrent;
        // Calculate the difference between the lower-bound and the current index
        long diff = k - lb - 1;

        // The algorithm will now attempt to find the first half of the palindrome (i.e. prefix)
        long numberOfCharactersInPrefix = (long) Math.ceil(numCharacters / 2.0);

        // The dBinary is the binary representation of the prefix, excluding the first "1"
        String dBinary = Long.toBinaryString(diff);
        if (numberOfCharactersInPrefix == 1) {
            assert diff == 0;
            assert k == 2;
            dBinary = "";
        } else if (dBinary.length() != (numberOfCharactersInPrefix - 1)) {
            // Pads number with the correct number of 0's
            // Note: string builder is not required as this is done automatically in the compiler
            for (long i = dBinary.length(); i < numberOfCharactersInPrefix - 1; i++) {
                dBinary = "0" + dBinary;
            }
        }

        String prefix = "1" + dBinary; // All binary numbers must start with 1, except 0.

        String result;
        if (evenNumOfChars) {
            StringBuilder stringBuilder = new StringBuilder(prefix);
            result = prefix + stringBuilder.reverse().toString();
        } else {
            // If the palindrome has an odd number of characters, it uses the end digit of prefix as the middle of the palindrome
            String repeatedPart = prefix.substring(0, prefix.length() - 1);
            StringBuilder stringBuilder = new StringBuilder(repeatedPart);
            result = prefix + stringBuilder.reverse().toString();
        }

        return Long.parseLong(result, 2); // converts binary number back to base 10
    }

    static long naivePalindrome(int n) {
        if (n == 0) {
            return 0;
        }

        int numFound = 0;
        long currentNumber = 1;

        while (true) {
            String b = Long.toBinaryString(currentNumber);

            boolean isPalindrome = true;
            for (int i = 0; i <= Math.ceil(b.length() / 2.0) - 1; i++) {
                if (b.charAt(i) != b.charAt(b.length() - 1 - i)) {
                    isPalindrome = false;
                    break;
                }
            }

            if (isPalindrome) {
                numFound++;
                if (numFound == n) {
                    return currentNumber;
                }
            }

            currentNumber++;
        }

    }

}