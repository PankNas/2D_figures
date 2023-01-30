package panknas.model.figures2d;

import org.junit.jupiter.api.Test;
import panknas.model.points.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class TrapezeTest {

    private static final Trapeze trapeze = new Trapeze(
        new Point2D[]{
            new Point2D(new double[]{-1, 4}),
    new Point2D(new double[]{1, 10}),
    new Point2D(new double[]{5, 9}),
            new Point2D(new double[]{2, 0}),
        }
    );

    @Test
    void area() {
        assertEquals(trapeze.area(), 32.5, 1e-6);
    }
}