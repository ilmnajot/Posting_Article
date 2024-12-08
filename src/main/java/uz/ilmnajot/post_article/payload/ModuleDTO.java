package uz.ilmnajot.post_article.payload;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ModuleDTO {

    private Long id;
    private String name;
    private Long courseId;

}
