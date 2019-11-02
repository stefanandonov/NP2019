package mk.ukim.finki.np.av1;


import java.util.Objects;

class Person implements Comparable<Person>{
    private String firstName;
    private String lastName;

    public Person() {
        firstName="no first name";
        lastName = "no last name";
    }

    public Person(String firstName) {
        this.firstName = firstName;
        this.lastName = "UNKNOWN";
    }

    public Person(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

//    @Override
//    public String toString() {
//        //return "Person with first name: " + firstName + "and last name: " + lastName;
//
//        return String.format("Person with first name: %s and last name: %s", firstName, lastName);
//    }

    @Override
    public String toString() {
        return "Person{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return Objects.equals(firstName, person.firstName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName);
    }

    @Override
    public int compareTo(Person otherPerson) {
        return this.firstName.compareTo(otherPerson.firstName);
    }
}

public class PersonTest {

    public static void main(String[] args) {


        Person person1 = new Person();
        Person person2 = new Person("Stefan");
        Person person3 = new Person("Stefan", "Andonov");

        System.out.println("Person 1 is: " + person1);
        System.out.println("Person 2 is: " + person2);
        System.out.println("Person 3 is: " + person3);

        String name1 = "Stefan Andonov";
        String name2 = "Stefan Andonovski";

        System.out.println(name1.equals(name2));
    }
}
