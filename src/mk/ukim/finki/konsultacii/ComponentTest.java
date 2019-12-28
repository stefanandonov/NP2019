package mk.ukim.finki.konsultacii;

//package com.company;

import java.util.*;
import java.util.stream.Collectors;

class InvalidPositionException extends Exception {
    public InvalidPositionException(String message) {
        super(message);
    }
}

class Component {
    String color;
    int weight;
    Set<Component> componentSet;

    public Component(String color, int weight) {
        this.color = color;
        this.weight = weight;
        Comparator<Component> componentComparator = Comparator.comparing(Component::getWeight)
                .thenComparing(Component::getColor);
        componentSet = new TreeSet<>(componentComparator);
        //COMPARATOR FOR THE TREEMAP COMPARATOR
    }

    public void addComponent(Component component) {
        componentSet.add(component);
    }

    public String getColor() {
        return color;
    }

    public String toString(int indent) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < indent; i++)
            sb.append("-");
        sb.append(this.weight).append(":").append(this.color).append("\n");

        componentSet.forEach(component -> sb.append(component.toString(indent+3)));

        return sb.toString();

    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public void changeColor(int weight, String color) {
        if (this.weight<weight)
            this.color = color;

        componentSet.forEach(c -> c.changeColor(weight, color));
    }
}

class Window {
    String name;
    Map<Integer, Component> map;

    public Window(String name) {
        this.name = name;
        map = new HashMap<>();
    }

    public void addComponent(int position, Component component) throws InvalidPositionException {

        if (map.containsKey(position))
            throw new InvalidPositionException(String.format("Invalid position %d, alredy taken!", position));

        map.putIfAbsent(position, component);
    }

    @Override
    public String toString() {
        return String.format("WINDOW %s\n%s",
                name,
                map.entrySet().stream()
                        .map(entry -> String.format("%d:%s", entry.getKey(), entry.getValue().toString(0)))
                        .collect(Collectors.joining(""))

        );
    }

    public void changeColor(int weight, String color) {
        for (Component component : map.values()) {
            component.changeColor(weight, color);
        }
    }

    public void swichComponents(int pos1, int pos2) {

        Component c1 = map.get(pos1);
        Component c2 = map.get(pos2);

        map.put(pos1, c2);
        map.put(pos2,c1);
    }
}

public class ComponentTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String name = scanner.nextLine();
        Window window = new Window(name);
        Component prev = null;
        while (true) {
            try {
                int what = scanner.nextInt();
                scanner.nextLine();
                if (what == 0) {
                    int position = scanner.nextInt();
                    window.addComponent(position, prev);
                } else if (what == 1) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev = component;
                } else if (what == 2) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                    prev = component;
                } else if (what == 3) {
                    String color = scanner.nextLine();
                    int weight = scanner.nextInt();
                    Component component = new Component(color, weight);
                    prev.addComponent(component);
                } else if (what == 4) {
                    break;
                }

            } catch (InvalidPositionException e) {
                System.out.println(e.getMessage());
            }
            scanner.nextLine();
        }

        System.out.println("=== ORIGINAL WINDOW ===");
        System.out.println(window);
        int weight = scanner.nextInt();
        scanner.nextLine();
        String color = scanner.nextLine();
        window.changeColor(weight, color);
        System.out.println(String.format("=== CHANGED COLOR (%d, %s) ===", weight, color));
        System.out.println(window);
        int pos1 = scanner.nextInt();
        int pos2 = scanner.nextInt();
        System.out.println(String.format("=== SWITCHED COMPONENTS %d <-> %d ===", pos1, pos2));
        window.swichComponents(pos1, pos2);
        System.out.println(window);
    }
}

// вашиот код овде