package panknas.model.figures2d;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import panknas.model.figures2d.helpers.Vector2D;
import panknas.model.figures2d.helpers.annotations.Creatable;
import panknas.model.points.Point2D;

@Creatable
@ToString
public class Circle implements Shape {

    @Getter
    @Setter
    private Point2D center;

    @Getter
    @Setter
    private double radius;

    public Circle(){
        this.center = new Point2D();
        this.radius = 0;
    }

    public Circle(Point2D center, double radius) {
        this.center = center;
        this.radius = radius;
    }


    @Override
    public double area() {
        return Math.PI * Math.pow(this.radius, 2);
    }

    @Override
    public double length() {
        return Math.PI * 2 * this.radius;
    }


    @Override
    public Circle shift(Point2D a) {
        return new Circle(this.center.add(a).into(Point2D.class), this.radius);
    }

    @Override
    public Circle rotate(double angle) {
        return new Circle(this.center.rotate(angle), this.radius);
    }

    @Override
    public Circle symAxis(int i) {
        return new Circle(this.center.symAxis(i), this.radius);
    }

    @Override
    public boolean isCrossing(Shape shape) {
        if (!(shape instanceof Circle c)){
            throw new UnsupportedOperationException();
        }
        double distance = Vector2D.fromPoints(this.center, c.getCenter()).abs();
        return distance <= this.radius + c.getRadius();
    }
}
