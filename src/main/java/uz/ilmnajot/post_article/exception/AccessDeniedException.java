package uz.ilmnajot.post_article.exception;

import org.springframework.http.HttpStatus;

public class AccessDeniedException extends CustomBaseException {
    public AccessDeniedException(String message, HttpStatus status) {
        super(message, status);
    }
}
