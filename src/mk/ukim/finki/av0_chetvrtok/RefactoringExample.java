package mk.ukim.finki.np.av1;

public class RefactoringExample {

    public static void main(String[] args) {

        int start = 10;
        int end = 100;
        boolean even = true;


        int sum = getSum(start, end, even);
        System.out.println(
                String.format("Sum of the %s numbers is: %d",
                        even ? "even" : "odd", sum ));

    }

    private static int getSum(int start, int end, boolean even) {
        int sum = 0;
        for (int i = start; i <= end; i++) {
            if (even) {
                if (i % 2 == 0) {
                    sum += i;
                }
            }
            else {
                if (i%2 != 0) {
                    sum+=i;
                }
            }
        }
        return sum;
    }
}
