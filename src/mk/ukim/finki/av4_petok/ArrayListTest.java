package mk.ukim.finki.av4_petok;

import java.util.*;

public class ArrayListTest {

    public static void main(String[] args) {
        List<Integer> integersList = new ArrayList<>();


        integersList.size(); //.length
        integersList.add(5); //add to the end
        integersList.get(0); //niza[i]
        integersList.add(1,6); //add to specific position
        integersList.addAll(Arrays.asList(5,6,7,8));
        System.out.println(integersList);
        System.out.println(integersList.size());


    }
}
