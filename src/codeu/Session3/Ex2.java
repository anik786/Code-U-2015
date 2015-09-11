package codeu.Session3;

import java.util.HashMap;

public class Ex2 {

    static Integer hasMajority(int[] array) {
        // Average-Complexity: O(n)
        HashMap<Integer, Integer> counter = new HashMap<>();
        int target = array.length / 2;
        for (int i: array) {
            int newValue = 1;
            Integer current = counter.get(i);
            if (current != null) {
                newValue = current + 1;
            }
            counter.put(i, newValue);
            if (newValue > target) {
                return i;
            }
        }
        return null;
    }

    public static void main(String[] args) {
        System.out.println(hasMajority(new int[] {5, 10, 3, 3, 3, 4, 3})); //3
        System.out.println(hasMajority(new int[] {3, 3, 3, 8, 3, 5})); //3
        System.out.println(hasMajority(new int[] {3, 3, 7, 8, 3, 5})); //null
        System.out.println(hasMajority(new int[] {3, 3, 3, 3, 5, 5})); //3
        System.out.println(hasMajority(new int[] {3, 3, 3, 5, 5, 5})); //null
        System.out.println(hasMajority(new int[] {3, 3, 5, 5, 5, 5})); //5
        System.out.println(hasMajority(new int[] {})); //null
    }


}
