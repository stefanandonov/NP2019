package mk.ukim.finki.av6_petok;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

interface Scalable {
    void scale (float scaleFactor);
}

interface Stackable {
    float weight ();
}

abstract class Shape implements Scalable, Stackable {
    String id;
    Color color;

    public Shape(String id, Color color) {
        this.id = id;
        this.color = color;
    }

    @Override
    public String toString() {
        return String.format("%5s %10s %10.2f\n", id, color.toString(), weight());
    }
}

class Circle extends Shape {
    float radius;

    public Circle(String id, Color color, float radius) {
        super(id, color);
        this.radius = radius;
    }

    @Override
    public void scale(float scaleFactor) {
        radius*=scaleFactor;
    }

    @Override
    public float weight() {
        return (float)(Math.PI * Math.pow(radius,2));
    }

    @Override
    public String toString() {
        return String.format("C: %s", super.toString());
    }
}

class Rectangle extends Shape {
    float width;
    float height;

    public Rectangle(String id, Color color, float width, float height) {
        super(id, color);
        this.width = width;
        this.height = height;
    }


    @Override
    public void scale(float scaleFactor) {
        width*=scaleFactor;
        height*=scaleFactor;
    }

    @Override
    public float weight() {
        return width*height;
    }

    @Override
    public String toString() {
        return String.format("R: %s", super.toString());
    }
}

class Canvas {
    List<Shape> shapes;

    Canvas() {
        shapes = new ArrayList<>();
    }

    private void addShape (Shape shape) {
        if (shapes.size()==0){
            shapes.add(shape);
            return ;
        }

        for (int i=0;i<shapes.size();i++) {
            if (shape.weight()>shapes.get(i).weight()) {
                shapes.add(i,shape);
                return ;
            }
        }

        shapes.add(shape);
    }

    public void add(String id, Color color, float radius) {
        Shape circle = new Circle(id, color, radius);
        addShape(circle);
    }

    public void add(String id, Color color, float width, float height) {
        Shape rectangle = new Rectangle(id, color, width, height);
        addShape(rectangle);
    }

    public void scale(String id, float scaleFactor) {
//        Shape shapeToDelete = null;
//
//        for (Shape s : shapes) {
//            if (s.id.equals(id)) {
//                shapeToDelete = s;
//            }
//        }
//
//        if (shapeToDelete==null)
//            return ;

        Optional<Shape> optional = shapes.stream().filter(s -> s.id.equals(id)).findFirst();
        if (optional.isPresent()) {
            Shape shapetoDelete = optional.get();
            shapes.remove(shapetoDelete);
            shapetoDelete.scale(scaleFactor);
            addShape(shapetoDelete);
        }

    }

    @Override
    public String toString() {
        return shapes.stream().map(Shape::toString).collect(Collectors.joining());
    }
}
enum Color {
    RED, GREEN, BLUE
}
public class ShapesTest {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Canvas canvas = new Canvas();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            String[] parts = line.split(" ");
            int type = Integer.parseInt(parts[0]);
            String id = parts[1];
            if (type == 1) {
                Color color = Color.valueOf(parts[2]);
                float radius = Float.parseFloat(parts[3]);
                canvas.add(id, color, radius);
            } else if (type == 2) {
                Color color = Color.valueOf(parts[2]);
                float width = Float.parseFloat(parts[3]);
                float height = Float.parseFloat(parts[4]);
                canvas.add(id, color, width, height);
            } else if (type == 3) {
                float scaleFactor = Float.parseFloat(parts[2]);
                System.out.println("ORIGNAL:");
                System.out.print(canvas);
                canvas.scale(id, scaleFactor);
                System.out.printf("AFTER SCALING: %s %.2f\n", id, scaleFactor);
                System.out.print(canvas);
            }

        }
    }
}
