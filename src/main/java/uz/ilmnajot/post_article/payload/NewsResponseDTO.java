package uz.ilmnajot.post_article.payload;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Setter
@Getter
public class NewsResponseDTO {

    private Long id;
    private String title;
    private String content;
    private String imageURL;
    private Timestamp createdAt;
    private Long createdBy;

}
