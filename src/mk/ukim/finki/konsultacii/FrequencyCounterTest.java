package mk.ukim.finki.konsultacii;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.Map.Entry;

public class FrequencyCounterTest {
    public static void main(String[] args) {
        CharacterFrequency cf = new CharacterFrequency();
        Map<Character, Integer> counts = cf.count(System.in);
        System.out.println(counts.size());
        System.out.println("BY CHARACHTER");
        cf.printSortedByCharacter();
        System.out.println("BY COUNT");
        cf.printSortedByCount();

    }
}

interface ICount {
    Map<Character, Integer> count(InputStream inputStream);

    void printSortedByCount();

    void printSortedByCharacter();
}

class CharacterFrequency implements ICount {

    TreeMap<Character, Integer> countingMap = new TreeMap<>();

    @Override
    public Map<Character, Integer> count(InputStream inputStream) {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));

        br.lines()
                .flatMap(line -> line.chars()
                        .mapToObj(i -> (char)(i)))
                .forEach(c -> {
                    countingMap.putIfAbsent(c, 0);
                    Integer count = countingMap.get(c);
                    count++;
                    countingMap.put(c,count); //iskomentiraj go na kraj;
                });


        return countingMap;
    }

    @Override
    public void printSortedByCount() {
        Comparator<Map.Entry<Character, Integer>> entryComparator = Comparator.comparingInt(Map.Entry::getValue);
        countingMap.entrySet().stream()
                .sorted(entryComparator.reversed())
                .map(entry -> String.format("%d : %c", entry.getValue(), entry.getKey()))
                .forEach(System.out::println);
    }

    @Override
    public void printSortedByCharacter() {
        countingMap.entrySet()
                .stream()
                .map(entry -> String.format("%c : %d", entry.getKey(), entry.getValue()))
                .forEach(System.out::println);
    }
}

// vashiot kod ovde