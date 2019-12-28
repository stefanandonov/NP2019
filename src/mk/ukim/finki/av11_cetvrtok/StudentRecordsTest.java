package mk.ukim.finki.av11_cetvrtok;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

class Student {
    String code;
    String program;
    List<Integer> grades;

    public Student(String code, String program, List<Integer> grades) {
        this.code = code;
        this.program = program;
        this.grades = grades;
    }

    public static Student createStudent(String input) {
        String [] parts = input.split("\\s+");
        String code = parts[0];
        String program = parts[1];
        List<Integer> grades = Arrays.stream(parts)
                .skip(2)
                .mapToInt(Integer::parseInt)
                .boxed()
                .collect(Collectors.toList());

        return new Student (code, program, grades);
    }

    public String getCode() {
        return code;
    }

    public String getProgram() {
        return program;
    }

    public List<Integer> getGrades() {
        return grades;
    }

    public double getAverage () {
        return grades.stream().
                mapToInt(i -> i)
                .average()
                .orElse(0);
    }

    public String toString() {
        return String.format("%s %.2f", code, getAverage());
    }
}

class Program {
    String name;
    Map<Integer, Integer> gradesCountingMap;

    public Program(String name) {
        this.name = name;
        gradesCountingMap = new TreeMap<>();
        IntStream.range(6,11).forEach(i -> gradesCountingMap.put(i,0));
    }

    public void addGradeByStudent (Student student) {
        student.getGrades().forEach(grade -> {
            int gradeCount = gradesCountingMap.get(grade);
            gradesCountingMap.put(grade, gradeCount+1);
//            gradesCountingMap.computeIfPresent(grade, (k, v) -> {
//            v++;
//            return v;
//            });
        });
    }

    private static String stars (Map.Entry<Integer,Integer> entry) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("%2d", entry.getKey())).append(" | ");

        int count = entry.getValue();
        int temp = count;
        while (temp>0) {
            sb.append("*");
            temp-=10;
        }

        sb.append("(").append(entry.getValue()).append(")");

        return sb.toString();

    }

    public int getAs () {
        return gradesCountingMap.get(10);
    }

    public String toString() {
        return String.format("%s\n%s",
                name,
                gradesCountingMap.entrySet()
                        .stream()
                        .map(Program::stars).collect(Collectors.joining("\n")));
    }


}

class StudentRecords {

    static StudentRecords instance;
    Map<String, TreeSet<Student>> studentsByProgram;
    Map<String, Program> gradesByProgram;
    private static Comparator<Student> studentComparator = Comparator.comparingDouble(Student::getAverage)
            .reversed().thenComparing(Student::getCode);


    public static StudentRecords getInstance() {
        if (instance==null) {
            instance = new StudentRecords();
        }
        return instance;
    }

    private StudentRecords () {
        studentsByProgram = new TreeMap<>();
        gradesByProgram = new HashMap<>();
    }

    public int readRecords(InputStream in) {

        List<Student>  students= new BufferedReader(new InputStreamReader(in))
                .lines()
                .map(Student::createStudent)
                .collect(Collectors.toList());

        for (Student student : students) {
            String programName = student.getProgram();

            studentsByProgram.putIfAbsent(programName, new TreeSet<>(studentComparator));
            studentsByProgram.get(programName).add(student);

            gradesByProgram.putIfAbsent(programName, new Program(programName));
            gradesByProgram.get(programName).addGradeByStudent(student);

        }

        return students.size();
    }

    public void writeTable(OutputStream out) {
        PrintWriter pw = new PrintWriter(out);
        studentsByProgram.entrySet().stream().forEach(entry -> {
            pw.println(entry.getKey());
            entry.getValue().forEach(pw::println);
        });

        pw.flush();
    }


    public void writeDistribution(OutputStream out) {
        PrintWriter pw = new PrintWriter(out);
        Comparator<Program> programComparator = Comparator.comparingInt(Program::getAs).reversed();
        gradesByProgram.values().stream().sorted(programComparator).forEach(pw::println);

        pw.flush();
    }
}

/**
 * January 2016 Exam problem 1
 */
public class StudentRecordsTest {
    public static void main(String[] args) {
        System.out.println("=== READING RECORDS ===");
        StudentRecords studentRecords = StudentRecords.getInstance();
        int total = studentRecords.readRecords(System.in);
        System.out.printf("Total records: %d\n", total);
        System.out.println("=== WRITING TABLE ===");
        studentRecords.writeTable(System.out);
        StudentRecords studentRecords1 = StudentRecords.getInstance();
        System.out.println("=== WRITING DISTRIBUTION ===");
        studentRecords1.writeDistribution(System.out);
    }
}

// your code here