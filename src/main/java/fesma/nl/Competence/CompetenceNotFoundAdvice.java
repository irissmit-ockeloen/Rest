package fesma.nl.Competence;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class CompetenceNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(CompetenceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String CompetenceNotFoundExceptionHandler(CompetenceNotFoundException ex) {
        return ex.getMessage();
    }
}
