package uz.ilmnajot.post_article.exception;

import org.springframework.http.HttpStatus;

public class AuthenticationException extends CustomBaseException{
    public AuthenticationException(String message) {
        super(message);
    }
}
