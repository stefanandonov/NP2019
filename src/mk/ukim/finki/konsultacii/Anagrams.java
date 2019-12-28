package mk.ukim.finki.konsultacii;

import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Anagrams {

    public static void main(String[] args) {
        findAll(System.in);
    }

    public static void findAll(InputStream inputStream) {
        Scanner scanner = new Scanner(inputStream);
        ArrayList<String> inputWords = new ArrayList<>();
        TreeMap<String, List<Integer>> anagramMap = new TreeMap<>();

        while(scanner.hasNextLine()) {
            inputWords.add(scanner.nextLine());
        }


        List<String> keyList = inputWords.stream()
                .map(s -> Stream.of(s.split(""))
                        .sorted()
                        .collect(Collectors.joining()))
                .collect(Collectors.toList());

        for(int i=0;i<keyList.size();i++){
            anagramMap.putIfAbsent(keyList.get(i), new ArrayList<>());
            anagramMap.get(keyList.get(i)).add(i);
        }

        for(Map.Entry<String,List<Integer>> entry : anagramMap.entrySet()){
            entry.getValue().stream().forEach(index -> System.out.print(inputWords.get(index) + " "));
            System.out.println();
        }

        scanner.close();
    }
}