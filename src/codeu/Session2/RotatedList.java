package codeu.Session2;

import java.util.*;

public class RotatedList {

    public static boolean isRotation(List<Integer> list1, List<Integer> list2) {
        if (list1.size() != list2.size()) {
            return false;
        }

        // --- Create Prefix Table ---
        Iterator<Integer> itr1 = list1.iterator();
        Iterator<Integer> itr2 = list2.iterator();

        int[] prefixIndexes = new int[list1.size()];
        Integer[] prefixCharacters = new Integer[list1.size()];
        prefixIndexes[0] = -1;
        prefixIndexes[1] = 0;
        prefixCharacters[0] = list1.get(0);
        prefixCharacters[1] = list1.get(0);

        int pos = 2;
        int candidatePos = 0;
        Integer prev = list1.get(1);
        Integer candidate = list1.get(2);

        while (pos < list1.size()) {
//            if (list1.get(pos - 1).equals(list1.get(substringPos))) {
            if (prev.equals(candidate)) {
                // If the substring continues
                candidatePos++;
                prefixIndexes[pos] = candidatePos;
                prefixCharacters[pos] = prefixCharacters[candidatePos];
                pos++;
                prev = candidate;
                candidate = itr1.next();
            } else if (candidatePos > 0) {
                // If it doesn't, fall back if possible
                candidatePos = prefixIndexes[candidatePos];
            } else {
                // No more potential substrings
                prefixIndexes[pos] = 0;
                pos++;
            }
        }
        itr1 = list1.iterator(); //reset iterator


//        while (pos < list1.size()) {
//            if (list1.get(pos - 1).equals(list1.get(substringPos))) {
//                // If the substring continues
//                substringPos++;
//                prefixIndexes[pos] = substringPos;
//                pos++;
//            } else if (substringPos > 0) {
//                // If it doesn't, fall back if possible
//                substringPos = prefixIndexes[substringPos];
//            } else {
//                // No more potential shoestrings
//                prefixIndexes[pos] = 0;
//                pos++;
//            }
//        }

//        -- Perform KMP Search---
        int listSize = list1.size();
        int matchStartIndex = 0;
        int i = 0;

        List<Integer> list2b = new ArrayList<>(list2);
        list2b.addAll(list2); // add all elements twice
        Iterator<Integer> itr2b = list2b.iterator();

        Integer i1;
        Integer i2 = itr2b.next();
//        list2b.get(j)

        int j = 0;
        while (j < list2b.size()) {
            if (i2.equals(list1.get(i))) {
                if (i == list1.size() - 1) {
                    return true;
                } else {
                    i++;
                    j++;
                    if (itr2b.hasNext()) {
                        i2 = itr2b.next();
                    }
                }
            } else {
                if (prefixIndexes[i] > -1) {
                    i = prefixIndexes[i]; //TODO: store letter so .get() method is not need
                } else {
                    i = 0;
                    j++;
                    if (itr2b.hasNext()) {
                        i2 = itr2b.next();
                    }
                }
            }
        }


//        while (matchStartIndex + i < list2b.size()) { //TODO: remove 2nd list
//
//        }
//
//
//        while (matchStartIndex + i < list2b.size()) { //TODO: remove 2nd list
//            if (list2b.get(matchStartIndex + i).equals(list1.get(i))) {
//                if (i == list1.size() - 1) {
//                    return true;
//                } else {
//                    i++;
//                }
//            } else {
//                if (prefixIndexes[i] > -1) {
//                    matchStartIndex += i - prefixIndexes[i];
//                    i = prefixIndexes[i]; //TODO: store letter
//                } else {
//                    i = 0;
//                    matchStartIndex++;
//                }
//            }
//        }

//        mainLoop:
//        for (int startIndex = 0; startIndex < list1.size(); startIndex++) {
//            int i = startIndex;
//            for (Integer i2: list2) {
////                if (!itr1.hasNext()) {
////                    itr1 = list1.iterator(); //Reinitialise iterator
////                    itr1.
////                }
//                if (!list1.get(i).equals(i2)) {
//                    continue mainLoop;
//                }
//
//                i = (i+1) % list1.size();
//            }
//            return true;
//        }

//        Iterator<Integer> itr = list1.iterator();
//        while (itr.hasNext()) {
//            itr = list1.iterator(); // re-init iterator
//        }

        return false;
    }

    public static class Test_IsRotation {
        // Test framework for isRotation()
        // Instead of calling isRotation directly, call expectFailure or expectSuccess
        // with the desired input arguments.  Call init() to start collecting results
        // data for a series of tests, and call report() to print a short report on the
        // outcomes so far.
        private static int failCount;
        private static int successCount;

        public static void init() {
            failCount = 0;
            successCount = 0;
        }

        public static void expectFailure(List<Integer> a, List<Integer> b) {
            // STUDENT: PROVIDE IMPLEMENTATION
            if (!isRotation(a, b)) {
                successCount++;
            } else {
                failCount++;
            }
        }

        public static void expectSuccess(List<Integer> a, List<Integer> b) {
            // STUDENT: PROVIDE IMPLEMENTATION
            if (isRotation(a, b)) {
                successCount++;
            } else {
                failCount++;
            }
        }

        private static void report(List<Integer> a, List<Integer> b, boolean got) {
            // Helper routine that may be used by expectFailure and expectSuccess.
            // It should be called only if the unexpected occurs. In other words,
            // if all tests work as expected, then the program will be silent.

            // STUDENT: PROVIDE IMPLEMENTATION
        }

        public static void reportSummary() {
            if (successCount == 0) {
                System.out.println("Summary: all " + failCount +
                        " tests FAILED!");
            } else if (failCount == 0) {
                System.out.println("Summary: all " + successCount +
                        " tests SUCCEEDED!");
            } else {
                System.out.println("Summary: " + successCount +
                        " tests succeeded, " + failCount +
                        " tests failed.");
            }
        }
    }

    public static void main(String[] args) {

        Test_IsRotation.init();

        List<Integer> list1, list2;
        list1 = new LinkedList<Integer>(Arrays.asList(1,2,3,4));
        list2 = new LinkedList<Integer>(Arrays.asList(3,4,1,2));
        Test_IsRotation.expectSuccess(list1, list2);
        list1 = new LinkedList<Integer>(Arrays.asList(1,2,3,4));
        list2 = new LinkedList<Integer>(Arrays.asList(3,4,2,1));
        Test_IsRotation.expectFailure(list1, list2);

        // STUDENT: ADD TEST CASES HERE

        // Check performance
//        int N = 10000;
        int N = 100000;
        Integer[] a1 = new Integer[N];
        Integer[] a2 = new Integer[N];
        for (int i = 0; i < N; ++i) {
            a1[i] = 0;
            a2[i] = 0;
        }
        // (STUDENT: WHY THIS CASE?)
        a1[0] = 1;
        a2[N/2] = 1;
        list1 = new LinkedList<Integer>(Arrays.asList(a1));
        list2 = new LinkedList<Integer>(Arrays.asList(a2));
        long start_time, end_time;
        start_time = System.nanoTime();
        Test_IsRotation.expectSuccess(list1, list2);
        end_time = System.nanoTime();
        System.out.println("10000 elements: " +
                (end_time - start_time) + " nsec");

        // STUDENT: DESIGN SOME BETTER TESTS FOR EVALUATING PERFORMANCE

        Test_IsRotation.reportSummary();
    }
}

