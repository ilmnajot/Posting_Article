package uz.ilmnajot.post_article.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
@Component
@EnableJpaAuditing
public class AuditAwareImpl {



    @Bean
    public AuditorAware<Long> auditor() {
        return new AuditingAware();
    }




}
