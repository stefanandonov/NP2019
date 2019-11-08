package mk.ukim.finki.konsultacii;


import java.io.InputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Scanner;

/**
 * I partial exam 2016
 */

abstract class Measurement {
    double value;

    public Measurement(double value) {
        this.value = value;
    }

    abstract double getCelsius ();
    abstract double getFahrenheit ();
}

class CelsiusMeasurement extends Measurement {

    public CelsiusMeasurement(double value) {
        super(value);
    }

    @Override
    double getCelsius() {
        return value;
    }

    @Override
    double getFahrenheit() {
        return (value*9.0/5.0) + 32.0;
    }
}

class FahrenheitClass extends Measurement {

    public FahrenheitClass(double value) {
        super(value);
    }

    @Override
    double getCelsius() {
        return (value-32)*5.0/9.0;
    }

    @Override
    double getFahrenheit() {
        return value;
    }
}

class DailyMeasurement implements Comparable<DailyMeasurement>{
    int day;
    List<Measurement> measurements;

    public DailyMeasurement(int day, List<Measurement> measurements) {
        this.day = day;
        this.measurements = measurements;
    }

    public String toString(char scale) {
        DoubleSummaryStatistics doubleSummaryStatistics = new DoubleSummaryStatistics();

        measurements.stream().mapToDouble(m -> {
            if (scale=='C') {
                return m.getCelsius();
            }
            else {
                return m.getFahrenheit();
            }
        })
                .forEach(doubleSummaryStatistics::accept);

        return String.format(" %d: Count:   %d Min:  %.2f%c Max:  %.2f%c Avg:  %.2f%c",
                day,
                (int) doubleSummaryStatistics.getCount(),
                doubleSummaryStatistics.getMin(), scale,
                doubleSummaryStatistics.getMax(), scale,
                doubleSummaryStatistics.getAverage(), scale);

    }

    @Override
    public int compareTo(DailyMeasurement dailyMeasurement) {
        return Integer.compare(this.day, dailyMeasurement.day);
    }
}

class DailyMeasurementFactory {

    public static DailyMeasurement createDailyMeasurement (String input) {
        String [] parts = input.split("\\s+");

        Integer day = Integer.parseInt(parts[0]);
        List<Measurement> measurements = new ArrayList<>();
        for (int i=1;i<parts.length;i++) {
            char lastChar = parts[i].charAt(parts[i].length()-1);
            Double value = Double.parseDouble(parts[i].substring(0,parts[i].length()-1));
            Measurement m;
            if (lastChar=='C') {
                m = new CelsiusMeasurement(value);
            }
            else {
                m = new FahrenheitClass(value);
            }
            measurements.add(m);
        }

        return new DailyMeasurement(day, measurements);
    }
}

class DailyTemperatures {
    List<DailyMeasurement> dailyMeasurements;

    public DailyTemperatures() {
        dailyMeasurements = new ArrayList<>();
    }


    public void readTemperatures(InputStream in) {
        Scanner sc = new Scanner(in);

        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            DailyMeasurement dm = DailyMeasurementFactory.createDailyMeasurement(line);
            dailyMeasurements.add(dm);
        }
    }

    public void writeDailyStats(PrintStream out, char c) {
        PrintWriter pw = new PrintWriter(out);

        dailyMeasurements.sort(null);

        dailyMeasurements.stream().forEach(dailyMeasurement -> pw.println(dailyMeasurement.toString(c)));

        pw.flush();

    }
}


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