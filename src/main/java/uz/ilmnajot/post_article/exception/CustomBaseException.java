package uz.ilmnajot.post_article.exception;

import org.springframework.http.HttpStatus;

public class CustomBaseException extends RuntimeException {
    private final HttpStatus status;

    public CustomBaseException(String message, HttpStatus status) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
