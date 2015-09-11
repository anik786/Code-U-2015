package codeu.Session4;

public class Ex2 {

    public static void main(String[] args) {
        LongestPalindrome longestPalindrome = new LongestPalindrome();

        System.out.println(longestPalindrome.naiveLongestPalindrome("1"));
        System.out.println(longestPalindrome.naiveLongestPalindrome("11"));
        System.out.println(longestPalindrome.naiveLongestPalindrome("111"));
        System.out.println(longestPalindrome.naiveLongestPalindrome("1111"));
        System.out.println(longestPalindrome.naiveLongestPalindrome("11111"));
        System.out.println(longestPalindrome.naiveLongestPalindrome("011111"));
        System.out.println(longestPalindrome.naiveLongestPalindrome("0111110"));
        System.out.println(longestPalindrome.naiveLongestPalindrome("12111122221212121")); // 9, 7
        System.out.println(longestPalindrome.naiveLongestPalindrome("2111122221212121"));
        System.out.println(longestPalindrome.naiveLongestPalindrome("12111122221212121"));
        System.out.println(longestPalindrome.naiveLongestPalindrome("0201"));
        System.out.println(longestPalindrome.naiveLongestPalindrome("01123"));
        System.out.println(longestPalindrome.naiveLongestPalindrome("0123"));

        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 10_000; i++) {
            stringBuilder.append("1");
        }
        String b = stringBuilder.toString();

        System.out.println(longestPalindrome.naiveLongestPalindrome(b));

    }

}

class LongestPalindrome {
    class PalindromeProperties {
        int startIndex;
        int length;
//        boolean isDirect; //DEBUG
//        int middleIndex; //DEBUG

        public PalindromeProperties(int startIndex, int length) {
            this.startIndex = startIndex;
            this.length = length;
        }

        @Override
        public String toString() {
            return "Start Index: " + startIndex + " | Length:" + length;
        }

//        DEBUG
//        public PalindromeProperties(int startIndex, int length, int middleIndex, boolean isDirect) {
//            this.startIndex = startIndex;
//            this.length = length;
//            this.middleIndex = middleIndex;
//            this.isDirect = isDirect;
//        }

//        DEBUG
//        @Override
//        public String toString() {
//            return "Start Index: " + startIndex + " | Length:" + length + "| Middle Index: " + middleIndex
//                    + "| Is Direct: " + isDirect;
//        }

    }


    public PalindromeProperties naiveLongestPalindrome(String b) {
        /*
        TODO: potential improvement - start searching palindromes from the middle outwards, so can break early if it is known
        that there is no better solution
        */

        if (b.length() == 0) {
            return new PalindromeProperties(-1, 0);
        }

        int longestPalindromeLength = 0;
        int longestPalindromeStartIndex = 0;
//        int longestPalindromeMiddleIndex = 0; // DEBUG
//        boolean longestIsDirect = true; //DEBUG

        boolean directMiddle = true; // means middle number is also the middle of the palindrome

        int middleIndexOfPalindrome = 0; // Middle index of the palindrome. If between 2 numbers, takes smaller index.


        while(middleIndexOfPalindrome < b.length()) {

//            boolean isPalindrome = true;
            int currentPalindromeLength = 0;
            int maxOffset;

            if (directMiddle) {
                maxOffset = Math.min(middleIndexOfPalindrome, b.length() - middleIndexOfPalindrome - 1);
                for (int i = 0; i <= maxOffset; i++) {
                    int leftIndex = middleIndexOfPalindrome - i;
                    int rightIndex = middleIndexOfPalindrome + i;

                    if (b.charAt(leftIndex) != b.charAt(rightIndex)) {
                        break;
                    }

                    if (leftIndex == rightIndex) {
                        currentPalindromeLength++;
                    } else {
                        currentPalindromeLength += 2;
                    }

                }
            } else {
                maxOffset = Math.min(middleIndexOfPalindrome, b.length() - middleIndexOfPalindrome - 2);

                for (int i = 0; i <= maxOffset; i++) {
                    int leftIndex = middleIndexOfPalindrome - i;
                    int rightIndex = middleIndexOfPalindrome + i + 1;

                    if (b.charAt(leftIndex) != b.charAt(rightIndex)) {
                        break;
                    }

                    currentPalindromeLength += 2;
                }
            }

            if (currentPalindromeLength > longestPalindromeLength) {
                longestPalindromeLength = currentPalindromeLength;
//                longestPalindromeMiddleIndex = middleIndexOfPalindrome; // DEBUG
//                longestIsDirect = directMiddle; // DEBUG

                if (directMiddle) {
                    longestPalindromeStartIndex = middleIndexOfPalindrome - ((currentPalindromeLength - 1) / 2);
                } else {
                    longestPalindromeStartIndex = middleIndexOfPalindrome + 1 - ((currentPalindromeLength) / 2);
                }
            }

            if (!directMiddle) {
                middleIndexOfPalindrome++;
            }

            directMiddle = !directMiddle;
        }


        return new PalindromeProperties(longestPalindromeStartIndex, longestPalindromeLength);
//        return new PalindromeProperties(longestPalindromeStartIndex, longestPalindromeLength,
//                longestPalindromeMiddleIndex, longestIsDirect); //DEBUG

    }
}