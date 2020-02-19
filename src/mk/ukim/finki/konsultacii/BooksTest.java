package mk.ukim.finki.konsultacii;

import java.util.*;
import java.util.stream.Collectors;

class Book{
    String title, category;
    float price;

    public Book(String title, String category, float price) {
        this.title = title;
        this.category = category;
        this.price = price;
    }

    public String getTitle() {
        return title.toLowerCase();
    }

    public String getCategory() {
        return category;
    }

    public float getPrice() {
        return price;
    }

    @Override
    public String toString(){
        return String.format("%s (%s) %.2f", title, category, price);
    }

}

class BookCollection{
    List<Book> list;

    public BookCollection() {
        list = new ArrayList<>();
    }

    public void addBook(Book book){
        list.add(book);
    }

    public void printByCategory(String category){
        Comparator<Book> comparator = Comparator.comparing(Book::getTitle).thenComparing(Book::getPrice);
        list.stream().filter(x -> x.category.equals(category)).sorted(comparator).forEach(x -> System.out.println(x.toString()));
    }

    public List<Book> getCheapestN(int n){
        Comparator<Book> comparator = Comparator.comparing(Book::getPrice).thenComparing(Book::getTitle);
        return list.stream().sorted(comparator).limit(n).collect(Collectors.toList());
    }
}


//
//class BookCollection{
//    Map<Book, String> map;
//
//    public BookCollection() {
//        map = new HashMap<>();
//    }
//
//    public void addBook(Book book){
//        map.put(book, book.category);
//    }
//
//    public void printByCategory(String category){
//        map.entrySet().stream()
//                .filter(x -> x.getValue().equals(category))
//                .sorted(Comparator.comparing(x -> x.getKey().title.toLowerCase()))        //<---          da li ne mozes da koristes thenComparing ako rabotes so MapEntry?
//                .forEach(x -> System.out.println(x.getKey().toString()));                 // ka probuvam da koristam thencomparing getKey stanuva crveno
//    }
//
//    public List<Book> getCheapestN(int n){
//        return map.entrySet().stream()
//                .sorted(Comparator.comparing(x -> x.getKey().price))
//                .limit(n)
//                .map(Map.Entry::getKey)
//                .collect(Collectors.toList());
//    }
//}


public class BooksTest {
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

// Вашиот код овде