package panknas.model.figures2d;

public interface Segmentable extends Shape {
    Segment[] intoSegments();

    @Override
    default boolean isCrossing(Shape shape) {
        if (!(shape instanceof Segmentable other)) {
            throw new UnsupportedOperationException();
        }

        for (Segment s1: this.intoSegments()){
            for (Segment s2: other.intoSegments()){
                if (s1.isCrossing(s2)){
                    return true;
                }
            }
        }
        return false;
    }
}
