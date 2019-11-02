package mk.ukim.finki.np.av2;

import java.util.stream.IntStream;

public class StringPrefixTest {

    private static boolean isPrefix(String str1, String str2) {

        int str1Length = str1.length();
        int str2Length = str2.length();

        if (str1Length > str2Length)
            return false;

        for (int i = 0; i < str1Length; i++) {
            if (str1.charAt(i) != str2.charAt(i))
                return false;
        }

        return true;
    }

    private static boolean isPrefixWithStreams(String str1, String str2) {

        int str1Length = str1.length();
        int str2Length = str2.length();

        if (str1Length > str2Length)
            return false;


        long matchedCharacters = IntStream.range(0, str1Length)
                .filter(i -> str1.charAt(i) == str2.charAt(i))
                .count();

        return str1Length == matchedCharacters;

    }

    private static boolean isPrefixWithAllMatchStreams(String str1, String str2) {
        int str1Length = str1.length();
        int str2Length = str2.length();

        if (str1Length > str2Length)
            return false;


        return IntStream.range(0, str1Length)
                .allMatch(i -> str1.charAt(i) == str2.charAt(i));
    }

    private static boolean isPrefixOneLine(String str1, String str2) {
        return str1.startsWith(str2);
    }

    public static void main(String[] args) {
        String str1 = "Nap";
        String str2 = "Napredno";

        System.out.println(isPrefix(str1, str2));
        System.out.println(isPrefixWithStreams(str1, str2));
        System.out.println(isPrefixWithAllMatchStreams(str1,str2));
    }
}
