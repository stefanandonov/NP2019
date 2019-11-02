package mk.ukim.finki.np.av2;

import java.util.Arrays;

public class MatrixTest {

    public static double sumWithStreams(double[][] a) {

        return Arrays.stream(a)
                .mapToDouble(array -> Arrays.stream(array).sum())
                .sum();

    }

    public static double averageWithStreams(double[][] a) {

        return Arrays.stream(a)
                .flatMapToDouble(array -> Arrays.stream(array))
                .average()
                .getAsDouble();
    }

    public static double sumRegular(double[][] a) {

        double sum = 0;

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a[0].length; j++) {
                sum += a[i][j];
            }
        }

        return sum;

    }

    public static double averageRegular(double[][] a) {
        return sumRegular(a) / (a.length * a[0].length);
    }

    public static void main(String[] args) {

    }
}
