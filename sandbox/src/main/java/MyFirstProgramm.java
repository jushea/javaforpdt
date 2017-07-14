import coordinats.Point;

public class MyFirstProgramm {

    public static double distance(Point p1, Point p2) {
        double x = p2.x - p1.x;
        double y = p2.y - p1.y;
        return Math.sqrt(x * x + y * y);
    }

    public static void main(String[] args) {

        Point p1 = new Point(-1.2, 2.4);
        Point p2 = new Point(3.4, 5.6);

        double d = distance(p1, p2);
        System.out.printf("This is work with function: distance - %.2f", d);

        System.out.println();

        System.out.printf("This is work with method: The distance between points p1(%.2f, %.2f) и p2(%.2f, %.2f) is %.2f", p1.x, p1.y, p2.x, p2.y, p1.distance(p2));
    }
}