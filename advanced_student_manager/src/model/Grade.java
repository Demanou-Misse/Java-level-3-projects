package model;

import java.io.Serial;
import java.io.Serializable;

public class Grade implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    private double value;

    public Grade (double value) {
        this.value= value;
    }

    public double getValue() {
        return value;
    }
    public void setValue(double newValue) {
        value = newValue;
    }

}
