package project.guakamole.global.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import project.guakamole.global.auth.jwt.JwtTokenProvider;
import project.guakamole.global.auth.jwt.JwtAuthenticationFilter;

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
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(requestPermitAll()).permitAll()
                        .requestMatchers(requestHasRoleUser()).hasRole("USER")
                        .requestMatchers(requestHasRoleAdmin()).hasRole("ADMIN")
                        .anyRequest().denyAll())
                .csrf(AbstractHttpConfigurer::disable)
                .headers(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterAfter(new JwtAuthenticationFilter(jwtTokenProvider),
                        SecurityContextHolderFilter.class);
        return http.build();
    }

    private RequestMatcher[] requestPermitAll() {
        List<RequestMatcher> requestMatchers = List.of(
                antMatcher(GET, "/auth/**"),
                antMatcher(POST, "/auth/**"));
        return requestMatchers.toArray(RequestMatcher[]::new);
    }

    private RequestMatcher[] requestHasRoleUser() {
        List<RequestMatcher> requestMatchers = List.of(
                antMatcher(GET, "/api/applicants/**"),
                antMatcher(POST, "/api/applicants/**"),
                antMatcher(PATCH, "/api/applicants/**")
        );
        return requestMatchers.toArray(RequestMatcher[]::new);
    }

    private RequestMatcher[] requestHasRoleAdmin() {
        List<RequestMatcher> requestMatchers = List.of(
                antMatcher("/api/v1/main-categories/**"),
                antMatcher("/api/v1/sub-categories/**"),
                antMatcher("/api/v1/items/**"),
                antMatcher("/api/v1/events/**"),
                antMatcher(POST, "/api/v1/coupons"));
        return requestMatchers.toArray(RequestMatcher[]::new);
    }
}