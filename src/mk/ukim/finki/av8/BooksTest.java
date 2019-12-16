package mk.ukim.finki.av8;
//
//import sun.util.resources.cldr.om.CalendarData_om_ET;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.List;
//import java.util.Scanner;
//import java.util.Set;
//import java.util.TreeSet;
//import java.util.stream.Collectors;
//
class Book {
    String name;
    String category;
    float price;

    public Book(String name, String category, float price) {
        this.name = name;
        this.category = category;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%s (%s) %.2f", name, category, price);
    }
//
//
}
//
////class BookCollection {
////
////    List<Book> booksCollection;
////
////    public BookCollection() {
////        booksCollection = new ArrayList<>();
////    }
////
////    public void printByCategory(String category) {
////
////        //lambda expression
//////        Comparator<Book> nameComparator = (book, otherBook) -> {
//////            int nameCompare = book.name.compareTo(otherBook.name);
//////            if (nameCompare==0) {
//////                return Float.compare(book.price, otherBook.price);
//////            }
//////            else {
//////                return nameCompare;
//////            }
//////        };
////
////        //anonimous class
//////        Comparator<Book> nameComparator = new Comparator<Book>() {
//////            @Override
//////            public int compare(Book book, Book otherBook) {
//////                int nameCompare = book.name.compareTo(otherBook.name);
//////                if (nameCompare==0) {
//////                    return Float.compare(book.price, otherBook.price);
//////                }
//////                else {
//////                    return nameCompare;
//////                }
//////            }
//////        };
////
////        Comparator<Book> bookComparator = Comparator.comparing(Book::getName).thenComparingDouble(Book::getPrice);
////
////        booksCollection.stream()
////                .filter(book -> book.category.equalsIgnoreCase(category))
////                .sorted(bookComparator)
////                .forEach(System.out::println);
////    }
////
////    public List<Book> getCheapestN(int n) {
////
////        Comparator<Book> priceComparator = Comparator.comparingDouble(Book::getPrice).thenComparing(Book::getName);
////
////        return booksCollection.stream()
////                .sorted(priceComparator)
////                .limit(n)
////                .collect(Collectors.toList());
////
////    }
////
////    public void addBook(Book book) {
////        booksCollection.add(book);
////    }
////}
//
//public class BooksTest {
//    public static void main(String[] args) {
//        Scanner scanner = new Scanner(System.in);
//        int n = scanner.nextInt();
//        scanner.nextLine();
//        BookCollection booksCollection = new BookCollection();
//        Set<String> categories = fillCollection(scanner, booksCollection);
//        System.out.println("=== PRINT BY CATEGORY ===");
//        for (String category : categories) {
//            System.out.println("CATEGORY: " + category);
//            booksCollection.printByCategory(category);
//        }
//        System.out.println("=== TOP N BY PRICE ===");
//        print(booksCollection.getCheapestN(n));
//    }
//
//    static void print(List<Book> books) {
//        for (Book book : books) {
//            System.out.println(book);
//        }
//    }
//
//    static TreeSet<String> fillCollection(Scanner scanner,
//                                          BookCollection collection) {
//        TreeSet<String> categories = new TreeSet<String>();
//        while (scanner.hasNext()) {
//            String line = scanner.nextLine();
//            String[] parts = line.split(":");
//            Book book = new Book(parts[0], parts[1], Float.parseFloat(parts[2]));
//            collection.addBook(book);
//            categories.add(parts[1]);
//        }
//        return categories;
//    }
//}
//
//// Вашиот код овде