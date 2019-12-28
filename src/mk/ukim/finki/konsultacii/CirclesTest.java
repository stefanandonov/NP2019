//package mk.ukim.finki.konsultacii;
//
//import java.util.Scanner;
//import java.util.Arrays;
//
//enum TYPE {
//    POINT,
//    CIRCLE
//}
//
//enum DIRECTION {
//    UP,
//    DOWN,
//    LEFT,
//    RIGHT
//}
//
//class ObjectCanNotBeMovedException extends Exception {
//    ObjectCanNotBeMovedException(int x, int y) {
//        super(String.format("Point (%d,%d) is out of bounds", x,y));
//    }
//}
//
//class MovableObjectNotFittableException extends Exception {
//    MovableObjectNotFittableException(Movable m) {
//        super(m.toString() + " can not be fitted into the collection");
//    }
//}
//
//class MovablesCollection {
//    private Movable[] movables;
//
//    public static int X_MAX = 0;
//    public static int Y_MAX = 0;
//
//    public MovablesCollection(int X_MAX, int Y_MAX) {
//        MovablesCollection.X_MAX = X_MAX;
//        MovablesCollection.Y_MAX = Y_MAX;
//        movables = new Movable[0];
//    }
//
//    public static void setxMax(int i) {
//        MovablesCollection.X_MAX = i;
//    }
//
//    public static void setyMax(int i) {
//        MovablesCollection.Y_MAX = i;
//    }
//
//    void addMovableObject(Movable m) throws MovableObjectNotFittableException {
//        if (!canFit(m))
//            throw new MovableObjectNotFittableException(m);
//        movables = Arrays.copyOf(movables, movables.length+1);
//        movables[movables.length-1] = m;
//    }
//
//    private boolean canFit(Movable m) {
//        int x = m.getX();
//        int y = m.getY();
//        int r = 0;
//        if (x<0 || x>MovablesCollection.X_MAX || y<0 || y>MovablesCollection.Y_MAX)
//            return false;
//        if (m.getType() == TYPE.CIRCLE)
//            r = ((MovingCircle)m).getRadius();
//        if (x-r<0 || x+r>MovablesCollection.X_MAX || y-r<0 || y+r>MovablesCollection.Y_MAX)
//            return false;
//        return true;
//    }
//
//    public void moveObjectsFromTypeWithDirection(TYPE type, DIRECTION direction)  {
//        for (Movable m : movables)
//            if (m.getType()==type)
//                switch (direction) {
//                    case UP:    m.moveUp();    break;
//                    case DOWN:  m.moveDown();  break;
//                    case LEFT:  m.moveLeft();  break;
//                    case RIGHT: m.moveRight(); break;
//                }
//    }
//
//    @Override
//    public String toString() {
//        StringBuilder sb = new StringBuilder();
//        sb.append(String.format("Collection of movable objects with size %d:\n", movables.length));
//        for (Movable movable : movables)
//            sb.append(movable.toString() + "\n");
//        return sb.toString();
//    }
//}
//
//
//class MovingCircle implements Movable {
//    private int radius;
//    private MovingPoint center;
//
//    public MovingCircle(int radius, MovingPoint center) {
//        this.radius = radius;
//        this.center = center;
//    }
//    @Override
//    public void moveUp() throws ObjectCanNotBeMovedException {
////        if (center.getY() + center.getySpeed() + radius > MovablesCollection.Y_MAX) {
////            throw new ObjectCanNotBeMovedException(this);
////        }
//        center.moveUp();
//    }
//
//    @Override
//    public void moveDown() throws ObjectCanNotBeMovedException {
////        if (center.getY() - center.getySpeed() - radius < 0) {
////            throw new ObjectCanNotBeMovedException(this);
////        }
//        center.moveDown();
//    }
//
//    @Override
//    public void moveRight() throws ObjectCanNotBeMovedException {
////        if (center.getX() + center.getxSpeed() + radius > MovablesCollection.X_MAX) {
////            throw new ObjectCanNotBeMovedException(this);
////        }
//        center.moveRight();
//    }
//
//    @Override
//    public void moveLeft() throws ObjectCanNotBeMovedException {
////        if (center.getX() - center.getxSpeed() - radius < 0) {
////            throw new ObjectCanNotBeMovedException(this);
////        }
//        center.moveLeft();
//    }
//
//    @Override
//    public int getX() {
//        return center.getX();
//    }
//
//    @Override
//    public int getY() {
//        return center.getY();
//    }
//
//    public int getRadius() {
//        return radius;
//    }
//
//    @Override
//    public TYPE getType() {
//        return TYPE.CIRCLE;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("Movable circle with center (%d,%d) and radius %d",
//                center.getX(), center.getY(), radius);
//    }
//}
//
//class MovingPoint implements Movable {
//    private int x;
//    private int y;
//    private int xSpeed;
//    private int ySpeed;
//
//    public MovingPoint(int x, int y, int xSpeed, int ySpeed) {
//        this.x = x;
//        this.y = y;
//        this.xSpeed = xSpeed;
//        this.ySpeed = ySpeed;
//    }
//
//    public MovingPoint getCopy() {
//        MovingPoint point = new MovingPoint(x,y,xSpeed,ySpeed);
//        return point;
//    }
//
//    @Override
//    public void moveUp() throws ObjectCanNotBeMovedException {
//        if (y + ySpeed > MovablesCollection.Y_MAX) {
//            throw new ObjectCanNotBeMovedException(this.x, this.y+this.ySpeed);
//        }
//        y += ySpeed;
//    }
//
//    @Override
//    public void moveDown() throws ObjectCanNotBeMovedException {
//        if (y - ySpeed < 0) {
//            throw new ObjectCanNotBeMovedException(this.x, this.y - this.ySpeed);
//        }
//        y -= ySpeed;
//    }
//
//    @Override
//    public void moveRight() throws ObjectCanNotBeMovedException {
//        if (x + xSpeed > MovablesCollection.X_MAX) {
//            throw new ObjectCanNotBeMovedException(this.x + xSpeed, y);
//        }
//        x += xSpeed;
//    }
//
//    public int getxSpeed() {
//        return xSpeed;
//    }
//
//    public int getySpeed() {
//        return ySpeed;
//    }
//
//    @Override
//    public void moveLeft() throws ObjectCanNotBeMovedException {
//        if (x - xSpeed < 0) {
//            throw new ObjectCanNotBeMovedException(this.x - xSpeed, y);
//        }
//        x -= xSpeed;
//    }
//
//    @Override
//    public int getX() {
//        return x;
//    }
//
//    @Override
//    public int getY() {
//        return y;
//    }
//
//    @Override
//    public TYPE getType() {
//        return TYPE.POINT;
//    }
//
//    @Override
//    public String toString() {
//        return String.format("Movable point with coordinates (%d,%d)", x, y);
//    }
//}
//
//
//interface Movable {
//    public void moveUp() throws ObjectCanNotBeMovedException;
//    public void moveDown() throws ObjectCanNotBeMovedException;
//    public void moveRight() throws ObjectCanNotBeMovedException;;
//    public void moveLeft() throws ObjectCanNotBeMovedException;;
//
//    public int getX();
//    public int getY();
//
//    public TYPE getType();
//
//    public String toString();
//}
//
//public class CirclesTest {
//
//    public static void main(String[] args) throws MovableObjectNotFittableException, ObjectCanNotBeMovedException {
//
//        System.out.println("===COLLECTION CONSTRUCTOR AND ADD METHOD TEST===");
//        MovablesCollection collection = new MovablesCollection(100, 100);
//        Scanner sc = new Scanner(System.in);
//        int samples = Integer.parseInt(sc.nextLine());
//        for (int i = 0; i < samples; i++) {
//            String inputLine = sc.nextLine();
//            String[] parts = inputLine.split(" ");
//
//            int x = Integer.parseInt(parts[1]);
//            int y = Integer.parseInt(parts[2]);
//            int xSpeed = Integer.parseInt(parts[3]);
//            int ySpeed = Integer.parseInt(parts[4]);
//
//            if (Integer.parseInt(parts[0]) == 0) {  //point
//                try {
//                    collection.addMovableObject(new MovingPoint(x, y, xSpeed, ySpeed));
//                } catch (MovableObjectNotFittableException e) {
//                    System.out.println(e.getMessage());
//                }
//            } else {    //circle
//                try {
//                    int radius = Integer.parseInt(parts[5]);
//                    collection.addMovableObject(new MovingCircle(radius, new MovingPoint(x, y, xSpeed, ySpeed)));
//                } catch (MovableObjectNotFittableException e) {
//                    System.out.println(e.getMessage());
//                }
//            }
//
//        }
//        System.out.println(collection.toString());
//
//        System.out.println("MOVE POINTS TO THE LEFT");
//        try {
//            collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.LEFT);
//        } catch (ObjectCanNotBeMovedException e) {
//            System.out.println(e.getMessage());
//        }
//        System.out.println(collection.toString());
//
//        System.out.println("MOVE CIRCLES DOWN");
//        try {
//            collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.DOWN);
//        } catch (ObjectCanNotBeMovedException e) {
//            System.out.println(e.getMessage());
//        }
//        System.out.println(collection.toString());
//
//        System.out.println("CHANGE X_MAX AND Y_MAX");
//        MovablesCollection.setxMax(90);
//        MovablesCollection.setyMax(90);
//
//        System.out.println("MOVE POINTS TO THE RIGHT");
//        try {
//            collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.RIGHT);
//        } catch (ObjectCanNotBeMovedException e) {
//            System.out.println(e.getMessage());
//        }
//        System.out.println(collection.toString());
//
//        System.out.println("MOVE CIRCLES UP");
//        try {
//            collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.UP);
//        } catch (ObjectCanNotBeMovedException e) {
//            System.out.println(e.getMessage());
//        }
//        System.out.println(collection.toString());
//    }
//}
