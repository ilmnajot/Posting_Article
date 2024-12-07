package uz.ilmnajot.post_article.security.config;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.access.AccessDeniedHandler;

@Configuration
public class AccessDeniedHandlerConfig {

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return ((request,
                 response,
                 accessDeniedException) -> {
            response.setContentType("application/json");
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
            response.getWriter().write("{\"error\":\"Access denied}");
        });
    }
}
