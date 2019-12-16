package mk.ukim.finki.av8;

import java.util.*;

class Participant {

    String code;
    String name;
    int age;

    public Participant(String code, String name, int age) {

        this.code = code;
        this.name = name;
        this.age = age;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Participant that = (Participant) o;
        return code.equals(that.code);
    }

    @Override
    public int hashCode() {
        return Objects.hash(code);
    }

    public String toString () {
        return String.format("%s %s %d", code, name, age);
    }
}

class Audition {
    Map<String, Set<Participant>> participantsByCity;

    public Audition() {
        participantsByCity = new HashMap<>();
    }

    public void addParticpant(String city, String code, String name, int age) {

        participantsByCity.computeIfAbsent(city, (k) -> new HashSet<>());

        Set<Participant> participantHashSet = participantsByCity.get(city);
        Participant newParticipant = new Participant(code,name, age);
        if (!participantHashSet.contains(newParticipant)) {
            participantHashSet.add(newParticipant);
        }

    }

    public void listByCity(String city) {

        Comparator<Participant> participantComparator = Comparator.comparing(Participant::getName)
                .thenComparing(Participant::getAge)
                .thenComparing(Participant::getCode);
        participantsByCity.get(city)
                .stream()
                .sorted(participantComparator)
                .forEach(System.out::println);
    }
}

public class AuditionTest {
    public static void main(String[] args) {
        Audition audition = new Audition();
        List<String> cities = new ArrayList<String>();
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(";");
            if (parts.length > 1) {
                audition.addParticpant(parts[0], parts[1], parts[2],
                        Integer.parseInt(parts[3]));
            } else {
                cities.add(line);
            }
        }
        for (String city : cities) {
            System.out.printf("+++++ %s +++++\n", city);
            audition.listByCity(city);
        }
        scanner.close();
    }
}