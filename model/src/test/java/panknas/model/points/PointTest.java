package panknas.model.points;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import panknas.model.points.exceptions.DimensionMismatchException;

import static org.junit.jupiter.api.Assertions.*;

class PointTest {

    private Point point1;
    private Point point2;

    @BeforeEach
    public void createPoints() {
        this.point1 = new Point(3, new double[]{1.0, 2.0, -5.0});
        this.point2 = new Point(3, new double[]{2, 1, 0});
    }

    @Test
    public void pointShouldHaveDimensions() {
        assertEquals(point1.getDimensions(), 3);
    }

    @Test
    public void pointShouldHaveProvidedCoordinates() {
        assertArrayEquals(point1.getCoordinates(), new double[] {1d, 2d, -5d});
    }

    @Test
    public void shouldCreateZeroedByDefault() {
        assertArrayEquals(new Point(2).getCoordinates(), new double[]{0, 0});
    }

    @Test
    public void shouldThrowDimensionMismatch() {
        assertThrows(DimensionMismatchException.class, () -> {
            new Point(2, new double[] {1, 2, 3});
        });
    }

    @Test
    public void shouldComputeAbs(){
        assertEquals(point1.abs(), 5.4772, 1e-4);
    }

    @Test
    public void shouldAdd(){
        assertArrayEquals(point1.add(point2).getCoordinates(), new double[]{3, 3, -5});
    }

    @Test
    public void shouldMultiply(){
        assertArrayEquals(point1.mult(2).getCoordinates(), new double[]{2, 4, -10});
    }

    @Test
    public void shouldMultiplyWithPoint(){
        assertEquals(point1.mult(point2), 4, 1e-4);
    }
}