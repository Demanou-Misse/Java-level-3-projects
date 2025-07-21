package model;

import java.io.Serial;
import java.io.Serializable;

public class StudentNotFoundException extends Exception implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    public StudentNotFoundException(String message) {
        super(message);
    }
}
