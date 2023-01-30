package panknas.model.figures2d;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import panknas.model.figures2d.helpers.annotations.Creatable;
import panknas.model.figures2d.helpers.annotations.VariableSize;
import panknas.model.points.Point2D;

import java.util.Arrays;
import java.util.stream.IntStream;

@ToString
@VariableSize(atLeast = 2)
@Creatable
public class Polyline extends panknas.model.figures2d.OpenFigure implements PolyPoint, Segmentable {

    @Getter
    @Setter
    private Point2D[] points;

    public Polyline(){
        this.points = null;
    }

    public Polyline(Point2D[] points) {
        this.points = points;
    }

    @JsonIgnore
    public int getN() {
        return this.points.length;
    }

    @JsonIgnore
    @Override
    public Point2D getPoint(int index) {
        return this.points[index];
    }

    @JsonIgnore
    @Override
    public void setPoint(Point2D point, int index) {
        this.points[index] = point;
    }

    @Override
    public double length() {
        return IntStream.range(0, this.getN() - 1)
            .mapToDouble(
                idx -> this.getPoint(idx)
                    .sub(this.getPoint(idx + 1))
                    .abs())
            .sum();
    }


    @Override
    public Polyline shift(Point2D a) {
        return new Polyline(
            Arrays.stream(this.getPoints())
                .map(point -> point.add(a))
                .toArray(Point2D[]::new)
        );
    }

    @Override
    public Polyline rotate(double angle) {
        return new Polyline(
            Arrays.stream(this.getPoints())
                .map(point -> point.rotate(angle))
                .toArray(Point2D[]::new)
        );
    }

    @Override
    public Polyline symAxis(int i) {
        return new Polyline(
            Arrays.stream(this.getPoints())
                .map(point -> point.symAxis(i))
                .toArray(Point2D[]::new)
        );
    }

    public Segment[] intoSegments() {
        return IntStream.range(0, this.getN()-1).mapToObj(
            idx -> new Segment(this.getPoint(idx), this.getPoint((idx+1)%this.getN()))
        ).toArray(Segment[]::new);
    }
}
