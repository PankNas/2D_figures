package panknas.model.figures2d;

import org.junit.jupiter.api.Test;
import panknas.model.points.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class RectangleTest {

    private static final Rectangle rectangle = new Rectangle(
        new Point2D[]{
            new Point2D(new double[]{0, 4}),
            new Point2D(new double[]{8, 10}),
            new Point2D(new double[]{11, 6}),
            new Point2D(new double[]{3, 0})
        }
    );

    @Test
    void area() {
        assertEquals(rectangle.area(), 50, 1e-6);
    }
}