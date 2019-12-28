package mk.ukim.finki.konsultacii;

//package VtorKolokviumVezbi;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MoviesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        MoviesList moviesList = new MoviesList();
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; ++i) {
            String title = scanner.nextLine();
            int x = scanner.nextInt();
            int[] ratings = new int[x];
            for (int j = 0; j < x; ++j) {
                ratings[j] = scanner.nextInt();
            }
            scanner.nextLine();
            moviesList.addMovie(title, ratings);
        }
        scanner.close();
        List<Movie> movies = moviesList.top10ByAvgRating();
        System.out.println("=== TOP 10 BY AVERAGE RATING ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
        movies = moviesList.top10ByRatingCoef();
        System.out.println("=== TOP 10 BY RATING COEFFICIENT ===");
        for (Movie movie : movies) {
            System.out.println(movie);
        }
    }
}

class Movie
{
    String naslov;
    List<Integer> rating;

    public Movie(String naslov,int [] ratings) {
        this.naslov = naslov;

        this.rating = IntStream.range(0,ratings.length).mapToObj(x->ratings[x]).collect(Collectors.toList());
    }
    public double average()
    {
        OptionalDouble optionalAverage = rating.stream().mapToInt(x->x).average();

        if (optionalAverage.isPresent())
            return optionalAverage.getAsDouble();
        else
            return 0.0;
    }
    public String toString()
    {
        return String.format("%s (%.2f) of %d ratings",naslov,average(),rating.size());

    }
    public String getNaslov()
    {
        return naslov;
    }

    public int getRatingSize()
    {
        return this.rating.size();
    }


}
class MoviesList
{
    List<Movie> lista;
    public MoviesList()
    {
        this.lista = new ArrayList<>();
    }
    public void addMovie(String title,int [] ratings)
    {
        Movie m = new Movie(title,ratings);
        lista.add(m);
    }
    public List<Movie> top10ByAvgRating()
    {
        Comparator<Movie> comparator = Comparator.comparing(Movie::average).reversed().thenComparing(Movie::getNaslov);

        return lista.stream().sorted(comparator).limit(10).collect(Collectors.toList());
    }
    public List<Movie> top10ByRatingCoef()
    {
        int max = lista.stream().mapToInt(x->x.rating.size()).max().getAsInt();
        Comparator<Movie> comparator = (o1,o2) ->{
            double coef1 = o1.average()*o1.rating.size() / max;
            double coef2 = o2.average() * o2.rating.size() / max;

            if(Double.compare(coef1,coef2) == 0)
            {
                return o1.naslov.compareTo(o2.naslov);
            }
            else{
                return Double.compare(coef2,coef1);
            }
        };


        List<Movie> l1 = lista.stream().sorted(comparator).limit(10).collect(Collectors.toList());
        return l1;


    }


}