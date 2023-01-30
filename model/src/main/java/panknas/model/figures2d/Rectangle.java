package panknas.model.figures2d;

import lombok.ToString;
import panknas.model.exceptions.PointMisalignmentException;
import panknas.model.figures2d.helpers.Vector2D;
import panknas.model.figures2d.helpers.annotations.Creatable;
import panknas.model.points.Point2D;

@ToString(callSuper=true)
@Creatable
public class Rectangle extends panknas.model.figures2d.QGon {

    public Rectangle(){
        super();
    }

    public Rectangle(Point2D[] points) {
        super(points);

        if (
            !Vector2D.fromPoints(points[0], points[1])
                .isPerpendicular(Vector2D.fromPoints(points[1], points[2])) ||
            !Vector2D.fromPoints(points[1], points[2])
                .isPerpendicular(Vector2D.fromPoints(points[2], points[3])) ||
            !Vector2D.fromPoints(points[2], points[3])
                .isPerpendicular(Vector2D.fromPoints(points[3], points[0])) ||
            !Vector2D.fromPoints(points[3], points[0])
                .isPerpendicular(Vector2D.fromPoints(points[0], points[1]))
        ){
            throw new PointMisalignmentException();
        }
    }

    @Override
    public double area() {
        return Vector2D.fromPoints(this.getPoint(0), this.getPoint(1)).abs() *
            Vector2D.fromPoints(this.getPoint(1), this.getPoint(2)).abs();
    }
}
