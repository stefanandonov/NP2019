package mk.ukim.finki.exams.january.TuplesCollection_2solution;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

interface Tuple2<T extends Comparable<T>> {
    T getFirst();
    T getSecond();
}

class TuplesBuilder {

    public static Tuple2<Integer> buildIntegersTuple(int e1, int e2) {
        return new Tuple2<Integer>() {
            @Override
            public Integer getFirst() {
                return e1;
            }

            @Override
            public Integer getSecond() {
                return e2;
            }
        };
    }

    public static Tuple2<Double> buildDoublesTuple(double e1, double e2) {
        return new Tuple2<Double>() {
            @Override
            public Double getFirst() {
                return e1;
            }

            @Override
            public Double getSecond() {
                return e2;
            }
        };
    }

    public static Tuple2<String> buildStringsTuple(String e1, String e2) {
        return new Tuple2<String>() {
            @Override
            public String getFirst() {
                return e1;
            }

            @Override
            public String getSecond() {
                return e2;
            }
        };
    }
}

class TuplesCollection<T extends Comparable<T>> {
    List<Tuple2<T>> allTuples;
    Map<T, List<Tuple2<T>>> tuplesByFirst;
    Map<T, List<Tuple2<T>>> tuplesBySecond;
    Map<T, Integer> countsByFirst;
    Map<T, Integer> countsBySecond;

    TuplesCollection() {
        allTuples = new ArrayList<>();
        tuplesByFirst = new TreeMap<>();
        tuplesBySecond = new TreeMap<>();
        countsByFirst = new TreeMap<>(Comparator.reverseOrder());
        countsBySecond = new TreeMap<>(Comparator.reverseOrder());
    }

    public void addTuple(Tuple2<T> newTuple) {

        boolean containsTuple = allTuples.stream()
                .anyMatch(tuple -> tuple.getFirst().compareTo(newTuple.getFirst())==0
                        && tuple.getSecond().compareTo(newTuple.getSecond())==0);

        if (containsTuple)
            return ;

        allTuples.add(newTuple);
        tuplesByFirst.computeIfAbsent(newTuple.getFirst(), k -> new ArrayList<>());
        tuplesByFirst.get(newTuple.getFirst()).add(newTuple);

        tuplesBySecond.computeIfAbsent(newTuple.getSecond(), k -> new ArrayList<>());
        tuplesBySecond.get(newTuple.getSecond()).add(newTuple);

        countsByFirst.putIfAbsent(newTuple.getFirst(), 0);
        countsByFirst.computeIfPresent(newTuple.getFirst(), (k,v) -> ++v);

        countsBySecond.putIfAbsent(newTuple.getSecond(), 0);
        countsBySecond.computeIfPresent(newTuple.getSecond(), (k,v) -> ++v);

    }

    public Collection<Tuple2<T>> getFilteredTuples(Predicate<Tuple2<T>> predicate) {
        Comparator<Tuple2<T>> comparator = Comparator.comparing(Tuple2<T>::getFirst).thenComparing(Tuple2::getSecond);
        return allTuples.stream().filter(predicate).sorted(comparator).collect(Collectors.toList());
    }

    public Map<T, Integer> reduceBy(boolean first) {
        return first ? countsByFirst : countsBySecond;
    }

    public Map<T, List<Tuple2<T>>> groupBy(boolean first) {
        return first ? tuplesByFirst : tuplesBySecond;
    }

    public Collection<Tuple2<T>> uniqueTuples(boolean first) {
        return groupBy(first).values()
                .stream()
                .filter(tuple2s -> tuple2s.size() == 1)
                .map(tuple2s -> tuple2s.get(0))
                .collect(Collectors.toList());
    }

    public int compare(TuplesCollection<? extends Comparable<?>> otherCollection) {
        return Integer.compare(this.allTuples.size(), otherCollection.allTuples.size());
    }
}
public class TuplesCollectionTest {

    private static <T extends Comparable<T>> void printTuple(Tuple2<T> tuple) {
        System.out.println(String.format("(%s,%s)", tuple.getFirst().toString(), tuple.getSecond().toString()));

    }

    private static <T extends Comparable<T>> String tupleToString(Tuple2<T> tuple) {
        return String.format("(%s,%s)", tuple.getFirst().toString(), tuple.getSecond().toString());
    }

    private static <T extends Comparable<T>> void printCountingMaps(Map<T, Integer> countingMap) {
        countingMap.entrySet()
                .stream()
                .map(entry -> String.format("%s : %d", entry.getKey().toString(), entry.getValue()))
                .forEach(System.out::println);
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        TuplesCollection<Integer> integerTuplesCollection = new TuplesCollection<>();
        TuplesCollection<String> stringTuplesCollection = new TuplesCollection<>();
        TuplesCollection<Double> doubleTuplesCollection = new TuplesCollection<>();
        int n = sc.nextInt();
        for (int i = 0; i < n; i++) {
            int type = sc.nextInt();

            if (type == 1) {
                int e1, e2;
                e1 = sc.nextInt();
                e2 = sc.nextInt();
                integerTuplesCollection.addTuple(TuplesBuilder.buildIntegersTuple(e1, e2));
            } else if (type == 2) {
                double e1, e2;
                e1 = sc.nextDouble();
                e2 = sc.nextDouble();
                doubleTuplesCollection.addTuple(TuplesBuilder.buildDoublesTuple(e1, e2));
            } else {
                String e1, e2;
                e1 = sc.next();
                e2 = sc.next();
                stringTuplesCollection.addTuple(TuplesBuilder.buildStringsTuple(e1, e2));
            }
        }
        int testCase = sc.nextInt();

        Predicate<Tuple2<Integer>> lessThanFive = tuple -> tuple.getFirst() < 5 && tuple.getSecond() < 5;
        Predicate<Tuple2<Integer>> greaterThanFive = tuple -> tuple.getFirst() > 5 || tuple.getSecond() > 5;

        Predicate<Tuple2<Double>> lestThan2 = doubleTuple2 -> doubleTuple2.getFirst() < 2.0 && doubleTuple2.getSecond() < 2.0;
        Predicate<Tuple2<Double>> greaterThan2 = doubleTuple2 -> doubleTuple2.getFirst() >= 2.0 || doubleTuple2.getSecond() >= 2.0;

        Predicate<Tuple2<String>> left = stringTuple2 -> stringTuple2.getFirst().toLowerCase().compareTo("m") <= 0 && stringTuple2.getSecond().toLowerCase().compareTo("m") <= 0;
        Predicate<Tuple2<String>> right = stringTuple2 -> stringTuple2.getFirst().toLowerCase().compareTo("m") > 0 && stringTuple2.getSecond().toLowerCase().compareTo("m") > 0;


        if (testCase == 1) {
            System.out.println("===getFilteredTuples, integers, less than 5===");
            integerTuplesCollection.getFilteredTuples(lessThanFive).forEach(TuplesCollectionTest::printTuple);
            System.out.println("===getFilteredTuples, integers, greater than 5===");
            integerTuplesCollection.getFilteredTuples(greaterThanFive).forEach(TuplesCollectionTest::printTuple);
        } else if (testCase == 2) {
            System.out.println("===getFilteredTuples, doubles, less than 2.0===");
            doubleTuplesCollection.getFilteredTuples(lestThan2).forEach(TuplesCollectionTest::printTuple);
            System.out.println("===getFilteredTuples, doubles, greater than 2.0===");
            doubleTuplesCollection.getFilteredTuples(greaterThan2).forEach(TuplesCollectionTest::printTuple);
        } else if (testCase == 3) {
            System.out.println("===getFilteredTuples, Strings, left===");
            stringTuplesCollection.getFilteredTuples(left).forEach(TuplesCollectionTest::printTuple);
            System.out.println("===getFilteredTuples, Strings, right===");
            stringTuplesCollection.getFilteredTuples(right).forEach(TuplesCollectionTest::printTuple);
        } else if (testCase == 4) {

            System.out.println("reduceBy(first)");
            printCountingMaps(integerTuplesCollection.reduceBy(true));
            System.out.println("groupBy(first)");
            printElementsMaps(integerTuplesCollection.groupBy(true));
            System.out.println("uniqueTuples(first)");
            integerTuplesCollection.uniqueTuples(true).forEach(TuplesCollectionTest::printTuple);

            System.out.println("reduceBy(second)");
            printCountingMaps(integerTuplesCollection.reduceBy(false));
            System.out.println("groupBy(second)");
            printElementsMaps(integerTuplesCollection.groupBy(false));
            System.out.println("uniqueTuples(second)");
            integerTuplesCollection.uniqueTuples(false).forEach(TuplesCollectionTest::printTuple);

        } else if (testCase == 5) {
            System.out.println("reduceBy(first)");
            printCountingMaps(doubleTuplesCollection.reduceBy(true));
            System.out.println("groupBy(first)");
            printElementsMaps(doubleTuplesCollection.groupBy(true));
            System.out.println("uniqueTuples(first)");
            doubleTuplesCollection.uniqueTuples(true).forEach(TuplesCollectionTest::printTuple);

            System.out.println("reduceBy(second)");
            printCountingMaps(doubleTuplesCollection.reduceBy(false));
            System.out.println("groupBy(second)");
            printElementsMaps(doubleTuplesCollection.groupBy(false));
            System.out.println("uniqueTuples(second)");
            doubleTuplesCollection.uniqueTuples(false).forEach(TuplesCollectionTest::printTuple);
        } else if (testCase == 6) {
            System.out.println("reduceBy(first)");
            printCountingMaps(stringTuplesCollection.reduceBy(true));
            System.out.println("groupBy(first)");
            printElementsMaps(stringTuplesCollection.groupBy(true));
            System.out.println("uniqueTuples(first)");
            stringTuplesCollection.uniqueTuples(true).forEach(TuplesCollectionTest::printTuple);

            System.out.println("reduceBy(second)");
            printCountingMaps(stringTuplesCollection.reduceBy(false));
            System.out.println("groupBy(second)");
            printElementsMaps(stringTuplesCollection.groupBy(false));
            System.out.println("uniqueTuples(second)");
            stringTuplesCollection.uniqueTuples(false).forEach(TuplesCollectionTest::printTuple);
        }
        else if (testCase == 7) {
            System.out.println("===compare===");
            System.out.println(stringTuplesCollection.compare(doubleTuplesCollection));
            System.out.println(stringTuplesCollection.compare(integerTuplesCollection));
            System.out.println(integerTuplesCollection.compare(doubleTuplesCollection));
            System.out.println(integerTuplesCollection.compare(integerTuplesCollection));
        }
    }

    private static <T extends Comparable<T>> void printElementsMaps(Map<T, List<Tuple2<T>>> map) {
        map.entrySet()
                .stream()
                .map(entry -> String.format("%s : [%s]",
                        entry.getKey().toString(),
                        entry.getValue().stream().map(TuplesCollectionTest::tupleToString).collect(Collectors.joining(", "))))
                .forEach(System.out::println);
    }
}