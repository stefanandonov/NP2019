package mk.ukim.finki.av11_cetvrtok;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/**
 * Exam 2017 June
 */

class Song {
    String name;
    Integer duration; //in seconds

    public Song(String name, Integer duration) {
        this.name = name;
        this.duration = duration;
    }

    public String getName() {
        return name;
    }

    public Integer getDuration() {
        return duration;
    }

    public String toString () {
        return String.format("%-50s%2d:%02d", name, duration/60, duration%60);
    }
}

class Album {
    String name;
    LocalDate publishedAt;
    List<Song> songs;

    public Album(String name, LocalDate publishedAt) {
        this.name = name;
        this.publishedAt = publishedAt;
        songs = new ArrayList<>();
    }

    public void addSong (Song song) {
        songs.add(song);
    }

    public long totalDuration () {
        return songs.stream().mapToLong(Song::getDuration).sum();
    }

    public String getName() {
        return name;
    }

    public LocalDate getPublishedAt() {
        return publishedAt;
    }

    public List<Song> getSongs() {
        return songs;
    }
}

class SongLibrary {

    List<Album> albums;
    Map<Character, Long> durationWithFirstLetter;
//    Map<String, Long> albumDurations;

    SongLibrary () {
        albums = new ArrayList<>();
        durationWithFirstLetter = new HashMap<>();
    }

    public void addAlbum(String name, LocalDate publishedAt, String[] songNames, int[] songDurations) {

        Album album = new Album(name, publishedAt);

        for (int i = 0; i < songNames.length; i++) {
            album.addSong(new Song(songNames[i], songDurations[i]));
        }

        albums.add(album);

        Character firstLetterOfAlbum = album.name.charAt(0);

        durationWithFirstLetter.putIfAbsent(firstLetterOfAlbum, 0L);
        durationWithFirstLetter.computeIfPresent(firstLetterOfAlbum, (k,v) -> {
            v+=album.totalDuration();
            return v;
        });

    }

    public List<Song> findAllSongsLongerThan(int duration) {

        Comparator<Song> songComparator = Comparator.comparing(Song::getName)
                .thenComparing(Song::getDuration);

        return albums.stream()
                .flatMap(album -> album.songs.stream())
                .filter(song -> song.duration>duration)
                .sorted(songComparator)
                .collect(Collectors.toList());
    }

    public long totalDurationAlbumsWithFirstLetter(Character letter) {
        
        return durationWithFirstLetter.get(letter);
    }

    public Song shortestSongInRange(LocalDate from, LocalDate to) {

        Comparator<Song> songComparator = Comparator.comparingInt(Song::getDuration);
        return albums.stream()
                .filter(album -> album.publishedAt.isAfter(from) && album.publishedAt.isBefore(to))
                .flatMap(album -> album.songs.stream())
                .min(songComparator)
                .get();


    }

    public Map<String, Long> durationByAlbum() {

        return albums.stream().collect(Collectors.toMap(
                Album::getName,
                Album::totalDuration
        ));
    }
}
public class SongLibraryTest {
    public static void main(String[] args) {
        SongLibrary songLibrary = new SongLibrary();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            LocalDate publishedAt = stringToDate(parts[1]);
            int songsCount = (parts.length - 2) / 2;
            String[] songNames = new String[songsCount];
            int[] songDurations = new int[songsCount];
            for (int j = 0; j < songsCount; ++j) {
                songNames[j] = parts[j * 2 + 2];
                String[] durParts = parts[(j * 2 + 1) + 2].split(":");
                songDurations[j] = Integer.parseInt(durParts[0]) * 60 + Integer.parseInt(durParts[1]);
            }
            songLibrary.addAlbum(parts[0], publishedAt, songNames, songDurations);
        }
        int action = scanner.nextInt();
        scanner.nextLine();
        if (action == 0) {
            int duration = scanner.nextInt();
            System.out.println("== Find all songs longer than " + duration);
            List<Song> songs = songLibrary.findAllSongsLongerThan(duration);
            songs.forEach(System.out::println);
        } else if (action == 1) {
            Character letter = scanner.next().charAt(0);
            System.out.println("== Total duration of albums with first letter: " + letter);
            long totalDuration = songLibrary.totalDurationAlbumsWithFirstLetter(letter);
            System.out.println(String.format("%d hours and %d minutes", totalDuration / 60, totalDuration % 60));
        } else if (action == 2) {
            String[] range = scanner.nextLine().split(":");
            LocalDate from = stringToDate(range[0]);
            LocalDate to = stringToDate(range[1]);
            Song shortestSongInRange = songLibrary.shortestSongInRange(from, to);
            System.out.println(String.format("== Shortest song in range [%s-%s]", from, to));
            System.out.println(shortestSongInRange);
        } else if (action == 3) {
            System.out.println("== Duration by album");
            Map<String, Long> durationByAlbum = songLibrary.durationByAlbum();
            durationByAlbum.keySet().stream()
                    .sorted()
                    .forEach(key -> System.out.printf("%s -> %2d:%02d\n", key, durationByAlbum.get(key) / 60,
                            durationByAlbum.get(key) % 60));
        }
        scanner.close();
    }

    static LocalDate stringToDate(String date) {
        String[] dateParts = date.split("-");
        return LocalDate.of(Integer.parseInt(dateParts[0]),
                Integer.parseInt(dateParts[1]),
                Integer.parseInt(dateParts[2]));
    }
}

// Your code here


