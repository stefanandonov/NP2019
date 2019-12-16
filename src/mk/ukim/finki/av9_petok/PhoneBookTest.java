//package mk.ukim.finki.av9_petok;
//
//import java.util.*;
//
//class DuplicateNumberException extends Exception {
//    DuplicateNumberException (String number) {
//        super (String.format("Duplicate number: [%s]", number));
//    }
//}
//
//class Contact {
//    String name;
//    String phoneNumber;
//
//    public Contact(String name, String phoneNumber) {
//        this.name = name;
//        this.phoneNumber = phoneNumber;
//    }
//
//    @Override
//    public String toString() {
//        final StringBuilder sb = new StringBuilder("Contact{");
//        sb.append("name='").append(name).append('\'');
//        sb.append(", phoneNumber='").append(phoneNumber).append('\'');
//        sb.append('}');
//        return sb.toString();
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public String getPhoneNumber() {
//        return phoneNumber;
//    }
//}
//
//class PhoneBook {
//    Set<String> allPhoneNumbers;
//    Map<String, Set<Contact>> contactsByPhoneParts;
//    Map<String, Set<Contact>> contactsByName;
//
//    Comparator<Contact> contactComparator = Comparator.comparing(Contact::getName)
//            .thenComparing(Contact::getPhoneNumber).;
//
//    public PhoneBook() {
//        allPhoneNumbers = new TreeSet<>();
//        contactsByPhoneParts = new TreeMap<>();
//        contactsByName = new HashMap<>();
//    }
//
//    void addContact(String name, String number) throws DuplicateNumberException {
//        if (allPhoneNumbers.contains(number))
//            throw new DuplicateNumberException(number);
//        allPhoneNumbers.add(number);
//
//        List<String> phoneParts = getPhoneParts(number);
//        for (String part : phoneParts) {
//            contactsByPhoneParts.putIfAbsent(part, new TreeSet<>(contactComparator));
//            contactsByPhoneParts.get(part).add(new Contact(name, number));
//        }
//
//        contactsByName.putIfAbsent(name, new TreeSet<>(contactComparator));
//        contactsByName.get(name).add(new Contact(name, number));
//
//    }
//
//    private List<String> getPhoneParts(String number) {
//        List<String> results = new ArrayList<>();
//
//        for (int i=3;i<=number.length();i++) {
//            for (int j=0;j<number.length()-i+1;j++) {
//                results.add(number.substring(j,j+i));
//            }
//        }
//
//        return results;
//    }
//
//    void contactsByNumber(String number) {
//        contactsByPhoneParts.get(number).forEach(System.out::println);
//    }
//
//    void contactsByName(String name) {
//
//        if (contactsByName.containsKey(name)) {
//            contactsByName.get(name).forEach(System.out::println);
//        }
////        contactsByName.getOrDefault(name, new TreeSet<>(contactComparator)).forEach(System.out::println);
//    }
//}
//public class PhoneBookTest {
//
//    public static void main(String[] args) {
//        PhoneBook phoneBook = new PhoneBook();
//
//        Scanner sc = new Scanner(System.in);
//
//        while (sc.hasNextLine()) {
//            String line = sc.nextLine();
//            String [] parts = line.split("\\s+");
//            String name = parts[0];
//            String [] numbers = Arrays.copyOfRange(parts,1,parts.length);
//
//            for (String number : numbers) {
//                try {
//                    phoneBook.addContact(name, number);
//                } catch (DuplicateNumberException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//
//            if (line.equals("END"))
//                break;
//
//        }
//
//        System.out.println("CONTACTS BY NUMBER");
//        phoneBook.contactsByNumber("076");
//        phoneBook.contactsByNumber("078");
//        phoneBook.contactsByNumber("077");
//        phoneBook.contactsByNumber("075");
//        phoneBook.contactsByNumber("070");
//
//        System.out.println("CONTACTS BY NAME");
//        phoneBook.contactsByName("Andrej");
//        phoneBook.contactsByName("Gajduk");
//        phoneBook.contactsByName("Tesst");
//        phoneBook.contactsByName("Sample");
//
//    }
//}
