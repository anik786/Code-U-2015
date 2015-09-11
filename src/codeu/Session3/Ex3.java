package codeu.Session3;

import java.util.Arrays;
import java.util.Collections;
import java.util.TreeSet;

public class Ex3 {
    public static void main(String[] args) {
        Largest largest = new Largest(new Integer[]{5, 9, 8, 7, 7, 7, 5, 2, 4});
        System.out.println(largest.nthLargest(1));
        System.out.println(largest.nthLargest(2));
        System.out.println(largest.nthLargest(3));
        System.out.println(largest.nthLargest(4));
        System.out.println(largest.nthLargest(5));
        System.out.println(largest.nthLargest(6));
//        System.out.println(largest.nthLargest(7));
//        System.out.println(largest.nthLargest(0));
    }
}

class Largest {
    Integer[] values;

    Largest(Integer[] array) {
        // Complexity: O(nlogn)
        TreeSet<Integer> set = new TreeSet<>(Collections.reverseOrder());
        set.addAll(Arrays.asList(array));
        this.values = set.toArray(new Integer[set.size()]);
    }

    int nthLargest(int n) {
//        Complexity: O(1)
        if (n == 0 || n > values.length) {
            throw new IndexOutOfBoundsException("Invalid index: " + n + ", for nth largest number!");
        }
        return this.values[n - 1];
    }

}
