package panknas.shapes.data;

import lombok.Getter;
import panknas.model.figures2d.*;
import panknas.model.points.Point2D;

import java.util.Arrays;
import java.util.stream.Stream;

public abstract class ShapeOut {

    @Getter
    String type;
    @Getter
    String name;
    @Getter
    Shape code;

    @Getter
    int idx;

    public static ShapeOut fromShape(Shape shape, int idx) {
        if (shape instanceof Circle c) {
            return new CircleOut(PointOut.from(c.getCenter()), c.getRadius(), c, idx);
        }
        if (shape instanceof OpenFigure o){
            var segmentable = ((Segmentable)o);
            var segments = segmentable.intoSegments();

            return new OpenShape(ShapeOut.fromSegments(segments), o, idx);
        }
        var segmentable = ((Segmentable)shape);
        var segments = segmentable.intoSegments();
        return new ClosedShape(ShapeOut.fromSegments(segments), shape, idx);
    }


    private static Point2D[] fromSegments(Segment[] s) {
        return Stream.concat(
            Stream.of(s[0].getStart()),
            Arrays.stream(s).map(Segment::getFinish)).toArray(Point2D[]::new);
    }

    static class CircleOut extends ShapeOut {

        @Getter
        private PointOut center;
        @Getter
        private double radius;


        public CircleOut(PointOut center, double radius, Shape code, int idx) {
            this.center = center;
            this.radius = radius;
            this.name = code.toString();
            this.code = code;
            this.idx = idx;
            this.type = "circle";
        }
    }

    record PointOut(double x, double y) {
        public static PointOut from(Point2D p){
            return new PointOut(p.getX(), p.getY());
        }
    }

    static class OpenShape extends ShapeOut {
        @Getter
        PointOut[] points;
        OpenShape(Point2D[] points, Shape code, int idx) {
            this.points = Arrays.stream(points).map(PointOut::from).toArray(PointOut[]::new);
            this.name = code.toString();
            this.code = code;
            this.type = "open";
            this.idx = idx;
        }
    }

    static class ClosedShape extends ShapeOut {
        @Getter
        PointOut[] points;
        ClosedShape(Point2D[] points, Shape code, int idx) {
            this.points = Arrays.stream(points).map(PointOut::from).toArray(PointOut[]::new);
            this.name = code.toString();
            this.type = "closed";
            this.code = code;
            this.idx = idx;
        }
    }
}
