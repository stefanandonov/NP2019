package mk.ukim.finki.av8;

import java.util.*;

class BirthdayParadox {
    int maxPeople;
    static int TRIALS = 50000;
    Random random = new Random();

    public BirthdayParadox(int maxPeople) {
        this.maxPeople = maxPeople;
    }

    public Map<Integer, Double> getProbabilities () {
        Map<Integer,Double> result = new TreeMap<>();
        for (int i=2;i<=maxPeople;i++) {
            double prob = getProbabilityFor(i);
            result.put(i, prob);
        }

        return result;
    }

    public static int getTRIALS() {
        return TRIALS;
    }

    public static void setTRIALS(int TRIALS) {
        BirthdayParadox.TRIALS = TRIALS;
    }

    private double getProbabilityFor(int peopleInRoom) {
        int positiveExperiments = 0;
        for (int i = 1; i<=TRIALS; i++) {
            boolean experimentResult = conductExperiment(peopleInRoom);
            if (experimentResult)
                positiveExperiments++;
        }

        return (float) positiveExperiments / TRIALS;

    }

    private boolean conductExperiment(int peopleInRoom) {
        Set<Integer> birthdays = new HashSet<>();
        int sameBirthdays = 0;
        for (int i=0;i<peopleInRoom;i++) {
            int birthday = random.nextInt(365)+1;
            if (birthdays.contains(birthday)) {
                sameBirthdays++;
            }
            birthdays.add(birthday);
        }

        return sameBirthdays >= 1;
    }
}

public class BirthdayParadoxTest {

    public static void main(String[] args) {
        BirthdayParadox birthdayParadox = new BirthdayParadox(50);
        birthdayParadox.getProbabilities().entrySet().forEach(entry -> {
            System.out.println(String
                    .format("For %d people, the probability of two birthdays is about %f",
                            entry.getKey(),
                            entry.getValue()
                    )
            );
        });
    }
}
