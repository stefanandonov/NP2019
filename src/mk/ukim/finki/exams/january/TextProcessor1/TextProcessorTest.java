package mk.ukim.finki.exams.january.TextProcessor1;

import java.io.*;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

class TextProcessor {
    List<String> longestWords;

    TextProcessor() {
        longestWords = new ArrayList<>();
    }

    public void readText(InputStream is) {
        longestWords = new BufferedReader(new InputStreamReader(is)).lines()
                .map(line -> line.replaceAll("[^A-Za-z\\s+]", ""))
                .map(line -> Arrays.stream(line.split("\\s+"))
                        .max(Comparator.comparingInt(String::length).thenComparing(Function.identity())).orElse("none"))
                .collect(Collectors.toList());
    }

    void printProcessedText(OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        longestWords.stream().forEach(pw::println);
        pw.flush();
    }

}

public class TextProcessorTest {

    public static void main(String[] args) {
        TextProcessor textProcessor = new TextProcessor();

        textProcessor.readText(System.in);

        textProcessor.printProcessedText(System.out);


    }
}
