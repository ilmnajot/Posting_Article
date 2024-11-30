package uz.ilmnajot.post_article.security.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.ilmnajot.post_article.security.jwt.JwtFilter;

import java.util.List;

@RequiredArgsConstructor
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    private final AuthenticationProvider authenticationProvider;
    private final AuthenticationEntryPoint authenticationEntryPoint;
    private final AccessDeniedHandler accessDeniedHandler;


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(authRequest -> authRequest
//                        .requestMatchers(getWhitelist())
//                        .permitAll()
//                        .requestMatchers(getCustomWhitelist())
//                        .permitAll()
                        .anyRequest().permitAll()
//                        .authenticated()
                )
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler))
                .formLogin(login -> login
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/home")
                        .failureHandler(new CustomAuthenticationFailureHandler())
                        .failureUrl("/login?error=true") //redirect to the home page
                        .permitAll()
                )
                .logout(logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessHandler(((request, response, authentication) -> {
                                    response.setStatus(HttpServletResponse.SC_OK);
                                    response.sendRedirect("/home?logout");
                                }))// Add logout URL
//                        .logoutSuccessUrl("/login?logout")  // Redirect to login after logout
                                .invalidateHttpSession(true)  // Invalidate the session
                                .clearAuthentication(true)  // Clear authentication
                                .permitAll()
                );
        return http.build();
    }

    private String[] getWhitelist() {
        return new String[]{
                "/v2/api-docs",
                "/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources",
                "/swagger-resources/**",
                "/swagger-ui/**",
                "/webjars/**",
                "/swagger-ui.html",
                "/configuration/ui",
                "/configuration/security"
        };
    }

    private String [] getCustomWhitelist(){
        return new String[]{
                "/api/auth/**,",
                "/login",
                "/sign-up",
                "/email-verify",
                "/verify-email",
                "/home",
                "/verification-success",
                "/category-list",
                "/topics/**",
                "/topic-list/**",
                "/category-list",
                "/categories/**",
                "/articles/**",
                "/topics/topicId/articles",
                "/topics/topicId/articles/articleId",
                "/news-list",
                "/news",
                "/details/id",
                "/aboutus",

        };
    }

}
