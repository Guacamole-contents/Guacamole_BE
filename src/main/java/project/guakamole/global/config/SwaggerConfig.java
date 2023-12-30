package project.guakamole.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "guakamole admin API 명세서",
                description = "과카몰리 관리자 페이지 API 명세서",
                version = "v1"))
@Configuration
public class SwaggerConfig {

}