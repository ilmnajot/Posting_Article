package uz.ilmnajot.post_article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ilmnajot.post_article.entity.Course;

import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByIdAndDeleteFalse(Long courseId);
    Optional<Course> findByTitleAndDeleteFalse(String title);
}