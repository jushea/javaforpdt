import coordinats.Point;

public class MyFirstProgramm {

    public static void main(String[] args) {
        System.out.println("Hello, world!");

        Point p1 = new Point(-1.2, 2.4);
        Point p2 = new Point(3.4, 5.6);

        System.out.printf("The distance between points p1(%.2f, %.2f) и p2(%.2f, %.2f) is %.2f", p1.x, p1.y, p2.x, p2.y, p1.distance(p2));
    }
}