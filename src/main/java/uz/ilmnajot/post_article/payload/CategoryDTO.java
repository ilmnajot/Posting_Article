package uz.ilmnajot.post_article.payload;

import lombok.*;

@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDTO {

    private Long id;
    private String name;
    private String description;

    public CategoryDTO(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
