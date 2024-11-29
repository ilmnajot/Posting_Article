package uz.ilmnajot.post_article.payload;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Setter
@Getter
public class NewsDTO {

    private Long id;
    private String title;
    private String content;
    private String imageURL;
    private Timestamp createdAt;
    private Long createdBy;

}
