package uz.ilmnajot.post_article.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.ilmnajot.post_article.entity.Topic;
import uz.ilmnajot.post_article.exception.AlreadyExistsException;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.mapper.TopicMapper;
import uz.ilmnajot.post_article.payload.TopicRequestDTO;
import uz.ilmnajot.post_article.payload.TopicResponseDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.repository.TopicRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TopicServiceImpl implements TopicService {

    private final TopicRepository topicRepository;
    private final TopicMapper topicMapper;

    public TopicServiceImpl(TopicRepository topicRepository, TopicMapper topicMapper) {
        this.topicRepository = topicRepository;
        this.topicMapper = topicMapper;
    }

    @Override
    public ApiResponse addTopic(TopicRequestDTO topicRequestDTO) {
        Optional<Topic> optionalTopic = topicRepository.findByTitleAndDeleteFalse(topicRequestDTO.getTitle());
        if (optionalTopic.isPresent()) {
            throw new AlreadyExistsException("Topic is already exists");
        }
        Topic topicEntity = topicMapper.toTopicEntity(topicRequestDTO);
        topicEntity = topicRepository.save(topicEntity);
        TopicResponseDTO topicDTO = topicMapper.toTopicDTO(topicEntity);
        return new ApiResponse(true, "success", HttpStatus.CREATED, topicDTO);
    }

    @Override
    public ApiResponse getTopic(Long topicId) {
        Topic topicById = getTopicById(topicId);
        TopicResponseDTO topicDTO = topicMapper.toTopicDTO(topicById);
        return new ApiResponse(true, "success", HttpStatus.OK, topicDTO);
    }


    @Override
    public TopicResponseDTO getTopicByID(Long topicId) {
        Topic topicById = getTopicById(topicId);
        TopicResponseDTO topicDTO = topicMapper.toTopicDTO(topicById);
        return topicDTO;
    }

    @Override
    public List<TopicResponseDTO> getAllTopics() {
        List<Topic> all = topicRepository.findAll();
        return all.stream().map(topicMapper::toTopicDTO).toList();
    }

    @Override
    public ApiResponse deleteTopic(Long topicId) {
        Topic topicById = getTopicById(topicId);
        topicById.setDelete(true);
        topicRepository.save(topicById);
        return new ApiResponse(true, "success", HttpStatus.NO_CONTENT, "Topic has been deleted successfully");
    }

    @Override
    public ApiResponse updateTopic(Long topicId, TopicRequestDTO topicRequestDTO) {
        Topic topicById = getTopicById(topicId);
        Topic updateTopic = topicMapper.toUpdateTopic(topicById, topicRequestDTO);
        Topic saved = topicRepository.save(updateTopic);
        TopicResponseDTO topicDTO = topicMapper.toTopicDTO(saved);
        return new ApiResponse(true, "success", HttpStatus.OK, topicDTO);
    }

    @Override
    public List<TopicResponseDTO> getTopicsByCategoryId(Long categoryId) {
        List<Topic> topicList = topicRepository.findAllByCategory_Id(categoryId);
        return topicList.stream().map(topicMapper::toTopicDTO).toList();
    }

    private Topic getTopicById(Long topicId) {
        return topicRepository.findByIdAndDeleteFalse(topicId).orElseThrow(
                () -> new ResourceNotFoundException("Topic not found"));
    }
}
