package coordinats;

/**
 * Created by yshepeleva on 14.07.2017.
 */
public class Point {
    public double x;
    public double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double distance(Point p2) {
        double x = p2.x - this.x;
        double y = p2.y - this.y;
        return Math.sqrt(x * x + y * y);
    }
}
