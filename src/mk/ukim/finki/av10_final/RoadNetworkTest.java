package mk.ukim.finki.av10_final;
import java.util.*;
import java.util.stream.Collectors;

class CityNotFoundException extends Exception {
    CityNotFoundException(String message) {
        super(message);
    }
}

class City {
    String cityName;
    int population;
    Map<String, Double> connections;

    public City (String cityName, int population, String [] cities, float [] distances) {
        this.cityName = cityName;
        this.population = population;

        connections = new TreeMap<>();
        for (int i=0;i<cities.length;i++)
            connections.put(cities[i], (double) distances[i]);
    }

    public String toString() {
        return String.format("%s\n%d\n%s",
                cityName,
                population,
                connections.entrySet().stream().map(entry -> String.format("%s : %.1f km",
                        entry.getKey(),
                        entry.getValue())
                ).collect(Collectors.joining("\n"))
        );
    }

    public double calculateCoefficient (City otherCity) {
        if (!connections.containsKey(otherCity.cityName))
            return 0;
        return (population + otherCity.population) / connections.get(otherCity.cityName);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        City city = (City) o;
        return population == city.population &&
                cityName.equals(city.cityName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cityName, population);
    }
}

class RoadNetwork {
    String county;
    Map<String,City> cityMap;

    public RoadNetwork(String country) {
        this.county=country;
        this.cityMap = new HashMap<>();
    }

    public void addCity(String cityName, int population, String[] cities, float[] distances) {

        City city = new City(cityName, population, cities, distances);
        cityMap.put(cityName, city);

    }

    public void search(String cityName) throws CityNotFoundException {

        City result = cityMap.getOrDefault(cityName, null);

        if (result == null) {
            throw new CityNotFoundException (cityName + "does not exist");
        }
        else {
            System.out.println(result);
        }

    }

    public float roadNetwork() {
        return (float) cityMap.values()
                .stream()
                .flatMap(city -> city.connections.values().stream())
                .mapToDouble(i -> i)
                .sum();
    }

    public void mostDense() {
        Collection<City> allCities = cityMap.values();
        Map<String,Double> coefMap = new HashMap<>();
        for (City city : allCities) {
            for (City otherCity : allCities) {
                if (!city.equals(otherCity)) {
                    double coefficient = city.calculateCoefficient(otherCity);
                    String key1 = city.cityName + " - " + otherCity.cityName;
                    String key2 = otherCity.cityName + " - " + city.cityName;
                    if (!coefMap.containsKey(key1) && !coefMap.containsKey(key2)) {
                        coefMap.put(key1, coefficient);
                    }
                }
            }
        }

        Map.Entry<String,Double> max = coefMap.entrySet()
                .stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .get();

        System.out.println(max.getKey()+ " : " + String.format("%.2f%%", max.getValue()));

    }
}

public class RoadNetworkTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String country = scanner.nextLine();
        RoadNetwork roadNetwork = new RoadNetwork(country);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            String cityName = parts[0];
            int population = Integer.parseInt(parts[1]);
            String[] cities = new String[(parts.length - 2) / 2];
            float[] distances = new float[(parts.length - 2) / 2];
            int k = 0;
            for (int j = 2; j < parts.length; j += 2) {
                cities[k] = parts[j];
                distances[k++] = Float.parseFloat(parts[j + 1]);
            }
            roadNetwork.addCity(cityName, population, cities, distances);
        }
        System.out.println("SEARCH");
        String cityName = scanner.nextLine();
        scanner.close();
        try {
            roadNetwork.search(cityName);
        } catch(CityNotFoundException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("ROAD NETWORK");
        System.out.printf("%.2f\n", roadNetwork.roadNetwork());
        System.out.println("MAX DENSE");
        roadNetwork.mostDense();
    }
}

// вашиот код овде