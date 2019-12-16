package mk.ukim.finki.av8;

import java.util.*;
import java.util.stream.Collectors;

class BookCollection {
    Map<String, Set<Book>> booksByCategories;
    Set<Book> allBooks;

    BookCollection () {
        booksByCategories = new HashMap<>();
        Comparator<Book> priceComparator = Comparator.comparingDouble(Book::getPrice).thenComparing(Book::getName);
        allBooks = new TreeSet<>(priceComparator);
    }

    public void printByCategory(String category) {
        booksByCategories.get(category).forEach(System.out::println);
    }

    public void addBook(Book book) {
        allBooks.add(book);
        Comparator<Book> bookComparator = Comparator.comparing(Book::getName).thenComparingDouble(Book::getPrice);

        booksByCategories.computeIfAbsent(book.getCategory(), (k) -> new TreeSet<>(bookComparator));

        booksByCategories.get(book.getCategory()).add(book);
//        booksByCategories.computeIfPresent(book.getCategory(), (k,v) -> {
//            v.add(book);
//            return v;
//        });

    }

    public List<Book> getCheapestN(int n) {
        return allBooks.stream().limit(n).collect(Collectors.toList());
    }
}

public class BooksTestWithCollections {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        BookCollection booksCollection = new BookCollection();
        Set<String> categories = fillCollection(scanner, booksCollection);
        System.out.println("=== PRINT BY CATEGORY ===");
        for (String category : categories) {
            System.out.println("CATEGORY: " + category);
            booksCollection.printByCategory(category);
        }
        System.out.println("=== TOP N BY PRICE ===");
        print(booksCollection.getCheapestN(n));
    }

    static void print(List<Book> books) {
        for (Book book : books) {
            System.out.println(book);
        }
    }

    static TreeSet<String> fillCollection(Scanner scanner,
                                          BookCollection collection) {
        TreeSet<String> categories = new TreeSet<String>();
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] parts = line.split(":");
            Book book = new Book(parts[0], parts[1], Float.parseFloat(parts[2]));
            collection.addBook(book);
            categories.add(parts[1]);
        }
        return categories;
    }
}
