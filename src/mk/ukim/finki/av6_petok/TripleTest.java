package mk.ukim.finki.av6_petok;

import java.util.*;
import java.util.stream.Collectors;

class Triple<T extends Number> {
    List<T> elements;


    public Triple(T a, T b, T c) {
        elements = new ArrayList<>();
        elements.add(a);
        elements.add(b);
        elements.add(c);
    }

    public double max() {
        return elements.stream()
                .mapToDouble(Number::doubleValue)
                .max()
                .getAsDouble();
    }

    public double average() {
        return elements.stream()
                .mapToDouble(Number::doubleValue)
                .average()
                .getAsDouble();
    }

    public void sort() {
        elements.sort(Comparator.comparingDouble(Number::doubleValue));

//        elements.sort(new Comparator<T>() {
//            @Override
//            public int compare(T t, T t1) {
//                return Double.compare(t.doubleValue(), t1.doubleValue());
//            }
//        });
//
//        elements.sort((t, t1) -> Double.compare(t.doubleValue(), t1.doubleValue()));
    }

    @Override
    public String toString() {
        return elements.stream()
                .map(element -> String.format("%.2f", element.doubleValue()))
                .collect(Collectors.joining(" "));

        //return String.format("%.2f %.2f %.2f", elements.getClass(0), ele)
    }
}

class TriplesCollection {
    DoubleSummaryStatistics statistics;

    TriplesCollection () {
        statistics = new DoubleSummaryStatistics();
    }

    public void processTriple (Triple<? extends Number> triple) {
        statistics.accept(triple.average());
    }

    @Override
    public String toString() {
        return String.format("Statistics for the triples:\n %s", statistics.toString());
    }
}

public class TripleTest {
    public static void main(String[] args) {
        TriplesCollection triplesCollection = new TriplesCollection();
        Scanner scanner = new Scanner(System.in);
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        Triple<Integer> tInt = new Triple<Integer>(a, b, c);
        System.out.printf("%.2f\n", tInt.max());
        System.out.printf("%.2f\n", tInt.average());
        tInt.sort();
        System.out.println(tInt);
        float fa = scanner.nextFloat();
        float fb = scanner.nextFloat();
        float fc = scanner.nextFloat();
        Triple<Float> tFloat = new Triple<Float>(fa, fb, fc);
        System.out.printf("%.2f\n", tFloat.max());
        System.out.printf("%.2f\n", tFloat.average());
        tFloat.sort();
        System.out.println(tFloat);
        double da = scanner.nextDouble();
        double db = scanner.nextDouble();
        double dc = scanner.nextDouble();
        Triple<Double> tDouble = new Triple<Double>(da, db, dc);
        System.out.printf("%.2f\n", tDouble.max());
        System.out.printf("%.2f\n", tDouble.average());
        tDouble.sort();
        System.out.println(tDouble);

        triplesCollection.processTriple(tInt);
        triplesCollection.processTriple(tFloat);
        triplesCollection.processTriple(tDouble);
        System.out.println(triplesCollection);
    }
}
// vasiot kod ovde
// class Triple


