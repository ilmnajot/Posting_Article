package uz.ilmnajot.post_article.mapper;

import uz.ilmnajot.post_article.entity.Topic;
import uz.ilmnajot.post_article.payload.TopicRequestDTO;

public interface TopicMapper {

    Topic toTopicEntity(TopicRequestDTO topicRequestDTO);
    TopicRequestDTO toTopicDTO(Topic topic);
    Topic toUpdateTopic(Topic topic, TopicRequestDTO topicRequestDTO);
}
