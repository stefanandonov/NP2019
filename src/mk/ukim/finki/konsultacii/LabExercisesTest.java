package mk.ukim.finki.konsultacii;

import java.util.*;
import java.util.stream.Collectors;

public class LabExercisesTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        LabExercises labExercises = new LabExercises();
        while (sc.hasNextLine()) {

            String input = sc.nextLine();
            String[] parts = input.split("\\s+");
            String index = parts[0];
            List<Integer> points = Arrays.stream(parts).skip(1)
                    .mapToInt(Integer::parseInt)
                    .boxed()
                    .collect(Collectors.toList());

            labExercises.addStudent(new Student(index, points));
        }

        System.out.println("===printByAveragePoints (ascending)===");
        labExercises.printByAveragePoints(true, 100);
        System.out.println("===printByAveragePoints (descending)===");
        labExercises.printByAveragePoints(false, 100);
        System.out.println("===failed students===");
        labExercises.failedStudents().forEach(System.out::println);
        System.out.println("===statistics by year");
        labExercises.getStatisticsByYear().entrySet().stream()
                .map(entry -> String.format("%d : %.2f", entry.getKey(), entry.getValue()))
                .forEach(System.out::println);

    }
}

class Student{
    String index;
    List<Integer> points;

    public Student(String index, List<Integer> points) {
        this.index = index;

        this.points = points;


    }

    @Override
    public String toString() {
        return String.format("%s %s %.2f",index,potpis(),poeni());
    }
    public String potpis(){
        if(points.size()>=8){
            return "YES";
        }
        else{
            return  "NO";
        }

    }

    public String getIndex() {
        return index;
    }

    public double poeni(){
        //double sum = poeni.stream().mapToInt(i->i).sum();
        double suma=0;
        for(int i =0;i<points.size();i++){
            suma+=points.get(i);//kako da stavam eden po eden poen
        }
        double average=suma/10.0;
        return average;
    }
    public int godiste(){
        int prvidve= Integer.parseInt(index.substring(0,2));
        return 20-prvidve;
    }

}
class LabExercises{
    List<Student> set;

    public LabExercises() {
        this.set=new ArrayList<>();
    }

    public void addStudent(Student student){
        set.add(student);

    }
    public void printByAveragePoints (boolean ascending, int n){

        if(ascending){

            Comparator<Student> comparator=Comparator.comparing(Student::poeni).thenComparing(Student::getIndex);
            set.stream().sorted(comparator).limit(n).forEach(System.out::println);
        }
        else{
            Comparator<Student> comparator1=Comparator.comparing(Student::poeni).thenComparing(Student::getIndex).reversed();
            set.stream().sorted(comparator1).limit(n).forEach(System.out::println);
        }

    }
    public List<Student> failedStudents (){
        Comparator<Student> comparator2=Comparator.comparing(Student::getIndex).thenComparing(Student::poeni);
        return  set.stream().filter(student -> student.potpis().equals("NO")).sorted(comparator2).collect(Collectors.toList());
    }
    public Map<Integer,Double> getStatisticsByYear(){
        // tie so nemaat potpis,posle od drugite da vidime koaj god se,i posle od sekoaj god da napraviem suma na site poeni i prosek (kolku vk studenti ima u taa god)


        Map<Integer,Double> sumMap=new TreeMap<>();
        Map<Integer, Integer> countMap = new TreeMap<>();

        set.stream().filter(student -> student.potpis().equals("YES")).forEach(student -> {
            sumMap.putIfAbsent(student.godiste(), 0.0);
            countMap.putIfAbsent(student.godiste(), 0);

            sumMap.computeIfPresent(student.godiste(), (k,v) -> v+=student.poeni());
            countMap.computeIfPresent(student.godiste(), (k,v) -> ++v);
        });
        //set.stream().forEach(student -> student.godiste());

        sumMap.entrySet().forEach(entry -> entry.setValue(entry.getValue()/countMap.get(entry.getKey())));
        return sumMap;
    }



}


//public Map<Integer,Double> getStatisticsByYear() - метод којшто враќа мапа од просекот од сумарните поени на студентите според година на студирање.
// Да се игнорираат студентите кои не добиле потпис