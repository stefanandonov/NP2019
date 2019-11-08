package mk.ukim.finki.konsultacii;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.Scanner;
import java.util.*;
import java.lang.*;
import java.util.stream.Collectors;

public class WeatherStationTest {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
        int n = scanner.nextInt();
        scanner.nextLine();
        WeatherStation ws = new WeatherStation(n);
        while (true) {
            String line = scanner.nextLine();
            if (line.equals("=====")) {
                break;
            }
            String[] parts = line.split(" ");
            float temp = Float.parseFloat(parts[0]);
            float wind = Float.parseFloat(parts[1]);
            float hum = Float.parseFloat(parts[2]);
            float vis = Float.parseFloat(parts[3]);
            line = scanner.nextLine();
            Date date = df.parse(line);
            ws.addMeasurement(temp, wind, hum, vis, date);
        }
        String line = scanner.nextLine();
        Date from = df.parse(line);
        line = scanner.nextLine();
        Date to = df.parse(line);
        scanner.close();
        System.out.println(ws.total());
        try {
            ws.status(from, to);
        } catch (RuntimeException e) {
            System.out.println(e);
        }
    }
}

class WeatherMeasurement implements Comparable {
    private float temperature;
    private float wind;
    private float humidity;
    private float visibility;
    private Date date;

    WeatherMeasurement(float temperature, float wind, float humidity,
                       float visibility, Date date) {
        this.temperature = temperature;
        this.wind = wind;
        this.humidity = humidity;
        this.visibility = visibility;
        this.date = new Date(date.getTime());
    }

    public WeatherMeasurement() {
        this.temperature = 0;
        this.wind = 0;
        this.humidity = 0;
        this.visibility = 0;
        this.date = null;
    }

    public Date getDate() {
        return date;
    }

    @Override
    public String toString() {
        return temperature +
                " " + wind +
                " km/h " + humidity +
                " % " + visibility +
                " km " + date;
    }

    @Override
    protected Object clone() {
        return new WeatherMeasurement(this.temperature, this.wind, this.humidity, this.visibility, this.date);
    }

    @Override
    public int compareTo(Object o) {
        WeatherMeasurement otherWeatherMeasurement = (WeatherMeasurement) o;
        if (this.date.after(otherWeatherMeasurement.getDate())) {
            return 1;
        } else if (this.date.before(otherWeatherMeasurement.getDate())) {
            return -1;
        } else
            return 0;
    }
}

class WeatherStation {
    private int days;
    int numOfWeatherMeasurements;
    List<WeatherMeasurement> weatherMeasurement = null;


    WeatherStation(int d) {
        this.days = d;
        this.numOfWeatherMeasurements = 0;
        this.weatherMeasurement = new ArrayList<WeatherMeasurement>();
    }

    public void addMeasurement(float temperature, float wind, float humidity,
                               float visibility, Date date) {


        List<WeatherMeasurement> temp = new ArrayList<>();

        for (WeatherMeasurement wm : weatherMeasurement) {
            wm.getDate().toInstant();
            LocalDateTime measurementData = LocalDateTime.ofInstant(wm.getDate().toInstant(), ZoneId.of("GMT"));
            LocalDateTime newMeasurementData = LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("GMT"));
            if (!measurementData.plusDays(days).isAfter(newMeasurementData)) {
                temp.add(wm);
            }
        }

        this.weatherMeasurement = temp;
        //filter na startite i gi brishime
//        this.weatherMeasurement = weatherMeasurement.stream().filter(measurement -> {
//            measurement.getDate().toInstant();
//            LocalDateTime measurementData = LocalDateTime.ofInstant(measurement.getDate().toInstant(), ZoneId.of("GMT"));
//            LocalDateTime newMeasurementData = LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("GMT"));
//            return measurementData.isAfter(newMeasurementData.plusDays(days));
//        }).collect(Collectors.toList());

        //
//        if (this.weatherMeasurement.stream().anyMatch(m -> {
//            m.getDate().toInstant();
//            LocalDateTime measurementData = LocalDateTime.ofInstant(m.getDate().toInstant(), ZoneId.of("GMT"));
//            LocalDateTime newMeasurementData = LocalDateTime.ofInstant(date.toInstant(), ZoneId.of("GMT"));
//            Duration dur = Duration.between(m, newMeasurementData);
//            return dur.getSeconds() <= 150L;
//        })) {
//            return ;
//        }

        this.weatherMeasurement.add(new WeatherMeasurement(temperature, wind, humidity,
                visibility, date));
        this.numOfWeatherMeasurements++;
    }

    public int total() {
        return this.numOfWeatherMeasurements;
    }

    public void status(Date from, Date to) {
        List<WeatherMeasurement> sortedWeatherMeasurement = new ArrayList<WeatherMeasurement>();
        for (WeatherMeasurement w : weatherMeasurement) {
            sortedWeatherMeasurement.add((WeatherMeasurement) w.clone());
        }
        Collections.sort(sortedWeatherMeasurement);

        for (WeatherMeasurement d : sortedWeatherMeasurement) {
            if (d.getDate().after(from) && d.getDate().before(to)) {
                System.out.println(d.toString());
            }
        }
    }
}