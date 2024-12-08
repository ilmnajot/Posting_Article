package uz.ilmnajot.post_article.payload;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private String name;
    private String description;


}
