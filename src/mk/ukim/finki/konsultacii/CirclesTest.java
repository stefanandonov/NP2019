//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.List;
//import java.util.Scanner;
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
//interface Movable{
//    void moveUp() throws ObjectCanNotBeMovedException;
//    void moveDown() throws ObjectCanNotBeMovedException;
//    void moveLeft() throws ObjectCanNotBeMovedException;
//    void moveRight() throws ObjectCanNotBeMovedException;
//    int getCurrentPositionX();
//    int getCurrentPositionY();
//    boolean canFit (int X_MAX, int Y_MAX);
//}
//
//class ObjectCanNotBeMovedException extends Exception{
//    public ObjectCanNotBeMovedException(String message) {
//        System.out.println("ObjectCanNotBeMovedException");
//    }
//}
//
//class MovablePoint implements Movable{
//    int x, y, xSpeed, ySpeed;
//
//    public MovablePoint(int x, int y, int xSpeed, int ySpeed) {
//        this.x = x;
//        this.y = y;
//        this.xSpeed = xSpeed;
//        this.ySpeed = ySpeed;
//    }
//
//    @Override
//    public void moveUp(){
//        y += ySpeed;
//    }
//
//    @Override
//    public void moveDown(){
//        y -= ySpeed;
//    }
//
//    @Override
//    public void moveLeft(){
//        x -= xSpeed;
//    }
//
//    @Override
//    public void moveRight(){
//        x += xSpeed;
//    }
//
//    @Override
//    public int getCurrentPositionX(){
//        return x;
//    }
//
//    @Override
//    public int getCurrentPositionY(){
//        return y;
//    }
//
//    @Override
//    public boolean canFit(int X_MAX, int Y_MAX) {
//        return x>=0 && x<=X_MAX && y>=0 && y<=Y_MAX;
//    }
//
//    @Override
//    public String toString(){
//        return String.format("Movable point with coordinates (%d, %d)", x, y);
//    }
//}
//
//class MovableCircle implements Movable{
//    int radius;
//    MovablePoint center;
//
//    public MovableCircle(int radius, MovablePoint center) {
//        this.radius = radius;
//        this.center = center;
//    }
//
//    @Override
//    public void moveUp(){
//        center.y += center.ySpeed;
//    }
//
//    @Override
//    public void moveDown(){
//        center.y -= center.ySpeed;
//    }
//
//    @Override
//    public void moveLeft(){
//        center.x -= center.xSpeed;
//    }
//
//    @Override
//    public void moveRight(){
//        center.x += center.xSpeed;
//    }
//
//    @Override
//    public int getCurrentPositionX(){
//        return center.x;
//    }
//
//    @Override
//    public int getCurrentPositionY(){
//        return center.y;
//    }
//
//    @Override
//    public boolean canFit(int X_MAX, int Y_MAX) {
//        return false;
//    }
//
//    @Override
//    public String toString(){
//        return String.format("Movable circle with center coordinates (%d,%d) and radius %d", center.x, center.y, radius);
//    }
//}
//
//class MovablesCollection{
//    Movable movable[];
//    static int x_MAX, y_MAX;
//    public MovablesCollection(int x_MAX_, int y_MAX_){
//        x_MAX = x_MAX_;
//        y_MAX = y_MAX_;
//    }
//
//    public static void setxMax(int x_MAX) {
//        MovablesCollection.x_MAX = x_MAX;
//    }
//
//    public static void setyMax(int y_MAX) {
//        MovablesCollection.y_MAX = y_MAX;
//    }
//
//    public void addMovableObject(Movable m){
//        if (!m.canFit(x_MAX, y_MAX))
//            thro
//    }
//
//    public void moveObjectsFromTypeWithDirection (TYPE type, DIRECTION direction){
//
//    }
//
//    @Override
//    public String toString(){
//        return String.format("Collection of movable objects with size [%d]: ", movable.length);
//    }
//}
//
//public class CirclesTest {
//
//    public static void main(String[] args) {
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
//            if (Integer.parseInt(parts[0]) == 0) { //point
//                collection.addMovableObject(new MovablePoint(x, y, xSpeed, ySpeed));
//            } else { //circle
//                int radius = Integer.parseInt(parts[5]);
//                collection.addMovableObject(new MovableCircle(radius, new MovablePoint(x, y, xSpeed, ySpeed)));
//            }
//
//        }
//        System.out.println(collection.toString());
//
//        System.out.println("MOVE POINTS TO THE LEFT");
//        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.LEFT);
//        System.out.println(collection.toString());
//
//        System.out.println("MOVE CIRCLES DOWN");
//        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.DOWN);
//        System.out.println(collection.toString());
//
//        System.out.println("CHANGE X_MAX AND Y_MAX");
//        MovablesCollection.setxMax(90);
//        MovablesCollection.setyMax(90);
//
//        System.out.println("MOVE POINTS TO THE RIGHT");
//        collection.moveObjectsFromTypeWithDirection(TYPE.POINT, DIRECTION.RIGHT);
//        System.out.println(collection.toString());
//
//        System.out.println("MOVE CIRCLES UP");
//        collection.moveObjectsFromTypeWithDirection(TYPE.CIRCLE, DIRECTION.UP);
//        System.out.println(collection.toString());
//
//
//    }
//
//
//}
