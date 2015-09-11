package codeu.Session3;

import java.util.HashMap;

public class Ex1 {
    /* Assumptions on Dictionary
        - 0 based index
        - Connective indexes (e.g. words in 0 - 50, NOT 0, 5, 24...)
        - Size is less than or equal to: 2^31 - 1
        - No elements added or removed during runtime
        - Is in dictionary order
     */

    static HashMap<String, Boolean> lookup = new HashMap<>();
    static int size;

    static {
        // Calculate size of dictionary
        size = 1 + binarySearch(0, Integer.MAX_VALUE - 1, index -> {
            if (TrivialDictionary.wordAt(index) == null) {
                return Tester.SearchResult.TOO_LARGE;
            } else if (TrivialDictionary.wordAt(index + 1) != null) {
                return Tester.SearchResult.TOO_SMALL;
            } else {
                return Tester.SearchResult.FOUND;
            }
        });
    }

    // ? is there a java built in implementation of this?
    private static int binarySearch(int lowerBound, int upperBound, Tester tester) {
        Tester.SearchResult searchResult;

        while(upperBound > lowerBound + 1) {
            int i = (int) (((long) upperBound + (long) lowerBound) / (long) 2);
            searchResult = tester.test(i);

            switch (searchResult) {
                case TOO_LARGE:
                    upperBound = i;
                    break;
                case TOO_SMALL:
                    lowerBound = i;
                    break;
                case FOUND:
                    return i;
            }
        }

        // Potentially adjacent numbers
        searchResult = tester.test(lowerBound);
        if (searchResult == Tester.SearchResult.FOUND) {
            return lowerBound;
        }

        searchResult = tester.test(upperBound);
        if (searchResult == Tester.SearchResult.FOUND) {
            return upperBound;
        }

        return -1;
    }

    private interface Tester{
        enum SearchResult {
            TOO_SMALL, TOO_LARGE, FOUND
        }
        SearchResult test(int index);
    }

    public static void main(String[] args) {
        System.out.println(size);
        System.out.println(isInDictionary("hi"));
        System.out.println(isInDictionary("bye"));
        System.out.println(isInDictionary("abc"));
        System.out.println(isInDictionary("aba"));
        System.out.println(isInDictionary("cc"));
        System.out.println(isInDictionary("cc"));
    }

    static boolean isInDictionary(String word) {
        int i;

        if (lookup.containsKey(word)) {
            return lookup.get(word);
        }

        /*
        NOTE: Further enhancements can be made by storing the index of other words in the lookup table and using it
        to give a better upper and lower bound of where the current word might be.
        */

        i = binarySearch(0, size, index -> {
            String s = TrivialDictionary.wordAt(index);
            if (s == null) {
                return Tester.SearchResult.TOO_SMALL;
            }
            int comparison = s.compareTo(word);
            if (comparison < 0) {
                return Tester.SearchResult.TOO_SMALL;
            } else if (comparison > 0) {
                return Tester.SearchResult.TOO_LARGE;
            } else {
                return Tester.SearchResult.FOUND;
            }
        });

        boolean isFound = i != -1;
        lookup.put(word, isFound);

        return isFound;
    }
}

class TrivialDictionary {
    private static String words[] = {
            "aaa",
            "aab",
            "aac",
            "aad",
            "aae",
            "aba",
            "abb",
            "cc",
            "cca",
            "ccb",
            "ccc",
    };

    static String wordAt(int index) {
        if (index >= words.length) {
            return null;
        } else {
            return words[index];
        }
    }
}



