package project.guakamole.global.auth.api.service;

import com.amazonaws.services.kms.model.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.guakamole.domain.user.entity.User;
import project.guakamole.domain.user.entity.UserRole;
import project.guakamole.domain.user.repository.UserRepository;
import project.guakamole.global.auth.api.dto.response.AuthResponse;
import project.guakamole.global.auth.exception.LoginFailException;
import project.guakamole.global.auth.jwt.JwtToken;
import project.guakamole.global.auth.jwt.JwtTokenProvider;
import project.guakamole.global.config.AppConfig;

@Slf4j
@Service
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;
    private final RefreshTokenService refreshTokenService;

    private final PasswordEncoder passwordEncoder;

    public AuthService(JwtTokenProvider jwtTokenProvider, UserRepository userRepository, RefreshTokenService refreshTokenService, PasswordEncoder passwordEncoder) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userRepository = userRepository;
        this.refreshTokenService = refreshTokenService;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Long signUp(String email, String password){
        checkAdminEmail(email);

        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .userRole(UserRole.ROLE_USER)
                .build();

        return userRepository.save(user).getId();
    }

    @Transactional
    public Long adminSignup(String email, String password){
        User user = User.builder()
                .email(email)
                .password(passwordEncoder.encode(password))
                .userRole(UserRole.ROLE_ADMIN)
                .build();

        return userRepository.save(user).getId();
    }

    public AuthResponse checkLoginValidity(String email, String password){
        User findUser = findUserByEmail(email);

        if (!passwordEncoder.matches(password, findUser.getPassword()))
            throw new LoginFailException("비밀번호가 일치하지 않습니다.");

        JwtToken jwtToken = jwtTokenProvider.createJwtToken(findUser.getId(), findUser.getUserRole().toString());
        return AuthResponse.of(jwtToken);
    }

    public User findUserByEmail(String email){
        return userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException(email+"로 가입된 계정이 존재하지 않습니다.")
        );
    }

    public User findUserByEmailForLogin(String email){
        return userRepository.findByEmail(email).orElseThrow(
                () -> new LoginFailException("존재하지 않는 이메일입니다.")
        );
    }

    private void checkAdminEmail(String email) {
        if(email.equals(AppConfig.getAdminEmail()))
            throw new IllegalArgumentException("이 계정은 사용할 수 없는 계정입니다. (관리자 계정)");
    }
}