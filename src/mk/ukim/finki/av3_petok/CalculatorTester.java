package mk.ukim.finki.av3_petok;

import javax.naming.OperationNotSupportedException;
import java.util.Scanner;

class Calculator {
    double result;
    private static final IOperation SUM = (a,b) -> a+b;
    private static final IOperation DIFFERENCE = (a,b) -> a-b;
    private static final IOperation PRODUCT = (a, b) -> a*b;
    private static final IOperation DIVISION = (a, b) -> a/b;

    public Calculator() {
        System.out.println("New calculator created!");
        result = 0.0;
    }

    public void evaluateExpression (Character operator, double value) throws OperationNotSupportedException {
        switch (operator) {
            case '+':
                //result += value;
                result = SUM.execute(result,value);
                break;
            case '-':
                //result-=value;
                result = DIFFERENCE.execute(result,value);
                break;
            case '/':
                //result/=value;
                result = DIVISION.execute(result,value);
                break;
            case '*':
                //result*=value;
                result = PRODUCT.execute(result,value);
                break;
            default:
                throw new OperationNotSupportedException(
                        String.format("Operator %c is not supported", operator)
                );
        }

        System.out.println("Expression evaluation success " + this);
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Calculator{");
        sb.append("result=").append(result);
        sb.append('}');
        return sb.toString();
    }
}

public class CalculatorTester {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Calculator calculator = new Calculator();

        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            if (line.startsWith("R")) {
                System.out.println("Calculation stopped by user request! " + calculator);
                System.out.println("Do you wanna continue?? (y/n)");
                String choice = sc.nextLine();
                if (choice.equals("y")) {
                    calculator = new Calculator();
                }
                else {
                    break;
                }
            }
            else {
                Character operator = line.charAt(0);
                Double value = null;
                try {
                    value = Double.parseDouble(line.substring(1));
                }
                catch (Exception e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                try {
                    calculator.evaluateExpression(operator, value);
                } catch (OperationNotSupportedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
