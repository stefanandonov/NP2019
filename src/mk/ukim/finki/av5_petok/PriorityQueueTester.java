package mk.ukim.finki.av5_petok;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

class Node<T> implements Comparable<Node<T>> {
    T element;
    int priority;

    public Node(T element, int priority) {
        this.element = element;
        this.priority = priority;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("Node{");
        sb.append("element=").append(element);
        sb.append(", priority=").append(priority);
        sb.append('}');
        return sb.toString();
    }


    @Override
    public int compareTo(Node<T> otherNode) {
        return Integer.compare(this.priority, otherNode.priority);
    }
}

class PriorityQueue <T>{
    List<Node<T>> elements;

    PriorityQueue () {
        elements = new ArrayList<>();
    }

    public void add(T item, int priority) {
        Node<T> node = new Node<>(item, priority);
        elements.add(node);
    }

    public T remove() {
        if (elements.size()==0)
            return null;

        elements.sort(null);
        return elements.remove(elements.size()-1).element;
    }

    @Override
    public String toString() {
        return String.format("There are %d elements left\n", elements.size());
    }
}

public class PriorityQueueTester {

    public static void main(String[] args) {
        PriorityQueue<String> stringPriorityQueue = new PriorityQueue<>();

        stringPriorityQueue.add("NP",1);
        stringPriorityQueue.add("VP", -1);
        stringPriorityQueue.add("SP",0);
        stringPriorityQueue.add("OOP", 2);
        stringPriorityQueue.add("PV", -100);

        String result;

        while ((result=stringPriorityQueue.remove())!=null) {
            System.out.println(result);
            System.out.println( stringPriorityQueue.toString());
        }

    }
}
