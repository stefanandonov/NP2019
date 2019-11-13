package mk.ukim.finki.konsultacii;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.*;

class Racer implements Comparable<Racer>{
    String name;
    String[] laps;
    String bestLap;

    public Racer() {
        name = "";
        laps = new String[3];
    }

    public Racer(String s) {
        laps = new String[3];
        String[] inputs = s.split("\\s+");

        name = inputs[0];

        for (int i = 0; i < 3; i++) {
            laps[i] = inputs[i + 1];
        }

        findBestLap();
    }

    public Racer(Racer r) {
        this.name = r.name;

        this.laps = new String[3];

        for(int i = 0; i < laps.length; i++){
            this.laps[i] = r.laps[i];
        }

        this.bestLap = r.bestLap;
    }

    public void findBestLap(){

        bestLap = Arrays.stream(laps).min(Comparator.naturalOrder()).get();
//        bestLap = laps[0];
//        if (laps[1].compareTo(laps[0])>0) {
//            bestLap = laps[1];
//        }
//
//        if (laps[2].compareTo(laps[0])>0) {
//            bestLap = laps[2];
//        }
//        String bestLap = findFasterLap(laps[0], laps[1]);
//
//        bestLap = findFasterLap(bestLap, laps[2]);

//        return bestLap;
    }

//    public String findFasterLap(String l1, String l2){
//        String[] input1 = l1.split(":");
//        String[] input2 = l2.split(":");
//
//        if(Integer.parseInt(input1[0]) < Integer.parseInt(input2[0])){
//            return l1;
//        }
//        else if(Integer.parseInt(input1[0]) > Integer.parseInt(input2[0])){
//            return l2;
//        }
//
//        if(Integer.parseInt(input1[1]) < Integer.parseInt(input2[1])){
//            return l1;
//        }
//        else if(Integer.parseInt(input1[1]) > Integer.parseInt(input2[1])){
//            return l2;
//        }
//
//        if(Integer.parseInt(input1[2]) < Integer.parseInt(input2[2])){
//            return l1;
//        }
//        else if(Integer.parseInt(input1[2]) > Integer.parseInt(input2[2])) {
//            return l2;
//        }
//
//        return "";
//    }

    @Override
    public String toString() {
        return String.format("%-10s%10s", name, bestLap);
    }

    @Override
    public int compareTo(Racer racer) {
        return this.bestLap.compareTo(racer.bestLap);
    }
}

class F1Race {
    List<Racer> racers;

    public F1Race() {
        racers = new ArrayList<>();
    }

    void readResults(InputStream inputStream){
        Scanner sc = new Scanner(inputStream);
        while(sc.hasNextLine()) {
            String input = sc.nextLine();
            Racer racer = new Racer(input);

            racers.add(racer);
        }
    }

    public void printSorted(PrintStream out) {
//        for(int i = 0; i < racers.size()-1; i++){
//            for(int j = i+1; j < racers.size(); j++){
//                String currentLap = racers.get(i).findFasterLap(racers.get(i).bestLap, racers.get(j).bestLap);
//                if(
//                        currentLap.equals(racers.get(j).bestLap)
//                ){
//                    Racer temp = new Racer(racers.remove(i));
//                    racers.add(i, racers.remove(j-1));
//                    racers.add(j-1, temp);
//                }
//
//            }
//        }

        racers.sort(null);

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < racers.size(); i++) {
            sb.append(String.format("%d. %s\n",
                    i+1,
                    racers.get(i))
            );
        }

        out.print(sb.toString());
    }
}

public class F1Test {

    public static void main(String[] args) {
        F1Race f1Race = new F1Race();
        f1Race.readResults(System.in);
        f1Race.printSorted(System.out);
    }

}
