package panknas.model.figures2d;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import panknas.model.figures2d.helpers.annotations.Creatable;
import panknas.model.figures2d.helpers.annotations.VariableSize;
import panknas.model.points.Point2D;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.stream.IntStream;

@ToString
@VariableSize(atLeast = 3)
@Creatable
public class NGon implements Shape, PolyPoint, Segmentable {

    public NGon(){
        this.points = null;
    }

    @Getter
    @Setter
    private Point2D[] points;

    public NGon(Point2D[] points) {
        this.points = points;
    }

    @JsonIgnore
    public int getN(){
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
    public double area() {
        //формула шнурования

        return Math.abs( IntStream.range(0, this.getN()).mapToDouble(
            i -> this.getPoint(i).getX() *
                (this.getPoint((i+1) % this.getN() ).getY() -
                    this.getPoint((this.getN() + i-1) % this.getN()).getY()
            )
        ).sum()) / 2d;
    }

    @Override
    public double length() {
        return IntStream.range(0, this.getN()).mapToDouble(
            i -> new Segment(this.getPoint(i), this.getPoint((i+1)%this.getN())).length()
        ).sum();
    }

    @Override
    public NGon shift(Point2D a) {
        try {
            return this.getClass()
                .getConstructor(Point2D[].class)
                .newInstance(
                    (Object) Arrays.stream(this.points)
                        .map(point -> point.add(a))
                        .toArray(Point2D[]::new)
                );
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public NGon rotate(double angle) {
        try {
            return this.getClass()
                .getConstructor(Point2D[].class)
                .newInstance(
                    (Object) Arrays.stream(this.points).map(point -> point.rotate(angle)).toArray(Point2D[]::new)
                );
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public NGon symAxis(int i) {
        try {
            return this.getClass()
                .getConstructor(Point2D[].class)
                .newInstance(
                    (Object) Arrays.stream(this.points).map(point -> point.symAxis(i)).toArray(Point2D[]::new)
                );
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Segment[] intoSegments() {
        return IntStream.range(0, this.getN()).mapToObj(
            idx -> new Segment(this.getPoint(idx), this.getPoint((idx+1)%this.getN()))
        ).toArray(Segment[]::new);
    }

}
