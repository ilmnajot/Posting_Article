package uz.ilmnajot.post_article.payload.common;
import lombok.*;
import org.springframework.http.HttpStatus;

@Setter
@Getter
@NoArgsConstructor
@Builder
public class ApiResponse{

    private boolean success;
    private String message;
    private HttpStatus status;
    private Object info;

    public ApiResponse(boolean success, String message, HttpStatus status) {
        this.success = success;
        this.message = message;
        this.status = status;
    }
    public ApiResponse(boolean success, String message, HttpStatus status, Object info) {
        this.success = success;
        this.message = message;
        this.status = status;
        this.info = info;
    }
}
