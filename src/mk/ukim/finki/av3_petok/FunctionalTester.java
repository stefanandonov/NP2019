package mk.ukim.finki.av3_petok;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

interface Printable {
    void print();
}

interface IOperation {
    double execute (double a, double b);
}


public class FunctionalTester {

    public static void main(String[] args) {

//        //anonimna klasa
//        Printable printable = new Printable() {
//            @Override
//            public void print() {
//                System.out.println("Interface implemented with anonymous class");
//            }
//        };
//
//        //lambda expression
//        Printable printable1 = () -> {
//            System.out.println("Interface implemented with lambda expression!");
//        };
//
//        printable.print();
//        printable1.print();

        IOperation sum = (a,b) -> a+b;
        IOperation difference = (a,b) -> a-b;
        IOperation product = (a, b) -> a*b;
        IOperation division = (a, b) -> a/b;

        List<IOperation> operations = new ArrayList<>();
        operations.add(sum);
        operations.add(difference);
        operations.add(product);
        operations.add(division);


        double a = 5.1;
        double b = 3.2;

//        System.out.println(sum.execute(a,b));
//        System.out.println(difference.execute(a,b));
//        System.out.println(product.execute(a,b));
//        System.out.println(division.execute(a,b));

        operations.forEach(iOperation -> System.out.println(iOperation.execute(a,b)));

    }
}
