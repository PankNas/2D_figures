package panknas.model.figures2d;

import org.junit.jupiter.api.Test;
import panknas.model.points.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class CircleTest {

    private static final Circle circle = new Circle(new Point2D(), 3);

    @Test
    void area() {
        assertEquals(circle.area(), Math.PI*3*3, 1e-6);
    }

    @Test
    void length() {
        assertEquals(circle.length(), Math.PI*2*3, 1e-6);
    }
}