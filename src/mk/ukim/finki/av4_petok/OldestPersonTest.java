package mk.ukim.finki.av4_petok;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

class Person implements Comparable<Person>{
    String name;
    int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public int compareTo(Person otherPerson) {
        if (Integer.compare(this.age,otherPerson.age)==0) {
            return this.name.compareTo(otherPerson.name);
        }
        else
            return Integer.compare(this.age,otherPerson.age);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Person{");
        sb.append("name='").append(name).append('\'');
        sb.append(", age=").append(age);
        sb.append('}');
        return sb.toString();
    }
}

class Persons {
    List<Person> personsList;

    public Persons() {
        personsList = new ArrayList<>();
    }

    public void addPerson(String line) {
        String [] parts  = line.split("\\s+");
        String name = parts[0];
        int age = Integer.parseInt(parts[1]);
        Person p =  new Person(name, age);
        personsList.add(p);
    }

    public Person findOldest () {
//        personsList.sort(Comparator.reverseOrder());
//        return personsList.get(0);

        return personsList.stream().max(Comparator.naturalOrder()).get();
    }
}

public class OldestPersonTest {

    public static void main(String[] args) throws FileNotFoundException {
        Persons persons = new Persons();

        BufferedReader br = new BufferedReader(
                new FileReader(
                        new File(
                                "/home/stefan5andonov/work/NP_2019_petok/src/mk/ukim/finki/av4_petok/persons.txt"
                        )
                )
        );

        br.lines().forEach(persons::addPerson);

        System.out.println(persons.findOldest());
    }


}
