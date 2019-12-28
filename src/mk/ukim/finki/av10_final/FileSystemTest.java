package mk.ukim.finki.av10_final;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Partial exam II 2016/2017
 */

class File {
    String name;
    int size;
    LocalDateTime date;

    public File(String name, int size, LocalDateTime date) {
        this.name = name;
        this.size = size;
        this.date = date;
    }

    public String toString() {
        return String.format("%-10s %5dB %s", name, size, date.toString());
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public LocalDateTime getDate() {
        return date;
    }
}

class FileSystem {
    Map<Character, Set<File>> filesByFolders;
    Map<Integer, Set<File>> filesByYear;
    Map<String, Long> filesByMonthAndDay;
    List<File> allFiles;
    private static Comparator<File> fileComparator = Comparator.comparing(File::getDate)
            .thenComparing(File::getName)
            .thenComparing(File::getSize);

    FileSystem () {
        filesByFolders = new HashMap<>();
        filesByYear = new TreeMap<>();
        filesByMonthAndDay = new TreeMap<>();
        allFiles = new ArrayList<>();
    }

    public void addFile(char folder, String name, int size, LocalDateTime createdAt) {
        File file = new File (name, size, createdAt);

        filesByFolders.putIfAbsent(folder, new TreeSet<>(fileComparator));
        filesByFolders.get(folder).add(file);

        allFiles.add(file);

        Integer yearOfFile = file.getDate().getYear();

        filesByYear.putIfAbsent(yearOfFile, new TreeSet<>(fileComparator));
        filesByYear.get(yearOfFile).add(file);

        String key = file.getDate().getMonth().toString() + "-" + file.getDate().getDayOfMonth();

        filesByMonthAndDay.putIfAbsent(key, 0L);
        filesByMonthAndDay.computeIfPresent(key, (k,v) -> {
            v+=(long)file.size;
            return v;
        });

    }

    public List<File> findAllHiddenFilesWithSizeLessThen(int size)  {
        return allFiles.stream()
                .filter(file -> file.name.startsWith(".") && file.size<size)
                .collect(Collectors.toList());

    }

    public int totalSizeOfFilesFromFolders(List<Character> folders) {

        return folders.stream()
                .flatMapToInt(folderName -> filesByFolders.get(folderName).stream().mapToInt(File::getSize))
                .sum();

//        return folders.stream()
//                .mapToInt(folderName -> filesByFolders.get(folderName)
//                        .stream()
//                        .mapToInt(File::getSize)
//                        .sum()
//                )
//                .sum();
    }

    public Map<Integer, Set<File>> byYear() {
        return filesByYear;
    }

    public Map<String, Long> sizeByMonthAndDay() {
        return filesByMonthAndDay;
    }
}

public class FileSystemTest {
    public static void main(String[] args) {
        FileSystem fileSystem = new FileSystem();
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        for (int i = 0; i < n; i++) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            fileSystem.addFile(parts[0].charAt(0), parts[1],
                    Integer.parseInt(parts[2]),
                    LocalDateTime.of(2016, 12, 29, 0, 0, 0).minusDays(Integer.parseInt(parts[3]))
            );
        }
        int action = scanner.nextInt();
        if (action == 0) {
            scanner.nextLine();
            int size = scanner.nextInt();
            System.out.println("== Find all hidden files with size less then " + size);
            List<File> files = fileSystem.findAllHiddenFilesWithSizeLessThen(size);
            files.forEach(System.out::println);
        } else if (action == 1) {
            scanner.nextLine();
            String[] parts = scanner.nextLine().split(":");
            System.out.println("== Total size of files from folders: " + Arrays.toString(parts));
            int totalSize = fileSystem.totalSizeOfFilesFromFolders(Arrays.stream(parts)
                    .map(s -> s.charAt(0))
                    .collect(Collectors.toList()));
            System.out.println(totalSize);
        } else if (action == 2) {
            System.out.println("== Files by year");
            Map<Integer, Set<File>> byYear = fileSystem.byYear();
            byYear.keySet().stream().sorted()
                    .forEach(key -> {
                        System.out.printf("Year: %d\n", key);
                        Set<File> files = byYear.get(key);
                        files.stream()
                                .sorted()
                                .forEach(System.out::println);
                    });
        } else if (action == 3) {
            System.out.println("== Size by month and day");
            Map<String, Long> byMonthAndDay = fileSystem.sizeByMonthAndDay();
            byMonthAndDay.keySet().stream().sorted()
                    .forEach(key -> System.out.printf("%s -> %d\n", key, byMonthAndDay.get(key)));
        }
        scanner.close();
    }
}

// Your code here

