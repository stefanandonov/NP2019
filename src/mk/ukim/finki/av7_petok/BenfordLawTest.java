package mk.ukim.finki.av7_petok;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class BenfordLawProcessor {

    List<Integer> firstDigits;
    double [] countingArray = {0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0};

    BenfordLawProcessor() {
        firstDigits = new ArrayList<>();
    }

    public void readData (InputStream is) {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));

        firstDigits = br.lines().map(line -> {
            String [] parts = line.split("\\s+");
            return Integer.parseInt(String.valueOf(parts[2].charAt(0)));
        }).collect(Collectors.toList());

        firstDigits.forEach(digit -> countingArray[digit]+=1.0);


    }

    public void printPercentage (OutputStream os) {
        PrintWriter pw = new PrintWriter(os);

        for (int i=1;i<10;i++) {
            countingArray[i]/=firstDigits.size();
            pw.println(String.format("%d\t%.2f%%", i, countingArray[i]*100));
        }


        pw.flush();
    }

    public void printVisualization (OutputStream os) {
        PrintWriter pw = new PrintWriter(os);
        for (int i=1;i<10;i++) {
            pw.println(String.format("%d|%s", i, printStars(countingArray[i]*100)));
        }

        pw.flush();
    }

    private String printStars(double value) {
        int roundedValue = (int) Math.round(value);

        return IntStream.range(0,roundedValue)
                .mapToObj(i -> "*")
                .collect(Collectors.joining());

    }

}

public class BenfordLawTest {

    public static void main(String[] args) throws FileNotFoundException {

        BenfordLawProcessor blp = new BenfordLawProcessor();

        blp.readData(new FileInputStream(
                new File("/home/stefan5andonov/work/" +
                        "NP_2019_petok/src/mk/ukim/finki/" +
                        "av7_petok/random_numbers.txt")));

        blp.printPercentage(System.out);

        blp.printVisualization(System.out);

    }
}
