package codeu.Session3;

import java.sql.Timestamp;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class Ex4 {
    public static void main(String[] args) {
        QueryStream queryStream = new QueryStream();
        queryStream.add("hello hi bye", new Timestamp(1440948896));
        queryStream.add("My name is yahya uddin!", new Timestamp(1440948900));
        queryStream.add("Whats up!!!", new Timestamp(1440949640));
        queryStream.add("hey you!!!", new Timestamp(1450949641));

        for (String s : queryStream) {
            System.out.println(s);
        }


    }
}

class QueryStream implements Iterable<String>{
    private ArrayList<Query> queries = new ArrayList<>();

    public void add(String word, Timestamp timestamp) {
        Query query = new Query();
        query.words = word;
        query.timestamp = timestamp;
        queries.add(query);
    }

    @Override
    public Iterator<String> iterator() {
        return new Iterator<String>() {
            private int index = 0;
            ArrayDeque<String> currentWords = new ArrayDeque<>();
            Timestamp lastQueryTimestamp = null;

            @Override
            public boolean hasNext() {
                return index < queries.size() || !currentWords.isEmpty();
            }

            @Override
            public String next() {
                if (currentWords.isEmpty()) {
                    Query newQuery = queries.get(index);
                    index++;

                    currentWords.addAll(Arrays.asList(newQuery.words.split("\\s")));

                    long timeDifference;
                    if (lastQueryTimestamp == null) {
                        timeDifference = 0;
                    } else {
                        timeDifference = newQuery.timestamp.getTime()- lastQueryTimestamp.getTime();
                    }

                    lastQueryTimestamp = newQuery.timestamp;

                    return "<" + timeDifference + "ms since last query>";
                }
                return currentWords.removeFirst();
            }
        };
    }


    class Query {
        Timestamp timestamp;
        String words; // would be better if this was an array list
    }
}