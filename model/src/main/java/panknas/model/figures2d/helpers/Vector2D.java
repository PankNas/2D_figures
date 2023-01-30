package panknas.model.figures2d.helpers;

import panknas.model.points.Point2D;

public class Vector2D {
    private final Point2D point;

    public Vector2D(Point2D point) {
        this.point = point;
    }

    public static Vector2D fromPoints(Point2D start, Point2D finish) {
        return new Vector2D(finish.sub(start).into(Point2D.class));
    }

    public double getX() {
        return this.point.getCoordinates()[0];
    }

    public double getY() {
        return this.point.getCoordinates()[1];
    }

    public boolean isParallel(Vector2D other) {
        return Math.abs(this.getX() * other.getY() - this.getY() * other.getX()) < 1e-9;
    }

    public boolean isPerpendicular(Vector2D other) {
        return this.dot(other) < 1e-9f;
    }

    public Vector2D constructPerpendicular(){
        return new Vector2D(new Point2D(new double[]{this.getY(), -this.getX()}));
    }

    public double dot(Vector2D other) {
        return this.getX() * other.getX() + this.getY() * other.getY();
    }

    public Vector2D normalize() {
        double absValue = this.abs();
        return new Vector2D(new Point2D(new double[]{this.getX()/absValue, this.getY()/absValue}));
    }

    public Vector2D multiply(double value){
        return new Vector2D(new Point2D(new double[]{this.getX()*value, this.getY()*value}));
    }

    public Vector2D project(Vector2D onto) {
        return onto.multiply(this.dot(onto) / onto.dot(onto));
    }

    public double abs(){
        return this.point.abs();
    }

}
