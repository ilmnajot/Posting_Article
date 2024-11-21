package uz.ilmnajot.post_article.payload.common;

import jakarta.persistence.Entity;
import lombok.*;
import org.springframework.http.HttpStatus;
import uz.ilmnajot.post_article.component.AbstractEntity;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class ApiResponse extends AbstractEntity{

    private boolean success;
    private String message;
    private HttpStatus status;
    private Object object;

    public ApiResponse(boolean success, String message, HttpStatus status) {
        this.success = success;
        this.message = message;
        this.status = status;
    }
    public ApiResponse(boolean success, String message, HttpStatus status, Object object) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.object = object;
    }
}
