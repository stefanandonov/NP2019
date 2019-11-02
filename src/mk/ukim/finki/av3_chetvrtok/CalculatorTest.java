package mk.ukim.finki.av3;

import java.util.Scanner;

class UnsupportedOperatorException extends Exception {


    public UnsupportedOperatorException(Character operator) {
        super(String.format("Operator %c is not supported", operator.charValue()));
    }
}
class Calculator {
    double result;

    public Calculator() {
        result=0.0;
        System.out.println("New calculator initialized :)");
    }

    public void evaluateExpression (Character operator, Double operand) throws UnsupportedOperatorException {

        switch (operator) {
            case '+':
                result+=operand;
                break;
            case '-':
                result-=operand;
                break;
            case '/':
                result/=operand;
                break;
            case '*':
                result*=operand;
                break;
            default:
                throw new UnsupportedOperatorException(operator);
        }
        System.out.println("Operation succeeded! Continue with next expression");
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Calculator{");
        sb.append("result=").append(result);
        sb.append('}');
        return sb.toString();
    }
}

public class CalculatorTest {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Calculator calculator = new Calculator();


        while (sc.hasNextLine()) {

            String line = sc.nextLine();
            if (!(line.startsWith("R") || line.startsWith("y") || line.startsWith("n"))) {
                Character operator = line.charAt(0);
                Double operand = Double.parseDouble(line.substring(1));
                try {
                    calculator.evaluateExpression(operator,operand);
                } catch (UnsupportedOperatorException e) {
                    System.out.println(e.getMessage());
                }
            }
            else {
                if (line.toLowerCase().startsWith("r")) {
                    //REKET
                    System.out.println(calculator);
                    System.out.println("Do you wanna continue?");
                    String choice = sc.nextLine();
                    if (choice.equals("y")) {
                        calculator = new Calculator();
                    }
                    else {
                        System.out.println("Thank you good bye :(");
                        break;
                    }

                }
            }
        }
    }

}
