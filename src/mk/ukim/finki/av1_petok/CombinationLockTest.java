package mk.ukim.finki.av1;

class WrongComboException extends Exception {

    WrongComboException(String wrongCombination) {
        super(String.format("The combination %s is wrong.", wrongCombination));;
    }
}

class CombinationLock {
    private String combo;
    boolean opened;

    public CombinationLock(String combo) {
        this.combo = combo;
        this.opened = false;
    }

    public boolean open (String entryCombination)  {

        if (this.combo.equals(entryCombination)) {
            opened=true;
            return true;
        }
        else {
            try {
                throw new WrongComboException(entryCombination);
            } catch (WrongComboException e) {
                System.out.println(e.getMessage());
            }

            System.out.println(String.format("The combination %s is wrong.", entryCombination));
        }

        return false;
    }

    public boolean changeCombo (String oldCombo, String newCombo) throws WrongComboException {

        if (this.combo.equals(oldCombo)) {
            this.combo=newCombo;
            return true;
        }
        else {
            throw new WrongComboException(oldCombo);
        }

    }
}

public class CombinationLockTest {

    public static void main(String[] args) {

        CombinationLock combinationLock = new CombinationLock("123");

//        try {
//            System.out.println(combinationLock.open("12*"));
//            System.out.println(combinationLock.open("123"));
//        }


    }
}
