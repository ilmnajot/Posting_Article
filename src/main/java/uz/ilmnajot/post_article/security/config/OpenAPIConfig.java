package uz.ilmnajot.post_article.security.config;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.context.annotation.Configuration;
@OpenAPIDefinition(info = @Info(
        description = "OpenAPI documentation for Edicational Website",
        title = "Educational Website",
        version = "1.0"
)
//        security = @SecurityRequirement(
//                name = "BearerAuth"
//        )
)
//@SecurityScheme(
//        name = "BearerAuth",
//        description = "JWT-based authentication",
//        scheme = "bearer",
//        type = SecuritySchemeType.HTTP,
//        bearerFormat = "JWT",
//        in = SecuritySchemeIn.HEADER
//)
@Configuration
public class OpenAPIConfig {


}
