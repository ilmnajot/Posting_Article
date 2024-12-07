package uz.ilmnajot.post_article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.ilmnajot.post_article.entity.Course;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    Optional<Course> findByIdAndDeleteFalse(Long courseId);
    Optional<Course> findByTitleAndDeleteFalse(String title);

    @Query("select c from course as c join c.enrolledUsers as u  where u.id= :userId")
    Set<Course> getCoursesByUserId(@Param("userId")Long userId);
}
