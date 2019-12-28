//package mk.ukim.finki.konsultacii;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Scanner;
//import java.util.stream.Collectors;
//import java.util.stream.IntStream;
//
//class IntegerList{
//    List<Integer> integerArrayList;
//
//    public IntegerList() {
//        integerArrayList = null;
//    }
//
//    public IntegerList(Integer[] a){
//        integerArrayList = new ArrayList<>();
//                // dali e dobra praksa da se koristi assert?
//        integerArrayList.addAll(Arrays.asList(a));
//    }
//
//    public IntegerList (IntegerList other) {
//        this.integerArrayList = new ArrayList<>();
//        this.integerArrayList.addAll(other.integerArrayList);
//    }
//
//    public void add(int el, int idx){
//        integerArrayList.add(idx, el);
//    }
//
//    public void remove(int idx){
//        integerArrayList.remove(idx);
//    }
//
//    public void set(int el, int idx){
//        integerArrayList.set(idx, el);
//    }
//
//    public int get(int idx){
//        return integerArrayList.get(idx);
//    }
//
//    public int size(){
//        return integerArrayList.size();
//    }
//
//    public int count(int el){
//        return (int)integerArrayList.stream().filter(x -> x.equals(el)).count();
//    }
//
//    public void removeDuplicates(){
//
//        integerArrayList = integerArrayList.stream().distinct().collect(Collectors.toList());
////        for(int i = 0; i<integerArrayList.size()-1; i++){
////            for(int j = i+1; j<integerArrayList.size(); j++){
////                if(integerArrayList.get(i).equals(integerArrayList.get(j))){
////                    integerArrayList.remove(j);
////                }
////            }
////        }
//    }
//
//    public int sumFirst(int k){
//        return integerArrayList.stream().limit(k).mapToInt(Integer::valueOf).sum();
//    }
//
//    public int sumLast(int k){
//        return integerArrayList.stream().skip(k).mapToInt(Integer::valueOf).sum();
//    }
//
//    public IntegerList addValue(int nextInt) {
//
//    }
//
//    public void swap(int i, int j){
//        int tmp = i;
//        i = j;
//        j = tmp;
//    }
//
//    public void shiftRight(int idx, int k){
//        swap(integerArrayList.get(idx), integerArrayList.get(idx+k));
//    }
//
//    public void shiftLeft(int idx, int k){
//        swap(integerArrayList.get(idx), integerArrayList.get(idx-k));
//    }
//
//    public IntegerList addValue(int value){
////        return new IntegerList(
////                integerArrayList.stream()
////                        .map(objectInteger -> new Integer(objectInteger + value))
////                        .collect(Collectors.toCollection(ArrayList::new)) //.collect(Collectors.toList()))
////        );
//    }
//}
//
//public class IntegerListTest {
//
//    public static void main(String[] args) {
//        Scanner jin = new Scanner(System.in);
//        int k = jin.nextInt();
//        if ( k == 0 ) { //test standard methods
//            int subtest = jin.nextInt();
//            if ( subtest == 0 ) {
//                IntegerList list = new IntegerList();
//                while ( true ) {
//                    int num = jin.nextInt();
//                    if ( num == 0 ) {
//                        list.add(jin.nextInt(), jin.nextInt());
//                    }
//                    if ( num == 1 ) {
//                        list.remove(jin.nextInt());
//                    }
//                    if ( num == 2 ) {
//                        print(list);
//                    }
//                    if ( num == 3 ) {
//                        break;
//                    }
//                }
//            }
//            if ( subtest == 1 ) {
//                int n = jin.nextInt();
//                Integer a[] = new Integer[n];
//                for ( int i = 0 ; i < n ; ++i ) {
//                    a[i] = jin.nextInt();
//                }
//                IntegerList list = new IntegerList(a);
//                print(list);
//            }
//        }
//        if ( k == 1 ) { //test count,remove duplicates, addValue
//            int n = jin.nextInt();
//            Integer a[] = new Integer[n];
//            for ( int i = 0 ; i < n ; ++i ) {
//                a[i] = jin.nextInt();
//            }
//            IntegerList list = new IntegerList(a);
//            while ( true ) {
//                int num = jin.nextInt();
//                if ( num == 0 ) { //count
//                    System.out.println(list.count(jin.nextInt()));
//                }
//                if ( num == 1 ) {
//                    list.removeDuplicates();
//                }
//                if ( num == 2 ) {
//                    print(list.addValue(jin.nextInt()));
//                }
//                if ( num == 3 ) {
//                    list.add(jin.nextInt(), jin.nextInt());
//                }
//                if ( num == 4 ) {
//                    print(list);
//                }
//                if ( num == 5 ) {
//                    break;
//                }
//            }
//        }
//        if ( k == 2 ) { //test shiftRight, shiftLeft, sumFirst , sumLast
//            int n = jin.nextInt();
//            Integer a[] = new Integer[n];
//            for ( int i = 0 ; i < n ; ++i ) {
//                a[i] = jin.nextInt();
//            }
//            IntegerList list = new IntegerList(a);
//            while ( true ) {
//                int num = jin.nextInt();
//                if ( num == 0 ) { //count
//                    list.shiftLeft(jin.nextInt(), jin.nextInt());
//                }
//                if ( num == 1 ) {
//                    list.shiftRight(jin.nextInt(), jin.nextInt());
//                }
//                if ( num == 2 ) {
//                    System.out.println(list.sumFirst(jin.nextInt()));
//                }
//                if ( num == 3 ) {
//                    System.out.println(list.sumLast(jin.nextInt()));
//                }
//                if ( num == 4 ) {
//                    print(list);
//                }
//                if ( num == 5 ) {
//                    break;
//                }
//            }
//        }
//    }
//
//    public static void print(IntegerList il) {
//        if ( il.size() == 0 ) System.out.print("EMPTY");
//        for ( int i = 0 ; i < il.size() ; ++i ) {
//            if ( i > 0 ) System.out.print(" ");
//            System.out.print(il.get(i));
//        }
//        System.out.println();
//    }
//
//}