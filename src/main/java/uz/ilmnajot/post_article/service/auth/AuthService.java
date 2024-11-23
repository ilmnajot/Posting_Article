package uz.ilmnajot.post_article.service.auth;

import uz.ilmnajot.post_article.payload.UserDTO;
import uz.ilmnajot.post_article.payload.AuthRequest;
import uz.ilmnajot.post_article.payload.common.ApiResponse;

public interface AuthService {

    ApiResponse signUp(UserDTO authDTO);

    ApiResponse login(AuthRequest authRequest);

    ApiResponse verifyEmail(String email, String emailCode);
}
