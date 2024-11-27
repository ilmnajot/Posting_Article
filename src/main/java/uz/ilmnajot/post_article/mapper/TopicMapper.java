package uz.ilmnajot.post_article.mapper;

import uz.ilmnajot.post_article.entity.Topic;
import uz.ilmnajot.post_article.payload.TopicRequestDTO;
import uz.ilmnajot.post_article.payload.TopicResponseDTO;

public interface TopicMapper {

    Topic toTopicEntity(TopicRequestDTO topicRequestDTO);
    TopicResponseDTO toTopicDTO(Topic topic);
    Topic toUpdateTopic(Topic topic, TopicRequestDTO topicRequestDTO);
}
