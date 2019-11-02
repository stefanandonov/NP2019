package mk.ukim.finki.av3;

import java.util.Random;

class NegativeNumberException extends Exception{


    public NegativeNumberException(int thePositiveNumber) {
        super(String.format("The user tried to enter a " +
                "negative number %d into the positive number wrapper!!!1111",
                thePositiveNumber)
        );
    }
}
class PositiveNumber {
    int thePositiveNumber;

    public PositiveNumber(int thePositiveNumber) throws NegativeNumberException {
        if (thePositiveNumber<=0)
            throw new NegativeNumberException(thePositiveNumber);
        this.thePositiveNumber = thePositiveNumber;
    }
}

public class ExceptionTest {

    public static void main(String[] args) throws NegativeNumberException {

        Random random = new Random();
        for (int i=0;i<1000;i++) {
            int number = random.nextInt(20)-2;
            System.out.println(i);
            PositiveNumber positiveNumber = new PositiveNumber(number);
        }

    }


}
