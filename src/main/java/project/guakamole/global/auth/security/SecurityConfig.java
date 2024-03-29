package project.guakamole.global.auth.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.SecurityContextHolderFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
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
                        .requestMatchers(antMatcher(OPTIONS)).permitAll()
                        .requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
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
                .addFilter(corsFilter())
                .addFilterAfter(new JwtAuthenticationFilter(jwtTokenProvider),
        CorsFilter.class)
                .cors(AbstractHttpConfigurer::disable);
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
                antMatcher(DELETE, "/api/violations/**"),
                antMatcher(GET, "/file/download/**")
        );
        return requestMatchers.toArray(RequestMatcher[]::new);
    }

    @Bean
    @Order(1)
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOrigins(
                List.of("http://localhost:3000", "http://localhost:3001"));
        config.setAllowedMethods(
                List.of("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        config.setAllowCredentials(true);
        config.setAllowedHeaders(List.of("*"));
        config.setExposedHeaders(List.of("*"));
        config.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
}