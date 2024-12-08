package uz.ilmnajot.post_article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.ilmnajot.post_article.entity.Lesson;

import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Long> {

    Optional<Lesson> findByIdAndDeleteFalse(Long lessonId);

    List<Lesson> findByModule_IdOrderByOrderIndex(Long moduleId);

    @Query("select lsn from lesson as lsn where lsn.name like %:keyword%")
    List<Lesson> searchLessonsByName(@Param("keyword") String keyword);
}
