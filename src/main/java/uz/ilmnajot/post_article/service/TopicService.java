package uz.ilmnajot.post_article.service;

import uz.ilmnajot.post_article.payload.TopicRequestDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;

public interface TopicService {
    ApiResponse addTopic(TopicRequestDTO topicRequestDTO);

    ApiResponse getTopic(Long topicId);

    ApiResponse getAllTopics();

    ApiResponse deleteTopic(Long topicId);

    ApiResponse updateTopic(Long topicId, TopicRequestDTO topicRequestDTO);
}
