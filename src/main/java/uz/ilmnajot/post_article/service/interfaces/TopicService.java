package uz.ilmnajot.post_article.service.interfaces;

import uz.ilmnajot.post_article.payload.ArticleResponseDTO;
import uz.ilmnajot.post_article.payload.TopicRequestDTO;
import uz.ilmnajot.post_article.payload.TopicResponseDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;

import java.util.List;

public interface TopicService {
    ApiResponse addTopic(TopicRequestDTO topicRequestDTO);

    ApiResponse getTopic(Long topicId);

    TopicResponseDTO getTopicByID(Long topicId);

    List<TopicResponseDTO> getAllTopics();

    ApiResponse deleteTopic(Long topicId);

    ApiResponse updateTopic(Long topicId, TopicRequestDTO topicRequestDTO);

    List<TopicResponseDTO> getTopicsByCategoryId(Long categoryId);
    List<ArticleResponseDTO> getArticlesByTopicId(Long topicId);
}
