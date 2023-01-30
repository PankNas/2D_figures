package panknas.shapes;

import org.springframework.stereotype.Component;
import panknas.model.figures2d.Shape;

import java.util.ArrayList;
import java.util.List;

@Component
public class ShapeRepository {

    private List<Shape> shapes;

    public ShapeRepository() {
        this.shapes = new ArrayList<>();
//        this.shapes.add(
//            new Circle(new Point2D(), 10)
//        );
//        this.shapes.add(new Segment(new Point2D(new double[]{0.0, 0.0}), new Point2D(new double[]{0.0, 50.0})));
    }

    public List<Shape> list() {
        return this.shapes;
    }

    public void clear(){
        this.shapes.clear();
    }

    public void set(List<Shape> shapes){
        this.shapes = new ArrayList<>(shapes);
    }

    public Shape getOrError(int idx){
        return this.shapes.get(idx);
    }

    public void add(Shape s) {
        this.shapes.add(s);
    }

    public void delete(int idx){
        this.shapes.remove(idx);
    }

    public void update(String name, Shape upd) {
        this.shapes.replaceAll(s -> {
            if (s.toString().equals(name)){
                return upd;
            }
            return s;
        });
    }

}
