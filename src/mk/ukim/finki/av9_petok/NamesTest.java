package mk.ukim.finki.av9_petok;

import java.io.*;
import java.nio.Buffer;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

enum SEX {
    MALE,
    FEMALE
}

class Name {
    String name;
    Integer maleCount;
    Integer femaleCount;

    public Name(String name) {
        this.name = name;
    }

    public void setMaleCount(int maleCount) {
        this.maleCount = maleCount;
    }

    public void setFemaleCount(int femaleCount) {
        this.femaleCount = femaleCount;
    }

    public int getTotalCount () {
        return maleCount + femaleCount;
    }

    public String toString() {
        return String.format("%s, Total count: %d, %.2f%% male names, %.2f%% female names",
                name,
                getTotalCount(),
                (float) maleCount/(maleCount+femaleCount)*100.0,
                (float)femaleCount/(maleCount+femaleCount)*100.0);
    }
}

class Names {

    Map<String, Name> nameMap;

    Names() {
        nameMap = new HashMap<>();
    }

    public void readNames (String fileLocation, SEX sex) throws FileNotFoundException {
        File file = new File(fileLocation);
        BufferedReader br = new BufferedReader(new FileReader(file));

        br.lines().forEach(line -> {
            String [] parts = line.split("\\s+");
            String name = parts[0];
            int frequency = Integer.parseInt(parts[1]);

            nameMap.putIfAbsent(name, new Name(name));
            if (sex.equals(SEX.MALE))
                nameMap.get(name).setMaleCount(frequency);
            else
                nameMap.get(name).setFemaleCount(frequency);
        });
    }

    public void printDuplicates (OutputStream os) {
        PrintWriter pw = new PrintWriter(os);

        nameMap.values().stream()
                .filter(name -> name.femaleCount!=null && name.maleCount!=null)
                .sorted(Comparator.comparingInt(Name::getTotalCount).reversed())
                .forEach(pw::println);

        pw.flush();
    }
}

public class NamesTest {

    public static void main(String[] args) {
        Names names = new Names();

        try {
            names.readNames("/home/stefan5andonov/work/NP_2019_petok/src/mk/ukim/finki/av9_petok/boynames.txt",
                    SEX.MALE);
            names.readNames("/home/stefan5andonov/work/NP_2019_petok/src/mk/ukim/finki/av9_petok/girlnames.txt",
                    SEX.FEMALE);

            names.printDuplicates(System.out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

    }
}
