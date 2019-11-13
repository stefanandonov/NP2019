
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.Random;
import java.util.Arrays;

import static java.util.Arrays.*;

class InsufficientElementsException extends Exception{
    public InsufficientElementsException(String message) {
        super(message);
    }
}
class InvalidRowNumberException extends Exception{
    public InvalidRowNumberException(String message) {
        super(message);
    }
}
class InvalidColumnNumberException extends Exception{
    public InvalidColumnNumberException(String message) {
        super(message);
    }
}

final class DoubleMatrix{
    private double [] [] matrix;

    public DoubleMatrix(double[] array,int m,int n) throws InsufficientElementsException {
        if (array.length>m*n){
            throw new InsufficientElementsException("Invalid column number");
        }
        else if(array.length<m*n){
            for (int i=0,j=0;i<array.length;i++,j++){
                int temp=m*n;
                while (temp>array.length){
                    j++;
                    temp--;
                }
                matrix[i%m][i/n]=array[i];
            }
        }
        else{
            for (int i=0;i<array.length;i++){
                matrix[i%m][i/n]=array[i];

            }
        }
    }
    public String getDimensions(){
        StringBuilder sb=new StringBuilder();
        sb.append("[").append(matrix.length).append(" x ")
                .append(matrix[0].length).append("]");
        return sb.toString();
    }
    public int rows(){
        return matrix.length;
    }
    public int columns(){
        return matrix[0].length;
    }
    public double maxElementAtRow(int row) throws InvalidRowNumberException {
        if (row<=0 || row>rows()){
            throw new InvalidRowNumberException("row ima vrednost od [1, "+rows()+"]");
        }
        else{
            double max=0;
            for (int i=0;i<columns();i++){
                if (matrix[i][row]>max)
                    max=matrix[i][row];
            }
            return max;
        }
    }
    public double maxElementAtColumn(int column) throws InvalidColumnNumberException {
        if (column<=0 || column>columns()){
            throw new InvalidColumnNumberException("column ima vrednost od [1, "+columns()+"]");
        }
        else{
            double max=0;
            for (int j=0;j<rows();j++){
                if (matrix[column][j]>max)
                    max=matrix[column][j];

            }
            return max;
        }
    }
    public double sum(){
        int sum=0;
        for (int i=0;i<matrix.length;i++){
            for (int j=0;i<matrix[0].length;i++){
                sum+=matrix[i][j];
            }
        }
        return sum;
    }
    public double[] toSortedArray(){
        double[] temp=new double[matrix.length*matrix[0].length];
        int k=0;
        for (int i=0;i<matrix.length;i++){
            for (int j=0;j<matrix[0].length;i++){
                temp[k]=matrix[i][j];
                k++;
            }
        }
        Arrays.sort(temp);
        return temp;
    }
    public String  toString(){
        StringBuilder sb=new StringBuilder();
        for (int i=0;i<matrix.length;i++){
            for (int j=0;j<matrix[0].length;j++){
                sb.append(String.format("%d.2\t"));
            }
            sb.append("\n");
        }
        return sb.toString();
    }


}
class MatrixReader{
    public static DoubleMatrix read(InputStream input) throws InsufficientElementsException {
        Scanner sc=new Scanner(input);

        int n=sc.nextInt();
        int m=sc.nextInt();
        double [] array=new double[n*m];
        for (int i=0;i<n*m;i++){
            array[i]=sc.nextDouble();
        }
        return new DoubleMatrix(array,n,m);
    }
}

public class DoubleMatrixTester {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);

        int tests = scanner.nextInt();
        DoubleMatrix fm = null;

        double[] info = null;

        DecimalFormat format = new DecimalFormat("0.00");

        for (int t = 0; t < tests; t++) {

            String operation = scanner.next();

            switch (operation) {
                case "READ": {
                    int N = scanner.nextInt();
                    int R = scanner.nextInt();
                    int C = scanner.nextInt();

                    double[] f = new double[N];

                    for (int i = 0; i < f.length; i++)
                        f[i] = scanner.nextDouble();

                    try {
                        fm = new DoubleMatrix(f, R, C);
                        info = Arrays.copyOf(f, f.length);

                    } catch (InsufficientElementsException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }

                    break;
                }

                case "INPUT_TEST": {
                    int R = scanner.nextInt();
                    int C = scanner.nextInt();

                    StringBuilder sb = new StringBuilder();

                    sb.append(R + " " + C + "\n");

                    scanner.nextLine();

                    for (int i = 0; i < R; i++)
                        sb.append(scanner.nextLine() + "\n");

                    fm = MatrixReader.read(new ByteArrayInputStream(sb
                            .toString().getBytes()));

                    info = new double[R * C];
                    Scanner tempScanner = new Scanner(new ByteArrayInputStream(sb
                            .toString().getBytes()));
                    tempScanner.nextDouble();
                    tempScanner.nextDouble();
                    for (int z = 0; z < R * C; z++) {
                        info[z] = tempScanner.nextDouble();
                    }

                    tempScanner.close();

                    break;
                }

                case "PRINT": {
                    System.out.println(fm.toString());
                    break;
                }

                case "DIMENSION": {
                    System.out.println("Dimensions: " + fm.getDimensions());
                    break;
                }

                case "COUNT_ROWS": {
                    System.out.println("Rows: " + fm.rows());
                    break;
                }

                case "COUNT_COLUMNS": {
                    System.out.println("Columns: " + fm.columns());
                    break;
                }

                case "MAX_IN_ROW": {
                    int row = scanner.nextInt();
                    try {
                        System.out.println("Max in row: "
                                + format.format(fm.maxElementAtRow(row)));
                    } catch (InvalidRowNumberException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                    break;
                }

                case "MAX_IN_COLUMN": {
                    int col = scanner.nextInt();
                    try {
                        System.out.println("Max in column: "
                                + format.format(fm.maxElementAtColumn(col)));
                    } catch (InvalidColumnNumberException e) {
                        System.out.println("Exception caught: " + e.getMessage());
                    }
                    break;
                }

                case "SUM": {
                    System.out.println("Sum: " + format.format(fm.sum()));
                    break;
                }

                case "CHECK_EQUALS": {
                    int val = scanner.nextInt();

                    int maxOps = val % 7;

                    for (int z = 0; z < maxOps; z++) {
                        double work[] = Arrays.copyOf(info, info.length);

                        int e1 = (31 * z + 7 * val + 3 * maxOps) % info.length;
                        int e2 = (17 * z + 3 * val + 7 * maxOps) % info.length;

                        if (e1 > e2) {
                            double temp = work[e1];
                            work[e1] = work[e2];
                            work[e2] = temp;
                        }

                        DoubleMatrix f1 = fm;
                        DoubleMatrix f2 = new DoubleMatrix(work, fm.rows(),
                                fm.columns());
                        System.out
                                .println("Equals check 1: "
                                        + f1.equals(f2)
                                        + " "
                                        + f2.equals(f1)
                                        + " "
                                        + (f1.hashCode() == f2.hashCode()&&f1
                                        .equals(f2)));
                    }

                    if (maxOps % 2 == 0) {
                        DoubleMatrix f1 = fm;
                        DoubleMatrix f2 = new DoubleMatrix(new double[]{3.0, 5.0,
                                7.5}, 1, 1);

                        System.out
                                .println("Equals check 2: "
                                        + f1.equals(f2)
                                        + " "
                                        + f2.equals(f1)
                                        + " "
                                        + (f1.hashCode() == f2.hashCode() && f1
                                        .equals(f2)));
                    }

                    break;
                }

                case "SORTED_ARRAY": {
                    double[] arr = fm.toSortedArray();

                    String arrayString = "[";

                    if (arr.length > 0)
                        arrayString += format.format(arr[0]) + "";

                    for (int i = 1; i < arr.length; i++)
                        arrayString += ", " + format.format(arr[i]);

                    arrayString += "]";

                    System.out.println("Sorted array: " + arrayString);
                    break;
                }

            }

        }

        scanner.close();
    }
}