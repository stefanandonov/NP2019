package mk.ukim.finki.konsultacii;

import java.util.Scanner;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.TreeSet;

class IntegerList {

    private LinkedList<Integer> list;

    public IntegerList() {
        list = new LinkedList<Integer>();
    }

    public IntegerList( Integer... numbers ) {
        this();
        list.addAll(Arrays.asList(numbers));
    }

    public void add(Integer el , int idx) {
        while ( idx > list.size() ) {
            list.add(0);
        }
        list.add(idx,el);
    }

    public int remove( int idx) {
        return list.remove(idx);
    }

    public void set(int el , int idx ) {
        list.set(idx, el);
    }

    public int get( int idx ) {
        return list.get(idx);
    }

    public int count(int el){
        int counter = 0;
        for ( int e : list ) if ( e == el ) ++counter;
        return counter;
    }

    public int sumFirst(int k) {
        int result = 0;
        for ( Iterator<Integer> it = list.iterator(); it.hasNext()&&k > 0 ; --k )
            result += it.next();
        return result;
    }

    public int sumLast(int k) {
        int result = 0;
        for ( Iterator<Integer> it = list.descendingIterator(); it.hasNext()&&k > 0 ; --k )
            result += it.next();
        return result;
    }

    public IntegerList addValue ( int value ) {
        IntegerList result = new IntegerList();int k = 0;
        for ( Iterator<Integer> it = list.iterator();it.hasNext(); ++k )
            result.add(it.next()+value,k);
        return result;
    }

    public int size() {
        return list.size();
    }

}


public class IntegerListTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if ( k == 0 ) { //test standard methods
            int subtest = jin.nextInt();
            if ( subtest == 0 ) {
                IntegerList list = new IntegerList();
                while ( true ) {
                    int num = jin.nextInt();
                    if ( num == 0 ) {
                        list.add(jin.nextInt(), jin.nextInt());
                    }
                    if ( num == 1 ) {
                        list.remove(jin.nextInt());
                    }
                    if ( num == 2 ) {
                        print(list);
                    }
                    if ( num == 3 ) {
                        break;
                    }
                }
            }
            if ( subtest == 1 ) {
                int n = jin.nextInt();
                Integer a[] = new Integer[n];
                for ( int i = 0 ; i < n ; ++i ) {
                    a[i] = jin.nextInt();
                }
                IntegerList list = new IntegerList(a);
                print(list);
            }
        }
        if ( k == 1 ) { //test count,remove duplicates, addValue
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for ( int i = 0 ; i < n ; ++i ) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while ( true ) {
                int num = jin.nextInt();
                if ( num == 0 ) { //count
                    System.out.println(list.count(jin.nextInt()));
                }
                if ( num == 1 ) {
                    //list.removeDuplicates();
                }
                if ( num == 2 ) {
                    print(list.addValue(jin.nextInt()));
                }
                if ( num == 3 ) {
                    list.add(jin.nextInt(), jin.nextInt());
                }
                if ( num == 4 ) {
                    print(list);
                }
                if ( num == 5 ) {
                    break;
                }
            }
        }
        if ( k == 2 ) { //test shiftRight, shiftLeft, sumFirst , sumLast
            int n = jin.nextInt();
            Integer a[] = new Integer[n];
            for ( int i = 0 ; i < n ; ++i ) {
                a[i] = jin.nextInt();
            }
            IntegerList list = new IntegerList(a);
            while ( true ) {
                int num = jin.nextInt();
                if ( num == 0 ) { //count
                    //list.shiftLeft(jin.nextInt(), jin.nextInt());
                }
                if ( num == 1 ) {
                    //list.shiftRight(jin.nextInt(), jin.nextInt());
                }
                if ( num == 2 ) {
                    System.out.println(list.sumFirst(jin.nextInt()));
                }
                if ( num == 3 ) {
                    System.out.println(list.sumLast(jin.nextInt()));
                }
                if ( num == 4 ) {
                    print(list);
                }
                if ( num == 5 ) {
                    break;
                }
            }
        }
    }

    public static void print(IntegerList il) {
        if ( il.size() == 0 ) System.out.print("EMPTY");
        for ( int i = 0 ; i < il.size() ; ++i ) {
            if ( i > 0 ) System.out.print(" ");
            System.out.print(il.get(i));
        }
        System.out.println();
    }

}
