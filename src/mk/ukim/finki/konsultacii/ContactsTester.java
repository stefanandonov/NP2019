package mk.ukim.finki.konsultacii;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Scanner;



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

                    if (faculty.getStudent(rindex).getEmailContacts().length > 0&&faculty.getStudent(rindex).getPhoneContacts().length > 0) {
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

                        System.out.println(faculty.getStudent(rindex)
                                .getEmailContacts()[posEmail].isNewerThan(faculty
                                .getStudent(rindex).getPhoneContacts()[posPhone]));
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


abstract class Contact{
    String date;
    int day, month, year;

    public Contact(String date_){
        date = date_;
        day = Integer.parseInt(date.substring(8, 10));
        month = Integer.parseInt(date.substring(5, 7));
        year = Integer.parseInt(date.substring(0, 4));
    }

    public boolean isNewerThan(Contact c){
        if(year > c.year){
            return true;
        }
        if(year < c.year){
            return false;
        }
        if(month > c.month){
            return true;
        }
        if(month < c.month){
            return false;
        }
        if(day > c.day){
            return true;
        }
        return false;
//        if(year < c.year){
//            return true;
//        }
//        else {
//            if (year <= c.year) {
//                if (month < c.month) {
//                    return true;
//                } else if (day < c.day) {
//                    return true;
//                }
//            }
//        }
//        return false;
    }

    public abstract String getType();
}
class EmailContact extends Contact{
    String email;

    public EmailContact(String date, String email_){
        super(date);
        email = email_;
    }

    public String getEmail(){
        return email;
    }

    public String getType(){
        return "Email";
    }
}

class PhoneContact extends Contact{
    String phone;
    enum Operator{VIP, ONE, TMOBILE};
    Operator operator;

    public PhoneContact(String date, String phone_){
        super(date);
        phone = phone_;
        if(phone_.charAt(2) == '0' || phone_.charAt(2) == '1' || phone_.charAt(2) == '2'){
            this.operator =  Operator.TMOBILE;
        }
        else if(phone_.charAt(2) == '5' || phone_.charAt(2) == '6'){
            this.operator =  Operator.ONE;
        }
        else {
            this.operator =  Operator.VIP;
        }
    }

    public String getPhone(){
        return phone;
    }

    public Operator getOperator(){
        return this.operator;
    }

    public String getType(){
        return "Phone";
    }
}

class Student{
    String ime, prezime, grad;
    int vozrast, numberContacts, phoneContacts, emailContacts;
    long indeks;
    Contact contact[];

    public Student(String firstName, String lastName, String city, int age, long index){
        ime = firstName;
        prezime = lastName;
        vozrast = age;
        grad = city;
        indeks = index;
        numberContacts = 0;
        phoneContacts = 0;
        emailContacts = 0;
    }

    public void addEmailContact(String date, String email){
        Contact tmp[] = new Contact[numberContacts+1];

        for(int i = 0; i<numberContacts; i++){
            tmp[i] = contact[i];
        }

        tmp[numberContacts++] = new EmailContact(date, email);
        contact = tmp;
        emailContacts++;
    }

    public void addPhoneContact(String date, String phone){
        Contact tmp[] = new Contact[numberContacts+1];

        for(int i = 0; i<numberContacts; i++){
            tmp[i] = contact[i];
        }

        tmp[numberContacts++] = new PhoneContact(date, phone);
        contact = tmp;
        phoneContacts++;
    }

    public Contact[] getEmailContacts(){
        Contact email[] = new Contact[emailContacts];
        int i = 0;
        for(Contact x: contact){
            if(x.getType().equals("Email")){
                email[i++] = x;
            }
        }
        return email;
    }

    public Contact[] getPhoneContacts(){
        Contact phone[] = new Contact[phoneContacts];
        int i = 0;
        for(Contact x: contact){
            if(x.getType().equals("Phone")){
                phone[i++] = x;
            }
        }
        return phone;
    }

    public String getCity(){
        return  grad;
    }

    public String getFullName(){
        return (ime+ " " + prezime).toUpperCase();
    }

    public long getIndex(){
        return indeks;
    }

    public Contact getLatestContact(){
        int newest = 0;
        for(int i = 1; i<numberContacts; i++){
            if(contact[i].isNewerThan(contact[newest])){
                newest = i;
            }
        }
        return contact[newest];
    }

    @Override
    public String toString() {
        //JSON reprezentacija

        StringBuilder sb = new StringBuilder();
        sb.append("{\"ime\":\"").append(ime).append("\", \"prezime\":\"").append(prezime);
        sb.append("\", \"vozrast\":").append(vozrast).append(", \"grad\":\"").append(grad);
        sb.append("\", \"indeks\":").append(indeks).append(", \"telefonskiKontakti\":[");

        PhoneContact[] pc = (PhoneContact[]) getPhoneContacts();
        for (int i=0; i<phoneContacts-1; i++){
            sb.append("\"").append(pc[i].getPhone()).append("\", ");
        }
        if (phoneContacts>0)
            sb.append("\"").append(pc[phoneContacts-1].getPhone()).append("\"");
        sb.append("], ");

        sb.append("\"emailKontakti\":[");
        EmailContact[] ec = (EmailContact[]) getEmailContacts();
        for (int i=0; i<emailContacts-1; i++){
            sb.append("\"").append(ec[i].getEmail()).append("\", ");
        }
        if (emailContacts>0)
            sb.append("\"").append(ec[emailContacts-1].getEmail()).append("\"");
        sb.append("]}");

        return sb.toString();
    }
}

class Faculty{
    String name;
    Student student[];

    public Faculty(String name_, Student student_[]){
        name = name_;
        student = student_;
    }

    public int countStudentsFromCity(String cityName){
        int counter = 0;
        for(Student x: student){
            if(x.getCity().equals(cityName)){
                counter++;
            }
        }
        return counter;
    }

    public Student getStudent(long index){
        Student tmp = null;
        for(Student x: student){
            if(x.getIndex() == index){
                tmp = x;
            }
        }
        return tmp;
    }

    public double getAverageNumberOfContacts(){
        int number = 0;
        for(Student x: student){
            number += x.numberContacts;
        }
        return (double)number/student.length;
    }

    public Student getStudentWithMostContacts(){
        int najmnogu = 0, indeks = 0;
        for(int i = 0; i<student.length; i++){
            if(student[i].numberContacts > najmnogu){
                najmnogu = student[i].numberContacts;
                indeks = i;
            }
        }
        return student[indeks];
    }

    @Override
    public String toString() {
        //JSON reprezentacija

        StringBuilder sb = new StringBuilder();
        sb.append("{\"fakultet\":\"").append(name).append("\", \"studenti\":[");
        for (int i=0; i<student.length-1; i++) {
            sb.append(student[i].toString()).append(", ");
        }
        sb.append(student[student.length-1].toString()).append("]}");
        return sb.toString();
    }
}