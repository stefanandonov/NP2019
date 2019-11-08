package mk.ukim.finki.av5_petok;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Box <T> {
    List<T> items;

    public Box() {
        items = new ArrayList<>();
    }

    public void add (T element) {
        items.add(element);
    }

    public boolean isEmpty () {
        return items.size()==0;
    }

    public T drawItem () {
        if (isEmpty())
            return null;

        Random random = new Random();
        int indexToDraw = random.nextInt(items.size());
        T result = items.get(indexToDraw);
        items.remove(indexToDraw);
        return result;
    }
}

public class BoxTester {

    public static void main(String[] args) {

        Box<Integer> box = new Box<>();

        box.add(1);
        box.add(2);
        box.add(3);
        box.add(4);
        box.add(5);box.add(7);box.add(8);
        box.add(9);
        box.add(10);

        Integer result;
        while ((result = box.drawItem())!=null) {
            System.out.println(result);
        }
        System.out.println("Drawing is over!");


    }
}
