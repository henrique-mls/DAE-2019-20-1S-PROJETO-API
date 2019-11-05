package exceptions;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.Set;

public class Utils {
    public static String getConstraintViolationMessages(ConstraintViolationException e) {
        Set<ConstraintViolation<?>> cvs = e.getConstraintViolations();
        StringBuilder errorMessages = new StringBuilder();
        for (ConstraintViolation<?> cv : cvs) {
            errorMessages.append(cv.getMessage());
            errorMessages.append("; ");
        }
        return errorMessages.toString();
    }

}
