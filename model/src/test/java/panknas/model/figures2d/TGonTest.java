package panknas.model.figures2d;

import org.junit.jupiter.api.Test;
import panknas.model.points.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class TGonTest {

    private static final TGon tgon = new TGon(
        new Point2D[]{
            new Point2D(new double[]{1, 5}),
            new Point2D(new double[]{2, 10}),
            new Point2D(new double[]{3, 8})
        }
    );

    @Test
    void area() {
        assertEquals(tgon.area(), 3.5, 1e-6);
    }
}