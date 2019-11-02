package mk.ukim.finki.np.av2;

class CombinationNotValid extends Exception {

    CombinationNotValid(String combination) {
        super("Combination " + combination + " is not valid!!!!");
    }
}

class CombinationLock {

    private String combination;
    private boolean opened;

    public CombinationLock(String combination) throws CombinationNotValid {
        if (!isCombinationValid(combination))
            throw new CombinationNotValid(combination);
        this.combination = combination;
        opened = false;
    }

    public boolean open(String userCombination) {
        if (this.combination.equals(userCombination)) {
            this.opened = true;
            return true;
        } else {
            return false;
        }
    }

    public boolean changeCombo(String oldCombination, String newCombination) {
        if (this.combination.equals(oldCombination)) {
            this.combination = newCombination;
            this.opened=false;
            return true;
        } else {
            return false;
        }
    }

    public static boolean isCombinationValid(String combination) {
        if (combination.length() != 3)
            return false;

        for (int i = 0; i < 3; i++) {
            if (!Character.isDigit(combination.charAt(i)))
                return false;
        }

        return true;
    }

    public boolean isOpened() {
        return opened;
    }

    @Override
    public String toString() {
        return "CombinationLock{" +
                "combination='" + combination + '\'' +
                ", opened=" + opened +
                '}';
    }
}

public class CombinationLockTest {

    public static void main(String[] args) {

        CombinationLock lock1 = null;
        CombinationLock lock2 = null;
        CombinationLock lock3 = null;
        try {
            lock1 = new CombinationLock("213");
            lock2 = new CombinationLock("12*"); //exception is being throw.
            lock3 = new CombinationLock("123");
        } catch (CombinationNotValid combinationNotValid) {
            System.out.println(combinationNotValid.getMessage());
        }

        System.out.println(lock1);

        System.out.println(lock1.open("123"));
        System.out.println(lock1.open("213"));
        System.out.println(lock1.changeCombo("123","123"));
        System.out.println(lock1.changeCombo("213","123"));
        System.out.println(lock1.isOpened());
        System.out.println(lock1.open("123"));
        System.out.println(lock1.isOpened());

    }
}
