package About_me;
import fesma.nl.Payroll.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice

public class About_meNotFoundAdvice {

        @ResponseBody
        @ExceptionHandler(About_meNotFoundException.class)
        @ResponseStatus(HttpStatus.NOT_FOUND)
        String About_meNotFoundExceptionHandler(EmployeeNotFoundException ex) {
            return ex.getMessage();
        }
    }

