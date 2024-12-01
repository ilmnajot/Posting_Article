package uz.ilmnajot.post_article.enums;

public enum ResponseMessage {
    SUCCESS("success"),
    ERROR("error"),
    NOT_FOUND("NOT FOUND");

    private final String message;

    ResponseMessage(String message) {
        this.message = message;
    }
    public String getMessage() {
        return message;
    }
}
