package uz.ilmnajot.post_article.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.ilmnajot.post_article.entity.Category;
import uz.ilmnajot.post_article.entity.Topic;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.payload.TopicRequestDTO;
import uz.ilmnajot.post_article.payload.TopicResponseDTO;
import uz.ilmnajot.post_article.repository.CategoryRepository;
@RequiredArgsConstructor
@Component
public class TopicMapperImpl implements TopicMapper {

    private final CategoryRepository categoryRepository;


    @Override
    public Topic toTopicEntity(TopicRequestDTO topicRequestDTO) {
        Category category = categoryRepository.findByIdAndDeleteFalse(topicRequestDTO.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category not found"));
        Topic topic = new Topic();
        topic.setTitle(topicRequestDTO.getTitle());
        topic.setDescription(topicRequestDTO.getDescription());
        topic.setCategory(category);
        return topic;
    }

    @Override
    public TopicResponseDTO toTopicDTO(Topic topic) {
        TopicResponseDTO dto = new TopicResponseDTO();
        dto.setId(topic.getId());
        dto.setTitle(topic.getTitle());
        dto.setDescription(topic.getDescription());
        dto.setCategoryId(topic.getCategory().getId());
        return dto;
    }

    @Override
    public Topic toUpdateTopic(Topic topic, TopicRequestDTO topicRequestDTO) {
        Category category = categoryRepository.findByIdAndDeleteFalse(
                topic
                        .getCategory()
                        .getId()).orElseThrow(() -> new ResourceNotFoundException("Category not found"));
        if (topicRequestDTO.getTitle() != null) {
            topic.setTitle(topicRequestDTO.getTitle());
        }
        if (topicRequestDTO.getDescription() != null) {
            topic.setDescription(topicRequestDTO.getDescription());
        }
        if (topicRequestDTO.getCategoryId() != null) {
            topic.setCategory(category);
        }
        return null;
    }
}
