package mk.ukim.finki.konsultacii;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Scanner;

class ObjectCanNotBeMovedException extends Exception {
    public ObjectCanNotBeMovedException(String message) {
        super(message);
    }
}

class MovableObjectNotFittableException extends Exception {
    public MovableObjectNotFittableException(String message) {
        super(message);
    }
}

enum TYPE {
    POINT,
    CIRCLE
};

enum DIRECTION {
    UP,
    DOWN,
    LEFT,
    RIGHT
};

interface Movable {
    public void moveUp() throws ObjectCanNotBeMovedException;

    public void moveRight() throws ObjectCanNotBeMovedException;

    public void moveDown() throws ObjectCanNotBeMovedException;

    public void moveLeft() throws ObjectCanNotBeMovedException;

    public int getCurrentXPosition() throws ObjectCanNotBeMovedException;

    public int getCurrentYPosition() throws ObjectCanNotBeMovedException;

    public TYPE getType();

    public boolean canFit(int xMax, int yMax);

}

class MovingPoint implements Movable {
    private int x;
    private int y;
    private int xSpeed;
    private int ySpeed;

    public MovingPoint(int x, int y, int xSpeed, int ySpeed) {
        this.x = x;
        this.y = y;
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
    }

    @Override
    public String toString() {
        return String.format("Movable point with coordinates (%d,%d)", x, y);
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        if (y + ySpeed > MovablesCollection.getY_MAX()) {
            throw new ObjectCanNotBeMovedException("Object can not be moved");
        } else {
            y = ySpeed;
        }
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        if (x + xSpeed > MovablesCollection.getX_MAX()) {
            throw new ObjectCanNotBeMovedException("Objet can not be moved");
        } else {
            x = x + xSpeed;
        }
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        if (y - ySpeed < 0) {
            throw new ObjectCanNotBeMovedException("Object can not be moved");
        }

    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        if (x - xSpeed < 0) {
            throw new ObjectCanNotBeMovedException("Object can not be moved");
        } else {
            x = x - xSpeed;
        }
    }

    @Override
    public int getCurrentXPosition() {
        return x;
    }

    @Override
    public int getCurrentYPosition() {
        return y;
    }

    @Override
    public TYPE getType() {
        return TYPE.POINT;
    }

    @Override
    public boolean canFit(int xMax, int yMax) {
        return x >= 0 && x <= xMax && y <= yMax && y >= 0;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getYSpeed() {
        return ySpeed;
    }

    public int getXSpeed() {
        return xSpeed;
    }
}

class MovingCircle implements Movable {
    int radius;
    MovingPoint center;

    public MovingCircle(int radius, MovingPoint center) {
        this.radius = radius;
        this.center = center;
    }

    @Override
    public String toString() {
        return String.format("Movable circle with center coordinates (%d,%d) and radius %d",
                center.getCurrentXPosition(), center.getCurrentYPosition(), radius);
    }

    @Override
    public void moveUp() throws ObjectCanNotBeMovedException {
        if (center.getCurrentYPosition() + center.getYSpeed() + radius > MovablesCollection.getY_MAX()) {
            throw new ObjectCanNotBeMovedException("Object can not be moved");
        } else {
            center.setY(center.getCurrentYPosition() + center.getYSpeed());
        }
    }

    @Override
    public void moveRight() throws ObjectCanNotBeMovedException {
        if (center.getXSpeed() + center.getCurrentXPosition() + radius > MovablesCollection.getX_MAX()) {
            throw new ObjectCanNotBeMovedException("Object can not be moved");
        } else {
            center.setX(center.getCurrentXPosition() + center.getXSpeed());
        }
    }

    @Override
    public void moveDown() throws ObjectCanNotBeMovedException {
        if (center.getCurrentYPosition() - center.getYSpeed() - radius < 0) {
            throw new ObjectCanNotBeMovedException("Object can not be moved");
        } else {
            center.setY(center.getCurrentYPosition() - center.getYSpeed());
        }
    }

    @Override
    public void moveLeft() throws ObjectCanNotBeMovedException {
        if (center.getCurrentXPosition() - center.getXSpeed() - radius < 0) {
            throw new ObjectCanNotBeMovedException("Object can not be moved");
        } else {
            center.setX(center.getCurrentXPosition() - center.getXSpeed());
        }
    }

    @Override
    public int getCurrentXPosition() {
        return 0;
    }

    @Override
    public int getCurrentYPosition() {
        return 0;
    }

    @Override
    public TYPE getType() {
        return TYPE.CIRCLE;
    }

    @Override
    public boolean canFit(int xMax, int yMax) {
        if (!center.canFit(xMax, yMax)) {
            return false;
        }

        if ((center.getX() - radius) < 0 || (center.getX() + radius) > xMax
                || (center.getY() - radius) < 0 || (center.getY() + radius) > yMax)
            return false;
        else
            return true;
    }
}

class MovablesCollection {
    private List<Movable> movables;
    private static int x_MAX;
    private static int y_MAX;

    public MovablesCollection(int x_MAX, int y_MAX) {
        MovablesCollection.x_MAX = x_MAX;
        MovablesCollection.y_MAX = y_MAX;
        movables = new ArrayList<>();
    }

    public static void setyMax(int x_MAX) {
        MovablesCollection.x_MAX = x_MAX;
    }

    public static void setxMax(int y_MAX) {
        MovablesCollection.y_MAX = y_MAX;
    }

    public static int getX_MAX() {
        return x_MAX;
    }

    public static int getY_MAX() {
        return y_MAX;
    }

    public void addMovableObject(Movable m) throws MovableObjectNotFittableException, ObjectCanNotBeMovedException {
        //How to check the type of the object???
        if (!m.canFit(x_MAX, y_MAX)) {
            throw new MovableObjectNotFittableException("This object can't be added");
        }
        movables.add(m);


    }

    public void moveObjectsFromTypeWithDirection(TYPE type, DIRECTION direction) throws ObjectCanNotBeMovedException {
        for (Movable i : movables) {
            if (i.getType() == type) {
                if (direction == DIRECTION.UP) {
                    i.moveUp();
                } else if (direction == DIRECTION.DOWN) {
                    i.moveDown();
                } else if (direction == DIRECTION.RIGHT) {
                    i.moveRight();
                } else if (direction == DIRECTION.LEFT) {
                    i.moveLeft();
                }
            }
        }
    }

    @Override
    public String toString() {
        String s = String.format("Collection of movable objects with size [%d]", movables.size());
        for (int i = 0; i < movables.size(); i++) {
            s = s + movables.get(i).toString();
        }
        return s;
    }

}

public class CirclesTest {

    public static void main(String[] args) {
        // write your code here
        System.out.println("===COLLECTION CONSTRUCTOR AND ADD METHOD TEST===");
        MovablesCollection collection = new MovablesCollection(100, 100);
        Scanner sc = new Scanner(System.in);
        int samples = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < samples; i++) {
            String inputLine = sc.nextLine();
            String[] parts = inputLine.split(" ");

            int x = Integer.parseInt(parts[1]);
            int y = Integer.parseInt(parts[2]);
            int xSpeed = Integer.parseInt(parts[3]);
            int ySpeed = Integer.parseInt(parts[4]);
            ////FIRST CATCH BLOCCKK ======================
            if (Integer.parseInt(parts[0]) == 0) { //point
                try {
                    collection.addMovableObject(new MovingPoint(x, y, xSpeed, ySpeed));
                } catch (MovableObjectNotFittableException e) {
                    System.out.println(e.getMessage());
                } catch (ObjectCanNotBeMovedException e) {
                    System.out.println(e.getMessage());
                }
            } else { //circle
                try {
                    int radius = Integer.parseInt(parts[5]);
                    collection.addMovableObject(new MovingCircle(radius, new MovingPoint(x, y, xSpeed, ySpeed)));
                } catch (MovableObjectNotFittableException e) {
                    System.out.println(e.getMessage());
                } catch (ObjectCanNotBeMovedException e) {
                    System.out.println(e.getMessage());
                }
            }

        }
        System.out.println(collection.toString());
        try {
            System.out.println("MOVE POINTS TO THE LEFT");
            collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.LEFT);
            System.out.println(collection.toString());
        } catch (ObjectCanNotBeMovedException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println("MOVE CIRCLES DOWN");
            collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.DOWN);
            System.out.println(collection.toString());
        } catch (ObjectCanNotBeMovedException e) {
            System.out.println(e.getMessage());
        }
        System.out.println("CHANGE X_MAX AND Y_MAX");
        MovablesCollection.setxMax(90);
        MovablesCollection.setyMax(90);
        try {
            System.out.println("MOVE POINTS TO THE RIGHT");
            collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.RIGHT);
            System.out.println(collection.toString());
        } catch (ObjectCanNotBeMovedException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println("MOVE CIRCLES UP");
            collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.UP);
            System.out.println(collection.toString());
        } catch (ObjectCanNotBeMovedException e) {
            System.out.println(e.getMessage());
        }
    }
}


