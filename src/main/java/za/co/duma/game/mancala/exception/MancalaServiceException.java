package za.co.duma.game.mancala.exception;

import org.springframework.http.HttpStatus;

public class MancalaServiceException extends RuntimeException{
    private HttpStatus httpStatus;

    public MancalaServiceException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public MancalaServiceException(String message) {
        super(message);
    }

    public MancalaServiceException(String message, HttpStatus httpStatus, Throwable cause) {
        super(message, cause);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }
}
