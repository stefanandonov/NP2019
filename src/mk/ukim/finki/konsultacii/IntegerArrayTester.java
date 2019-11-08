package mk.ukim.finki.konsultacii;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

import java.io.InputStream;
import java.util.Arrays;
import java.util.Scanner;

final class IntegerArray{
    private int[] a;

    public IntegerArray(int[] a) {
        this.a = new int[a.length];
        for(int i = 0; i < a.length; i++){
            this.a[i] = a[i];
        }
    }

    public int length(){
        return a.length;
    }

    public int getElementAt(int i){
        return a[i];
    }

    public int sum(){
        int sum = 0;

        for(int i = 0; i < a.length; i++){
            sum += a[i];
        }

        return sum;
    }

    public double average(){
        return (sum() * 1.0) / a.length;
    }

    public IntegerArray getSorted(){
        int [] b = Arrays.copyOf(a, a.length);
        Arrays.sort(b);
        return new IntegerArray(b);
    }

    public IntegerArray concat(IntegerArray ia){
        int[] temp = new int[a.length + ia.length()];
        IntegerArray concatenated = new IntegerArray(temp);

        for(int i = 0; i < a.length; i++){
            concatenated.a[i] = this.a[i];
        }

        for(int i = 0; i < ia.length(); i++){
            concatenated.a[i + a.length] = ia.getElementAt(i);
        }

        return concatenated;
    }

    @Override
    public String toString() {
        String result = new String();
        result += "[";

        for(int i = 0; i < a.length; i++){
            result += a[i];
            if(i != a.length - 1){
                result += ", ";
            }
            else{
                result += "]";
            }
        }

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj == null){
            return false;
        }

        IntegerArray that = (IntegerArray) obj;

        if(a.length != that.length()){
            return false;
        }

        for(int i = 0; i < a.length; i++){
            if(a[i] != that.getElementAt(i)){
                return false;
            }
        }

        return true;
    }
}

class ArrayReader{
    public static IntegerArray readIntegerArray(InputStream input){
        Scanner s = new Scanner(input);

        int length = s.nextInt();

        int[] a = new int[length];

        for(int i = 0; i < length; i ++){
            a[i] = s.nextInt();
        }

        return new IntegerArray(a);
    }
}

public class IntegerArrayTester {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String s = scanner.nextLine();
        IntegerArray ia = null;
        switch (s) {
            case "testSimpleMethods":
                ia = new IntegerArray(generateRandomArray(scanner.nextInt()));
                testSimpleMethods(ia);
                break;
            case "testConcat":
                testConcat(scanner);
                break;
            case "testEquals":
                testEquals(scanner);
                break;
            case "testSorting":
                testSorting(scanner);
                break;
            case "testReading":
                testReading(new ByteArrayInputStream(scanner.nextLine().getBytes()));
                break;
            case "testImmutability":
                int a[] = generateRandomArray(scanner.nextInt());
                ia = new IntegerArray(a);
                testSimpleMethods(ia);
                testSimpleMethods(ia);
                IntegerArray sorted_ia = ia.getSorted();
                testSimpleMethods(ia);
                testSimpleMethods(sorted_ia);
                sorted_ia.getSorted();
                testSimpleMethods(sorted_ia);
                testSimpleMethods(ia);
                a[0] += 2;
                testSimpleMethods(ia);
                ia = ArrayReader.readIntegerArray(new ByteArrayInputStream(integerArrayToString(ia).getBytes()));
                testSimpleMethods(ia);
                break;
        }
        scanner.close();
    }

    static void testReading(InputStream in) {
        IntegerArray read = ArrayReader.readIntegerArray(in);
        System.out.println(read);
    }

    static void testSorting(Scanner scanner) {
        int[] a = readArray(scanner);
        IntegerArray ia = new IntegerArray(a);
        System.out.println(ia.getSorted());
    }

    static void testEquals(Scanner scanner) {
        int[] a = readArray(scanner);
        int[] b = readArray(scanner);
        int[] c = readArray(scanner);
        IntegerArray ia = new IntegerArray(a);
        IntegerArray ib = new IntegerArray(b);
        IntegerArray ic = new IntegerArray(c);
        System.out.println(ia.equals(ib));
        System.out.println(ia.equals(ic));
        System.out.println(ib.equals(ic));
    }

    static void testConcat(Scanner scanner) {
        int[] a = readArray(scanner);
        int[] b = readArray(scanner);
        IntegerArray array1 = new IntegerArray(a);
        IntegerArray array2 = new IntegerArray(b);
        IntegerArray concatenated = array1.concat(array2);
        System.out.println(concatenated);
    }

    static void testSimpleMethods(IntegerArray ia) {
        System.out.print(integerArrayToString(ia));
        System.out.println(ia);
        System.out.println(ia.sum());
        System.out.printf("%.2f\n", ia.average());
    }


    static String integerArrayToString(IntegerArray ia) {
        StringBuilder sb = new StringBuilder();
        sb.append(ia.length()).append('\n');
        for (int i = 0; i < ia.length(); ++i)
            sb.append(ia.getElementAt(i)).append(' ');
        sb.append('\n');
        return sb.toString();
    }

    static int[] readArray(Scanner scanner) {
        int n = scanner.nextInt();
        int[] a = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = scanner.nextInt();
        }
        return a;
    }


    static int[] generateRandomArray(int k) {
        Random rnd = new Random(k);
        int n = rnd.nextInt(8) + 2;
        int a[] = new int[n];
        for (int i = 0; i < n; ++i) {
            a[i] = rnd.nextInt(20) - 5;
        }
        return a;
    }

}
