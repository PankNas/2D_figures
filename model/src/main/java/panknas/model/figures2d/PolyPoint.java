package panknas.model.figures2d;

import panknas.model.points.Point2D;

public interface PolyPoint {
    Point2D getPoint(int index);

    void setPoint(Point2D point, int index);
}
