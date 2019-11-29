package mk.ukim.finki.av7_petok;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class ArrangeLetters {

    private static String transformWord (String word) {
        return IntStream.range(0, word.length())
                .mapToObj(word::charAt)
                .sorted()
                .map(String::valueOf)
                .collect(Collectors.joining());
    }

    public static String arrangeLetters (String input) {

        String [] words = input.split("\\s+");

        return Arrays.stream(words)
                .map(ArrangeLetters::transformWord)
                .sorted()
                .collect(Collectors.joining(" "));
    }
}

public class ArrangeLettersTest {

    public static void main(String[] args) {
        String testString = "kO pSk sO";

        System.out.println(ArrangeLetters.arrangeLetters(testString));
    }
}
