package mk.ukim.finki.konsultacii;

//package Np.IKol;

import java.util.Scanner;

public class MinAndMax {
    public static void main(String[] args) throws ClassNotFoundException {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        MinMax<String> strings = new MinMax<String>();
        for(int i = 0; i < n; ++i) {
            String s = scanner.next();
            strings.update(s);
        }
        System.out.println(strings);
        MinMax<Integer> ints = new MinMax<Integer>();
        for(int i = 0; i < n; ++i) {
            int x = scanner.nextInt();
            ints.update(x);
        }
        System.out.println(ints);
    }
}

class MinMax <T extends Comparable> {
    private T min;
    private T max;
    private int countElements;
    private int countChanges;

    public MinMax() {
        this.min = null;
        this.max = null;
        this.countElements = 0;
        this.countChanges = 0;

    }

    public void update(T element) {
        if(min == null&&max == null) {
            min = element;
            max = element;
            return;
        }
        if(min.compareTo(max) == 0) {
            if(element.compareTo(min) < 0) {
                min = element;
                return;
            }
            else if(element.compareTo(max) > 0) {
                max = element;
                return;
            }
        }
        countElements++;
        if(element.compareTo(max) == 0 || element.compareTo(min) == 0){
            countElements--;
            return;
        }
        if(element.compareTo(min) < 0) {
            min = element;
            return;
        }
        else if(element.compareTo(max) > 0) {
            max = element;
            return;
        }
    }

    public T max() {
        return this.max;
    }

    public T min() {
        return this.min;
    }

    @Override
    public String toString() {
        return min + " " + max + " " + countElements + "\n";
    }
}