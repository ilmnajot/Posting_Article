package uz.ilmnajot.post_article.controller;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.ilmnajot.post_article.payload.UserDTO;
import uz.ilmnajot.post_article.payload.AuthRequest;
import uz.ilmnajot.post_article.payload.common.ApiResponse;
import uz.ilmnajot.post_article.service.auth.AuthService;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/sign-up")
    public HttpEntity<ApiResponse> register(@RequestBody UserDTO authRequest) {
        ApiResponse apiResponse = authService.signUp(authRequest);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/verify-email")
    public HttpEntity<ApiResponse> verifyEmail(@RequestParam String email, @RequestParam String emailCode) {
        ApiResponse apiResponse = authService.verifyEmail(email, emailCode);
        return ResponseEntity.ok(apiResponse);
    }

    @PostMapping("/login")
    public HttpEntity<ApiResponse> login(@RequestBody AuthRequest userDTO) {
        ApiResponse login = authService.login(userDTO);
        return ResponseEntity.ok(login);
    }


}
