package panknas.model.points;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import panknas.model.points.exceptions.DimensionMismatchException;

import java.util.Arrays;
import java.util.stream.IntStream;

@ToString(onlyExplicitlyIncluded = true)
public class Point {

    @ToString.Include
    @Getter
    @Setter
    private double[] coordinates;

    public Point(int dimensions) {
        this.coordinates = new double[dimensions];
    }

    public Point(int dimensions, double[] coordinates) {
        if (coordinates.length != dimensions) {
            throw new DimensionMismatchException();
        }
        this.coordinates = coordinates;
    }

    @JsonIgnore
    public int getDimensions() {
        return this.coordinates.length;
    }

    @JsonIgnore
    public void setCoordinate(int coordinateIndex, double value) {
        this.coordinates[coordinateIndex] = value;
    }

    public double abs() {
        return Math.sqrt(Arrays.stream(this.coordinates).map(c -> c * c).sum());
    }

    public static Point add(Point a, Point b) {
        return a.add(b);
    }

    public Point add(Point other) { // -> Self ???
        if (this.getDimensions() != other.getDimensions()) throw new DimensionMismatchException();

        double[] sum_coordinates = IntStream
            .range(0, this.getDimensions())
            .mapToDouble(idx -> this.coordinates[idx] + other.coordinates[idx])
            .toArray();

        return new Point(this.getDimensions(), sum_coordinates);
    }

    public static Point sub(Point a, Point b) {
        return a.sub(b);
    }

    public Point sub(Point other) {
        return this.add(other.mult(-1));
    }

    public static Point mult(Point p, double value) {
        return p.mult(value);
    }

    public Point mult(double value) {
        double[] coordinates = Arrays.stream(this.getCoordinates())
            .map(coordinate -> coordinate * value)
            .toArray();
        return new Point(this.getDimensions(), coordinates);
    }

    public double mult(Point other) {
        if (this.getDimensions() != other.getDimensions()) throw new DimensionMismatchException();
        return IntStream.range(0, this.getDimensions())
            .mapToDouble(idx -> this.coordinates[idx] * other.coordinates[idx])
            .sum();
    }

    public static double mult(Point a, Point b) {
        return a.mult(b);
    }

    public Point symAxis(int axisIndex) {
        double[] coordinates = this.coordinates.clone();
        for(int i=0; i<coordinates.length; i++){
            if (i==axisIndex) continue;
            coordinates[i] = -coordinates[i];
        }

        return new Point(this.getDimensions(), coordinates);
    }

    public static Point symAxis(Point p, int axisIndex) {
        return p.symAxis(axisIndex);
    }

    public <T extends Point> T into(Class<T> c) {
        try{
            return c.getConstructor(double[].class).newInstance(this.coordinates);
        }catch (Exception e){
            throw new ClassCastException();
        }
    }
}
