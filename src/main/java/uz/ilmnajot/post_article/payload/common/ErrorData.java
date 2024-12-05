package uz.ilmnajot.post_article.payload.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorData {

    String errorMessage;
    String fieldName;
    int status;
    Timestamp timestamp;
    String path;

    public ErrorData(String errorMessage, Integer status, String path) {
        this.errorMessage = errorMessage;
        this.path = path;
        this.status = status;
        this.timestamp = Timestamp.valueOf(LocalDateTime.now());
    }
}
