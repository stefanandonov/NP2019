package mk.ukim.finki.av1;

import java.util.stream.IntStream;

public class StringPrefixTest {

    static boolean isPrefix(String str1, String str2) {

        if (str1.length() > str2.length())
            return false;

        for (int i = 0; i < str1.length(); i++) {
            if (Character.toLowerCase(str1.charAt(i)) != Character.toLowerCase(str2.charAt(i))) {
                return false;
            }
        }

        return true;

    }

    static boolean isPrefixWithStreams(String str1, String str2) {

        if (str1.length() > str2.length())
            return false;

        return IntStream.range(0, str1.length())
                .allMatch(i -> str1.charAt(i) == str2.charAt(i));
    }

    public static void main(String[] args) {

        String nap = "NAP";
        String napredno = "NA PR ED NO";

        System.out.println(napredno.startsWith(nap));



        System.out.println(isPrefix(nap, napredno));
        System.out.println(isPrefixWithStreams(nap, napredno));

    }
}
