package codeu.Session4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Ex5 {

    public static void main(String[] args) {
        // TODO: generate large dictionary & test with large file

        SearchDictionaryWords searchDictionaryWords = new SearchDictionaryWords(new Dictionary());
        System.out.println(Arrays.toString(searchDictionaryWords.breakIntoWords("abcdef")));
        System.out.println(Arrays.toString(searchDictionaryWords.breakIntoWords("aman")));
        System.out.println(Arrays.toString(searchDictionaryWords.breakIntoWords("aman")));
        System.out.println(Arrays.toString(searchDictionaryWords.breakIntoWords("iamamanandiamnotafraid")));

        System.out.println(Arrays.toString(searchDictionaryWords.breakIntoWords("traveledtowardthemountains")));
    }

}

class SearchDictionaryWords {

    Dictionary dictionary = new Dictionary();

    public SearchDictionaryWords(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public String[] breakIntoWords(String s) {
        HashMap<Integer, HashSet<Integer>> indexes = new HashMap<>();
//        System.out.println("s = " + s);

        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                String substring = s.substring(i, j);

                if (dictionary.exists(substring)) {
                    if (indexes.get(i) == null) {
                        indexes.put(i, new HashSet<>());
                    }

                    indexes.get(i).add(j);
                }
            }
        }


        ArrayDeque<String> result = splitWord(s, indexes, 0);
        if (result == null) {
            return new String[0];
        }

        Object[] resultArray = result.toArray();
        return Arrays.copyOf(resultArray, resultArray.length, String[].class);
    }


    ArrayDeque<String> splitWord(String s, HashMap<Integer, HashSet<Integer>>  indexes, int startIndex) {
        ArrayDeque<String> result = new ArrayDeque<>();

        if (startIndex == s.length()) {
            return result;
        }

        HashSet<Integer> endIndexes = indexes.get(startIndex);
        if (endIndexes == null) {
            // Cannot complete word
            return null;
        }

        for (int endIndex : endIndexes) {
            String currentWord = s.substring(startIndex, endIndex);

            ArrayDeque<String> nextWords = splitWord(s, indexes, endIndex);
            if (nextWords != null) {
                if (nextWords.isEmpty()) {
                    // last word -> no spaces required
                    result.add(currentWord);
                } else {
                    for (String nextWord : nextWords) {
                        result.add(currentWord + " " + nextWord); //TODO: string builder
                    }
                }
            }
        }

        return result;

    }
}

class Dictionary {
    // NOTE TO MARKER: a more efficient approach of storing a dictionary is using a trie tree.
    // Iterating through the trie tree efficiently for the above use case requires more careful handling.
    // I didn't use this approach as my computer was easily able to load the entire dictionary into a HashSet

    HashSet<String> dictionary = new HashSet<>();
    static final String UNIX_DICTIONARY_PATH = "/usr/share/dict/words";


    Dictionary() {
        this(UNIX_DICTIONARY_PATH);
    }

    Dictionary(String filePath) {
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                if (line.length() != 1 || line.equals("a") || line.equals("i")) {
                    dictionary.add(line);
                }

            }
        } catch (FileNotFoundException e) {
            System.err.println("Dictionary Not Found!");
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    Dictionary(String[] words) {
        // scan in input and add
        dictionary.addAll(Arrays.asList(words));
    }



    public boolean exists(String s) {
        return dictionary.contains(s);
    }

}


