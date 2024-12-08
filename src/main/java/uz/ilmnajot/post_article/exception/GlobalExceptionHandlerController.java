package uz.ilmnajot.post_article.exception;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import uz.ilmnajot.post_article.payload.common.ErrorData;
import uz.ilmnajot.post_article.payload.common.HttpCustomResponse;

@ControllerAdvice
public class GlobalExceptionHandlerController {

    Logger logger = LoggerFactory.getLogger(GlobalExceptionHandlerController.class);


    @ExceptionHandler(ResourceNotFoundException.class)
    public HttpEntity<?> handleException(ResourceNotFoundException exception, WebRequest request) {
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(HttpCustomResponse.errorResponse(
                exception.getMessage(),
                request.getDescription(false),
                HttpStatus.NOT_FOUND.value()),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public HttpEntity<HttpCustomResponse<ErrorData>> handleException(AlreadyExistsException exception, WebRequest request) {
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(
                HttpCustomResponse.errorResponse(
                        exception.getMessage(),
                        request.getDescription(false),
                        HttpStatus.NOT_FOUND.value()
                ), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ValidationException.class)
    public HttpEntity<HttpCustomResponse<ErrorData>> handleException(ValidationException exception, WebRequest request) {
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(HttpCustomResponse.errorResponse(
                exception.getMessage(),
                request.getDescription(false),
                HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public HttpEntity<?> internalError(Exception exception, WebRequest request) {
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(HttpCustomResponse.errorResponse(
                exception.getMessage(),
                request.getDescription(false),
                HttpStatus.FORBIDDEN.value()), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public HttpEntity<HttpCustomResponse<ErrorData>> handleException(AccessDeniedException exception, WebRequest request) {
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(HttpCustomResponse.errorResponse(
                exception.getMessage(),
                request.getDescription(false),
                HttpStatus.FORBIDDEN.value()),
                HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(AuthenticationException.class)
    public HttpEntity<?> handleException(AuthenticationException exception, WebRequest request) {
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(HttpCustomResponse.errorResponse(
                exception.getMessage(),
                request.getDescription(false),
                HttpStatus.FORBIDDEN.value()),
                HttpStatus.FORBIDDEN);
    }

}
