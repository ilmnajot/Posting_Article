package uz.ilmnajot.post_article.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ilmnajot.post_article.entity.Article;
import uz.ilmnajot.post_article.entity.Topic;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.payload.ArticleDTO;
import uz.ilmnajot.post_article.payload.ArticleResponseDTO;
import uz.ilmnajot.post_article.repository.TopicRepository;
import uz.ilmnajot.post_article.utils.MessageKey;
@RequiredArgsConstructor
@Component
public class ArticleMapperImpl implements ArticleMapper {

    private final TopicRepository topicRepository;


    public Article toArticleEntity(ArticleDTO articleDTO) {
//        User user = userRepository.findByIdAndDeleteFalse(articleDTO.getAuthorId()).orElseThrow(
//                () -> new ResourceNotFoundException(MessageKey.USER_NOT_FOUND));
//        User currentUser  = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Topic topic = topicRepository.findByIdAndDeleteFalse(articleDTO.getTopicId()).orElseThrow(
                () -> new ResourceNotFoundException(MessageKey.CATEGORY_NOT_FOUND));
        Article article = new Article();
        article.setTitle(articleDTO.getTitle());
        article.setContent(articleDTO.getContent());
//        article.setAuthor(user);
        article.setTopic(topic);
        return article;
    }

    public ArticleResponseDTO toArticleDTO(Article article) {
        ArticleResponseDTO articleDTO = new ArticleResponseDTO();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
//        articleDTO.setAuthorId(article.getAuthor().getId());
        articleDTO.setTopicId(article.getTopic().getId());
        return articleDTO;
    }

    public Article toUpdateArticleEntity(Article article, ArticleDTO articleDTO) {

//        User user = this.userRepository.findByIdAndDeleteFalse(articleDTO.getAuthorId()).orElseThrow(
//                () -> new ResourceNotFoundException("User not found"));
        Topic category = this.topicRepository.findByIdAndDeleteFalse(articleDTO.getTopicId()).orElseThrow(
                () -> new ResourceNotFoundException("Category not found"));

        if (articleDTO.getTitle() != null) {
            article.setTitle(articleDTO.getTitle());
        }
        if (articleDTO.getContent() != null) {
            article.setContent(articleDTO.getContent());
        }
//        if (articleDTO.getAuthorId() != null) {
//            article.setAuthor(user);
//        }
        if (articleDTO.getTopicId() != null) {
            article.setTopic(category);
        }
        return article;

    }
}
