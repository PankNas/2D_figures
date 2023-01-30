package panknas.model.figures2d;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import panknas.model.figures2d.helpers.annotations.Creatable;
import panknas.model.points.Point2D;

import java.awt.geom.Line2D;

@ToString
@Creatable
public class Segment extends panknas.model.figures2d.OpenFigure implements Segmentable {

    @Getter
    @Setter
    private Point2D start;

    @Getter
    @Setter
    private Point2D finish;

    public Segment(){
        this.start = null;
        this.finish = null;
    }

    public Segment(Point2D start, Point2D finish) {
        this.start = start;
        this.finish = finish;
    }


    @Override
    public double length() {
        return this.start.sub(this.finish).abs();
    }

    @Override
    public Segment shift(Point2D a) {
        return new Segment( this.start.add(a), this.finish.add(a));
    }

    @Override
    public Segment rotate(double angle) {
        return new Segment(this.start.rotate(angle), this.finish.rotate(angle));
    }

    @Override
    public Movable symAxis(int i) {
        return new Segment( this.start.symAxis(i), this.finish.symAxis(i));
    }

    public boolean isCrossing(Segment other) {
        Line2D line1 = new Line2D.Double(this.getStart().getCoordinates()[0], this.getStart().getCoordinates()[1],
            this.getFinish().getCoordinates()[0], this.getFinish().getCoordinates()[1]);
        Line2D line2 = new Line2D.Double(other.getStart().getCoordinates()[0], other.getStart().getCoordinates()[1],
            other.getFinish().getCoordinates()[0], other.getFinish().getCoordinates()[1]);

        return line1.intersectsLine(line2);
    }

    public Segment[] intoSegments() {
        return new Segment[] {this};
    }
}
