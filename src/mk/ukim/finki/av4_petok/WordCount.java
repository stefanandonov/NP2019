package mk.ukim.finki.av4_petok;

import java.io.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

class WordsCounter implements Consumer<String> {

    int characters=0, words=0, lines=0;

    @Override
    public void accept(String newLine) {
        lines++;
        characters+=newLine.length();
        words+=newLine.split("\\s+").length;
    }

    @Override
    public String toString() {
        return String.format("Characters: %d ; Words: %d ; Lines: %d\n",
                characters,
                words,
                lines);
    }
}


public class WordCount {

    public static String processFiles (List<String> fileLocations) throws FileNotFoundException {
        /*
        Output:
        fileLocation: chars: %d word: %d line: %d
        * */
        StringBuilder sb = new StringBuilder();

        for (String fileLocation : fileLocations) {
            int characters=0, words=0, lines=0;
            Scanner sc = new Scanner(new FileInputStream(fileLocation));
            while (sc.hasNextLine()) {
                String line = sc.nextLine();
                lines++;
                words += line.split("\\s+").length;
                characters += line.length();
            }
            sc.close();
            sb.append(String.format("File location: %s ; Characters: %d ; Words: %d ; Lines: %d\n",
                    fileLocation,
                    characters,
                    words,
                    lines));
        }

        return sb.toString();
    }

    public static String processFileWithBufferedReader (List<String> fileLocations) throws FileNotFoundException {
        StringBuilder sb = new StringBuilder();
        for (String fileLocation : fileLocations) {
            WordsCounter wordsCounterForFile = new WordsCounter();
            BufferedReader br = new BufferedReader(new FileReader(new File(fileLocation)));
            br.lines().forEach(wordsCounterForFile);
            sb.append("File location: ").append(fileLocation).append(" ").append(wordsCounterForFile.toString());
        }
        return sb.toString();
    }

    public static String processFilesWithStream (List<String> fileLocations) throws FileNotFoundException  {

        return fileLocations.stream().map(fileLocation -> {
            WordsCounter wordsCounter = new WordsCounter();
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(new File(fileLocation)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            br.lines().forEach(wordsCounter);
            return String.format("File location %s %s", fileLocation, wordsCounter.toString());
        }).collect(Collectors.joining("\n"));

    }

    public static void main(String[] args) {

        List<String> fileLocations = new ArrayList<>();
        fileLocations.add("/home/stefan5andonov/work/NP_2019_petok/src/mk/ukim/finki/av4_petok/test1.txt");
        fileLocations.add("/home/stefan5andonov/work/NP_2019_petok/src/mk/ukim/finki/av4_petok/test2.txt");

        try {
            System.out.println(processFiles(fileLocations));
            System.out.println(processFileWithBufferedReader(fileLocations));
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }


    }
}
