package project.guakamole.global.auth.api.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.guakamole.global.auth.api.dto.request.LoginRequest;
import project.guakamole.global.auth.api.dto.request.SignUpRequest;
import project.guakamole.global.auth.api.dto.response.AuthResponse;
import project.guakamole.global.auth.api.service.AuthService;
import project.guakamole.global.auth.jwt.JwtTokenProvider;

@Tag(name = "인증/인가 컨트롤러", description = "JWT 토큰 방식")
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthService authService;

    public AuthController(JwtTokenProvider jwtTokenProvider, AuthService authService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.authService = authService;
    }


    @PostMapping(value = "/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        AuthResponse response = authService.checkLoginValidity(request.getEmail(), request.getPassword());

        log.info("로그인 성공");
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/sign-up")
    public ResponseEntity<Long> signUp(@Valid @RequestBody SignUpRequest request) {
        Long savedUserId = authService.signUp(request.getEmail(), request.getPassword());

        return ResponseEntity.ok(savedUserId);
    }
}
