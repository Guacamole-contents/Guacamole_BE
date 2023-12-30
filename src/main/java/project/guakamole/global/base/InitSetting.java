package project.guakamole.global.base;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import project.guakamole.domain.user.entity.User;
import project.guakamole.domain.user.repository.UserRepository;
import project.guakamole.global.auth.api.service.AuthService;
import project.guakamole.global.config.AppConfig;

import java.util.Optional;

@Configuration
@Profile({"dev", "prod"})
@Slf4j
@Transactional
public class InitSetting {
    @Bean
    CommandLineRunner initData(
            AuthService authService,
            UserRepository userRepository
    ) {
        return args -> {
            Optional<User> _user = userRepository.findByEmail(AppConfig.getAdminEmail());
            if(_user.isEmpty()){
                authService.adminSignup(AppConfig.getAdminEmail(), AppConfig.getAdminPassword());
            }

            log.info("시작");

        };
    }
}
