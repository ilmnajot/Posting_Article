package uz.ilmnajot.post_article.payload.common;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Collections;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HttpCustomResponse<T> {
    String message;
    T data;
    boolean success;
    List<ErrorData> errorData;

    public HttpCustomResponse(boolean success) {
        this.success = success;
    }

    public HttpCustomResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public HttpCustomResponse(T data, Boolean success) {
        this.data = data;
        this.success = success;
    }

    public HttpCustomResponse(String message, String path, Integer errorCode) {
        this.success = false;
        this.errorData = Collections.singletonList(new ErrorData(message, path, errorCode));
    }

    public HttpCustomResponse(List<ErrorData> errorData) {
        this.success = false;
        this.errorData = errorData;
    }

    public static <E> HttpCustomResponse<E> successResponse(E data) {
        return new HttpCustomResponse<>(data, true);
    }

    public static <E> HttpCustomResponse<String> successResponse(String message) {
        return new HttpCustomResponse<>(message, true);
    }

    public static HttpCustomResponse<ErrorData> errorResponse(String message, String path, Integer errorCode) {
        return new HttpCustomResponse<>(message, path, errorCode);
    }

    public static HttpCustomResponse<ErrorData> errorResponse(List<ErrorData> errors) {
        return new HttpCustomResponse<>(errors);
    }


}
