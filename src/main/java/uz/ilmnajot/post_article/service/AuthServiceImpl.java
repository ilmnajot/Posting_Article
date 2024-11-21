package uz.ilmnajot.post_article.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.mapper.UserMapper;
import uz.ilmnajot.post_article.payload.AuthDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.repository.UserRepository;
import uz.ilmnajot.post_article.utils.MessageKey;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public AuthServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public ApiResponse signUp(AuthDTO authDTO) {
        userRepository.findByEmail(authDTO.getEmail()).orElseThrow(()
                -> new ResourceNotFoundException(MessageKey.USER_NOT_FOUND, HttpStatus.BAD_REQUEST));
        User userEntity = userMapper.toUserEntity(authDTO);
        User user = userRepository.save(userEntity);
        AuthDTO userMapperAuthDTO = userMapper.toAuthDTO(user);
        return new ApiResponse(true, "success", HttpStatus.CREATED, userMapperAuthDTO);
    }

    @Override
    public ApiResponse login(AuthDTO authDTO) {

        return null;
    }


}
