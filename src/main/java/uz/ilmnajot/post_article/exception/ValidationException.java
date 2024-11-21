package uz.ilmnajot.post_article.exception;

import org.springframework.http.HttpStatus;

public class ValidationException extends CustomBaseException{
    public ValidationException(String message, HttpStatus status) {
        super(message, status);
    }
}
