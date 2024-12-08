package uz.ilmnajot.post_article.payload;

import java.sql.Timestamp;

import jakarta.persistence.Entity;
import lombok.*;

@Setter
@Getter
public class NewsRequestDTO {

    private String title;
    private String content;

}
