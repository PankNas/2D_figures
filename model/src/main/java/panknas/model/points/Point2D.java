package panknas.model.points;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

public class Point2D extends Point implements Serializable {

    public Point2D() {
        super(2);
    }

    public Point2D(double[] coordinates) {
        super(2, coordinates);
    }

    public Point2D rotate(double angle) {
        double x = this.getCoordinates()[0];
        double y = this.getCoordinates()[1];
        double newX = x * Math.cos(angle) - y * Math.sin(angle);
        double newY = x * Math.sin(angle) + y * Math.cos(angle);

        return new Point2D(new double[]{newX, newY});
    }

    @Override
    public Point2D add(Point other) {
        return super.add(other).into(Point2D.class);
    }

    @Override
    public Point2D sub(Point other) {
        return super.sub(other).into(Point2D.class);
    }

    @Override
    public Point2D symAxis(int axisIndex) {
        return super.symAxis(axisIndex).into(Point2D.class);
    }

    public static Point2D rotate(Point2D p, double angle) {
        return p.rotate(angle);
    }

    @JsonIgnore
    public double getX() {
        return this.getCoordinates()[0];
    }

    @JsonIgnore
    public double getY() {
        return this.getCoordinates()[1];
    }
    
}
