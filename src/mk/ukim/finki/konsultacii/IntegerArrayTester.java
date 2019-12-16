import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;


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

final class IntegerArray {
    final int[] array;
    final int length;

    public IntegerArray(int a[]) {

        this.length = a.length;
        this.array = new int [length];
        for (int i=0;i<length;i++) {
            array[i]=a[i];
        }
    }
    public int length() {
        return this.length;
    }
    public int getElementAt(int i) {
        return array[i];
    }
    public int sum() {
        int sum = 0;
        for (int i=0; i<this.length; i++)
            sum += array[i];
        return sum;
    }
    public double average() {
        double average = 0d;
        average = (double)this.sum() / this.length;
        return average;
    }
    public IntegerArray getSorted() {
        int[] newArray = this.array.clone();
        IntegerArray newObject = new IntegerArray(newArray);
        Arrays.sort(newObject.array);
        return  newObject;
    }
    public IntegerArray concat(IntegerArray ia) {
        int[] newArray = Arrays.copyOf(this.array, this.length + ia.length);
        for (int i=this.length; i<(this.length + ia.length); i++)
            newArray[i] = ia.array[i - ia.length];
        IntegerArray newObject = new IntegerArray(newArray);
        return newObject;
    }
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        for(int i=0; i<this.length-1; i++)
            sb.append(this.array[i] + ", ");
        sb.append(this.array[this.length-1] + "]");
        return sb.toString();
    }
    @Override
    public boolean equals(Object other) {
        if(this == other) return true;
        if(!(other instanceof IntegerArray)) return false;
        return Arrays.equals(this.array, ((IntegerArray) other).array);
    }
}

class ArrayReader {
    public static IntegerArray readIntegerArray(InputStream input) {
        Scanner scanner = new Scanner(input);
        int n = scanner.nextInt();
        int[] array = new int[n];
        for (int i=0;i<n;i++)
            array[i] = scanner.nextInt();
        IntegerArray newObject = new IntegerArray(array);
        return newObject;
    }
}