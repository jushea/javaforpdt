package coordinats;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PointTests {

    @Test
    public void testDistanceBetweenPoints() {
        Point p1 = new Point(1, 2);
        Point p2 = new Point(1, 4);

        Assert.assertEquals(p1.distance(p2),2.0);
    }

    @Test
    public void testDistanceBetweenOnePoint() {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(1, 1);

        Assert.assertEquals(p1.distance(p2),0.0);
    }

    @Test
    public void testDistanceWithNegativeCoordinats() {
        Point p1 = new Point(-1, 2);
        Point p2 = new Point(1, -4);

        Assert.assertEquals(p1.distance(p2),6.324555320336759);
    }

    @Test
    public void testDistanceBetweenInfinity() {
        Point p1 = new Point(-23.123456, 2);
        Point p2 = new Point(23.123456, -4);

        Assert.assertEquals(p1.distance(p2),46.634502994411164);
    }

}
