package uz.ilmnajot.post_article.exception;

import org.springframework.http.HttpStatus;

public class ResourceNotFoundException extends CustomBaseException{
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
