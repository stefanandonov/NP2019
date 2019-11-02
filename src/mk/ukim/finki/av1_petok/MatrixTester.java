package mk.ukim.finki.av1;

import java.util.Arrays;

public class MatrixTester {

    public static double sum(double[][] a) {
        double sum = 0;
        for (int i=0;i<a.length;i++) {
            for (int j=0;j<a[i].length;j++) {
                sum+=a[i][j];
            }
        }

        return sum;

    }

    public static double sumWithStreams (double [][] a) {

        return Arrays.stream(a)
                .mapToDouble(array -> Arrays.stream(array)
                        .sum())
                .sum();

    }
    public static double average(double[][] a) {
        return sum(a)/(a.length * a[0].length);
    }

    public static void main(String[] args) {

    }
}
