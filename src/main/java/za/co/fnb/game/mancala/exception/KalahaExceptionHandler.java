package za.co.fnb.game.mancala.exception;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.math.BigDecimal;

@ControllerAdvice(basePackages = "za.co.fnb.game.mancala.controller")
public class KalahaExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {MancalaServiceException.class})
    protected ResponseEntity<Object> handleMancalaServiceException(
            MancalaServiceException serviceException, WebRequest request) {
        String bodyOfResponse = serviceException.getMessage();
        int statusCode = serviceException.getHttpStatus().value();
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setMessage(HttpStatus.valueOf(statusCode).getReasonPhrase());
        errorResponse.setStatus(BigDecimal.valueOf(statusCode));
        errorResponse.setError(HttpStatus.valueOf(statusCode).toString());
        return handleExceptionInternal(serviceException, errorResponse.getError(),
                new HttpHeaders(), HttpStatus.valueOf(statusCode), request);
    }

}
