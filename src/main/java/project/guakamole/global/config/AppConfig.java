package project.guakamole.global.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Getter
    static String adminEmail;
    @Getter
    static String adminPassword;

    @Value("${app.admin.email}")
    public void adminAccount(String adminEmail) {
        AppConfig.adminEmail = adminEmail;
    }

    @Value("${app.admin.password}")
    public void adminPassword(String adminPassword) {
        AppConfig.adminPassword = adminPassword;
    }

}