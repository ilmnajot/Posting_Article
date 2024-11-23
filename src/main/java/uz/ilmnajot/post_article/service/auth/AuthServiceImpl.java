package uz.ilmnajot.post_article.service.auth;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import uz.ilmnajot.post_article.entity.Role;
import uz.ilmnajot.post_article.entity.User;
import uz.ilmnajot.post_article.enums.RoleName;
import uz.ilmnajot.post_article.exception.AlreadyExistsException;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.mapper.UserMapper;
import uz.ilmnajot.post_article.payload.AuthRequest;
import uz.ilmnajot.post_article.payload.UserDTO;
import uz.ilmnajot.post_article.payload.AuthResponse;
import uz.ilmnajot.post_article.payload.UserResponseDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.repository.RoleRepository;
import uz.ilmnajot.post_article.repository.UserRepository;
import uz.ilmnajot.post_article.security.jwt.JwtProvider;
import uz.ilmnajot.post_article.service.interfaces.EmailService;
import uz.ilmnajot.post_article.utils.MessageKey;

import java.sql.Timestamp;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final EmailService emailService;
    private final RoleRepository roleRepository;


    @Override
    public ApiResponse signUp(UserDTO userDTO) {
        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();

            if (user.getRole().getRole().equals(RoleName.IN_REGISTRATION)) {
                userRepository.delete(user);
            } else if (user.getRole().getRole().equals(RoleName.USER)) {
            }
            throw new AlreadyExistsException("User already Registered");
        }
        User userEntity = userMapper.toUserEntity(userDTO);
        User addedUser = userRepository.save(userEntity);
        emailService.sendRegisterEmail(addedUser.getEmail());
        UserResponseDTO authDTO = userMapper.toUserResponseDTO(addedUser);
        return new ApiResponse(true, "success", HttpStatus.CREATED, authDTO);
    }

    @Override
    public ApiResponse verifyEmail(String email, String emailCode) {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("User not found"));
        Role roleUser = roleRepository.findByNameAndDeleteFalse(MessageKey.ROLE_USER).orElseThrow(
                () -> new ResourceNotFoundException("Role not found"));
        if (user.getEmailCode() != null && user.getEmailCode().equals(emailCode)) {

            Timestamp now = new Timestamp(System.currentTimeMillis());
            Timestamp expireTime = new Timestamp(user.getCreatedAt().getTime() + (15 * 60 * 1000));

            if (now.after(expireTime)) {
                throw new BadCredentialsException("Verification code has expired. Please request a new one.");
            }
            user.setEnable(true);
            user.setRole(roleUser);
            user.setEmailCode(null);
            userRepository.save(user);

        }
        return new ApiResponse(true, "success", HttpStatus.OK, "User has been verified successfully");
    }


    @Override
    public ApiResponse login(AuthRequest authRequest) {
        Authentication authenticated = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                authRequest.getEmail(),
                authRequest.getPassword()));
        User user = (User) authenticated.getPrincipal();
        User existUser = userRepository.findByEmail(user.getEmail()).orElseThrow(
                () -> new ResourceNotFoundException(MessageKey.USER_NOT_FOUND));
        String token = jwtProvider.generateAccessToken(existUser);
        AuthResponse authResponse = new AuthResponse();
        authResponse.setToken(token);
        authResponse.setUser(existUser);
        return new ApiResponse(true, "success", HttpStatus.OK, authResponse);
    }


}
