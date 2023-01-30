package panknas.shapes.data;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Getter;
import lombok.Setter;

@JsonTypeInfo(use= JsonTypeInfo.Id.DEDUCTION)
@JsonSubTypes({@JsonSubTypes.Type(MoveType.Shift.class),
    @JsonSubTypes.Type(MoveType.Rotate.class),
    @JsonSubTypes.Type(MoveType.Symmetry.class)})
public abstract class MoveType {

    public static class Shift extends MoveType {
        public Shift(){
            dx=0;
            dy=0;
        }
        @Getter
        @Setter
        private double dx;
        @Getter
        @Setter
        private double dy;
    }

    public static class Rotate extends MoveType {
        @Getter
        @Setter
        private double angle;
        public Rotate(){
            this.angle = 0;
        }
    }

    public static class Symmetry extends MoveType {
        @Getter
        @Setter
        private int axis;
        public Symmetry(){
            this.axis = 0;
        }
    }
}



