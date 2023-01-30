package panknas.model.figures2d;

import org.junit.jupiter.api.Test;
import panknas.model.points.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class NGonTest {

    private static final NGon ngon = new NGon(
        new Point2D[]{
            new Point2D(new double[]{-1.0, 4.0}),
            new Point2D(new double[]{1.0, 10.0}),
            new Point2D(new double[]{5.0, 9.0}),
            new Point2D(new double[]{2.0, 0.0})
        }

    );

    @Test
    void area() {
        assertEquals(ngon.area(), 32.5, 1e-6);
    }

    @Test
    void length() {
        assertEquals(ngon.length(), 24.9344939, 1e-6);
    }
}