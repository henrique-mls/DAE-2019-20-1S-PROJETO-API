package exceptions;

import java.io.Serializable;

public class MyIllegalArgumentException extends Exception implements Serializable {
    public MyIllegalArgumentException() {
    }

    public MyIllegalArgumentException(String message) {
        super(message);
    }
}
