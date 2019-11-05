package exceptions;

import java.io.Serializable;

public class MyEntityExistsException extends Exception implements Serializable {

    public MyEntityExistsException() {
    }

    public MyEntityExistsException(String message) {
        super(message);
    }

}
