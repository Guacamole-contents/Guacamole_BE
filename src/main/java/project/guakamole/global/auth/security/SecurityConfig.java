package project.guakamole.global.auth.security;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import project.guakamole.global.auth.jwt.JwtTokenProvider;
import project.guakamole.global.auth.jwt.JwtAuthenticationFilter;

import java.util.Collections;
import java.util.List;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;

    public SecurityConfig(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Bean
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            JwtTokenProvider jwtTokenProvider) throws Exception {
        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://54.180.207.76:8080"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L); //1시간
                        return config;
                    }
                }))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(requestPermitAll()).permitAll()
                        .requestMatchers(requestHasRoleUser()).hasRole("USER")
                        .requestMatchers(requestHasRoleAdmin()).hasRole("ADMIN")
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
                        .anyRequest().denyAll())
                .csrf(AbstractHttpConfigurer::disable)
                .headers(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAfter(new JwtAuthenticationFilter(jwtTokenProvider),
                        SecurityContextHolderFilter.class);
        return http.build();
    }


    private RequestMatcher[] requestPermitAll() {
        List<RequestMatcher> requestMatchers = List.of(
                antMatcher(GET, "/auth/**"),
                antMatcher(POST, "/auth/**"),
                antMatcher(GET, "/swagger-ui/**"),
                antMatcher(POST, "/swagger-ui/**"),
                antMatcher(GET, "/v3/**"),
                antMatcher(POST, "/v3/**"));
        return requestMatchers.toArray(RequestMatcher[]::new);
    }

    private RequestMatcher[] requestHasRoleUser() {
        List<RequestMatcher> requestMatchers = List.of(
        );
        return requestMatchers.toArray(RequestMatcher[]::new);
    }

    private RequestMatcher[] requestHasRoleAdmin() {
        List<RequestMatcher> requestMatchers = List.of(
                antMatcher(GET, "/api/applicants/**"),
                antMatcher(POST, "/api/applicants/**"),
                antMatcher(PATCH, "/api/applicants/**"),
                antMatcher(DELETE, "/api/applicants/**"),
                antMatcher(GET, "/api/copyrights/**"),
                antMatcher(POST, "/api/copyrights/**"),
                antMatcher(PATCH, "/api/copyrights/**"),
                antMatcher(DELETE, "/api/copyrights/**"),
                antMatcher(GET, "/api/creators/**"),
                antMatcher(POST, "/api/creators/**"),
                antMatcher(PATCH, "/api/creators/**"),
                antMatcher(DELETE, "/api/creators/**"),
                antMatcher(GET, "/api/violations/**"),
                antMatcher(POST, "/api/violations/**"),
                antMatcher(PATCH, "/api/violations/**"),
                antMatcher(DELETE, "/api/violations/**")
        );
        return requestMatchers.toArray(RequestMatcher[]::new);
    }
}