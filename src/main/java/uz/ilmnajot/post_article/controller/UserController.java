package uz.ilmnajot.post_article.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.ilmnajot.post_article.payload.AuthDTO;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.service.AuthService;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class UserController {

    private final AuthService authService;

    @PostMapping("/sign-up")
    public HttpEntity<ApiResponse> register(@RequestBody AuthDTO authDTO) {
        ApiResponse apiResponse = authService.signUp(authDTO);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/login")
    public HttpEntity<ApiResponse> login(@RequestBody AuthDTO authDTO) {
        ApiResponse login = authService.login(authDTO);
        return ResponseEntity.ok(login);
    }
}
