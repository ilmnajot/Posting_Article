package uz.ilmnajot.post_article.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetailsService;
import uz.ilmnajot.post_article.exception.ResourceNotFoundException;
import uz.ilmnajot.post_article.repository.UserRepository;

@Configuration
public class UserDetailsServiceConfig {


    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
        return username -> userRepository.findByEmail(username).orElseThrow(
                () -> new ResourceNotFoundException("User not found", HttpStatus.NOT_FOUND));
    }
}
