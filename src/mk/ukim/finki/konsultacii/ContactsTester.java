package mk.ukim.finki.konsultacii;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;

abstract class Contact {
    protected String date;

    public Contact(String date) { // YYYY-MM-DD
        this.date = date;
    }

    public abstract boolean isNewerThan(Contact c);

    public abstract String getType();
}

///////////

class EmailContact extends Contact {
    protected String email;

    EmailContact(String date, String email) {
        super(date);
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public boolean isNewerThan(Contact em) {
        int thisYear = Integer.parseInt(this.date.substring(0, 3));
        int thisMonth = Integer.parseInt(this.date.substring(5, 7));
        int thisDay = Integer.parseInt(this.date.substring(8, 9));

        int emYear = Integer.parseInt(em.date.substring(0, 3));
        int emMonth = Integer.parseInt(em.date.substring(5, 7));
        int emDay = Integer.parseInt(em.date.substring(8, 9));

        if (thisYear > emYear) return true;
        else if (thisYear == emYear && thisMonth > emMonth) return true;
        else if (thisYear == emYear && thisMonth == emMonth && thisDay > emDay) return true;
        else return false;
    }

    @Override
    public String getType() {
        return "Email";
    }
}

////////////

class PhoneContact extends Contact {
    protected String phone;

    protected enum Operator {TMOBILE, ONE, VIP}

    public PhoneContact(String date, String phone) {
        super(date);
        this.phone = phone;
    }

    public String getPhone() { /// 07X/YYY-ZZZ
        return phone;
    }

    public Operator getOperator() {
        String operator = phone.substring(0, 3);

        if (operator.equals("070") || operator.equals("071") || operator.equals("072")) return Operator.values()[0];
        if (operator.equals("075") || operator.equals("076")) return Operator.values()[1];
        if (operator.equals("077") || operator.equals("078")) return Operator.values()[2];
        return null;
    }

    @Override
    public boolean isNewerThan(Contact pc) {
        int thisYear = Integer.parseInt(this.date.substring(0, 3));
        int thisMonth = Integer.parseInt(this.date.substring(5, 7));
        int thisDay = Integer.parseInt(this.date.substring(8, 9));

        int pcYear = Integer.parseInt(pc.date.substring(0, 4));
        int pcMonth = Integer.parseInt(pc.date.substring(5, 7));
        int pcDay = Integer.parseInt(pc.date.substring(8, 9));

        if (thisYear > pcYear) return true;
        else if (thisYear == pcYear && thisMonth > pcMonth) return true;
        else if (thisYear == pcYear && thisMonth == pcMonth && thisDay > pcDay) return true;
        else return false;
    }

    public String getType() {
        return "Phone";
    }
}

//////////

class Student {
    private String firstName, lastName, city;
    private int age;
    private long index;
    private Contact[] contacts;

    public Student(String firstName, String lastName, String city, int age, long index) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.city = city;
        this.age = age;
        this.index = index;
        contacts = new Contact[0];
    }

    public void addEmailContact(String date, String email) {
        Contact[] temp = new Contact[contacts.length + 1];
        for (int i = 0; i < contacts.length; i++) {
            temp[i] = contacts[i];
        }
        temp[contacts.length] = new EmailContact(date, email);
        contacts = temp;
    }

    public void addPhoneContact(String date, String phone) {
        Contact[] temp = new Contact[contacts.length + 1];
        for (int i = 0; i < contacts.length; i++) {
            temp[i] = contacts[i];
        }
        temp[contacts.length] = new PhoneContact(date, phone);
        contacts = temp;
    }

    public Contact[] getEmailContacts() {
        int counter = 0;
        for (int i = 0; i < contacts.length; i++) {
            if (contacts[i].getType().equals("Email"))
                counter++;
        }
        Contact[] temp = new Contact[counter];
        int j = 0;
        for (int i = 0; i < counter; i++) {
            if (contacts[i].getType().equals("Email"))
                temp[j++] = contacts[i];
        }
        return temp;
    }

    public Contact[] getPhoneContacts() {
        int counter = 0;
        for (int i = 0; i < contacts.length; i++) {
            if (contacts[i].getType().equals("Phone"))
                counter++;
        }
        Contact[] temp = new Contact[counter];
        int j = 0;
        for (int i = 0; i < counter; i++) {
            if (contacts[i].getType().equals("Phone"))
                temp[j++] = contacts[i];
        }
        return temp;
    }

    public Contact getLatestContact() {
        Contact latest = contacts[0];
        for (int i = 1; i < contacts.length; i++) {
            if (contacts[i].isNewerThan(latest))
                latest = contacts[i];
        }
        return latest;
    }

    public int getNumberOfContacts() {
        return contacts.length;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public String getCity() {
        return city;
    }

    public long getIndex() {
        return index;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"ime\":");
        sb.append("\"" + firstName + "\", ");
        sb.append("\"prezime\":");
        sb.append("\"" + lastName + "\", ");
        sb.append("\"vozrast\":");
        sb.append(age + ", ");
        sb.append("\"grad\":");
        sb.append("\"" + city + "\", ");
        sb.append("\"indeks\":");
        sb.append(index + ", ");
        sb.append("\"telefonskiKontakti\":");
        sb.append("[");
        for (int i = 0; i < getPhoneContacts().length; i++) {
            PhoneContact conHelp = (PhoneContact) getPhoneContacts()[i];
            sb.append("\"" + conHelp + "\"");
            if (i < getPhoneContacts().length - 1) {
                sb.append(", ");
            }
        }
        sb.append("], ");
        sb.append("\"emailKontakti\":");
        sb.append("[");
        for (int i = 0; i < getEmailContacts().length; i++) {
            EmailContact conHelp = (EmailContact) getEmailContacts()[i];
            sb.append("\"" + conHelp + "\"");
            if (i < getEmailContacts().length - 1) {
                sb.append(", ");
            }
        }
        sb.append("]}");
        return sb.toString();
    }
}

//////////

class Faculty {
    private String name;
    private Student[] students;

    public Faculty(String name, Student[] students) {
        this.name = name;
        this.students = students;
    }

    public int countStudentsFromCity(String cityName) {
        int counter = 0;
        for (int i = 0; i < students.length; i++) {
            if (students[i].getCity().equals(cityName)) counter++;
        }
        return counter;
    }

    public Student getStudent(long index) {
        int helpInd = 0;
        for (int i = 0; i < students.length; i++) {
            if (students[i].getIndex() == index)
                helpInd = i;
        }
        return students[helpInd];
    }

    public double getAverageNumberOfContacts() {
        double sum = 0.0;
        for (int i = 0; i < students.length; i++) {
            sum += students[i].getEmailContacts().length + students[i].getPhoneContacts().length;
        }
        return sum / students.length;
    }

    public Student getStudentWithMostContacts() {
        Student helpMax = students[0];
        for (int i = 1; i < students.length; i++) {
            if (students[i].getNumberOfContacts() > helpMax.getNumberOfContacts()) {
                helpMax = students[i];
            }
            if (students[i].getNumberOfContacts() == helpMax.getNumberOfContacts()) {
                if (students[i].getIndex() > helpMax.getIndex())
                    helpMax = students[i];
            }
        }
        return helpMax;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        sb.append("\"fakultet\":");
        sb.append("\"" + name + "\", ");
        sb.append("\"studenti\":[");
        for (int i = 0; i < students.length; ++i) {
            sb.append(students[i]);
            if (i < students.length - 1)
                sb.append(", ");
        }
        sb.append("]}");
        return sb.toString();
    }
}


//////


public class ContactsTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        Faculty faculty = null;

        int rvalue = 0;
        long rindex = -1;

        DecimalFormat df = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            rvalue++;
            String operation = scanner.next();

            switch (operation) {
                case "CREATE_FACULTY": {
                    String name = scanner.nextLine().trim();
                    int N = scanner.nextInt();

                    Student[] students = new Student[N];

                    for (int i = 0; i < N; i++) {
                        rvalue++;

                        String firstName = scanner.next();
                        String lastName = scanner.next();
                        String city = scanner.next();
                        int age = scanner.nextInt();
                        long index = scanner.nextLong();

                        if ((rindex == -1) || (rvalue % 13 == 0))
                            rindex = index;

                        Student student = new Student(firstName, lastName, city,
                                age, index);
                        students[i] = student;
                    }

                    faculty = new Faculty(name, students);
                    break;
                }

                case "ADD_EMAIL_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String email = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addEmailContact(date, email);
                    break;
                }

                case "ADD_PHONE_CONTACT": {
                    long index = scanner.nextInt();
                    String date = scanner.next();
                    String phone = scanner.next();

                    rvalue++;

                    if ((rindex == -1) || (rvalue % 3 == 0))
                        rindex = index;

                    faculty.getStudent(index).addPhoneContact(date, phone);
                    break;
                }

                case "CHECK_SIMPLE": {
                    System.out.println("Average number of contacts: "
                            + df.format(faculty.getAverageNumberOfContacts()));

                    rvalue++;

                    String city = faculty.getStudent(rindex).getCity();
                    System.out.println("Number of students from " + city + ": "
                            + faculty.countStudentsFromCity(city));

                    break;
                }

                case "CHECK_DATES": {

                    rvalue++;

                    System.out.print("Latest contact: ");
                    Contact latestContact = faculty.getStudent(rindex)
                            .getLatestContact();
                    if (latestContact.getType().equals("Email"))
                        System.out.println(((EmailContact) latestContact)
                                .getEmail());
                    if (latestContact.getType().equals("Phone"))
                        System.out.println(((PhoneContact) latestContact)
                                .getPhone()
                                + " ("
                                + ((PhoneContact) latestContact).getOperator()
                                .toString() + ")");

                    if (faculty.getStudent(rindex).getEmailContacts().length > 0
                            && faculty.getStudent(rindex).getPhoneContacts().length > 0) {
                        System.out.print("Number of email and phone contacts: ");
                        System.out
                                .println(faculty.getStudent(rindex)
                                        .getEmailContacts().length
                                        + " "
                                        + faculty.getStudent(rindex)
                                        .getPhoneContacts().length);

                        System.out.print("Comparing dates: ");
                        int posEmail = rvalue
                                % faculty.getStudent(rindex).getEmailContacts().length;
                        int posPhone = rvalue
                                % faculty.getStudent(rindex).getPhoneContacts().length;

                        try {
                            System.out.println(faculty.getStudent(rindex)
                                    .getEmailContacts()[posEmail].isNewerThan(faculty
                                    .getStudent(rindex).getPhoneContacts()[posPhone]));
                        }
                        catch (NullPointerException e) {
                            System.out.println(e.getMessage());
                            System.out.println(faculty.getStudent(rindex)
                                    .getEmailContacts()[posEmail]);
                            System.out.println(faculty
                                    .getStudent(rindex).getPhoneContacts()[posPhone]);
                        }
                    }

                    break;
                }

                case "PRINT_FACULTY_METHODS": {
                    System.out.println("Faculty: " + faculty.toString());
                    System.out.println("Student with most contacts: "
                            + faculty.getStudentWithMostContacts().toString());
                    break;
                }

            }

        }

        scanner.close();
    }
}
