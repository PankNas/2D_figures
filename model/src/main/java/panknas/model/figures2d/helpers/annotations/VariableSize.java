package panknas.model.figures2d.helpers.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface VariableSize {

    public int atLeast();
}
