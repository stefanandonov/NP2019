package mk.ukim.finki.av10_final;

import java.util.*;
import java.util.stream.Collectors;

class Contact {
    String contactName;
    String phoneNumber;
    List<String> groups;

    public Contact(String contactName, String phoneNumber) {
        this.contactName = contactName;
        this.phoneNumber = phoneNumber;
        groups = new ArrayList<>();
    }

    public String toString () {
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s %s", contactName, phoneNumber));

        if (!groups.isEmpty()) {
            sb.append(String.format(" Groups: %s", String.join(", ", groups)));
        }

        return sb.toString();
    }

    public void addGroup (String groupName) {
        groups.add(groupName);
    }

    public void removeGroup (String groupName) {
        groups.remove(groupName);
    }
}

class PhoneDirectory {
    Map<String, Contact> allContacts;
    Map<String, List<Contact>> contactsByGroup;

    public PhoneDirectory() {
        allContacts = new HashMap<>();
        contactsByGroup = new HashMap<>();
    }

    public void addContact(String contactName, String phoneNumber) {
        if ((phoneNumber.length()==9 && phoneNumber.startsWith("07")) ||
                (phoneNumber.length()==12 && phoneNumber.startsWith("+3897"))) {
            Contact contact = new Contact(contactName, phoneNumber);
            allContacts.put(contactName, contact);
        }

    }

    public void addContactToGroup(String contactName, String groupName) {
        Contact contact = allContacts.get(contactName);
        contact.addGroup(groupName);

        contactsByGroup.putIfAbsent(groupName, new ArrayList<>());
        contactsByGroup.get(groupName).add(contact);
    }

    public int getNumberOfContacts() {
        return allContacts.size();
    }

    public List<Contact> retrieveNContacts(int N, String format, boolean ascending) {

        Comparator<Contact> contactComparator = Comparator.comparing(contact -> contact.phoneNumber);

        if (!ascending)
            contactComparator = contactComparator.reversed();

        return allContacts.values().stream()
                .filter(contact -> contact.phoneNumber.startsWith(format))
                .sorted(contactComparator)
                .limit(N)
                .collect(Collectors.toList());
    }

    public double getAverageNumberOfContactsPerGroup() {

        return contactsByGroup.values().stream()
                .mapToInt(List::size)
                .average()
                .getAsDouble();
    }

    public void deleteContactByName(String contactName) {
        Contact contact = allContacts.get(contactName);
        List<String> groupsOfUser = contact.groups;
        allContacts.remove(contactName);

        for (String group : groupsOfUser) {
            contactsByGroup.get(group).removeIf(c -> c.contactName.equals(contactName));

        }
    }

    public List<Contact> getGetContactsByGroup(String groupName) {
        return contactsByGroup.get(groupName);
    }
}

public class PhoneDirectoryTester {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        PhoneDirectory phoneDirectory = new PhoneDirectory();
        int test = Integer.parseInt(scanner.nextLine());
        int N = Integer.parseInt(scanner.nextLine());

        while(N > 0) {
            N--;
            String line = scanner.nextLine();
            String[] parts = line.split("\\s+");

            if(parts[0].equals("addContact"))
                phoneDirectory.addContact(parts[1], parts[2]);

            if(parts[0].equals("addToGroup"))
                phoneDirectory.addContactToGroup(parts[1], parts[2]);
        }

        //adding contacts, validation and sorted printing
        if(test == 1) {
            System.out.println("=====Printing=====");
            phoneDirectory.retrieveNContacts(phoneDirectory.getNumberOfContacts(), "+3897", true).stream()
                    .forEach(System.out::println);
            System.out.println("=====Printing=====");
            phoneDirectory.retrieveNContacts(phoneDirectory.getNumberOfContacts(), "07", false).stream()
                    .forEach(System.out::println);
        }

        //printing contacts with groups and average contacts per group
        if(test == 2) {
            System.out.println("=====Printing=====");
            phoneDirectory.retrieveNContacts(phoneDirectory.getNumberOfContacts(), "+3897", false).stream()
                    .forEach(System.out::println);
            System.out.println("=====Printing=====");
            phoneDirectory.retrieveNContacts(phoneDirectory.getNumberOfContacts(), "07", true).stream()
                    .forEach(System.out::println);
            System.out.println("=====Avg per group=====");
            System.out.println(phoneDirectory.getAverageNumberOfContactsPerGroup());
        }

        //deleting contacts who are not part of a group
        if(test == 3) {
            phoneDirectory.deleteContactByName("Patrick");
            phoneDirectory.deleteContactByName("Katherine");
            phoneDirectory.deleteContactByName("Dexter");
            System.out.println("=====Printing=====");
            phoneDirectory.retrieveNContacts(phoneDirectory.getNumberOfContacts(), "+3897", true).stream()
                    .forEach(System.out::println);
            System.out.println("=====Printing=====");
            phoneDirectory.retrieveNContacts(2, "07", false).stream()
                    .forEach(System.out::println);
        }

        //deleting contacts part and not part of a group
        if(test == 4) {
            phoneDirectory.deleteContactByName("Dexter");
            phoneDirectory.deleteContactByName("Arya");
            phoneDirectory.deleteContactByName("Jon");
            System.out.println("=====Printing=====");
            phoneDirectory.retrieveNContacts(phoneDirectory.getNumberOfContacts(), "+3897", false).stream()
                    .forEach(System.out::println);
            System.out.println("=====Printing=====");
            phoneDirectory.retrieveNContacts(phoneDirectory.getNumberOfContacts(), "07", true).stream()
                    .forEach(System.out::println);
            System.out.println("=====Avg per group=====");
            System.out.println(phoneDirectory.getAverageNumberOfContactsPerGroup());
        }

        //getGetContactsByGroup
        if(test == 5) {
            System.out.println("=====Printing=====");
            phoneDirectory.getGetContactsByGroup("dzabesteri").stream()
                    .forEach(System.out::println);
            System.out.println("=====Printing=====");
            phoneDirectory.getGetContactsByGroup("noone").stream()
                    .forEach(System.out::println);
        }

        scanner.close();
    }
}