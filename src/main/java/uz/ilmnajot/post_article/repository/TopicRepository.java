package uz.ilmnajot.post_article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ilmnajot.post_article.entity.Article;
import uz.ilmnajot.post_article.entity.Topic;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Optional<Topic> findByTitleAndDeleteFalse(String title);
    Optional<Topic> findByIdAndDeleteFalse(Long topicId);

        List<Topic> findAllByCategory_Id(Long categoryId);



}