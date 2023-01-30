package panknas.model.figures2d;

import org.junit.jupiter.api.Test;
import panknas.model.points.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class PolylineTest {

    private static final Polyline polyline = new Polyline(
        new Point2D[]{
            new Point2D(new double[]{0, 3}),
            new Point2D(new double[]{2, 1}),
            new Point2D(new double[]{1, 1})
        }
    );

    @Test
    void length() {
        assertEquals(polyline.length(), Math.sqrt(8)+1, 1e-6);
    }
}