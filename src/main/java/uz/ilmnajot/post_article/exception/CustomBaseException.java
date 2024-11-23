package uz.ilmnajot.post_article.exception;

import org.springframework.http.HttpStatus;

public class CustomBaseException extends RuntimeException {
    public CustomBaseException(String message) {
        super(message);

    }

}
