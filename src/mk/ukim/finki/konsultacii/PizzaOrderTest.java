package mk.ukim.finki.konsultacii;
import java.util.Scanner;

interface Item{
    String getType();
    int getPrice();
}

class InvalidExtraTypeException extends Exception{
    public InvalidExtraTypeException(){

    }
}

class InvalidPizzaTypeException extends Exception{
    public InvalidPizzaTypeException(){

    }
}

class ItemOutOfStockException extends Exception{
    public ItemOutOfStockException(Item item){

    }
}

class OrderLockedException extends Exception{
    public OrderLockedException(){

    }
}

class EmptyOrder extends Exception{
    public EmptyOrder(String msg){

    }
}

class ArrayIndexOutOfBоundsException extends Exception{
    public ArrayIndexOutOfBоundsException(int n){

    }
}



class ExtraItem implements Item{
    String type;

    public ExtraItem(String type_) throws InvalidExtraTypeException{
        if(type_.equals("Ketchup") || type_.equals("Coke")){
            type = type_;
        }
        else{
            throw new InvalidExtraTypeException();
        }
    }

    @Override
    public String getType(){
        return type;
    }

    @Override
    public int getPrice(){
        if(type.equals("Coke")){
            return 5;
        }
        return 3;
    }
}

class PizzaItem implements Item{
    String type;

    public PizzaItem(String type_) throws InvalidPizzaTypeException{
        if(type_.equals("Standard") || type_.equals("Pepperoni") || type_.equals("Vegetarian")){
            type = type_;
        }
        else{
            throw new InvalidPizzaTypeException();
        }
    }

    @Override
    public String getType(){
        return type;
    }

    @Override
    public int getPrice(){
        if(type.equals("Standard")){
            return 10;
        }
        if(type.equals("Pepperoni")){
            return 12;
        }
        return 8;
    }
}

class Order{
    Item items[];
    int n[];
    boolean locked;

    public Order(){
        items = new Item[0];
        n = new int[0];
        locked = false;
    }
    public void addItem(Item item, int count) throws ItemOutOfStockException, OrderLockedException{
        if(locked){
            throw new OrderLockedException();
        }
        if(count > 10){
            throw new ItemOutOfStockException(item);
        }
        int indeks = alreadyInOrder(item);
        if(indeks == -1){
            Item tmp[] = new Item[items.length+1];
            int tmp1[] = new int[n.length+1];
            System.arraycopy(items, 0, tmp, 0, items.length);
            System.arraycopy(n, 0, tmp1, 0, n.length);
            tmp[items.length] = item;
            tmp1[n.length] = count;
            items = tmp;
            n = tmp1;
        }
        else{
            n[indeks] = count; // test primero ne rabote valjda od tuka
        }

    }

    public int alreadyInOrder(Item item){
        for(int i = 0; i<items.length; i++){
            if(items[i].getType().equals(item.getType())){
                return i;
            }
        }
        return -1;
    }

    public int getPrice(){
        int sum = 0;
        for(int i = 0; i<items.length; i++){
            sum += items[i].getPrice() * n[i];
        }
        return sum;
    }

    public void displayOrder(){
        StringBuilder sb = new StringBuilder();
        for (int i=0; i<items.length; i++){
            sb.append(String.format("%3d.%-15sx%2d%5d$\n", i+1,items[i].getType(),n[i], items[i].getPrice() * n[i])); // ne mee jasno tuka
        }
        sb.append(String.format("%-22s%5d$","Total:",getPrice()));// i tuka
        System.out.println(sb.toString());
    }

    public void removeItem(int indeks) throws ArrayIndexOutOfBоundsException, OrderLockedException{
        if(locked){
            throw new OrderLockedException();
        }
        if(indeks < 0 || indeks >= items.length){
            throw new ArrayIndexOutOfBоundsException(indeks);
        }
        Item tmp[] = new Item[items.length-1];
        int tmp1[] = new int[n.length-1];
        int j = 0;
        for(int i = 0; i<items.length; i++){
            if(i != indeks){
                tmp[j] = items[i];
                tmp1[j] = n[i];
                j++;
            }
        }
        items = tmp;
        n = tmp1;
    }

    public void lock() throws EmptyOrder{
        if(items.length > 0){
            locked = true;
        }
        else{
            throw new EmptyOrder("EmptyOrder");
        }
    }


}


public class PizzaOrderTest {

    public static void main(String[] args) {
        Scanner jin = new Scanner(System.in);
        int k = jin.nextInt();
        if (k == 0) { //test Item
            try {
                String type = jin.next();
                String name = jin.next();
                Item item = null;
                if (type.equals("Pizza")) item = new PizzaItem(name);
                else item = new ExtraItem(name);
                System.out.println(item.getPrice());
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
        if (k == 1) { // test simple order
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 2) { // test order with removing
            Order order = new Order();
            while (true) {
                try {
                    String type = jin.next();
                    String name = jin.next();
                    Item item = null;
                    if (type.equals("Pizza")) item = new PizzaItem(name);
                    else item = new ExtraItem(name);
                    if (!jin.hasNextInt()) break;
                    order.addItem(item, jin.nextInt());
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            jin.next();
            System.out.println(order.getPrice());
            order.displayOrder();
            while (jin.hasNextInt()) {
                try {
                    int idx = jin.nextInt();
                    order.removeItem(idx);
                } catch (Exception e) {
                    System.out.println(e.getClass().getSimpleName());
                }
            }
            System.out.println(order.getPrice());
            order.displayOrder();
        }
        if (k == 3) { //test locking & exceptions
            Order order = new Order();
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.addItem(new ExtraItem("Coke"), 1);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.lock();
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
            try {
                order.removeItem(0);
            } catch (Exception e) {
                System.out.println(e.getClass().getSimpleName());
            }
        }
    }

}