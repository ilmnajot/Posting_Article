package uz.ilmnajot.post_article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ilmnajot.post_article.entity.Article;

import java.util.List;
import java.util.Optional;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

    Optional<Article> findByTitleAndDeleteFalse(String title);
    Optional<Article> findByIdAndDeleteFalse(Long articleId);
    List<Article> findAllByDeleteIsFalse();
    List<Article> findAllByDeleteIsTrue();

}
