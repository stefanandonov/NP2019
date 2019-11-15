package mk.ukim.finki.av6_petok;


import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

interface Convertable {

    double getAsCelsius();

    double getAsFahrenheit();
}

abstract class Measurement implements Convertable {
    double value;

    public Measurement(double value) {
        this.value = value;
    }
}

class CMeasurement extends Measurement {

    public CMeasurement(double value) {
        super(value);
    }

    @Override
    public double getAsCelsius() {
        return value;
    }

    @Override
    public double getAsFahrenheit() {
        return (value * 9 / 5) + 32;
    }
}

class FMeasurement extends Measurement {

    public FMeasurement(double value) {
        super(value);
    }

    @Override
    public double getAsCelsius() {
        return (value - 32) * 5 / 9.0;
    }

    @Override
    public double getAsFahrenheit() {
        return value;
    }
}

class DailyMeasurement implements Comparable<DailyMeasurement> {
    int day;
    List<Measurement> measurements;

    public DailyMeasurement(int day, List<Measurement> measurements) {
        this.day = day;
        this.measurements = measurements;
    }

    public static DailyMeasurement createInstance(String input) {
        String[] parts = input.split("\\s+");
        int day = Integer.parseInt(parts[0]);
        List<Measurement> measurements = new ArrayList<>();
        Measurement m;
        for (int i = 1; i < parts.length; i++) {
            Double value = Double.parseDouble(parts[i].substring(0, parts[i].length() - 1));
            char scale = parts[i].charAt(parts[i].length() - 1);
            if (scale == 'C') {
                m = new CMeasurement(value);
            } else {
                m = new FMeasurement(value);
            }
            measurements.add(m);
        }

//        measurements = Arrays.stream(parts).skip(1).map(in -> {
//            double value = Double.parseDouble(in.substring(0, in.length() - 1));
//            char scale = in.charAt(in.length() - 1);
//            if (scale == 'C') {
//                return new CMeasurement(value);
//            } else {
//                return new FMeasurement(value);
//            }
//        }).collect(Collectors.toList());

        return new DailyMeasurement(day, measurements);
    }


    public String toString(char scale) {
        DoubleSummaryStatistics doubleSummaryStatistics = new DoubleSummaryStatistics();
        for (Measurement m : measurements) {
            if (scale=='C')
                doubleSummaryStatistics.accept(m.getAsCelsius());
            else
                doubleSummaryStatistics.accept(m.getAsFahrenheit());
        }


//        measurements.stream().forEach(measurement -> {
//            if (scale=='C')
//                doubleSummaryStatistics.accept(measurement.getAsCelsius());
//            else
//                doubleSummaryStatistics.accept(measurement.getAsFahrenheit());
//        });

        return String.format("%3d: Count: %3d Min: %6.2f%c Max: %6.2f%c Avg: %6.2f%c",
                day,
                (int) doubleSummaryStatistics.getCount(),
                doubleSummaryStatistics.getMin(),
                scale,
                doubleSummaryStatistics.getMax(),
                scale,
                doubleSummaryStatistics.getAverage(),
                scale);
    }

    @Override
    public int compareTo(DailyMeasurement otherDailyMeasurement) {
        return Integer.compare(this.day, otherDailyMeasurement.day);
    }
}

class DailyTemperatures {
    List<DailyMeasurement> measurements;

    DailyTemperatures() {
        measurements = new ArrayList<>();
    }

    public void readTemperatures(InputStream in) {
//        Scanner sc = new Scanner(in);
//
//        while (sc.hasNext()) {
//            String input = sc.nextLine();
//            DailyMeasurement dm = DailyMeasurement.createInstance(input);
//            measurements.add(dm);
//        }
//
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        measurements = br.lines()
                .map(DailyMeasurement::createInstance)
                .collect(Collectors.toList());
    }

    public void writeDailyStats(OutputStream out, char scale) {
        PrintWriter pw = new PrintWriter(out);

        measurements.sort(null);
        for (DailyMeasurement m : measurements) {
            pw.println(m.toString(scale));
        }

        pw.flush();


    }
}


/**
 * I partial exam 2016
 */
public class DailyTemperatureTest {
    public static void main(String[] args) {
        DailyTemperatures dailyTemperatures = new DailyTemperatures();
        dailyTemperatures.readTemperatures(System.in);
        System.out.println("=== Daily temperatures in Celsius (C) ===");
        dailyTemperatures.writeDailyStats(System.out, 'C');
        System.out.println("=== Daily temperatures in Fahrenheit (F) ===");
        dailyTemperatures.writeDailyStats(System.out, 'F');
    }
}

// Vashiot kod ovde