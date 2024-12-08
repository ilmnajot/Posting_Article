package uz.ilmnajot.post_article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ilmnajot.post_article.entity.News;

import java.util.List;
import java.util.Optional;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findAllByDeleteIsFalse();

    Optional<News> findByTitle(String title);

    Optional<News> findByTitleAndDeleteFalse(String title);

    Optional<News> findByIdAndDeleteFalse(Long newsId);
}
