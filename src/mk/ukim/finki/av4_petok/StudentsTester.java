package mk.ukim.finki.av4_petok;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

class Student {
    String name;
    String lastName;
    int exam1;
    int exam2;
    int exam3;


    public Student(String name, String lastName, int exam1, int exam2, int exam3) {
        this.name = name;
        this.lastName = lastName;
        this.exam1 = exam1;
        this.exam2 = exam2;
        this.exam3 = exam3;
    }

    public int totalPoints () {
        return (int) (0.25*exam1 + 0.30*exam2 + 0.45*exam3);
    }

    public char calculateGrade() {
        int total = totalPoints();

        if (total<50)
            return 'F';
        else if (total<60)
            return 'E';
        else if (total<70)
            return 'D';
        else if (total<80)
            return 'C';
        else if (total<90)
            return 'B';
        else
            return 'A';
    }

    @Override
    public String toString() {
        return String.format("%s %s %d %d %d %d %c", name, lastName, exam1, exam2,
                exam3, totalPoints(), calculateGrade());
    }
}


class StudentsCollection {
    List<Student> students = new ArrayList<>();
    int [] counters;

    public StudentsCollection() {
        students = new ArrayList<>();
        counters = new int [6];
        for (int i=0;i<6;i++) {
            counters[i]=0;
        }
    }

    public void addStudent (String input) {
        /*input example : Doe:John:100:100:100*/
        String [] parts = input.split(":");
        String name = parts[0];
        String lastName = parts[1];
        int exam1 = Integer.parseInt(parts[2]);
        int exam2 = Integer.parseInt(parts[3]);
        int exam3 = Integer.parseInt(parts[4]);
        Student student = new Student(name, lastName,exam1,exam2,exam3);
        students.add(student);
        char grade = student.calculateGrade();
        counters[grade-'A']++;
    }

    public void readStudents(InputStream fis) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String line;
        while ((line = br.readLine())!=null) {
            addStudent(line);
        }
    }

    public void writeOutput(OutputStream fos) {
        PrintWriter pw = new PrintWriter(fos);

        for (Student s : students) {
            pw.println(s.toString());
        }

        for (int i=0;i<6;i++) {
            pw.println(String.format("%c: %d", i+'A', counters[i]));
        }

        pw.flush();
    }
}

public class StudentsTester {

    public static void main(String[] args) throws IOException {
        StudentsCollection collection = new StudentsCollection();

        InputStream fis = new FileInputStream(new File("/home/stefan5andonov/work/NP_2019_petok/src/mk/ukim/finki/av4_petok/grades.txt"));
        collection.readStudents(fis);
        FileOutputStream fos = new FileOutputStream(new File("/home/stefan5andonov/work/NP_2019_petok/src/mk/ukim/finki/av4_petok/output.txt"));
        collection.writeOutput(fos);


    }




}
