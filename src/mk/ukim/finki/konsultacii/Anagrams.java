package mk.ukim.finki.konsultacii;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

public class Anagrams {

    public static void main(String[] args) {
        findAll(System.in);
    }

    private static boolean isAnagram (String word1, String word2) {
        char [] chars1 = word1.toCharArray();
        Arrays.sort(chars1);
        String word1Sorted = String.valueOf(chars1);

        char [] chars2 = word2.toCharArray();
        Arrays.sort(chars2);
        String word2Sorted = String.valueOf(chars2);

        return word1Sorted.equals(word2Sorted);
    }

    public static void findAll(InputStream inputStream) {
        Scanner sc = new Scanner (inputStream);
        List<String> allWords = new ArrayList<>();
        Map<String, Set<String>> anagramsMap = new TreeMap<>();
        while (sc.hasNextLine()) {
            String word = sc.nextLine();
            allWords.add(word);
            anagramsMap.putIfAbsent(word, new TreeSet<>());
        }

        for (String word : allWords) {

            for (String word1 : allWords) {

                if (isAnagram(word,word1)) {
                    anagramsMap.computeIfPresent(word, (k,v) -> {
                        v.add(word1);
                        return v;
                    });
                }
            }
        }

        anagramsMap.values().stream()
                .filter(set -> set.size()>=5)
                .map(set -> String.join(" ", set))
                .forEach(System.out::println);


    }
}
