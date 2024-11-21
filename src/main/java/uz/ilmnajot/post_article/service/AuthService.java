package uz.ilmnajot.post_article.service;

import uz.ilmnajot.post_article.payload.AuthDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;

public interface AuthService {
    ApiResponse signUp(AuthDTO authDTO);

    ApiResponse login(AuthDTO authDTO);
}
