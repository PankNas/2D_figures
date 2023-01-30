package panknas.model.figures2d;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;


@JsonSubTypes(
    value = {
        @JsonSubTypes.Type(value = Circle.class),
        @JsonSubTypes.Type(value = NGon.class),
        @JsonSubTypes.Type(value = Polyline.class),
        @JsonSubTypes.Type(value = QGon.class),
        @JsonSubTypes.Type(value = Rectangle.class),
        @JsonSubTypes.Type(value = Segment.class),
        @JsonSubTypes.Type(value = TGon.class),
        @JsonSubTypes.Type(value = Trapeze.class)
    }
)
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS)
public interface Shape extends panknas.model.figures2d.Movable {
    public double area();

    public double length();

    public boolean isCrossing(Shape shape);
}
