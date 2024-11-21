package uz.ilmnajot.post_article.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistsException extends CustomBaseException{
    public AlreadyExistsException(String message, HttpStatus status) {
        super(message, status);
    }
}
