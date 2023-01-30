package panknas.model.figures2d;

import org.junit.jupiter.api.Test;
import panknas.model.points.Point2D;

import static org.junit.jupiter.api.Assertions.*;

class SegmentTest {

    private final Segment segment = new Segment(
        new Point2D(),
        new Point2D(new double[]{1, 1})
    );

    @Test
    public void shouldHaveLength(){
        assertEquals(segment.length(), Math.sqrt(2), 1e-6);
    }

    @Test
    public void shouldCross(){
        assertTrue(segment.isCrossing(
            new Segment(
                new Point2D(new double[]{0, 0.5}),
                new Point2D(new double[]{0.5, 0})
            )
        ));

        assertFalse(
            segment.isCrossing(
                new Segment(
                    new Point2D(new double[]{2, 2}),
                    new Point2D(new double[]{2, 3})
                )
            )
        );
    }
}