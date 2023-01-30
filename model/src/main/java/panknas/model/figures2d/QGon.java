package panknas.model.figures2d;

import lombok.ToString;
import panknas.model.exceptions.PointAmountMismatchException;
import panknas.model.figures2d.helpers.annotations.Creatable;
import panknas.model.points.Point2D;

@ToString(callSuper=true)
@Creatable
public class QGon extends panknas.model.figures2d.NGon {

    public QGon(){
        super();
    }

    public QGon(Point2D[] points) {
        super(points);
        if (points.length !=4) throw new PointAmountMismatchException();
    }


}
