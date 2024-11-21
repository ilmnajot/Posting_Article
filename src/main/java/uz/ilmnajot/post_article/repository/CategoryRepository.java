package uz.ilmnajot.post_article.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.ilmnajot.post_article.entity.Category;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Optional<Category> findByNameAndDeleteFalse(String name);
    Optional<Category> findByIdAndDeleteFalse(Long categoryId);
    List<Category> findAllByDeleteIsFalse();
    List<Category> findAllByDeleteIsTrue();
}
